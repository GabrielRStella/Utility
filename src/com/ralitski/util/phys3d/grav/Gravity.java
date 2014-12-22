package com.ralitski.util.phys3d.grav;

import com.ralitski.util.math.geom.d3.Point3d;
import com.ralitski.util.math.geom.d3.Vector3d;

public class Gravity implements GravitySource {
	
	private GravitySource source;
	private float strength;
	
	public Gravity(GravitySource source, float strength) {
		this.source = source;
		this.strength = strength;
	}

	@Override
	public Vector3d attract(Point3d point) {
		Vector3d v = source.attract(point);
		v.setMagnitude(strength);
		return v;
	}

	@Override
	public Point3d getClosestPointTo(Point3d point) {
		return source.getClosestPointTo(point);
	}
}
