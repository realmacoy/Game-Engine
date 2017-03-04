package com.github.theobjop.engine.input;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by 659bmw03 on 2/8/2017.
 */
public class Keyboard {

    static int keys[] = new int[65536];
    static Handler Button;

    public static class Handler extends GLFWKeyCallback {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            Keyboard.keys[key] = action;
        }
    }

    /**
     * Watches a key for pressed and release
     * Both of these results return a value based on the last time they were called.
     */
    public static class Watcher {
        private int key;
        private int oldState;

        private boolean wasPressed;
        private boolean wasReleased;

        public Watcher(int key) {
            this.key = key;
        }

        public void update() {
            int newState = Keyboard.getState(key);

            wasPressed = (newState == GLFW_PRESS || newState == GLFW_REPEAT) && oldState == GLFW_RELEASE;
            wasReleased = newState == GLFW_RELEASE && (oldState == GLFW_PRESS || oldState == GLFW_REPEAT);

            oldState = Keyboard.getState(key);
        }

        public boolean wasReleased() {
            this.update();
            return wasReleased;
        }

        public boolean wasPressed() {
            this.update();
            return wasPressed;
        }
    }

    /**
     * Gets if key is pressed
     * @param key - The key to check
     * @return True if the key is pressed
     */
    public static boolean isKeyDown(int key) {
        return keys[key] == GLFW_PRESS || keys[key] == GLFW_REPEAT;
    }

    public static boolean wasKeyReleased(int key) {
        return keys[key] == GLFW_RELEASE;
    }

    public static int getState(int key) {
        return keys[key];
    }
}
