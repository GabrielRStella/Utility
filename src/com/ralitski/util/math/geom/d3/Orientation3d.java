package com.ralitski.util.math.geom.d3;

public class Orientation3d {
	private Vector3d axis;
	private float roll;
	
	public Orientation3d() {
		this(new Vector3d(1, 0, 0));
	}
	
	public Orientation3d(Vector3d axis) {
		this(axis, 0);
	}
	
	public Orientation3d(Vector3d axis, float roll) {
		this.axis = axis;
		this.roll = roll;
	}
	
	public Vector3d getAxis() {
		return axis;
	}
	
	public Vector3d getHorizon() {
		return new Vector3d(0, 1, 0).crossProduct(axis);
	}
	
	public float getYaw() {
		return axis.getAngleY();
	}
	
	public void rotateYaw(float angle) {
		axis.rotateY(angle);
	}
	
	public float getPitch() {
		return axis.getAngleAround(getHorizon());
	}
	
	public void rotatePitch(float angle) {
		axis.rotateAround(getHorizon(), angle);
	}

	public float getRoll() {
		return roll;
	}
	
	public void rotateRoll(float angle) {
		roll += angle;
	}
	
	public Vector3d getRollVector() {
		Vector3d v = getHorizon();
		v.rotateAround(axis, roll);
		return v;
	}
	
	//vector stuff (TODO)
    
//    public float getAngleX() {
//    	return axis.getAngleX();
//    }
//    
//    public float getAngleXDegrees() {
//    	return (float)Math.toDegrees(getAngleX());
//    }
//    
//    public float getAngleY() {
//    	return axis.getAngleY();
//    }
//    
//    public float getAngleYDegrees() {
//    	return (float)Math.toDegrees(getAngleY());
//    }
//    
//    public float getAngleZ() {
//    	return axis.getAngleZ();
//    }
//    
//    public float getAngleZDegrees() {
//    	return (float)Math.toDegrees(getAngleZ());
//    }
//    
//    public void rotateXDegrees(float angle) {
//    	rotateX((float)Math.toRadians(angle));
//    }
//    
//    public void rotateX(float angle) {
//    	axis.rotateX(angle);
//    	roll.rotateX(angle);
//    }
//    
//    public void rotateYDegrees(float angle) {
//    	rotateY((float)Math.toRadians(angle));
//    }
//    
//    public void rotateY(float angle) {
//    	axis.rotateY(angle);
//    	roll.rotateY(angle);
//    }
//    
//    public void rotateZDegrees(float angle) {
//    	rotateZ((float)Math.toRadians(angle));
//    }
//    
//    public void rotateZ(float angle) {
//    	axis.rotateZ(angle);
//    	roll.rotateZ(angle);
//    }
//    
//    public float getAngleAround(Vector3d axis) {
//    	return this.axis.getAngleAround(axis);
//    }
//    
//    public void rotateAround(Vector3d axis, float angle) {
//    	this.axis.rotateAround(axis, angle);
//    	roll.rotateAround(axis, angle);
//    }
}
