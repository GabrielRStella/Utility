package com.ralitski.util.phys3d.grav;

import com.ralitski.util.math.geom.d3.Point3d;
import com.ralitski.util.math.geom.d3.Vector3d;

public class GravitySourceMulti implements GravitySource {
	
	private GravitySource[] sources;
	
	public GravitySourceMulti(GravitySource...sources) {
		this.sources = sources;
	}

	@Override
	public Vector3d attract(Point3d point) {
		Vector3d v = new Vector3d();
		for(GravitySource source : sources) {
			Vector3d add = source.attract(point);
			add.multiply(1F/add.magnitudeSquared());
			v.add(add);
		}
		return v;
	}

	@Override
	public Point3d getClosestPointTo(Point3d point) {
		Point3d closest = null;
		float min = 0;
		for(GravitySource source : sources) {
			Point3d p = source.getClosestPointTo(point);
			float f = p.lengthSquared(point);
			if(closest == null || min > f) {
				closest = p;
				min = f;
			}
		}
		return closest;
	}
	
	
}
