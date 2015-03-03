package com.ralitski.util.render;

import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import com.ralitski.util.math.geom.Interval;
import com.ralitski.util.math.geom.d3.Line3d;
import com.ralitski.util.math.geom.d3.Point3d;
import com.ralitski.util.math.geom.d3.Vector3d;
import com.ralitski.util.render.img.Color;

public class GLRenderHelper {

	public static final float LINE_MIN = -10000;
	public static final float LINE_MAX = 10000;

	public static final int LINE_WIDTH_THIN = 2;
	public static final int LINE_WIDTH = 4;
	public static final int LINE_WIDTH_WIDE = 6;
	
	public static void render(Iterable<Point3d> points) {
		render(points.iterator());
	}
	
	public static void render(Iterator<Point3d> points) {
		if(points.hasNext()) {
			Point3d start = points.next();
			while(points.hasNext()) {
				Point3d next = points.next();
				renderLine(start, next);
				start = next;
			}
		}
	}
	
	public static void renderLoop(Iterable<Point3d> points) {
		renderLoop(points.iterator());
	}
	
	public static void renderLoop(Iterator<Point3d> points) {
		if(points.hasNext()) {
			Point3d start = points.next();
			Point3d prev = start;
			while(points.hasNext()) {
				Point3d next = points.next();
				renderLine(prev, next);
				prev = next;
			}
			renderLine(prev, start);
		}
	}
	
	public static void renderLine(Line3d line) {
		Interval i = line.getInterval();
		float start = i.getMin() == Float.NEGATIVE_INFINITY ? LINE_MIN : Math.max(LINE_MIN, i.getMin());
		float end = i.getMax() == Float.POSITIVE_INFINITY ? LINE_MAX : Math.min(LINE_MAX, i.getMax());
		renderLine(line.getPointFromT(start), line.getPointFromT(end));
	}
	
	public static void renderLine(Point3d start, Point3d end) {
		GL11.glBegin(GL11.GL_LINES);
		bindPoint(start);
		bindPoint(end);
		GL11.glEnd();
	}
	
	public static void bindPoint(Point3d p) {
		GL11.glVertex3f(p.getX(), p.getY(), p.getZ());
	}
	
	private static Line3d x = new Line3d(new Vector3d(1, 0, 0));
	private static Line3d y = new Line3d(new Vector3d(0, 1, 0));
	private static Line3d z = new Line3d(new Vector3d(0, 0, 1));
	
	public static void renderAxes() {
		renderAxes(Color.RED, Color.GREEN, Color.BLUE);
	}
	
	public static void renderAxes(Color color) {
		renderAxes(color, color, color);
	}
	
	public static void renderAxes(Color xColor, Color yColor, Color zColor) {
		GL11.glPushMatrix();
		xColor.glColor();
		renderLine(x);
		yColor.glColor();
		renderLine(y);
		zColor.glColor();
		renderLine(z);
		GL11.glPopMatrix();
	}

}
