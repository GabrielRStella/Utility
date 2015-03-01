package com.ralitski.util.render.img;

import java.util.HashMap;
import java.util.Map;

public class GLImageList {

	private String fileType = "png";
    public Map<String, GLTexture> images;
    public String loc;

    public GLImageList() {
        this(".");
    }

    public GLImageList(String loc) {
        this.images = new HashMap<String, GLTexture>();
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
    public GLTexture setImage(String name, GLTexture data) {
        return this.images.put(name, data);
    }
    
    //get

    public GLTexture getImage(String string) {
    	return getImage(string, false);
    }

    public GLTexture getImage(String string, boolean load) {
        GLTexture tex = this.images.get(string);
        if (tex != null) {
            return tex;
        }
        return load ? this.loadImage(this.loc + string, string) : null;
    }
    
    //load

    public GLTexture loadImage(String loc, String name) {
    	GLImage image = new GLImage(Image.loadImage(loc + "." + fileType));
    	image.glPrepare();
        GLTexture texture = new GLTexture(image);
        this.images.put(name, texture);
        return texture;
    }

    public GLTexture loadImage(String name) {
    	GLImage image = new GLImage(Image.loadImage(this.loc + name + "." + fileType));
    	image.glPrepare();
        GLTexture texture = new GLTexture(image);
        this.images.put(name, texture);
        return texture;
    }
    
    //bind

    public boolean bindImage(String image) {
        return bindImage(image, false);
    }

    public boolean bindImage(String image, boolean flag) {
        GLTexture data = this.images.get(image);
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
    
    public GLTexture deleteImage(String name) {
    	GLTexture tex = getImage(name);
    	if(tex != null) tex.glDelete();
    	return tex;
    }
}
