package com.ralitski.util.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.ralitski.util.math.geom.d3.Orientation3d;
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
	private boolean enableBlend = true;
	private BlendFunc srcBlend = BlendFunc.autoSrc();
	private BlendFunc destBlend = BlendFunc.autoDest();
	private boolean enableCull = true;
	private boolean cullCW = true;
    
    public RenderManager(RenderManagerUser owner) {
    	this(owner, true);
    }
    
    public RenderManager(RenderManagerUser owner, boolean use3d) {
    	this.owner = owner;
    	this.is3D = use3d;
    }
    
    //get set

    public boolean isIs3D() {
		return is3D;
	}

	public void setIs3D(boolean is3d) {
		is3D = is3d;
	}

	public boolean useSmoothShading() {
		return useSmoothShading;
	}

	public void setUseSmoothShading(boolean useSmoothShading) {
		this.useSmoothShading = useSmoothShading;
	}

	public boolean isEnableDepthTest() {
		return enableDepthTest;
	}

	public void setEnableDepthTest(boolean enableDepthTest) {
		this.enableDepthTest = enableDepthTest;
	}

	public DepthFunc getDepthFunc() {
		return depthFunc;
	}

	public void setDepthFunc(DepthFunc depthFunc) {
		this.depthFunc = depthFunc;
	}

	public Color getClearColor() {
		return clearColor;
	}

	public void setClearColor(Color clearColor) {
		this.clearColor = clearColor;
	}

	public boolean isEnableBlend() {
		return enableBlend;
	}

	public void setEnableBlend(boolean enableBlend) {
		this.enableBlend = enableBlend;
	}

	public BlendFunc getSrcBlend() {
		return srcBlend;
	}

	public void setSrcBlend(BlendFunc srcBlend) {
		this.srcBlend = srcBlend;
	}

	public BlendFunc getDestBlend() {
		return destBlend;
	}

	public void setDestBlend(BlendFunc destBlend) {
		this.destBlend = destBlend;
	}
	
	public void setBlendAlpha() {
		srcBlend = BlendFunc.SRC_ALPHA;
		destBlend = BlendFunc.DST_ALPHA; //BlendFunc.ONE_MINUS_SRC_ALPHA;
		enableBlend = true;
	}

	public boolean isEnableCull() {
		return enableCull;
	}

	public void setEnableCull(boolean enableCull) {
		this.enableCull = enableCull;
	}

	public boolean isCullCW() {
		return cullCW;
	}

	public void setCullCW(boolean cullCW) {
		this.cullCW = cullCW;
	}
	
	//stoof

    public void setup() {
        // Start 3D Stuff
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();

        int w = owner.getWidth();
        int h = owner.getHeight();
        
        //fov-y, aspect, znear, zfar
        GLU.gluPerspective(100, (float)w / (float)h, 0.001f, 1000);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        GL11.glOrtho(0, w, 0, h, -1, 1);
        GL11.glViewport(0, 0, w, h);
        
        GL11.glShadeModel(useSmoothShading ? GL11.GL_SMOOTH : GL11.GL_FLAT);
        GL11.glClearColor(clearColor.getRedFloat(), clearColor.getGreenFloat(), clearColor.getBlueFloat(), clearColor.getAlphaFloat());
        GL11.glClearDepth(1.0f);
        if(enableDepthTest) {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthFunc(depthFunc.glCode());
        } else {
        	GL11.glDisable(GL11.GL_DEPTH_TEST);
        }
        if(enableBlend) {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(srcBlend.glCode(), destBlend.glCode());
        } else {
        	GL11.glDisable(GL11.GL_BLEND);
        }
        if(enableCull) {
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glFrontFace(cullCW ? GL11.GL_CW : GL11.GL_CCW);
            GL11.glCullFace(GL11.GL_FRONT);
        } else {
            GL11.glDisable(GL11.GL_CULL_FACE);
        }
    }
    
    public void render(float partial) {
    	//clear screen
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glLoadIdentity();
        GL11.glColor4f(1, 1, 1, 1);
        
        Camera camera = owner.getCamera();
        if(this.is3D && camera != null) {
            owner.render3dUntransformed(partial);

            //TODO: le test
            Orientation3d o = camera.getOrientation();
            GL11.glRotatef((float)Math.toDegrees(o.getPitch()), 1, 0, 0);
            GL11.glRotatef((float)Math.toDegrees(o.getRoll()), 0, 0, 1);
            GL11.glRotatef((float)Math.toDegrees(o.getYaw()), 0, 1, 0);
            owner.render3dRotated(partial);
            Point3d p = camera.getPosition();
            GL11.glTranslatef(-p.getX(), -p.getY(), -p.getZ());
            owner.render3dTransformed(partial);
        }
        
        //2D stoof
        begin2d();
        owner.render2d(partial);
        end2d();
    }
    
    public void begin2d() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0, owner.getWidth(), 0, owner.getHeight(), -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
    }

    public void end2d() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
    }
}
