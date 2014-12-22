package com.ralitski.util.phys3d.grav;

import com.ralitski.util.math.geom.d3.Point3d;

public class GravitySourcePoint extends GravitySourceAbstract {
	
	private Point3d center;
	
	public GravitySourcePoint(Point3d source) {
		this.center = source;
	}

	@Override
	public Point3d getClosestPointTo(Point3d point) {
		return center;
	}

}
