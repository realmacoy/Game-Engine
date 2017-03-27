package com.github.theobjop.engine.game;

import com.github.theobjop.engine.component.InputComponent;
import com.github.theobjop.engine.render.model.Material;
import com.github.theobjop.engine.render.Texture;
import com.github.theobjop.game.component.PlayerInputComponent;
import org.joml.Quaternionf;

/**
 * Created by Brandon on 2/24/2017.
 */
public class Player extends GameObject {

    private InputComponent input;

    public Player(PlayerInputComponent playerInputComponent) {
        super();
        Texture playerTex = new Texture("/textures/default_player.png");
        Material playerMat = new Material(playerTex, 1f);

        //Mesh playerMesh = new Mesh("/models/default_player.obj", playerMat);
        //setMesh(playerMesh);

        this.setScale(0.5f);
        this.setRotation(new Quaternionf(0, 1, 0, 45));
        this.input = playerInputComponent;
    }

    public void update(float delta, boolean inputEnabled) {
        if (inputEnabled)
            input.update(this, delta);
    }
}
