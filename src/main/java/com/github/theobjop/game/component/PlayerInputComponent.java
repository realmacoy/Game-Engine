package com.github.theobjop.game.component;

import com.github.theobjop.engine.component.InputComponent;
import com.github.theobjop.engine.game.Player;
import com.github.theobjop.engine.input.Keyboard;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by Brandon on 2/24/2017.
 */
public class PlayerInputComponent extends InputComponent {

    private static final int BUTTON_UP = GLFW_KEY_W;
    private static final int BUTTON_DOWN = GLFW_KEY_S;
    private static final int BUTTON_LEFT = GLFW_KEY_A;
    private static final int BUTTON_RIGHT = GLFW_KEY_D;

    @Override
    public void update(Object object, float delta) {
        if (!(object instanceof Player))
            return;

        Player gameObject = (Player)object;

        if (Keyboard.isKeyDown(BUTTON_UP)) {
            gameObject.getPosition().z -= 1 * delta;
            gameObject.getPosition().x -= 1 * delta;
        }

        if (Keyboard.isKeyDown(BUTTON_DOWN)) {
            gameObject.getPosition().z += 1 * delta;
            gameObject.getPosition().x += 1 * delta;
        }

        if (Keyboard.isKeyDown(BUTTON_LEFT)) {
            gameObject.getPosition().x -= 1 * delta;
            gameObject.getPosition().z += 1 * delta;
        }

        if (Keyboard.isKeyDown(BUTTON_RIGHT)) {
            gameObject.getPosition().z -= 1 * delta;
            gameObject.getPosition().x += 1 * delta;
        }
    }
}
