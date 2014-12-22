package com.ralitski.util.render.display;

import java.awt.Image;
import java.awt.event.WindowEvent;

import com.ralitski.util.Timer;

public abstract class AbstractDisplayUser implements DisplayUser {

    private DisplayManager manager;
    private int width, height;

    public AbstractDisplayUser() {
        this(800, 600);
    }

    public AbstractDisplayUser(int size) {
        this(size, size);
    }

    public AbstractDisplayUser(int width, int height) {
        this(width, height, true);
    }

    public AbstractDisplayUser(int width, int height, boolean exitOnWindowClose) {
        this.width = width;
        this.height = height;
        this.manager = new DisplayManager(this, exitOnWindowClose);
    }
    
    public DisplayManager getManager() {
        return manager;
    }

    public void setDimensions(int x, int y) {
        this.width = x;
        this.height = y;
    }

    @Override
    public boolean update(boolean tick, float partial) {
        if (tick) {
            this.updateTick();
        }
        this.updateRender(partial);
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

    public abstract void updateRender(float partial);

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getMinWidth() {
        return 400;
    }

    @Override
    public int getMinHeight() {
        return 300;
    }

    @Override
    public void setWidth(int i) {
        this.width = i;
    }

    @Override
    public void setHeight(int i) {
        this.height = i;
    }

    public boolean setFullscreen(boolean flag) {
        return manager.setFullscreen(flag);
    }

    public void setIconImage(String s) {
        manager.setIconImage(s);
    }

    public void setIconImage(Image i) {
        manager.setIconImage(i);
    }

    public void setTitle(String s) {
        manager.setTitle(s);
    }

    public void time(int ticksPerSecond) {
        manager.time(ticksPerSecond);
    }
    
    public void setTimer(Timer timer) {
        manager.setTimer(timer);
    }
    
    public boolean hasTimer() {
        return manager.hasTimer();
    }

    @Override
    public void windowActivated(WindowEvent event) {
        //window focused
    }

    @Override
    public void windowDeactivated(WindowEvent event) {
        //window focus lost
    }

    @Override
    public void windowClosed(WindowEvent event) {
        //window closed via call to dispose()
    }

    @Override
    public void windowClosing(WindowEvent event) {
        //stop requested (x button)
    }

    @Override
    public void windowDeiconified(WindowEvent event) {
        //window selected from taskbar
    }

    @Override
    public void windowIconified(WindowEvent event) {
        //window minimized to taskbar
    }

    @Override
    public void windowOpened(WindowEvent event) {
        //window created
    }
}
