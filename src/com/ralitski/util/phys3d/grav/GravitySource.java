package com.ralitski.util.phys3d.grav;

import com.ralitski.util.math.geom.d3.Point3d;
import com.ralitski.util.math.geom.d3.Vector3d;

public interface GravitySource {
	/**
	 * returns a vector of arbitrary scale pointing in the direction of acceleration for this gravity source.
	 * @param point the point to accelerate
	 * @return a vector pointing towards this gravity source, representing acceleration
	 */
	Vector3d attract(Point3d point);
	
	/**
	 * gets the closest point to the gravity source, for orientation purposes
	 * @param point the point to be checked
	 * @return the point closest to the given point
	 */
	Point3d getClosestPointTo(Point3d point);
}
