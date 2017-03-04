package com.github.theobjop.game;

import com.github.theobjop.engine.Window;
import com.github.theobjop.engine.input.InputHandler;

/**
 * Created by Brandon on 2/27/2017.
 */
public class Main {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;

    private static Window window;
    private static boolean running = true;

    public static void main(String[] args) {
        InputHandler.Init(); // Initialize the Input Handlers
        window = new Window("Game", WIDTH, HEIGHT, false, new Window.WindowOptions()); // Create a window (which uses handlers)

        // Create game and enter loop, exit when finished.
        Game game = new Game();
        while (!window.windowShouldClose() && running) {
            game.update();
            window.update();
        }
        game.destroy();
        window.destroy();
    }

    public static Window getWindow() {
        return Main.window;
    }

    public static void exit(String... error) {
        if (error.length > 0)
            System.out.println(String.format("%s", error));

        running = false;
    }
}
