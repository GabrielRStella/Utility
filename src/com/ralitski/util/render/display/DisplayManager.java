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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import com.ralitski.util.*;

public class DisplayManager implements WindowListener {

    private boolean running, stopOnClose, setup;
    private DisplayUser user;
    private Frame frame;
    private Ticker timer;
    
    //previous window dimensions, to detect resizing
    private int prevX, prevY;

    public DisplayManager(DisplayUser user) {
        this(user, true);
    }
    
    public DisplayManager(DisplayUser user, boolean stopOnWindowExit) {
        this.user = user;
        this.stopOnClose = stopOnWindowExit;
    }
    
    public boolean setup() {
        this.running = true;
        this.frame = new Frame();
        frame.setTitle(user.title());
        frame.setBackground(java.awt.Color.BLACK);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        
        //make sure the inside of the frame is the proper size
        Insets insets = frame.getInsets();

        int width = prevX = this.user.getWidth() + insets.left + insets.right;
        int height = prevY = this.user.getHeight() + insets.top + insets.bottom;
        frame.setSize(width, height);

        int minWidth = this.user.getMinWidth() + insets.left + insets.right;
        int minHeight = this.user.getMinHeight() + insets.top + insets.bottom;
        frame.setMinimumSize(new Dimension(minWidth, minHeight));
        
        @SuppressWarnings("serial")
		Canvas canvas = new Canvas() {

            @Override
            public final void removeNotify() {
                System.out.println("Window destroyed");
                super.removeNotify();
            }
        };
        canvas.setFocusable(true);
        canvas.requestFocus();
        canvas.setIgnoreRepaint(true);

        frame.add(canvas);
        frame.addWindowListener(this);

        try {
            Display.setParent(canvas);
            Display.create();
        } catch (Throwable e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Display Error!", e);
            canvas.setEnabled(false);
            frame.remove(canvas);
            frame.setEnabled(false);
            frame.dispose();
            frame = null;
            running = false;
            return false;
        }
        System.out.println("Window created!");

        this.user.setup();
        return setup = true;
    }
    
    public Frame getFrame() {
    	return frame;
    }

    public void start() {
        if(!setup) return;
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
        double d = timer != null ? timer.time() : 1;
        boolean flag = d >= 1;
        if (!this.user.update(flag, (float)d)) {
            this.running = false;
        }
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
    

    public void time(int ticksPerSecond) {
        this.timer = Ticker.ticksPerSecond(ticksPerSecond);
    }

    public void setTimer(Ticker t) {
        this.timer = t;
    }

    public boolean hasTimer() {
        return this.timer != null;
    }

    public void setIconImage(String imageFilePath) {
        try {
            BufferedImage img = ImageIO.read(new File(imageFilePath));
            this.setIconImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setIconImage(Image i) {
        this.frame.setIconImage(i);
    }

    public boolean setFullscreen(boolean flag) {
        try {
            Display.setFullscreen(flag);
        } catch (LWJGLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void setTitle(String s) {
        this.frame.setTitle(s);
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
    }

    @Override
    public void windowClosing(WindowEvent event) {
        // TODO stop requested (stop button)
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
