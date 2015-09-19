package com.ralitski.util.render.display;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import com.ralitski.util.*;

/**
 * 
 * @author ralitski
 *
 */
public class DisplayManager implements WindowListener {
	
	/* 
	 * frame.setUndecorated(true);
	 * frame.setBackground(new Color(0, 0, 0, 0));
	 */

	public static final int ERROR_NO_DISPLAY = 0;
	public static final int ERROR_LOAD_IMAGE = 1;
	public static final int ERROR_FULLSCREEN = 2;

    private boolean running;
    private boolean setup;
    private DisplayUser user;
    private Frame frame;
    private Ticker timer;
    
    private boolean stopOnClose = true;
    private boolean resizable = true;
    private int minWidth = 0;
    private int minHeight = 0;
    
    //previous window dimensions, to detect resizing
    private int prevX, prevY;
    
    private float partialTicks;
    
    public DisplayManager(DisplayUser user) {
        this.user = user;
    }
    
    public boolean setup() {
        this.running = true;
        this.frame = new Frame();
//        frame.setTitle("");
        frame.setBackground(java.awt.Color.BLACK);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setResizable(resizable);
        
        //make sure the inside of the frame is the proper size
        Insets insets = frame.getInsets();

        int width = prevX = this.user.getWidth() + insets.left + insets.right;
        int height = prevY = this.user.getHeight() + insets.top + insets.bottom;
        frame.setSize(width, height);
        prevX = width;
        prevY = height;

        int minWidth = this.minWidth + insets.left + insets.right;
        int minHeight = this.minHeight + insets.top + insets.bottom;
        frame.setMinimumSize(new Dimension(minWidth, minHeight));
        
		Canvas canvas = new Canvas();
        canvas.setFocusable(true);
        canvas.requestFocus();
        canvas.setIgnoreRepaint(true);

        frame.add(canvas);
        //frame.addWindowListener(user instanceof WindowListener ? (WindowListener)user : this);
        frame.addWindowListener(this);
        if(user instanceof WindowListener) frame.addWindowListener((WindowListener)user);

        try {
            Display.setParent(canvas);
            Display.create();
        } catch (Throwable e) {
            canvas.setEnabled(false);
            frame.remove(canvas);
            frame.setEnabled(false);
            frame.dispose();
            frame = null;
            running = false;
            
            user.getError(ERROR_NO_DISPLAY, e);
            
            return false;
        }

        this.user.setup();
        return setup = true;
    }
    
    public Frame getFrame() {
    	return frame;
    }

    public void start() {
        if(!setup) return;
        if(timer != null) timer.time();
        while (running) {
            this.update();
        }
        doExit();
    }

    public void stop() {
        running = false;
    }
    
    private void doExit() {
        Display.destroy();
        this.frame.dispose();
        this.user.close();
    }

    private void update() {
        resizeCheck();
        float f = timer != null ? (float)timer.time() : 1;
        partialTicks += f;
        boolean flag = partialTicks >= 1F;
        System.out.println(f + " " + partialTicks + " " + flag);
        if (!this.user.update(flag, f, partialTicks)) {
            this.running = false;
        }
        if(flag) partialTicks -= 1F; //get rid of extra built-up ticks
        //Display.sync(60);
        Display.update();
        if (Display.isCloseRequested()) {
            this.running = false;
        }
    }

    private void resizeCheck() {
        boolean resize = false;
        if (prevX != Display.getWidth()) {
            prevX = Display.getWidth();
            resize = true;
        }
        if (prevY != Display.getHeight()) {
            prevY = Display.getHeight();
            resize = true;
        }
        if (resize) {
            this.user.setWidth(this.prevX);
            this.user.setHeight(this.prevY);
            this.user.resize();
        }
    }

    public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public int getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	public boolean doStopOnClose() {
		return stopOnClose;
	}

	public void setStopOnClose(boolean stopOnClose) {
		this.stopOnClose = stopOnClose;
	}
    
    public void time(float ticksPerSecond) {
        this.timer = Ticker.ticksPerSecond(ticksPerSecond);
    }

	public void setTimer(Ticker t) {
        this.timer = t;
    }
    
    public Ticker getTimer() {
    	return timer;
    }

    public boolean hasTimer() {
        return this.timer != null;
    }

    public void setIconImage(String imageFilePath) {
        try {
            BufferedImage img = ImageIO.read(new File(imageFilePath));
            this.setIconImage(img);
        } catch (IOException e) {
            user.getError(ERROR_LOAD_IMAGE, e);
        }
    }

    public void setIconImage(Image i) {
        if(frame != null) this.frame.setIconImage(i);
    }
    
    public boolean isFullscreen() {
    	return Display.isFullscreen();
    }

    public boolean setFullscreen(boolean flag) {
        try {
            Display.setFullscreen(flag);
        } catch (LWJGLException e) {
            user.getError(ERROR_FULLSCREEN, e);
            return false;
        }
        return true;
    }

    public void setTitle(String s) {
        if(frame != null) this.frame.setTitle(s);
    }

    @Override
    public void windowActivated(WindowEvent event) {
        // TODO window focused
    }

    @Override
    public void windowDeactivated(WindowEvent event) {
        // TODO window focus lost
    }

    @Override
    public void windowClosed(WindowEvent event) {
        // TODO window closed via call to dispose()
    	this.stop();
    }

    @Override
    public void windowClosing(WindowEvent event) {
        //red X button clicked
        if(this.stopOnClose) this.stop();
    }

    @Override
    public void windowDeiconified(WindowEvent event) {
        // TODO window selected from taskbar
    }

    @Override
    public void windowIconified(WindowEvent event) {
        // TODO window minimized to taskbar
    }

    @Override
    public void windowOpened(WindowEvent event) {
        // TODO window created
    }
}
