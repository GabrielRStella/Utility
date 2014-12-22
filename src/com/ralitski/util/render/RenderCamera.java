package com.ralitski.util.render;

import com.ralitski.util.render.camera.Camera;
import com.ralitski.util.render.display.DisplayUser;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class RenderCamera {
    
    private DisplayUser user;
    private Camera camera;

    public RenderCamera(DisplayUser user, Camera camera) {
        this.user = user;
        this.camera = camera;
    }
    
    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    
    public void update() {
        clearScreen();
        translatePostion();
        GL11.glColor4f(1, 1, 1, 1);
    }

    private void clearScreen() {
        // Clear the screen
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();
    }

    private void translatePostion() {
        // This is the code that changes 3D perspective to the camera's
        // perspective.
        GL11.glRotatef(camera.getPitch(), 1, 0, 0);
        GL11.glRotatef(camera.getYaw(), 0, 1, 0);
        GL11.glRotatef(camera.getRoll(), 0, 0, 1);
        GL11.glTranslatef(-camera.getX(), -camera.getY(), -camera.getZ());
    }

    public void setup3D() {
        // Start 3D Stuff
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        int w = user.getWidth();
        int h = user.getHeight();
        
        GLU.gluPerspective(100, (float)w / (float)h, 0.001f, 1000);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        GL11.glOrtho(0, w, 0, h, -1, 1);
        GL11.glViewport(0, 0, w, h);
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glClearDepth(1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glFrontFace(GL11.GL_CW);
        GL11.glCullFace(GL11.GL_FRONT);
    }
    
    public void setup2D() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0, user.getWidth(), 0, user.getHeight(), -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
    }
    
    public void reset2D() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
    }
}
