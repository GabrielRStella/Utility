package com.ralitski.util.phys3d.grav;

import com.ralitski.util.math.geom.d3.Point3d;
import com.ralitski.util.math.geom.d3.Vector3d;

public abstract class GravitySourceAbstract implements GravitySource {

	@Override
	public Vector3d attract(Point3d point) {
		return new Vector3d(point, getClosestPointTo(point));
	}

}
