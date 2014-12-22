package com.ralitski.util.phys3d;

import com.ralitski.util.math.geom.d3.Point3d;
import com.ralitski.util.math.geom.d3.Vector3d;

public class MovingObject {
	
	private Point3d point;
	private Vector3d velocity;
	
	public MovingObject() {
		this(Point3d.origin());
	}
	
	public MovingObject(Point3d point) {
		this(point, new Vector3d());
	}
	
	public MovingObject(Point3d point, Vector3d velocity) {
		this.point = point;
		this.velocity = velocity;
	}
	
	public Point3d getPosition() {
		return point;
	}
	
	public Vector3d getVelocity() {
		return velocity;
	}
	
	public void move() {
		point.translate(velocity);
	}
}
