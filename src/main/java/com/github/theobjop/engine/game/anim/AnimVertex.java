package com.github.theobjop.engine.game.anim;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * Created by Brandon on 3/2/2017.
 */
public class AnimVertex {
    public Vector3f position;
    public Vector2f textCoords;
    public Vector3f normal;

    public float[] weights;
    public int[] jointIndices;

    public AnimVertex() {
        super();
        normal = new Vector3f(0, 0, 0);
    }
}
