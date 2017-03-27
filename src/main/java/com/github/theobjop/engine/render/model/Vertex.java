package com.github.theobjop.engine.render.model;

import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon on 3/17/2017.
 */
public class Vertex {

    Vector3f position = new Vector3f();
    Vector3f normal = new Vector3f();
    Vector2f texCoords = new Vector2f();

    float[] getPositions() {
        return new float[] { position.x, position.y, position.z };
    }

    float[] getNormals() {
        return new float[] { normal.x, normal.y, normal.z };
    }

    float[] getTextures() {
        return new float[] { texCoords.x, texCoords.y };
    }

    void posToArray(float[] array, int pos) {
        array[pos] = position.x;
        array[pos + 1] = position.y;
        array[pos + 2] = position.z;
    }

    void normToArray(float[] array, int pos) {
        array[pos] = normal.x;
        array[pos + 1] = normal.y;
        array[pos + 2] = normal.z;
    }

    void texToArray(float[] array, int pos) {
        array[pos] = texCoords.x;
        array[pos + 1] = texCoords.y;
    }

    public static class List extends ArrayList<Vertex> {
        float[] getPositions() {
            float[] positions = new float[this.size() * 3];
            for (int i = 0; i < this.size(); i++)
                this.get(i).posToArray(positions, i*3);
            return positions;
        }

        float[] getNormals() {
            float[] normals = new float[this.size() * 3];
            for (int i = 0; i < this.size(); i++)
                this.get(i).normToArray(normals, i*3);
            return normals;
        }

        float[] getTextures() {
            float[] texCoords = new float[this.size() * 2];
            for (int i = 0; i < this.size(); i++)
                this.get(i).texToArray(texCoords, i*2);
            return texCoords;
        }
    }
}
