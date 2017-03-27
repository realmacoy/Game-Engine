package com.github.theobjop.engine;

import com.github.theobjop.engine.component.InputComponent;
import com.github.theobjop.engine.game.GameObject;
import com.github.theobjop.engine.game.SkyBox;
import com.github.theobjop.engine.render.*;
import com.github.theobjop.engine.render.model.InstancedMesh;
import com.github.theobjop.engine.render.model.Mesh;
import com.github.theobjop.engine.render.particle.IParticleEmitter;
import com.github.theobjop.engine.render.weather.Fog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

/**
 * Created by Brandon on 3/2/2017.
 */
public class Scene {
    private final Map<Mesh, List<GameObject>> meshMap;
    private final Map<InstancedMesh, List<GameObject>> instancedMeshMap;

    protected Renderer renderer;
    protected Camera camera;
    protected SkyBox skyBox;
    protected SceneLight sceneLight;
    protected Fog fog;

    protected float delta;
    protected float lastTime;

    private boolean renderShadows;
    private IParticleEmitter[] particleEmitters;

    public Scene() {
        renderer = new Renderer();
        camera = new Camera(new InputComponent());
        meshMap = new HashMap();
        instancedMeshMap = new HashMap();
        fog = Fog.NOFOG;
        renderShadows = true;
    }

    public void render(Transformation transformation) {
        renderer.render(transformation, camera, this);
    }

    public void update() {
        float newTime = (float)glfwGetTime();
        delta = newTime - lastTime;

        for (Map.Entry<Mesh, List<GameObject>> meshListEntry : meshMap.entrySet()) {
            for (GameObject go : meshListEntry.getValue())
                go.update();
        }

        camera.updateViewMatrix();
        lastTime = newTime;
    }

    public Map<Mesh, List<GameObject>> getGameMeshes() {
        return meshMap;
    }

    public Map<InstancedMesh, List<GameObject>> getGameInstancedMeshes() {
        return instancedMeshMap;
    }

    public boolean isRenderShadows() {
        return renderShadows;
    }

    public void addGameObjects(GameObject... gameItems) {
        // Create a map of meshes to speed up rendering
        int numGameItems = gameItems != null ? gameItems.length : 0;
        for (int i = 0; i < numGameItems; i++) {
            GameObject gameItem = gameItems[i];
            Mesh[] meshes = gameItem.getMeshes();
            for (Mesh mesh : meshes) {
                boolean instancedMesh = mesh instanceof InstancedMesh;
                List<GameObject> list = instancedMesh ? instancedMeshMap.get(mesh) : meshMap.get(mesh);
                if (list == null) {
                    list = new ArrayList<>();
                    if (instancedMesh) {
                        instancedMeshMap.put((InstancedMesh)mesh, list);
                    } else {
                        meshMap.put(mesh, list);
                    }
                }
                list.add(gameItem);
            }
        }
    }

    public void cleanup() {
        for (Mesh mesh : meshMap.keySet()) {
            mesh.cleanUp();
        }
        for (Mesh mesh : instancedMeshMap.keySet()) {
            mesh.cleanUp();
        }
        if (particleEmitters != null) {
            for (IParticleEmitter particleEmitter : particleEmitters) {
                particleEmitter.cleanup();
            }
        }
    }

    public SkyBox getSkyBox() {
        return skyBox;
    }

    public void setRenderShadows(boolean renderShadows) {
        this.renderShadows = renderShadows;
    }

    public void setSkyBox(SkyBox skyBox) {
        this.skyBox = skyBox;
    }

    public SceneLight getSceneLight() {
        return sceneLight;
    }

    public void setSceneLight(SceneLight sceneLight) {
        this.sceneLight = sceneLight;
    }

    /**
     * @return the fog
     */
    public Fog getFog() {
        return fog;
    }

    /**
     * @param fog the fog to set
     */
    public void setFog(Fog fog) {
        this.fog = fog;
    }

    public IParticleEmitter[] getParticleEmitters() {
        return particleEmitters;
    }

    public void setParticleEmitters(IParticleEmitter[] particleEmitters) {
        this.particleEmitters = particleEmitters;
    }
}
