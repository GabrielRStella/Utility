package com.ralitski.util.render.img;

import java.util.HashMap;
import java.util.Map;

public class ImageList {

    public Map<String, Texture> images;
    public String loc;

    public ImageList() {
        this(".");
    }

    public ImageList(String loc) {
        this.images = new HashMap<String, Texture>();
        if (!loc.endsWith("/")) {
            loc = loc.concat("/");
        }
        this.loc = loc;
    }

    public String getBaseLoc() {
        return this.loc;
    }

    public void setBaseLoc(String loc) {
        this.loc = loc;
    }

    public boolean bind(String image) {
        return bind(image, false);
    }

    public boolean bind(String image, boolean flag) {
        Texture data = this.images.get(image);
        if (data == null) {
            if (flag) {
                this.loadImage(this.loc, image);
            } else {
                return false;
            }
        }
        ImageManager.bindTexture(data.id());
        return true;
    }

    public Texture loadImage(String loc, String name) {
        Texture texture = ImageManager.loadTexture(loc + ".png");
        this.images.put(name, texture);
        return texture;
    }

    public Texture loadImage(String name) {
        Texture texture = ImageManager.loadTexture(this.loc + name + ".png");
        this.images.put(name, texture);
        return texture;
    }

    public void addImage(String name, Texture data) {
        this.images.put(name, data);
    }

    public Texture get(String string) {
        Texture tex = this.images.get(string);
        if (tex != null) {
            return tex;
        }
        return this.loadImage(this.loc + string, string);
    }
}
