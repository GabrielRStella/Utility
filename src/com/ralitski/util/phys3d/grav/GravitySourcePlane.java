package com.ralitski.util.phys3d.grav;

import com.ralitski.util.math.geom.d3.Plane;
import com.ralitski.util.math.geom.d3.Point3d;

public class GravitySourcePlane extends GravitySourceAbstract {
	
	private Plane plane;
	
	public GravitySourcePlane(Plane plane) {
		this.plane = plane;
	}

	@Override
	public Point3d getClosestPointTo(Point3d point) {
		return plane.getClosestPointTo(point);
	}

}
