package com.ralitski.util.gui;

import java.awt.Font;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.text.DecimalFormat;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import com.ralitski.util.math.geom.d3.Orientation3d;
import com.ralitski.util.math.geom.d3.Point3d;
import com.ralitski.util.render.TrueTypeFont;
import com.ralitski.util.render.camera.Camera;
import com.ralitski.util.render.camera.CameraOwner;

/**
 *
 * @author ralitski
 */
public class GuiDebug {

    protected TrueTypeFont font;
    private int renderTicksPerThisSecond;
    private float renderTicksPerSecond;
    private long prevTime;
    
    private CameraOwner owner;
    
    public GuiDebug(CameraOwner owner) {
    	this.owner = owner;
        Font jFont = new Font("arial", Font.PLAIN, 20);
        font = new TrueTypeFont(jFont, true);
    }

    public void render() {
        Camera camera = owner.getCamera();
        
        long time = Sys.getTime() * 1000 / Sys.getTimerResolution();
        if(time - prevTime > 1000) {
            renderTicksPerSecond = renderTicksPerThisSecond;
            renderTicksPerThisSecond = 0;
            prevTime = time;
        }
        renderTicksPerThisSecond++;
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(1, 1, 1, 1);
        
        Point3d pos = camera.getPosition();
        DecimalFormat format = new DecimalFormat("#.##");
        font.drawString(10, 60, "FPS: " + format.format(renderTicksPerSecond), 1, 1);
        font.drawString(10, 40, "(X: " + format.format(pos.getX()) + ", Y: "
                + format.format(pos.getY()) + ", Z: "
                + format.format(pos.getZ()) + ")", 1, 1);
        Orientation3d o = camera.getOrientation();
        font.drawString(10, 20,
        		"Yaw: " + format.format(o.getYaw())
        		+ ", Pitch: " + format.format(o.getPitch())
        		+ ", Roll: " + format.format(o.getRoll()),
        		1, 1);
        MemoryMXBean mb = ManagementFactory.getMemoryMXBean();
        font.drawString(10, 0, "Memory: " + (mb.getHeapMemoryUsage().getUsed() / 1024) / 1024 + " MB ("
                + (mb.getHeapMemoryUsage().getMax() / 1024) / 1024 + " MB)", 1F, 1F);
//        if(this.screen != null) this.screen.render(Mouse.getX(), Mouse.getY());
    }
}
