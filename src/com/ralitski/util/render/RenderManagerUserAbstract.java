package com.ralitski.util.render;

import com.ralitski.util.render.display.AbstractDisplayUser;

public abstract class RenderManagerUserAbstract extends AbstractDisplayUser implements RenderManagerUser {

    protected RenderManager renderManager;

    public RenderManagerUserAbstract() {
      this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public RenderManagerUserAbstract(int size) {
      this(size, size);
    }

    public RenderManagerUserAbstract(int width, int height) {
      super(width, height);
      renderManager = new RenderManager(this);
    }
    
    //boolean is3D constructors

    public RenderManagerUserAbstract(boolean is3D) {
      this(DEFAULT_WIDTH, DEFAULT_HEIGHT, is3D);
    }

    public RenderManagerUserAbstract(int size, boolean is3D) {
      this(size, size, is3D);
    }

    public RenderManagerUserAbstract(int width, int height, boolean is3D) {
      super(width, height);
      renderManager = new RenderManager(this, is3D);
    }
    
    //stuff

    public void updatePartial(float partial) {
      renderManager.render(partial);
    }
    
    public void setup() {
      renderManager.setup();
    }
}
