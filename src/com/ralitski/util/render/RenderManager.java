package com.ralitski.util.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.ralitski.util.doc.Usage;
import com.ralitski.util.math.geom.d3.Point3d;
import com.ralitski.util.render.camera.Camera;
import com.ralitski.util.render.img.Color;

/**
 *
 * @author ralitski
 */
public class RenderManager {
    
	private RenderManagerUser owner;
	private boolean is3D;
	
	private boolean useSmoothShading = true;
	private boolean enableDepthTest = true;
	private DepthFunc depthFunc = DepthFunc.auto();
	private Color clearColor = Color.BLACK;
    
    public RenderManager(RenderManagerUser owner) {
    	this(owner, true);
    }
    
    public RenderManager(RenderManagerUser owner, boolean use3d) {
    	this.owner = owner;
    	this.is3D = use3d;
    }

    //TODO: make all this configurable
    //also TODO: separate viewport stuff from 3D stuff
    public void setup3D() {
        // Start 3D Stuff
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        int w = owner.getWidth();
        int h = owner.getHeight();
        
        GLU.gluPerspective(100, (float)w / (float)h, 0.001f, 1000);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        GL11.glOrtho(0, w, 0, h, -1, 1);
        GL11.glViewport(0, 0, w, h);
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(useSmoothShading ? GL11.GL_SMOOTH : GL11.GL_FLAT);
        GL11.glClearColor(clearColor.getRedFloat(), clearColor.getGreenFloat(), clearColor.getBlueFloat(), clearColor.getAlphaFloat());
        GL11.glClearDepth(1.0f);
        if(enableDepthTest) {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthFunc(depthFunc.glCode());
        } else {
        	GL11.glDisable(GL11.GL_DEPTH_TEST);
        }
        // https://www.opengl.org/sdk/docs/man/html/glBlendFunc.xhtml
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glFrontFace(GL11.GL_CW);
        GL11.glCullFace(GL11.GL_FRONT);
    }
    /*
     * from RenderCam2D
     * 

	public void setup(boolean deep)
	{
		if(this.textured) GL11.glEnable(GL11.GL_TEXTURE_2D);
		else GL11.glDisable(GL11.GL_TEXTURE_2D);
		if(!deep) GL11.glDisable(GL11.GL_DEPTH_TEST);
		else GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

        rescale();
	}
	
	public void rescale(int width, int height)
	{
		int mode = GL11.glGetInteger(GL11.GL_MATRIX_MODE);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		//left, right, bottom, top, zNear, zFar
		GL11.glOrtho(0, width, 0, height, 0, 1);
		//x, y, width, height
		GL11.glViewport(0, 0, width, height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(mode);
	}
     */
    
    public void render(float partial) {
    	//clear screen
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();
        GL11.glColor4f(1, 1, 1, 1);
        Camera camera = owner.getCamera();
        
        owner.render3d(partial);

        GL11.glRotatef(camera.getPitch(), 1, 0, 0);
        GL11.glRotatef(camera.getYaw(), 0, 1, 0);
        GL11.glRotatef(camera.getRoll(), 0, 0, 1);
        owner.render3dRotated(partial);
        
        Point3d p = camera.getPosition();
        GL11.glTranslatef(-p.getX(), -p.getY(), -p.getZ());
        owner.render3dTranslated(partial);
        
        //2D stoof
        setup2D();
        owner.render2d(partial);
        reset2D();
    }
    
    public void setup2D() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0, owner.getWidth(), 0, owner.getHeight(), -1, 1);
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
