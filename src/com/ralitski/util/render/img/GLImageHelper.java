package com.ralitski.util.render.img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

public class GLImageHelper {

    /*
     * FOR MIPMAPPING: for a texture of (width = n,
     * height = n) have divisors of 2 in texture side, and glTexture2D them: 256
     * 128 64 32 16 8 4 2 1
     */

    public static void bindTexture(int id) {
        if (id >= 0) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        }
    }

    public static void bindTexture(int id, int minFilter, int magFilter) {
        if (id >= 0) {
    		GLImageHelper.bindTexture(id);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, minFilter);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, magFilter);
        }
    }

	public static void resetImageFilters(int id, int minFilter, int magFilter) {
		if(id >= 0) {
			int bound = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
			bindTexture(id, minFilter, magFilter);
			bindTexture(bound);
		}
	}

    public static Image loadImage(String s) {
        File f = new File(s);
        return loadImage(f);
    }
	
	public static Image loadImage(File f) {
        try {
            BufferedImage image = ImageIO.read(f);
            return loadImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	public static Image loadImage(InputStream in) {
        try {
            BufferedImage image = ImageIO.read(in);
            return loadImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	public static Image loadImage(BufferedImage image) {
		return new Image(image);
	}

    public static void setup(Image i) {
    	
    	i.setId(newID());
        byte[] b = i.getData();
    	
        ByteBuffer imageData = ByteBuffer.allocateDirect(b.length).order(ByteOrder.nativeOrder());
        imageData.clear();
        imageData.put(b);
        imageData.position(0).limit(b.length);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, i.getId());
        
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                i.getMinFilter());
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
                i.getMagFilter());
        
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, i.getWidth(),
                i.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData);
    }
    
    public static void deleteImage(int i) {
    	if(i >= 0) GL11.glDeleteTextures(i);
    }

    public static int newID() {
        return GL11.glGenTextures();
    }

}
