package com.ralitski.util.math.geom.d3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.ralitski.util.io.Streamable;
import com.ralitski.util.math.geom.n.Point;

public class Point3d implements Cloneable, Streamable {

	public static Point3d midpoint(Point3d p1, Point3d p2) {
		return new Point3d((p1.x + p2.x) / 2F, (p1.y + p2.y) / 2F, (p1.z + p2.z) / 2F);
	}
	
	public static Point3d origin() {
		return ORIGIN.clone();
	}
	
	public static Point3d ORIGIN = new Point3d() {
	    
	    //add, get, set
	    
	    public void addX(float x) {
	    	throw new UnsupportedOperationException("Origin can't be edited");
	    }

	    public void setX(float x) {
	    	throw new UnsupportedOperationException("Origin can't be edited");
	    }

	    public float getX() {
	        return 0;
	    }
	    
	    public void addY(float y) {
	    	throw new UnsupportedOperationException("Origin can't be edited");
	    }

	    public void setY(float y) {
	    	throw new UnsupportedOperationException("Origin can't be edited");
	    }

	    public float getY() {
	        return 0;
	    }
	    
	    public void addZ(float z) {
	    	throw new UnsupportedOperationException("Origin can't be edited");
	    }

	    public void setZ(float z) {
	    	throw new UnsupportedOperationException("Origin can't be edited");
	    }

	    public float getZ() {
	        return 0;
	    }
	    
	    //translation
	    
	    public void translate(Point3d p) {
	    }
	    
	    public void translate(float x, float y, float z) {
	    }
	    
	    //length
	    
	    public float length() {
	    	return 0;
	    }
	    
	    public float lengthSquared() {
	    	return  0;
	    }
	    
	    public float length(Point3d center) {
	    	return center.length();
	    }
	    
	    public float lengthSquared(Point3d center) {
	    	return center.lengthSquared();
	    }
	    
	    public float length(float ox, float oy, float oz) {
	    	return (float)Math.sqrt(lengthSquared(ox, oy, oz));
	    }
	    
	    public float lengthSquared(float ox, float oy, float oz) {
	    	return ox * ox + oy * oy + oz * oz;
	    }
	    
	    //misc
	    
	    public Point3d negative() {
	    	return new Point3d(0, 0, 0);
	    }
	};
    
    private float x;
    private float y;
    private float z;
    
    public Point3d() {}
    
    public Point3d(Point point) {
    	this(point.get(1), point.get(2), point.get(3));
    }
    
    public Point3d(Vector3d v) {
    	this(v.getX(), v.getY(), v.getZ());
    }
    
    public Point3d(Point3d p) {
    	this(p.getX(), p.getY(), p.getZ());
    }
    
    public Point3d(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    //add, get, set
    
    public void addX(float x) {
    	this.setX(getX() + x);
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }
    
    public void addY(float y) {
    	this.setY(getY() + y);
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }
    
    public void addZ(float z) {
    	this.setZ(getZ() + z);
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getZ() {
        return z;
    }
    
    //translation, scaling
    
    public void translate(Point3d p) {
    	translate(p.getX(), p.getY(), p.getZ());
    }
    
    public void translate(Vector3d v) {
    	translate(v.getX(), v.getY(), v.getZ());
    }
    
    public void translate(float x, float y, float z) {
    	this.addX(x);
    	this.addY(y);
    	this.addZ(z);
    }
    
    public void multiply(float scale) {
    	setX(getX() * scale);
    	setY(getY() * scale);
    	setZ(getZ() * scale);
    }
    
    //length
    
    public float length() {
    	return (float)Math.sqrt(lengthSquared());
    }
    
    public float lengthSquared() {
    	float x = getX();
    	float y = getY();
    	float z = getZ();
    	return x * x + y * y + z * z;
    }
    
    public float length(Point3d center) {
    	return (float)Math.sqrt(lengthSquared(center));
    }
    
    public float lengthSquared(Point3d center) {
    	return lengthSquared(center.getX(), center.getY(), center.getZ());
    }
    
    public float length(float ox, float oy, float oz) {
    	return (float)Math.sqrt(lengthSquared(ox, oy, oz));
    }
    
    public float lengthSquared(float ox, float oy, float oz) {
    	float x = this.getX() - ox;
    	float y = this.getY() - oy;
    	float z = this.getZ() - oz;
    	return x * x + y * y + z * z;
    }
    
    public void setLength(float length) {
    	float l = length();
    	if(l == 0) throw new IllegalStateException("Cannot rescale the origin");
    	multiply(length / l);
    }
    
    public void setLength(Point3d center, float length) {
    	translate(-center.getX(), -center.getY(), -center.getZ());
    	setLength(length);
    	translate(center);
    }
    
    //rotate
    
    public float getAngleX() {
    	return (float)Math.atan2(y, -z);
    }
    
    public float getAngleXDegrees() {
    	return (float)Math.toDegrees(getAngleX());
    }
    
    public float getAngleY() {
    	return (float)Math.atan2(-z, x);
    }
    
    public float getAngleYDegrees() {
    	return (float)Math.toDegrees(getAngleY());
    }
    
    public float getAngleZ() {
    	return (float)Math.atan2(y, x);
    }
    
    public float getAngleZDegrees() {
    	return (float)Math.toDegrees(getAngleZ());
    }
    
    public void rotateXDegrees(float angle) {
    	rotateX((float)Math.toRadians(angle));
    }
    
    public void rotateX(float angle) {
    	float cos = (float)Math.cos(angle);
    	float sin = (float)Math.sin(angle);
    	float yPrime = cos * y - sin * z;
    	float zPrime = sin * y + cos * z;
    	y = yPrime;
    	z = zPrime;
    }
    
    public void rotateYDegrees(float angle) {
    	rotateY((float)Math.toRadians(angle));
    }
    
    public void rotateY(float angle) {
    	float cos = (float)Math.cos(angle);
    	float sin = (float)Math.sin(angle);
    	float xPrime = cos * x + sin * z;
    	float zPrime = cos * z - sin * x;
    	x = xPrime;
    	z = zPrime;
    }
    
    public void rotateZDegrees(float angle) {
    	rotateZ((float)Math.toRadians(angle));
    }
    
    public void rotateZ(float angle) {
    	float cos = (float)Math.cos(angle);
    	float sin = (float)Math.sin(angle);
    	float xPrime = cos * x - sin * y;
    	float yPrime = sin * x + cos * y;
    	x = xPrime;
    	y = yPrime;
    }
    
    public float getAngleAround(Line3d axis) {
    	return new Vector3d(axis.getClosestPointTo(this), this).getAngleAround(axis.getSlope());
    }
    
    public void rotateAround(Line3d axis, float angle) {
    	Point3d p = axis.getClosestPointTo(this);
    	Vector3d v = new Vector3d(p, this);
    	v.rotateAround(axis.getSlope(), angle);
    	p.translate(v);
    	setX(p.getX());
    	setY(p.getY());
    	setZ(p.getZ());
    }
    
    //misc
    
    public Surface3d getAsSurface(Vector3d orthogonalVector) {
    	return new SurfacePoint3d(this, new Plane(this, orthogonalVector));
    }
    
    public Point3d clone() {
    	return new Point3d(getX(), getY(), getZ());
    }
    
    public Point3d negative() {
    	return new Point3d(-getX(), -getY(), -getZ());
    }
    
    public String toString() {
    	return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o instanceof Point3d) {
            Point3d p = (Point3d)o;
            return p.getX() == getX() && p.getY() == getY() && p.getZ() == getZ();
        }
        return false;
    }

//    @Override
//    public int hashCode() {
//    	//uses the lowest bits of each part
//        return (Float.floatToIntBits(this.getX()) << 16) + (Float.floatToIntBits(this.getY()) & 65535);
//    }

	@Override
	public void write(DataOutputStream out) throws IOException {
		out.writeFloat(getX());
		out.writeFloat(getY());
		out.writeFloat(getZ());
	}

	@Override
	public void read(DataInputStream in) throws IOException {
		setX(in.readFloat());
		setY(in.readFloat());
		setZ(in.readFloat());
	}
	
	private static class SurfacePoint3d extends Point3d implements Surface3d {
		
		private Plane plane;
		
		SurfacePoint3d(Point3d p, Plane plane) {
			super(p);
			this.plane = plane;
		}

		@Override
		public Line3d getIntersection(Line3d line) {
			return line.contains(this) ? new Line3d(this) : null;
		}

		@Override
		public boolean contains(Point3d point) {
			return equals(point);
		}

		@Override
		public Point3d getClosestPointTo(Point3d point) {
			return point;
		}

		//return intersection.isPoint() ? (contains(line.getBase()) ? intersection : null) : getIntersectionInternal(intersection);
		@Override
		public Surface3d project(Plane plane, Vector3d direction) {
			Line3d line = plane.getIntersection(new Line3d(this, direction));
			return line.isPoint() ? new SurfacePoint3d(line.getBase(), plane) : null;
		}

		@Override
		public Surface3d crossSection(Plane plane) {
			return plane.contains(this) ? this : null;
		}

		@Override
		public Plane getPlane() {
			return plane;
		}
		
	}
}
