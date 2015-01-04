package com.ralitski.util.render.img;

import java.util.HashMap;
import java.util.Map;

public class ImageList {

	private String fileType = "png";
    public Map<String, Texture> images;
    public String loc;

    public ImageList() {
        this(".");
    }

    public ImageList(String loc) {
        this.images = new HashMap<String, Texture>();
        setBaseLoc(loc);
    }

    public String getBaseLoc() {
        return this.loc;
    }

    public void setBaseLoc(String loc) {
        if (!loc.endsWith("/")) {
            loc = loc.concat("/");
        }
        this.loc = loc;
    }
    
    public String getFileType() {
    	return fileType;
    }
    
    public void setFileType(String fileType) {
    	this.fileType = fileType;
    }
    
    //add

    /**
     * 
     * @param name
     * @param data
     * @return the image previously stored under the given name (null if no image previously stored)
     */
    public Texture setImage(String name, Texture data) {
        return this.images.put(name, data);
    }
    
    //get

    public Texture getImage(String string) {
    	return getImage(string, false);
    }

    public Texture getImage(String string, boolean load) {
        Texture tex = this.images.get(string);
        if (tex != null) {
            return tex;
        }
        return load ? this.loadImage(this.loc + string, string) : null;
    }
    
    //load

    public Texture loadImage(String loc, String name) {
    	Image image = GLImageHelper.loadImage(loc + "." + fileType);
    	image.glPrepare();
        Texture texture = new Texture(image);
        this.images.put(name, texture);
        return texture;
    }

    public Texture loadImage(String name) {
    	Image image = GLImageHelper.loadImage(this.loc + name + "." + fileType);
    	image.glPrepare();
        Texture texture = new Texture(image);
        this.images.put(name, texture);
        return texture;
    }
    
    //bind

    public boolean bindImage(String image) {
        return bindImage(image, false);
    }

    public boolean bindImage(String image, boolean flag) {
        Texture data = this.images.get(image);
        if (data == null) {
            if (flag) {
                data = this.loadImage(this.loc, image);
            } else {
                return false;
            }
        }
        data.glBind();
        return true;
    }
    
    //delete
    
    public Texture deleteImage(String name) {
    	Texture tex = getImage(name);
    	if(tex != null) tex.glDelete();
    	return tex;
    }
}
