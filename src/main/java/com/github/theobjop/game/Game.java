package com.github.theobjop.game;

import com.github.theobjop.engine.Scene;
import com.github.theobjop.engine.gui.Gui;
import com.github.theobjop.engine.render.Transformation;
import com.github.theobjop.game.gui.InGameGui;
import com.github.theobjop.game.scene.GameScene;

import static org.lwjgl.opengl.GL11.*;

public class Game {

    private Transformation transformation;
    private Scene scene;
    private Gui gui;

    public Game() {
        transformation = new Transformation();
        setScene(new GameScene());
        setGui(new InGameGui());
    }

    public void update() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        scene.update();
        gui.update();

        scene.render(transformation);
        gui.render(transformation);
    }

    public void destroy() {
        scene.cleanup();
    }

    public void setScene(Scene newScene) {
        if (this.scene != null)
            scene.cleanup();
        this.scene = newScene;
    }

    public void setGui(Gui newGui) {
        this.gui = newGui;
    }
}
