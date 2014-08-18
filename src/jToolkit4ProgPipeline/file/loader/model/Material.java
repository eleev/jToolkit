package jToolkit4ProgPipeline.file.loader.model;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Astemir Eleev on 17/12/13.
 */
public class Material {
    private Vector3f diffuse;
    private Vector3f ambient;
    private Vector3f specular;
    private String name;

    public Material(String name) {
        this.name = name;
        diffuse = new Vector3f(1, 1, 1);
        ambient = new Vector3f();
        specular = new Vector3f();
    }

    public Material(String name, Vector3f diffuse) {
        this.name = name;
        this.diffuse = diffuse;
        ambient = new Vector3f();
        specular = new Vector3f();
    }

    public Material(String name, Vector3f diffuse, Vector3f ambient) {
        this.name = name;
        this.diffuse = diffuse;
        this.ambient = ambient;
        specular = new Vector3f();
    }

    public Material(String name, Vector3f diffuse, Vector3f ambient, Vector3f specular) {
        this.name = name;
        this.diffuse = diffuse;
        this.ambient = ambient;
        this.specular = specular;
    }

    public Vector3f getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Vector3f diffuse) {
        this.diffuse = diffuse;
    }

    public Vector3f getAmbient() {
        return ambient;
    }

    public void setAmbient(Vector3f ambient) {
        this.ambient = ambient;
    }

    public Vector3f getSpecular() {
        return specular;
    }

    public void setSpecular(Vector3f specular) {
        this.specular = specular;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
