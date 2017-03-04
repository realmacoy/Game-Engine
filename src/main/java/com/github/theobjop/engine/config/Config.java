package com.github.theobjop.engine.config;

/**
 * Created by Brandon on 2/23/2017.
 */
public class Config {
    private static Config videoConfig = new Config("video");
    private static Config buttonConfig = new Config("button");
    private static Config soundConfig = new Config("sound");

    private String fileName;

    private Config(String fileName) {
        this.fileName = fileName;
        this.load();
    }

    private void load() {

    }

    public void save() {

    }
}
