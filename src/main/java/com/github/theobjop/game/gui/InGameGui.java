package com.github.theobjop.game.gui;

import com.github.theobjop.engine.game.GameObject;
import com.github.theobjop.engine.game.TextObject;
import com.github.theobjop.engine.gui.Gui;
import com.github.theobjop.engine.gui.IGui;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon on 2/26/2017.
 */
public class InGameGui extends Gui implements IGui {

    private final List<TextObject> guiItems;

    public InGameGui(TextObject... items) {
        super();
        guiItems = new ArrayList<>();

        //FontTexture fontTexture = new FontTexture.Builder("/fonts/Finale.ttf").build();
        //TextObject textObject = new TextObject("DEMO", fontTexture);

        //guiItems.add(textObject);

        for (TextObject to : guiItems) {
            to.getMesh().getMaterial().setColour(new Vector3f(1,1,1));
        }
        addIGui(this); // So we fetch "GuiItems" for rendering.
    }

    @Override
    public void update() {

    }

    @Override
    public List<? extends GameObject> getObjects() {
        return guiItems;
    }
}
