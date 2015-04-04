package com.ralitski.util.math.geom.d2;

import com.ralitski.util.math.Matrix;

public class Vector2d implements Cloneable {
	
	public Vector2d getI() {
		return new Vector2d(1, 0);
	}
	
	public Vector2d getJ() {
		return new Vector2d(0, 1);
	}
	
	//obj
	
	private float x;
	private float y;

    private float magnitude;
    private boolean magnitudeDirty = true;
    
    public Vector2d(Matrix m) {
    	if(m.getColumns() == 1) {
    		x = m.getValue(1, 1);
    		y = m.getValue(2, 1);
    	} else {
    		throw new IllegalArgumentException("Vectors can only be constructed from vector matrices");
    	}
    }
    
    public Vector2d() {
    	this(0, 0);
    }
    
    public Vector2d(Point2d terminal) {
    	this(terminal.getX(), terminal.getY());
    }
    
    public Vector2d(Vector2d terminal) {
    	this(terminal.getX(), terminal.getY());
    }

	public Vector2d(Point2d initial, Point2d terminal) {
		this(terminal.getX() - initial.getX(),
				terminal.getY() - initial.getY());
	}
	
	public Vector2d(float x, float y) {
		this.x = x;
		this.y = y;
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
    
    //angles, rotation
    
    public float getAngle() {
    	return (float)Math.atan2(y, x);
    }
    
    public float getAngleDegrees() {
    	return (float)Math.toDegrees(getAngle());
    }
    
    public void rotateDegrees(float angle) {
    	rotate((float)Math.toRadians(angle));
    }
    
    public void rotate(float angle) {
    	float cos = (float)Math.cos(angle);
    	float sin = (float)Math.sin(angle);
    	float xPrime = cos * x - sin * y;
    	float yPrime = sin * x + cos * y;
    	x = xPrime;
    	y = yPrime;
    }
    
    //misc
    
    public boolean isEmpty() {
    	return x == 0 && y == 0;
    }
    
    public Vector2d crossProduct() {
    	return new Vector2d(y, -x);
    }
    
    public float dot(Vector2d other) {
    	return (x * other.x) + (y * other.y);
    }
    
    public void add(Vector2d other) {
    	x += other.x;
    	y += other.y;
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
    	float mag = magnitude();
    	if(mag != 0F) multiply(m / mag);
    	else {
    		x = m;
    		y = 0;
    	}
    }
    
    public void multiply(float magnitude) {
    	x *= magnitude;
    	y *= magnitude;
    	magnitudeDirty = true;
    }

	public void invert() {
		x = -x;
		y = -y;
	}
    
    public float magnitude() {
    	recalcMagnitude();
    	return magnitude;
    }
    
    private void recalcMagnitude() {
    	if(magnitudeDirty) {
    		magnitude = (float)Math.sqrt(x * x + y * y);
    		magnitudeDirty = false;
    	}
    }
    
    /**
     * note: this method is not necessarily faster than magnitude() due to the use of a cache.
     * @return
     */
    public float magnitudeSquared() {
    	return x * x + y * y;
    }
    
    public float getAngleTo(Vector2d other) {
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
    			{1}
    	}) : new Matrix(new float[][]{
    			{x},
    			{y}
    	});
    }
    
    public boolean isAligned(Vector2d other) {
    	//do the alignment calculation first because it's faster...maybe
    	//also can I get a whoop whoop for nested tertiary statements
    	return (x > 0F ? other.x > 0F : (x < 0F ? other.x < 0F : (y > 0F ? other.y > 0F : (y < 0F ? other.y < 0F : other.y == 0F)))) && isParallel(other);
    }
    
    public boolean isParallel(Vector2d other) {
    	float xx = x / other.x;
    	float yy = y / other.y;
    	if(x == 0F) {
    		if(y == 0F) {
    			return other.x == 0F && other.y == 0F;
    		} else return other.x == 0;
    	} else if(y == 0F) {
    		return other.y == 0F;
    	} else return xx == yy;
    }
    
    public boolean isOrthogonal(Vector2d other) {
    	return this.dot(other) == 0;
    }
    
    public boolean equals(Object o) {
    	if(this == o) return true;
    	if(o instanceof Vector2d) {
    		Vector2d v = (Vector2d)o;
    		return x == v.x && y == v.y;
    	}
    	return false;
    }
    
    public String toString() {
    	return "<" + x + ", " + y + ">";
    }
    
    public Vector2d clone() {
    	return new Vector2d(x, y);
    }
    
    public Vector2d addCopy(Vector2d v) {
    	return new Vector2d(x + v.x, y + v.y);
    }
    
    public Vector2d subtractCopy(Vector2d v) {
    	return new Vector2d(x - v.x, y - v.y);
    }
    
    public Vector2d scaleCopy(float f) {
    	return new Vector2d(x * f, y * f);
    }
    
    public Vector2d scaleCopy(Vector2d v) {
    	return new Vector2d(x * v.x, y * v.y);
    }
    
    public Vector2d descaleCopy(float f) {
    	return new Vector2d(x / f, y / f);
    }
    
    public Vector2d descaleCopy(Vector2d v) {
    	return new Vector2d(x / v.x, y / v.y);
    }
    
    public Vector2d negateCopy() {
    	return new Vector2d(-x, -y);
    }
    
    public boolean isNaN() {
    	return Float.isNaN(x) || Float.isNaN(y);
    }
    
    public float[] toArray() {
    	return new float[]{x, y};
    }
}
