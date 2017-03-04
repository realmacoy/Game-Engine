package com.github.theobjop.engine.game;

import com.github.theobjop.engine.loader.OBJLoader;
import com.github.theobjop.engine.render.Material;
import com.github.theobjop.engine.render.Mesh;
import com.github.theobjop.engine.render.Texture;
import org.joml.Vector3f;

/**
 * Created by Brandon on 2/23/2017.
 */
public class SkyBox extends GameObject {

    private static final float DEFAULT_SCALE = 50.0f;

    public SkyBox(String objModel, String textureFile) {
        super();
        Mesh skyBoxMesh = OBJLoader.loadMesh(objModel);
        Texture skyBoxtexture = new Texture(textureFile);
        skyBoxMesh.setMaterial(new Material(skyBoxtexture, 0.0f));
        setMesh(skyBoxMesh);
        setPosition(0, 0, 0);
        setScale(DEFAULT_SCALE);
    }

    public SkyBox(String objModel, Vector3f colour) {
        super();
        Mesh skyBoxMesh = OBJLoader.loadMesh(objModel);
        Material material = new Material(colour, 0);
        skyBoxMesh.setMaterial(material);
        setMesh(skyBoxMesh);
        setPosition(0, 0, 0);
        setScale(DEFAULT_SCALE);
    }
}
