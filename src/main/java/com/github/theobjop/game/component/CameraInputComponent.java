package com.github.theobjop.game.component;

import com.github.theobjop.engine.component.InputComponent;
import com.github.theobjop.engine.input.Keyboard;
import com.github.theobjop.engine.input.Mouse;
import com.github.theobjop.engine.render.Camera;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Brandon on 2/25/2017.
 */
public class CameraInputComponent extends InputComponent {

    private static final float MOUSE_SENSITIVITY = 0.2f;
    private static final float CAMERA_POS_STEP = 0.05f;

    private static final int BUTTON_UP = GLFW_KEY_W;
    private static final int BUTTON_DOWN = GLFW_KEY_S;
    private static final int BUTTON_LEFT = GLFW_KEY_A;
    private static final int BUTTON_RIGHT = GLFW_KEY_D;

    private static final int BUTTON_JUMP = GLFW_KEY_SPACE;
    private static final int BUTTON_SNEAK = GLFW_KEY_LEFT_SHIFT;

    private final Vector3f camInc = new Vector3f(0,0,0);

    private Mouse.Watcher watcher = new Mouse.Watcher();
    private Keyboard.Watcher graveWatcher = new Keyboard.Watcher(GLFW_KEY_GRAVE_ACCENT);

    @Override
    public void update(Object object, float delta) {
        if (!(object instanceof Camera))
            return;
        Camera cam = (Camera)object;
        watcher.update();

        if (Mouse.scroll != 0) {
            cam.addZoom(Mouse.scroll * delta);
            Mouse.scroll = 0;
        }

        if (graveWatcher.wasReleased()) {
            cam.toggleNoclip();
            //cam.getGui().setText(cam.isNoclip() ? "Noclipped" : "DEMO");
        }

        Vector2f rotAxis = Mouse.getDisplVec();
        if (cam.isNoclip()) {
            if (Mouse.isButtonDown(GLFW_MOUSE_BUTTON_RIGHT)) {
                cam.moveRotation(rotAxis.x * MOUSE_SENSITIVITY, rotAxis.y * MOUSE_SENSITIVITY, 0);
            }

            camInc.set(0,0,0);
            if (Keyboard.isKeyDown(BUTTON_UP))
                camInc.z = -1;
            if (Keyboard.isKeyDown(BUTTON_DOWN))
                camInc.z = 1;
            if (Keyboard.isKeyDown(BUTTON_LEFT))
                camInc.x = -1;
            if (Keyboard.isKeyDown(BUTTON_RIGHT))
                camInc.x = 1;
            if (Keyboard.isKeyDown(BUTTON_JUMP))
                camInc.y = 1;
            if (Keyboard.isKeyDown(BUTTON_SNEAK))
                camInc.y = -1;

            cam.movePosition(camInc.x * CAMERA_POS_STEP, camInc.y * CAMERA_POS_STEP, camInc.z * CAMERA_POS_STEP);
        }
    }
}
