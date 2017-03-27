package com.github.theobjop.game.scene;

import com.github.theobjop.engine.Scene;
import com.github.theobjop.engine.SceneLight;
import com.github.theobjop.engine.game.GameObject;
import com.github.theobjop.engine.input.Keyboard;
import com.github.theobjop.engine.loader.AssimpLoader;
import com.github.theobjop.engine.render.model.Material;
import com.github.theobjop.engine.render.model.Mesh;
import com.github.theobjop.engine.render.Texture;
import com.github.theobjop.engine.render.Transformation;
import com.github.theobjop.game.component.CameraInputComponent;
import org.joml.Vector3f;

import static org.lwjgl.assimp.Assimp.aiProcess_FlipUVs;
import static org.lwjgl.assimp.Assimp.aiProcess_Triangulate;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_L;

/**
 * Created by Brandon on 3/14/2017.
 * This is for the testing of vertices.
 * Currently, our loader is not working, so we will simplify it and work on it.
 */
public class ModelScene extends Scene {

    GameObject cube;
    GameObject quadTest;

    public ModelScene() {
        super();
        this.setRenderShadows(false);
        camera.setInputComponent(new CameraInputComponent());
        super.setSceneLight(SceneLight.DEFAULT);

        camera.setPosition(0,0,0);

        Texture texture = new Texture("/textures/grassblock.png");
        Texture playerTexture = new Texture("/textures/default_player.png");
        Material material = new Material(texture, 1f);
        Material playerMat = new Material(new Vector3f(1,0,0), 1f);

        Mesh playerMesh = new Mesh("/models/default_player.obj", playerMat);
        cube = new GameObject(playerMesh);
        super.addGameObjects(cube);

        Mesh mesh = new Mesh("/models/cube.obj", material);
        Mesh testcube = new Mesh("/models/testcube.obj", material);

        float blockScale = 0.5f;
        Mesh[] quadtest = new AssimpLoader.Builder("/models/quadtest.obj",
                aiProcess_Triangulate | aiProcess_FlipUVs)
                .build("");

        float extension = 2.0f;
        float skyBoxScale = 1f;

        float startx = extension * (-skyBoxScale + blockScale);
        float startz = extension * (skyBoxScale - blockScale);
        float starty = -1.0f;
        float inc = blockScale * 2;

        float posx = startx;
        float posz = startz;

        int NUM_ROWS = (int)(extension * skyBoxScale * 2 / inc);
        int NUM_COLS = (int)(extension * skyBoxScale * 2/ inc);
        GameObject[] gameObjects = new GameObject[NUM_ROWS * NUM_COLS];

        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                GameObject gameItem = new GameObject();
                gameItem.setScale(blockScale);
                gameItem.setMesh(mesh);
                gameItem.setPosition(posx, starty, posz);
                gameObjects[i * NUM_COLS + j] = gameItem;

                posx += inc;
            }

            posx = startx;
            posz -= inc;
        }

        starty++;
        posx+=2;
        posz+=2;
        GameObject testCube = new GameObject();
        testCube.setScale(blockScale);
        testCube.setMesh(testcube);
        testCube.setPosition(posx, starty, posz);
        super.addGameObjects(testCube);

        quadTest = new GameObject();
        quadTest.setScale(blockScale);
        quadTest.setMeshes(quadtest);
        quadTest.setPosition(posx++, starty, posz++);
        super.addGameObjects(quadTest);

        camera.getPosition().z += 3;
        camera.getPosition().x += 2;
        camera.updateViewMatrix();
        camera.lookAt(quadTest);
        camera.updateViewMatrix();

        cube.setPosition(0,0,0);
        super.addGameObjects(gameObjects);
    }

    public void update() {

        if (Keyboard.isKeyDown(GLFW_KEY_L))
            camera.lookAt(quadTest);

        camera.update(delta);
        super.update();
    }

    public void render(Transformation transformation) {
        super.render(transformation);
    }
}
