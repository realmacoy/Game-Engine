package com.github.theobjop.engine.game;

import com.github.theobjop.engine.render.model.Mesh;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Created by Brandon on 2/23/2017.
 */
public class GameObject {

    private boolean selected;

    private Mesh[] meshes;

    private final Vector3f position;
    private final Quaternionf rotation;

    private float scale;
    private int textPos;
    private boolean insideFrustum;

    public GameObject() {
        selected = false;
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Quaternionf();
        textPos = 0;
        insideFrustum = true;
    }

    public GameObject(Mesh mesh) {
        this();
        this.meshes = new Mesh[]{mesh};
    }

    public GameObject(Mesh[] meshes) {
        this();
        this.meshes = meshes;
    }

    /**
     * Virtual Method
     */
    public void update() { }

    public Vector3f getPosition() {
        return position;
    }

    public int getTextPos() {
        return textPos;
    }

    public boolean isSelected() {
        return selected;
    }

    public final void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public float getScale() {
        return scale;
    }

    public final void setScale(float scale) {
        this.scale = scale;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public final void setRotation(Quaternionf q) {
        this.rotation.set(q);
    }

    public Mesh getMesh() {
        return meshes[0];
    }

    public Mesh[] getMeshes() {
        return meshes;
    }

    public void setMeshes(Mesh[] meshes) {
        this.meshes = meshes;
    }

    public void setMesh(Mesh mesh) {
        this.meshes = new Mesh[]{mesh};
    }

    public void cleanup() {
        int numMeshes = this.meshes != null ? this.meshes.length : 0;
        for (int i = 0; i < numMeshes; i++) {
            this.meshes[i].cleanUp();
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setTextPos(int textPos) {
        this.textPos = textPos;
    }

    public boolean isInsideFrustum() {
        return insideFrustum;
    }

    public void setInsideFrustum(boolean insideFrustum) {
        this.insideFrustum = insideFrustum;
    }
}