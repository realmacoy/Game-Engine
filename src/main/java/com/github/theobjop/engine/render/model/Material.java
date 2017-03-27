package com.github.theobjop.engine.render.model;

import com.github.theobjop.engine.loader.obj.builder.ReflectivityTransmiss;
import com.github.theobjop.engine.render.Texture;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.assimp.AIColor4D;
import org.lwjgl.assimp.AIMaterial;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.assimp.Assimp.*;

/**
 * Created by Brandon on 2/23/2017.
 */
public class Material {

    private static final ReflectivityTransmiss DEFAULT_COLOUR = new ReflectivityTransmiss(1.0f, 1.0f, 1.0f);

    public ReflectivityTransmiss ambient;
    public ReflectivityTransmiss diffuse;
    public ReflectivityTransmiss specular;
    public ReflectivityTransmiss transmissionFilter;

    private float reflectance;
    private float Ns = 1; // Specular Power (default 1, unless changed)

    public int illumModel = 0;
    public boolean dHalo = false;
    public float dFactor;
    public float sharpnessValue;
    public float niOpticalDensity;
    public int reflType;

    public String mapKaFilename;
    public String mapKdFilename;
    public String mapKsFilename;
    public String mapNsFilename;
    public String mapDFilename;
    public String decalFilename;
    public String dispFilename;
    public String bumpFilename;
    public String reflFilename;

    private Texture texture;
    private Texture normalMap;

    public Material(AIMaterial mMaterial) {
        this();

        // @TODO: Look at MD5's handling of diffuse, ambient, and specular textures.
        // This might lead to knowing HOW THE FUCK I should load the materials' textures.
        // http://www.mbsoftworks.sk/index.php?page=tutorials&series=1&tutorial=23
        // Above website under "Materials"

        AIColor4D mAmbientColor = AIColor4D.create();
        if (aiGetMaterialColor(mMaterial, AI_MATKEY_COLOR_AMBIENT, aiTextureType_NONE, 0, mAmbientColor) != 0)
            throw new RuntimeException(aiGetErrorString());

        System.out.println(mAmbientColor + ": " + mAmbientColor.r() + ", " + mAmbientColor.g() + ", " + mAmbientColor.b() + " (" + mAmbientColor.a() + ")");

        AIColor4D mDiffuseColor = AIColor4D.create();
        if (aiGetMaterialColor(mMaterial, AI_MATKEY_COLOR_DIFFUSE, aiTextureType_NONE, 0, mDiffuseColor) != 0)
            throw new RuntimeException(aiGetErrorString());

        AIColor4D mSpecularColor = AIColor4D.create();
        if (aiGetMaterialColor(mMaterial, AI_MATKEY_COLOR_SPECULAR, aiTextureType_NONE, 0, mSpecularColor) != 0)
            throw new RuntimeException(aiGetErrorString());

        // Not working.
        Ns = 1.0f;
        FloatBuffer floatArray = BufferUtils.createFloatBuffer(4);
        IntBuffer maxBuf = BufferUtils.createIntBuffer(1);
        if (aiGetMaterialFloatArray(mMaterial, AI_MATKEY_SHININESS, aiTextureType_NONE, 0, floatArray, maxBuf) != 0)
            throw new RuntimeException(aiGetErrorString());

        ambient = new ReflectivityTransmiss(mAmbientColor.r(), mAmbientColor.g(), mAmbientColor.b());
        diffuse = new ReflectivityTransmiss(mDiffuseColor.r(), mDiffuseColor.g(), mDiffuseColor.b());
        specular = new ReflectivityTransmiss(mSpecularColor.r(), mSpecularColor.g(), mSpecularColor.b());
    }

    public Material() {
        ambient = DEFAULT_COLOUR;
        diffuse = DEFAULT_COLOUR;
        specular = DEFAULT_COLOUR;
        reflectance = 0;
    }

    public Material(Vector3f colour, float reflectance) {
        this();
        this.ambient.fromVector(colour);
        this.reflectance = reflectance;
    }

    public Material(Texture texture) {
        this();
        this.texture = texture;
    }

    public Material(Texture texture, float reflectance) {
        this();
        this.texture = texture;
        this.reflectance = reflectance;
    }

    public Vector3f getAmbient() {
        return ambient.toVector();
    }
    public void setAmbient(Vector3f colour) {
        this.ambient.fromVector(colour);
    }

    public Vector3f getDiffuse() { return diffuse.toVector(); }
    public void setDiffuse(Vector3f dif) { this.diffuse.fromVector(dif); }

    public Vector3f getSpecular() { return specular.toVector(); }
    public void setSpecular(Vector3f spec) { this.specular.fromVector(spec); }

    public Vector3f getTransmissionFilter() { return transmissionFilter.toVector(); }
    public void setTransmissionFilter(Vector3f transmissionFilter) { this.transmissionFilter.fromVector(transmissionFilter); }

    public float getReflectance() {
        return reflectance;
    }
    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public boolean isTextured() {
        return this.texture != null;
    }
    public Texture getTexture() { return texture; }
    public void setTexture(Texture texture) { this.texture = texture; }

    public boolean hasNormalMap() {
        return this.normalMap != null;
    }
    public Texture getNormalMap() {
        return normalMap;
    }
    public void setNormalMap(Texture normalMap) {
        this.normalMap = normalMap;
    }

    public float getNs() { return Ns; }
    public void setNs(float Ns) { this.Ns = Ns; }
}
