package com.ralitski.util.math.geom;

/**
 * Various utilities.
 * 
 * @author ralitski
 */
public class Geometry {
	
	public static final float PI = (float)Math.PI;
	public static final float HALF_PI = PI/2F;
	public static final float THREE_HALVES_PI = 3F*HALF_PI;
	public static final float TAU = 2F*PI;
	
	public static float getReferenceAngle(float angle) {
		if(angle < 0) {
			angle = 360F - (-angle % 360F);
		} else {
			angle %= 360F;
		}
		if(angle > 270) {
			return 360 - angle;
		} else if(angle > 180) {
			return angle - 180;
		} else if(angle > 90) {
			return 180 - angle;
		} else {
			return angle;
		}
	}
	
	public static float getReferenceAngleRadians(float angle) {
		if(angle < 0) {
			angle = TAU - (-angle % TAU);
		} else {
			angle %= TAU;
		}
		if(angle > THREE_HALVES_PI) {
			return TAU - angle;
		} else if(angle > PI) {
			return angle - PI;
		} else if(angle > HALF_PI) {
			return PI - angle;
		} else {
			return angle;
		}
	}
}
