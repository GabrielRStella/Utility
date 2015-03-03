package com.ralitski.util.math.geom.n;

import java.util.Arrays;

public class Point extends SimpleDimensionalSet implements Cloneable {
	
	public Point(float...data) {
		super(data);
	}
	
	public float distanceSquared(Point other) {
		float sum = 0;
		for(int i = 0; i < data.length || i < other.data.length; i++) {
			float f = i < data.length ? data[i] : 0;
			float f2 = i < other.data.length ? other.data[i] : 0;
			f -= f2;
			sum += (f * f);
		}
		return sum;
	}
	
	public float distance(Point other) {
		return (float)Math.sqrt(distanceSquared(other));
	}
    
    @Override
    public String toString() {
        String inside = Arrays.toString(data);
        inside = inside.substring(1, inside.length() - 1);
        return "(" + inside + ")";
    }
    
    public Point clone() {
    	return new Point(data.clone());
    }

}
