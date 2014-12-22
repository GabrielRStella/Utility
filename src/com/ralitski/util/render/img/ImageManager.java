package com.ralitski.util.render.img;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

public class ImageManager {

    // image loading and storage

    /*
     * TODO: enable image deloading boolean array (or list, so it can resize...)
     * of used IDs, gets set to true on load / false on deload, newID() cycles
     * through for first false FOR MIPMAPPING: for a texture of (width = n,
     * height = n) have divisors of 2 in texture side, and glTexture2D them: 256
     * 128 64 32 16 8 4 2 1
     */
	public static boolean BIND_GL;
    public static int MIN_FILTER, MAG_FILTER;
    private static int nextID;

    static {
    	BIND_GL = true;
        MIN_FILTER = GL11.GL_NEAREST;
        MAG_FILTER = GL11.GL_NEAREST;
        nextID = 0;
    }

    public static void blend() {
        MIN_FILTER = GL11.GL_LINEAR;
        MAG_FILTER = GL11.GL_LINEAR;
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                MIN_FILTER);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
                MAG_FILTER);
    }

    public static void noBlend() {
        MIN_FILTER = GL11.GL_NEAREST;
        MAG_FILTER = GL11.GL_NEAREST;
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                MIN_FILTER);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
                MAG_FILTER);
    }

    public static void glTexParam(int min, int mag) {
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                min);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
                mag);
    }

    public static void bindTexture(Image i) {
        bindTexture(i.id());
    }

    public static void bindTexture(int i) {
        if (i >= 0) {
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, i);
        }
    }

    public static Texture loadTexture(String s) {
        return Texture.getData(loadImage(s));
    }

    public static int loadTextureId(String s) {
        return loadImage(s).id();
    }

    public static Image loadImage(String s) {
        File f = new File(s);
        try {
            BufferedImage image = null;
            image = ImageIO.read(f);

            int width = image.getWidth();
            int height = image.getHeight();
            int[] data = new int[width * height];
            image.getRGB(0, 0, width, height, data, 0, width);
            return image(width, height, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Image image(int width, int height, int[] data) {
        byte[] bata = new byte[data.length * 4];
        for (int c = 0; c < data.length; ++c) {
            int[] colors = ColorHelper.fromCompressed(data[c]);
            int a = colors[0];
            int r = colors[1];
            int g = colors[2];
            int b = colors[3];
            /*
             * if(a != 0) { if(r != g && r != b && g != b) {
             * System.out.println(c); System.out.println("r " + r);
             * System.out.println("g " + g); System.out.println("b " + b);
             * System.out.println("a " + a); } }
             */
            bata[c * 4 + 0] = (byte) r;
            bata[c * 4 + 1] = (byte) g;
            bata[c * 4 + 2] = (byte) b;
            bata[c * 4 + 3] = (byte) a;
        }
        Image i = new Image(width, height, bata);
        if(BIND_GL) register(i);
        return i;
    }

    public static void register(Image i) {
        if (i.id() == -1) {
            i.id(newID());
            setup(i);
        }
    }

    public static void setup(Image i) {
        ByteBuffer imageData = ByteBuffer.allocateDirect(i.data().length).order(ByteOrder.nativeOrder());
        imageData.clear();
        imageData.put(i.data());
        imageData.position(0).limit(i.data().length);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, i.id());
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                MIN_FILTER);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
                MAG_FILTER);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, i.width(),
                i.height(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData);
    }
    
    public static void deleteImage(int i) {
    	GL11.glDeleteTextures(i);
    }

    public static int newID() {
        return nextID++;
    }
}
