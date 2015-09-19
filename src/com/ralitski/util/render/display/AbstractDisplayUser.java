package com.ralitski.util.render.display;

import java.awt.Image;

import com.ralitski.util.Ticker;

public abstract class AbstractDisplayUser implements DisplayUser {

    protected DisplayManager manager;
    private int width;
    private int height;

    public AbstractDisplayUser() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public AbstractDisplayUser(int size) {
        this(size, size);
    }

    public AbstractDisplayUser(int width, int height) {
        this.width = width;
        this.height = height;
        this.manager = new DisplayManager(this);
    }
    
    public DisplayManager getManager() {
        return manager;
    }

    public void setDimensions(int x, int y) {
        this.width = x;
        this.height = y;
    }

    @Override
    public boolean update(boolean tick, float partial, float partialFromLast) {
        if (tick) updateTick();
        updatePartial(partial, partialFromLast);
        return true;
    }
    
    public void start() {
    	if(manager.setup()) {
    		manager.start();
    	}
    }

    public void stop() {
    	manager.stop();
    }

    public abstract void updateTick();

    public abstract void updatePartial(float partial, float partialFromLast);

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void setWidth(int i) {
        this.width = i;
    }

    @Override
    public void setHeight(int i) {
        this.height = i;
    }

    public boolean isResizable() {
    	return manager.isResizable();
	}

	public void setResizable(boolean resizable) {
		manager.setResizable(resizable);
	}

	public int getMinWidth() {
		return manager.getMinWidth();
	}

	public void setMinWidth(int minWidth) {
		manager.setMinWidth(minWidth);
	}

	public int getMinHeight() {
		return manager.getMinHeight();
	}

	public void setMinHeight(int minHeight) {
		manager.setMinHeight(minHeight);
	}

	public boolean doStopOnClose() {
		return manager.doStopOnClose();
	}

	public void setStopOnClose(boolean stopOnClose) {
		manager.setStopOnClose(stopOnClose);
	}
    
    public void time(float ticksPerSecond) {
    	manager.time(ticksPerSecond);
    }

	public void setTimer(Ticker t) {
		manager.setTimer(t);
    }
    
    public Ticker getTimer() {
    	return manager.getTimer();
    }

    public boolean hasTimer() {
    	return manager.hasTimer();
    }

    public void setIconImage(String imageFilePath) {
    	manager.setIconImage(imageFilePath);
    }

    public void setIconImage(Image i) {
    	manager.setIconImage(i);
    }
    
    public boolean isFullscreen() {
    	return manager.isFullscreen();
    }

    public boolean setFullscreen(boolean flag) {
    	return manager.setFullscreen(flag);
    }

    public void setTitle(String s) {
    	manager.setTitle(s);
    }
}
