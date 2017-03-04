package com.github.theobjop.game.scene;

import com.github.theobjop.engine.Scene;
import com.github.theobjop.engine.SceneLight;
import com.github.theobjop.engine.game.GameObject;
import com.github.theobjop.engine.game.Player;
import com.github.theobjop.engine.game.SkyBox;
import com.github.theobjop.engine.input.Keyboard;
import com.github.theobjop.engine.input.Mouse;
import com.github.theobjop.engine.loader.OBJLoader;
import com.github.theobjop.engine.loader.md5.MD5AnimModel;
import com.github.theobjop.engine.loader.md5.MD5Loader;
import com.github.theobjop.engine.loader.md5.MD5Model;
import com.github.theobjop.engine.render.*;
import com.github.theobjop.engine.render.light.DirectionalLight;
import com.github.theobjop.game.component.CameraInputComponent;
import com.github.theobjop.game.component.PlayerInputComponent;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

/**
 * Created by Brandon on 2/28/2017.
 */
public class GameScene extends Scene {

    private Player player;
    private GameObject monster;

    private static final float SKYBOX_SCALE = 100.0f;

    public GameScene() {
        super();
        camera = new Camera(new CameraInputComponent());
        skyBox = new SkyBox("/models/skybox.obj", new Vector3f(0.65f, 0.65f, 0.65f));
        skyBox.setScale(SKYBOX_SCALE);

        // Create Skybox texture for the skybox
        Mesh skyBoxMesh = OBJLoader.loadMesh("/models/skybox.obj");
        Texture skyBoxtexture = new Texture("/textures/skybox.png");
        skyBoxMesh.setMaterial(new Material(skyBoxtexture, 0.0f));
        skyBox.setMesh(skyBoxMesh);
        this.setSkyBox(skyBox);

        // Add it to the scene under the
        super.addGameObjects(skyBox);
        skyBoxMesh.deleteBuffers();

        player = new Player(new PlayerInputComponent());
        super.addGameObjects(player);

        // Create new Monster with MD5 Mesh
        monster = MD5Loader.process(MD5Model.parse("/models/monster/monster.md5mesh"),
                MD5AnimModel.parse("/models/monster/monster.md5anim"), new Vector3f(1,1,1));

        monster.getRotation().rotateX(-1.5554338f);
        monster.setScale(0.01f);
        super.addGameObjects(monster);

        // Create wood mesh
        Texture woodTex = new Texture("/textures/wood.png");
        Material wood = new Material(woodTex);
        Mesh woodMesh = new Mesh("/models/wood_wall.obj", wood);

        // Create new wall
        GameObject woodWall = new GameObject();
        woodWall.setPosition(0, -0.5f, 0);
        woodWall.setMesh(woodMesh);

        // Add it to the scene and bind the woodWall with the mesh
        super.addGameObjects(woodWall);

        Texture texture = new Texture("/textures/grassblock.png");
        Material material = new Material(texture, 1f);

        Mesh mesh = new Mesh("/models/cube.obj", material);

        float blockScale = 0.5f;
        float extension = 2.0f;
        float skyBoxScale = skyBox.getScale();

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

        super.addGameObjects(gameObjects);

        setupLights();
    }

    private Keyboard.Watcher escapeWatcher = new Keyboard.Watcher(GLFW_KEY_ESCAPE);

    @Override
    public void update() {
        // Use the camera before editing values...
        if (escapeWatcher.wasReleased())
            Mouse.toggle();

        player.update(delta, !camera.isNoclip());
        camera.update(delta);
        super.update();
    }

    @Override
    public void render(Transformation transformation) {
        super.render(transformation);
    }

    private void setupLights() {
        SceneLight sceneLight = new SceneLight();
        setSceneLight(sceneLight);

        // Ambient Light
        sceneLight.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));
        sceneLight.setSkyBoxLight(new Vector3f(1.0f, 1.0f, 1.0f));

        // Directional Light
        float lightIntensity = 1.0f;
        Vector3f lightDirection = new Vector3f(0, 1, 1);
        DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), lightDirection, lightIntensity);
        directionalLight.setShadowPosMult(10);
        directionalLight.setOrthoCords(-10.0f, 10.0f, -10.0f, 10.0f, -1.0f, 20.0f);
        sceneLight.setDirectionalLight(directionalLight);
    }
}
