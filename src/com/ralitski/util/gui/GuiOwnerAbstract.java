package com.ralitski.util.gui;

import com.ralitski.util.input.InputFeed;
import com.ralitski.util.input.InputMonitor;
import com.ralitski.util.input.LWJGLInputFeed;
import com.ralitski.util.render.DepthFunc;
import com.ralitski.util.render.RenderManagerUserAbstract;
import com.ralitski.util.render.camera.Camera;

public abstract class GuiOwnerAbstract extends RenderManagerUserAbstract implements GuiOwner {
	
	protected GuiManager guiManager;
	protected InputMonitor input;

    public GuiOwnerAbstract() {
    	super();
    }

    public GuiOwnerAbstract(int size) {
    	super(size);
    }

    public GuiOwnerAbstract(int width, int height) {
    	super(width, height);
    }

    public GuiOwnerAbstract(boolean is3D) {
    	super(is3D);
    }

    public GuiOwnerAbstract(int size, boolean is3D) {
    	super(size, is3D);
    }

    public GuiOwnerAbstract(int width, int height, boolean is3D) {
    	super(width, height, is3D);
    }
	
	public void setup() {
		super.setup();
		renderManager.setDepthFunc(DepthFunc.GL_LEQUAL);
		renderManager.setBlendAlpha();
		guiManager = new GuiManager(this);
		input = new InputMonitor(guiManager, getInputFeed());
		
		guiManager.openScreen(getMainMenu(guiManager));
	}
	
	protected InputFeed getInputFeed() {
		return LWJGLInputFeed.getFeed();
	}
	
	protected abstract Gui getMainMenu(GuiManager guiManager);

	@Override
	public void render3dTransformed(float partial) {
		guiManager.render3d(partial);
	}

	@Override
	public void render2d(float partial) {
		guiManager.render2d(partial);
	}

	@Override
	public Camera getCamera() {
		return null;
	}
	
	@Override
	public void updateTick() {
		input.update();
		guiManager.update();
	}

}
