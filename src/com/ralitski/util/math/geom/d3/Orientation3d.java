package com.ralitski.util.math.geom.d3;

public class Orientation3d {
	private float yaw;
	private float pitch;
	private float roll;
	
	public Orientation3d() {
		this(0, 0);
	}
	
	public Orientation3d(float yaw, float pitch) {
		this(yaw, pitch, 0);
	}
	
	public Orientation3d(Vector3d v) {
		this(v, 0);
	}
	
	public Orientation3d(Vector3d v, float roll) {
		this(v.getAngleY(), v.getAngleAround(new Vector3d(0, 1, 0).crossProduct(v)), roll);
	}
	
	public Orientation3d(float yaw, float pitch, float roll) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
	}
	
	public Vector3d getAxis() {
		Vector3d v = new Vector3d();
		v.rotateX(pitch);
		v.rotateY(yaw);
		return v;
	}
	
	public Vector3d getHorizon() {
		Vector3d v = new Vector3d(1, 0, 0);
		v.rotateY(yaw);
		return v;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public void rotateYaw(float angle) {
		yaw += angle;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public void rotatePitch(float angle) {
		pitch += angle;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getRoll() {
		return roll;
	}
	
	public void rotateRoll(float angle) {
		roll += angle;
	}
	
	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	public Vector3d getRollVector() {
		Vector3d v = getHorizon();
		v.rotateAround(getAxis(), roll);
		return v;
	}
	
	//vector stuff
    
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
