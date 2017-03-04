package com.github.theobjop.engine.input;

/**
 * Created by 659bmw03 on 2/8/2017.
 */
public class InputHandler {

    public static void Init() {
        Mouse.Button = new Mouse.ButtonHandler();
        Mouse.Cursor = new Mouse.CursorHandler();
        Mouse.Scroll = new Mouse.ScrollHandler();
        Keyboard.Button = new Keyboard.Handler();
    }

    public static Keyboard.Handler getKeyboardHandler() {
        return Keyboard.Button;
    }

    public static Mouse.CursorHandler getCursorHandler() {
        return Mouse.Cursor;
    }

    public static Mouse.ButtonHandler getMouseHandler() {
        return Mouse.Button;
    }

    public static Mouse.ScrollHandler getScrollHandler() {
        return Mouse.Scroll;
    }
}
