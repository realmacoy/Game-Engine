package com.github.theobjop.engine.render.particle;

import com.github.theobjop.engine.game.GameObject;

import java.util.List;

/**
 * Created by Brandon on 3/2/2017.
 */

public interface IParticleEmitter {

    void cleanup();
    Particle getBaseParticle();
    List<GameObject> getParticles();
}
