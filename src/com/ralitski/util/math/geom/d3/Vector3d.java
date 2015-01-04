package com.ralitski.util.math.geom.d3;

import com.ralitski.util.math.Matrix;

public class Vector3d implements Cloneable {
	
	private float x;
	private float y;
	private float z;

    private float magnitude;
    private boolean magnitudeDirty = true;
    
    public Vector3d(Matrix m) {
    	if(m.getColumns() == 1) {
    		x = m.getValue(1, 1);
    		y = m.getValue(2, 1);
    		z = m.getValue(3, 1);
    	} else {
    		throw new IllegalArgumentException("Vectors can only be constructed from vector matrices");
    	}
    }
    
    public Vector3d() {
    	this(0, 0, 0);
    }
    
    public Vector3d(Point3d terminal) {
    	this(terminal.getX(), terminal.getY(), terminal.getZ());
    }

	public Vector3d(Point3d initial, Point3d terminal) {
		this(terminal.getX() - initial.getX(),
				terminal.getY() - initial.getY(),
				terminal.getZ() - initial.getZ());
	}
	
	public Vector3d(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	//add, set, get
    
    public void addX(float x) {
    	this.setX(getX() + x);
    }

    public void setX(float x) {
        this.x = x;
    	magnitudeDirty = true;
    }

    public float getX() {
        return x;
    }
    
    public void addY(float y) {
    	this.setY(getY() + y);
    }

    public void setY(float y) {
        this.y = y;
    	magnitudeDirty = true;
    }

    public float getY() {
        return y;
    }
    
    public void addZ(float z) {
    	this.setZ(getZ() + z);
    }

    public void setZ(float z) {
        this.z = z;
    	magnitudeDirty = true;
    }

    public float getZ() {
        return z;
    }
    
    //angles, rotation
    
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
    
    public float getAngleAround(Vector3d axis) {
    	Vector3d v = clone();
    	Vector3d axisClone = axis.clone();
    	float angleX = axisClone.getAngleX();
    	v.rotateX(-angleX);
    	axisClone.rotateX(-angleX);
    	float angleY = axisClone.getAngleY() - (float)(Math.PI / 2F); //put it on Z axis
    	v.rotateY(-angleY);
    	return v.getAngleZ();
    }
    
    public void rotateAround(Vector3d axis, float angle) {
    	axis = axis.clone();
    	Vector3d v = clone();
    	float angleX = axis.getAngleX();
    	v.rotateX(-angleX);
    	axis.rotateX(-angleX);
    	float angleY = axis.getAngleY() - (float)(Math.PI / 2F);
    	v.rotateY(-angleY);
    	v.rotateZ(angle);
    	v.rotateY(angleY);
    	v.rotateZ(angleX);
    	x = v.x;
    	y = v.y;
    	z = v.z;
    }
    
    //misc
    
    public boolean isEmpty() {
    	return x == 0 || y == 0 || z == 0;
    }
    
    //TODO: test this
    public Vector3d project(Vector3d axis) {
    	axis = axis.clone();
    	Vector3d v = clone();
    	float angleX = axis.getAngleX();
    	v.rotateX(-angleX);
    	axis.rotateX(-angleX);
    	float angleY = axis.getAngleY() - (float)(Math.PI / 2F);
    	v.rotateY(-angleY);
    	v.z = 0;
    	v.rotateY(angleY);
    	v.rotateZ(angleX);
    	return v;
    }
    
    public Vector3d crossProduct(Vector3d other) {
    	float ox = other.x;
    	float oy = other.y;
    	float oz = other.z;
    	return new Vector3d(y * oz - z * oy, x * oz - z * ox, x * oy - y * ox);
    }
    
    public float dot(Vector3d other) {
    	return (x * other.x) + (y * other.y) + (z * other.z);
    }
    
    public void add(Vector3d other) {
    	x += other.x;
    	y += other.y;
    	z += other.z;
    	magnitudeDirty = true;
    }
    
    public void add(float magnitude) {
    	float oldMag = magnitude();
    	float newMag = oldMag + magnitude;
    	float mod = newMag / oldMag;
    	multiply(mod);
    }
    
    public void normalize() {
    	setMagnitude(1F);
    }
    
    public void setMagnitude(float m) {
    	multiply(m / magnitude());
    }
    
    public void multiply(float magnitude) {
    	x *= magnitude;
    	y *= magnitude;
    	z *= magnitude;
    	magnitudeDirty = true;
    }

	public void invert() {
		x = -x;
		y = -y;
		z = -z;
	}
    
    public float magnitude() {
    	recalcMagnitude();
    	return magnitude;
    }
    
    private void recalcMagnitude() {
    	if(magnitudeDirty) {
    		magnitude = (float)Math.sqrt(x * x + y * y + z * z);
    		magnitudeDirty = false;
    	}
    }
    
    /**
     * note: this method is not necessarily faster than magnitude() due to the use of a cache.
     * @return
     */
    public float magnitudeSquared() {
    	return x * x + y * y + z * z;
    }
    
    public float getAngleTo(Vector3d other) {
    	float dot = this.dot(other);
    	float div = this.magnitude() * other.magnitude();
    	if(div == 0) return 0;
    	float cosAngle = dot / div;
    	return (float)Math.acos(cosAngle);
    }
    
    public Matrix toMatrix(boolean extra) {
    	return extra ? new Matrix(new float[][]{
    			{x},
    			{y},
    			{z},
    			{1}
    	}) : new Matrix(new float[][]{
    			{x},
    			{y},
    			{z}
    	});
    }
    
    public boolean isAligned(Vector3d other) {
    	//do the alignment calculation first because it's faster
    	return (x > 0F ? other.x > 0F : (x < 0F ? other.x < 0F : (y > 0F ? other.y > 0F : (y < 0F ? other.y < 0F : (z > 0F ? other.z > 0F : (z < 0F ? other.z < 0F : other.z == 0F)))))) && isParallel(other);
    }
    
    public boolean isParallel(Vector3d other) {
    	float scalar = magnitude() / other.magnitude();
    	return x / scalar == other.x && y / scalar == other.y && z / scalar == other.z;
    }
    
    public boolean isOrthogonal(Vector3d other) {
    	return this.dot(other) == 0;
    }
    
    public boolean equals(Object o) {
    	if(this == o) return true;
    	if(o instanceof Vector3d) {
    		Vector3d v = (Vector3d)o;
    		return x == v.x && y == v.y && z == v.z;
    	}
    	return false;
    }
    
    public String toString() {
    	return "<" + x + ", " + y + ", " + z + ">";
    }
    
    public Vector3d clone() {
    	return new Vector3d(x, y, z);
    }
}
