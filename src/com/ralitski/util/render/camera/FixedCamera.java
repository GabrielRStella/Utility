package com.ralitski.util.render.camera;

import com.ralitski.util.math.geom.d3.Orientation3d;
import com.ralitski.util.math.geom.d3.Point3d;

public class FixedCamera implements Camera {
	
	private Point3d pos;
	private Orientation3d o;
	
	public FixedCamera(float x, float y, float z, float yaw, float pitch) {
		this(new Point3d(x, y, z), new Orientation3d(yaw, pitch));
	}
	
	public FixedCamera(float x, float y, float z, float yaw, float pitch, float roll) {
		this(new Point3d(x, y, z), new Orientation3d(yaw, pitch, roll));
	}
	
	public FixedCamera(Point3d pos, float yaw, float pitch) {
		this(pos, new Orientation3d(yaw, pitch));
	}
	
	public FixedCamera(float x, float y, float z, Orientation3d o) {
		this(new Point3d(x, y, z), o);
	}
	
	public FixedCamera(Point3d pos, float yaw, float pitch, float roll) {
		this(pos, new Orientation3d(yaw, pitch, roll));
	}
	
	public FixedCamera(Point3d pos, Orientation3d o) {
		this.pos = pos;
		this.o = o;
	}

	@Override
	public Point3d getPosition() {
		return pos;
	}

	@Override
	public Orientation3d getOrientation() {
		return o;
	}

}
