package com.github.theobjop.engine.gui;

import com.github.theobjop.engine.game.GameObject;

import java.util.List;

/**
 * Created by Brandon on 2/26/2017.
 */
public interface IGui {
    void update();
    List<? extends GameObject> getObjects();
}
