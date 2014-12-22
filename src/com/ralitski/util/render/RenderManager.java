package com.ralitski.util.render;

import com.ralitski.util.render.camera.Camera;

/**
 *
 * @author ralitski
 */
public class RenderManager {
    
	private RenderManagerUser owner;
    private RenderCamera renderCamera;
    
    public RenderManager(RenderManagerUser owner, Camera camera) {
    	this.owner = owner;
        this.renderCamera = new RenderCamera(owner, camera);
        renderCamera.setup3D();
    }
    
    public RenderCamera getCamera() {
        return renderCamera;
    }
    
    public void setCamera(Camera camera) {
        this.renderCamera.setCamera(camera);
    }
    
    public void render(float partial) {
    	
    	//2simple
    	
        renderCamera.update();
        owner.render3d(partial);
        renderCamera.setup2D();
        owner.render2d(partial);
        renderCamera.reset2D();
    }
}
