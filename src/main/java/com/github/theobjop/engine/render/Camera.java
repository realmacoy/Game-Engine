package com.github.theobjop.engine.render;

import com.github.theobjop.engine.component.InputComponent;
import com.github.theobjop.engine.game.GameObject;
import com.github.theobjop.engine.game.TextObject;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import static java.lang.Math.toRadians;

/**
 * Created by 659bmw03 on 2/14/2017.
 */
public class Camera {

    private static final float MIN_ZOOM = 0.75f;
    private static final float MAX_ZOOM = 2.0f;

    private static final float magicX = (float)toRadians(126.87);
    private static final float magicY = (float)toRadians(129.09);
    private static final float magicZ = (float)toRadians(104.04);

    private Vector3f position;

    private Vector3f rotation;
    private InputComponent input;
    private Matrix4f viewMatrix;

    private float zoom = 1f;
    private boolean noclip = false;
    private TextObject gui;

    public Camera(InputComponent input) {
        this.input = input;
        this.position = new Vector3f(zoom * magicX - 0.5f, zoom * magicY + 0.5f, zoom * magicZ - 0.5f);
        this.rotation = new Vector3f(0, 45, 45);
        viewMatrix = new Matrix4f();
    }

    public Matrix4f updateViewMatrix() {
        return Transformation.updateGenericViewMatrix(position, rotation, viewMatrix);
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void update(float delta) {
        input.update(this, delta);
    }

    public void lookAt(GameObject go) {
        Matrix4f dest = viewMatrix.lookAt(this.getPosition(), go.getPosition(), new Vector3f(0,1,0), new Matrix4f());
        this.setRotation(dest.getEulerAnglesZYX(new Vector3f()).normalize());
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if ( offsetZ != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public void setPosition(Vector3f pos) {
        position.x = pos.x;
        position.y = pos.y;
        position.z = pos.z;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    private void setRotation(Vector3f vec) {
        this.setRotation(vec.x, vec.y, vec.z);
    }

    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public Vector3f getRotation() {
        return this.rotation;
    }

    public float getZoom() {
        return this.zoom;
    }

    public void setZoom(float zoom) {
        this.zoom += zoom;
    }

    @Override
    public String toString() {
        return "Position: " + position.x + ", " + position.y + ", " + position.z
                + "\nRotation: " + rotation.x + ", " + rotation.y + ", " + rotation.z;
    }

    private void updateZoom() {
        this.position.x = this.zoom * magicX - 0.5f;
        this.position.y = this.zoom * magicY + 0.5f;
        this.position.z = this.zoom * magicZ - 0.5f;
    }

    public void addZoom(float v) {
        float newZoom = this.zoom + v;
        if (newZoom > MAX_ZOOM)
            newZoom = MAX_ZOOM;
        if (newZoom < MIN_ZOOM)
            newZoom = MIN_ZOOM;

        this.zoom = newZoom;
        updateZoom();
    }

    public void toggleNoclip() {
        this.noclip = !this.noclip;
        if (!noclip)
            updateZoom();
    }

    public boolean isNoclip() {
        return this.noclip;
    }

    public TextObject getGui() {
        return gui;
    }

    public void setInputComponent(InputComponent inputComponent) {
        this.input = inputComponent;
    }
}
