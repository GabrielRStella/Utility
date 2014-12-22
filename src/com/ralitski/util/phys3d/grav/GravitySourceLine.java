package com.ralitski.util.phys3d.grav;

import com.ralitski.util.math.geom.d3.Line3d;
import com.ralitski.util.math.geom.d3.Point3d;

public class GravitySourceLine extends GravitySourceAbstract {
	
	private Line3d line;
	
	public GravitySourceLine(Line3d line) {
		this.line = line;
	}

	@Override
	public Point3d getClosestPointTo(Point3d point) {
		return line.getClosestPointTo(point);
	}

}
