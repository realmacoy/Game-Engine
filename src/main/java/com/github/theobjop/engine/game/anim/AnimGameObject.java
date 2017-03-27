package com.github.theobjop.engine.game.anim;

import com.github.theobjop.engine.game.GameObject;
import com.github.theobjop.engine.render.model.Mesh;
import org.joml.Matrix4f;

import java.util.List;

/**
 * Created by Brandon on 3/2/2017.
 */
public class AnimGameObject extends GameObject {
    private int currentFrame;

    private List<AnimatedFrame> frames;

    private List<Matrix4f> invJointMatrices;

    public AnimGameObject(Mesh[] meshes, List<AnimatedFrame> frames, List<Matrix4f> invJointMatrices) {
        super(meshes);
        this.frames = frames;
        this.invJointMatrices = invJointMatrices;
        currentFrame = 0;
    }

    public List<AnimatedFrame> getFrames() {
        return frames;
    }

    public void setFrames(List<AnimatedFrame> frames) {
        this.frames = frames;
    }

    public AnimatedFrame getCurrentFrame() {
        return this.frames.get(currentFrame);
    }

    public AnimatedFrame getNextFrame() {
        int nextFrame = currentFrame + 1;
        if ( nextFrame > frames.size() - 1) {
            nextFrame = 0;
        }
        return this.frames.get(nextFrame);
    }

    public void nextFrame() {
        int nextFrame = currentFrame + 1;
        if ( nextFrame > frames.size() - 1) {
            currentFrame = 0;
        } else {
            currentFrame = nextFrame;
        }
    }

    public List<Matrix4f> getInvJointMatrices() {
        return invJointMatrices;
    }
}
