package com.github.theobjop.game.gui;

import com.github.theobjop.engine.game.GameObject;
import com.github.theobjop.engine.game.TextObject;
import com.github.theobjop.engine.gui.Gui;
import com.github.theobjop.engine.gui.IGui;
import com.github.theobjop.engine.render.FontTexture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon on 2/26/2017.
 */
public class MenuGui extends Gui implements IGui {

    private List<TextObject> elements;
    private FontTexture fontTexture;

    public MenuGui() {
        elements = new ArrayList<>();
        fontTexture = new FontTexture("/fonts/Finale.ttf", 75);

        TextObject title = new TextObject("Game", fontTexture);
        elements.add(title);
        addIGui(this);
    }

    @Override
    public void update() { }

    @Override
    public List<? extends GameObject> getObjects() {
        return this.elements;
    }
}
