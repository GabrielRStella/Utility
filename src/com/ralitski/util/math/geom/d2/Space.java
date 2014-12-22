package com.ralitski.util.math.geom.d2;

public enum Space {
	//quadrants
	I(0, 90),
	II(90, 180),
	III(180, 270),
	IV(270, 360),
	//axes
	X_PLUS(0, 0),
	X_MINUS(180, 180),
	Y_PLUS(90, 90),
	Y_MINUS(270, 270),
	//origin
	ORIGIN(0, 360) {
	};
	
	private float min;
	private float max;
	
	Space(float min, float max) {
		this.min = min;
		this.max = max;
	}
	
	public boolean contains(float degrees) {
		return (min == degrees && max == degrees) || (min < degrees && max > degrees);
	}

	public static Space fromRadians(float radians) {
		return fromDegrees((float)Math.toDegrees(radians));
	}

	public static Space fromDegrees(float degrees) {
		if(degrees < 0) {
			degrees = 360F - (-degrees % 360F);
		} else {
			degrees %= 360F;
		}
		for(Space q : values()) {
			//origin should come last and not interfere with other sections, but don't want to return
			if(q.contains(degrees) && q != ORIGIN) return q;
		}
		//shouldn't reach this point, but just in case
		return null;
	}
}
