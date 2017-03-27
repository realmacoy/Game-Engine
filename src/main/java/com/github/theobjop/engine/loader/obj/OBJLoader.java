package com.github.theobjop.engine.loader.obj;

import com.github.theobjop.engine.Util;
import com.github.theobjop.engine.loader.obj.builder.*;
import com.github.theobjop.engine.loader.obj.parser.Parse;
import com.github.theobjop.engine.render.model.Mesh;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon on 2/23/2017.
 * Loads an OBJ file using com.github.theobjop.engine.loader.obj.builder.Build
 * Gives a Mesh result
 */
public class OBJLoader {

    // I keep it un-commented because it won't minimize in my IDE if it is commented.
    public static Mesh oldLoadMesh(String fileName) {
        List<String> lines = null;
        try {
            lines = Util.readAllLines(fileName);
        } catch (Exception e) {
            throw new RuntimeException("Error trying to load mesh: " + e.getLocalizedMessage());
        }

        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Face> faces = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split("\\s+");
            switch (tokens[0]) {
                case "v":
                    // Geometric vertex
                    Vector3f vec3f = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));
                    vertices.add(vec3f);
                    break;
                case "vt":
                    // Texture coordinate
                    Vector2f vec2f = new Vector2f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]));
                    textures.add(vec2f);
                    break;
                case "vn":
                    // Vertex normal
                    Vector3f vec3fNorm = new Vector3f(
                            Float.parseFloat(tokens[1]),
                            Float.parseFloat(tokens[2]),
                            Float.parseFloat(tokens[3]));
                    normals.add(vec3fNorm);
                    break;
                case "f":
                    //Face face = new Face(tokens[1], tokens[2], tokens[3]);
                    //faces.add(face);
                    break;
                default:
                    // Ignore other lines
                    break;
            }
        }
        return reorderLists(vertices, textures, normals, faces);
    }

    public static Mesh loadMesh(String fileName) {
        Build build = (Build)Parse.SafeParse(Build.class, fileName);

        List<Vector3f> pos = new ArrayList<>();
        List<Vector3f> norm = new ArrayList<>();
        List<Vector2f> tex = new ArrayList<>();
        build.verticesG.forEach(vertexGeometric ->
                pos.add(new Vector3f(vertexGeometric.x, vertexGeometric.y, vertexGeometric.z)));
        build.verticesT.forEach(vertexTexture ->
                tex.add(new Vector2f(vertexTexture.u, vertexTexture.v)));
        build.verticesN.forEach(vertexNormal ->
                norm.add(new Vector3f(vertexNormal.x, vertexNormal.y, vertexNormal.z)));
        return reorderLists(pos, tex, norm, build.faces);
    }

    private static Mesh reorderLists(List<Vector3f> posList, List<Vector2f> textCoordList,
                                     List<Vector3f> normList, List<Face> facesList) {

        List<Integer> indices = new ArrayList<>();
        // Create position array in the order it has been declared
        float[] posArr = new float[posList.size() * 3];
        int i = 0;
        for (Vector3f pos : posList) {
            posArr[i * 3] = pos.x;
            posArr[i * 3 + 1] = pos.y;
            posArr[i * 3 + 2] = pos.z;
            i++;
        }
        float[] textCoordArr = new float[posList.size() * 2];
        float[] normArr = new float[posList.size() * 3];

        for (Face face : facesList) {
            for (FaceVertex vert : face.indices) {
                processFaceVertex(vert, textCoordList, normList,
                        indices, textCoordArr, normArr);
            }
        }
        int[] indicesArr = indices.stream().mapToInt((Integer v) -> v).toArray();
        return new Mesh(posArr, textCoordArr, normArr, indicesArr);
    }

    private static void processFaceVertex(FaceVertex indices, List<Vector2f> textCoordList,
                                          List<Vector3f> normList, List<Integer> indicesList,
                                          float[] texCoordArr, float[] normArr) {
        // Set index for vertex coordinates
        int posIndex = indices.v;
        indicesList.add(posIndex);

        // Reorder texture coordinates
        if (indices.t >= 0) {
            Vector2f textCoord = textCoordList.get(indices.t);
            texCoordArr[posIndex * 2] = textCoord.x;
            texCoordArr[posIndex * 2 + 1] = 1 - textCoord.y;
        }
        if (indices.n >= 0) {
            // Reorder vectornormals
            Vector3f vecNorm = normList.get(indices.n);
            normArr[posIndex * 3] = vecNorm.x;
            normArr[posIndex * 3 + 1] = vecNorm.y;
            normArr[posIndex * 3 + 2] = vecNorm.z;
        }
    }
}
