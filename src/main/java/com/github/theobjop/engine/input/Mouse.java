package com.github.theobjop.engine.input;

import com.github.theobjop.game.Main;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by 659bmw03 on 2/8/2017.
 */
public class Mouse {

    // Package-private
    static CursorHandler Cursor;
    static ButtonHandler Button;
    static ScrollHandler Scroll;

    public static Vector2f pos = new Vector2f();
    public static boolean inWindow = true;

    public static float scroll;

    private static Vector2f displVec = new Vector2f();

    static boolean paused;

    public static class Watcher {
        public Vector2f previousPos;

        public Watcher() {
            previousPos = new Vector2f();
        }

        public void update() {
            displVec.x = 0;
            displVec.y = 0;
            if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
                double deltax = pos.x - previousPos.x;
                double deltay = pos.y - previousPos.y;
                boolean rotateX = deltax != 0;
                boolean rotateY = deltay != 0;
                if (rotateX) {
                    displVec.y = (float) deltax;
                }
                if (rotateY) {
                    displVec.x = (float) deltay;
                }
            }
            previousPos.x = pos.x;
            previousPos.y = pos.y;
        }
    }

    // Button array
    private static int pressedButtons[] = new int[3];

    public static class CursorHandler extends GLFWCursorPosCallback {
        @Override
        public void invoke(long window, double xpos, double ypos) {
            Mouse.pos.x = (float)xpos;
            Mouse.pos.y = (float) Main.getWindow().getHeight() - (float)ypos;
        }
    }

    public static class ButtonHandler extends GLFWMouseButtonCallback {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            Mouse.pressedButtons[button] = action;
        }
    }

    public static class ScrollHandler extends GLFWScrollCallback {
        @Override
        public void invoke(long window, double xoffset, double yoffset) {
            Mouse.scroll = xoffset != 0 ? (float)xoffset : (float)yoffset;
        }
    }

    /**
     * Gets if button is pressed
     * @param button - The GL button to check
     * @return True if button is pressed
     */
    public static boolean isButtonDown(int button) {
        return pressedButtons[button] == GLFW_PRESS || pressedButtons[button] == GLFW_REPEAT;
    }

    public static void toggle() {
        setPaused(!Mouse.paused);
    }

    public static void setPaused(boolean paused) {
        Mouse.paused = paused;
        glfwSetInputMode(Main.getWindow().getWindowHandle(),
                GLFW_CURSOR, paused ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_HIDDEN);
    }

    public static Vector2f getDisplVec() {
        return displVec;
    }
}
