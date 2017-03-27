package com.github.theobjop.engine.loader;

import com.github.theobjop.engine.Util;
import com.github.theobjop.engine.render.model.Material;
import com.github.theobjop.engine.render.model.Mesh;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIMaterial;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.Assimp;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 659bmw03 on 3/21/2017.
 */
public class AssimpLoader {

    AIScene scene;
    List<Material> materials;
    List<Mesh> meshes;

    private AssimpLoader(String filename, int flags, String type) {
        materials = new ArrayList<>();
        meshes = new ArrayList<>();

        ByteBuffer buf = Util.readBytes(filename);
        buf.flip();
        scene = Assimp.aiImportFileFromMemory(buf, flags, type);
        if (scene == null)
            throw new RuntimeException("Error trying to load " + filename + ": " + Assimp.aiGetErrorString());

        System.out.println("Loading Model: " + filename);
        System.out.println("Meshes: " + scene.mNumMeshes());
        System.out.println("Materials: " + scene.mNumMaterials());

        int materialCount = scene.mNumMaterials();
        PointerBuffer materialsBuffer = scene.mMaterials();
        materials = new ArrayList<>();
        for (int i = 0; i < materialCount; ++i) {
            materials.add(new Material(AIMaterial.create(materialsBuffer.get(i))));
        }

        int meshCount = scene.mNumMeshes();
        PointerBuffer meshesBuffer = scene.mMeshes();
        meshes = new ArrayList<>();
        for (int i = 0; i < meshCount; ++i) {
            AIMesh meshConst = AIMesh.create(meshesBuffer.get(i));
            Mesh mesh = new Mesh(meshConst);
            mesh.setMaterial(materials.get(meshConst.mMaterialIndex()));
            meshes.add(mesh);
        }
    }

    public Mesh[] getMeshes() {
        return meshes.toArray(new Mesh[meshes.size()]);
    }

    public Material[] getMaterials() {
        return materials.toArray(new Material[materials.size()]);
    }

    public static class Builder {
        private final String filename;
        private int loadCall = 0;

        public Builder(String filename) {
            this.filename = filename;
        }

        public Builder(String filename, int flags) {
            this.filename = filename;
            this.loadCall = flags;
        }

        public void setFlags(int... flags) {
            loadCall = flags[0];
            for (int i = 1; i < flags.length; i++)
                loadCall |= flags[i];
        }

        public Mesh[] build() {
            return new AssimpLoader(filename, loadCall, "").getMeshes();
        }

        public Mesh[] build(String type) {
            return new AssimpLoader(filename, loadCall, type).getMeshes();
        }
    }
}
