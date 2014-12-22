package com.ralitski.util.math.geom;

import com.ralitski.util.doc.TODO;

public class Interval implements Comparable<Interval> {

	public static final Interval ALL = new Interval(Float.NEGATIVE_INFINITY, false, Float.POSITIVE_INFINITY, false);
	public static final Interval NONE = new Interval(0, false, 0, false);
	public static final Interval RADIANS = new Interval(0, true, Geometry.TAU, false);
	public static final Interval DEGREES = new Interval(0, true, 360F, false);
	public static final Interval POSITIVE = new Interval(0, false, Float.POSITIVE_INFINITY, false);
	public static final Interval POSITIVE_OR_ZERO = new Interval(0, true, Float.POSITIVE_INFINITY, false);
	public static final Interval NEGATIVE = new Interval(Float.NEGATIVE_INFINITY, false, 0, false);
	public static final Interval NEGATIVE_OR_ZERO = new Interval(Float.NEGATIVE_INFINITY, false, 0, true);
	
	public static Interval of(float num) {
		return new Interval(num, true, num, true);
	}
	
	private float min;
	private float max;
	private boolean includeMin;
	private boolean includeMax;
	
	public Interval(float min, boolean includeMin, float max, boolean includeMax) {
		this.min = min;
		this.max = max;
		this.includeMin = includeMin;
		this.includeMax = includeMax;
	}
	
	public float getMin() {
		return min;
	}
	
	public float getMax() {
		return max;
	}
	
	public boolean includeMin() {
		return includeMin;
	}
	
	public boolean includeMax() {
		return includeMax;
	}
	
	public boolean includes(float f) {
		return (f > min || (f == min && includeMin)) && (f < max || (f == max && includeMax));
	}
	
	public Interval shift(float shift) {
		return new Interval(min + shift, includeMin, max + shift, includeMax);
	}
	
	public Interval[] or(Interval other) {
		if(touching(other)) {
			float min;
			float max;
			boolean includeMin;
			boolean includeMax;
			if(this.min < other.min) {
				min = this.min;
				includeMin = this.includeMin;
			} else if(this.min > other.min){
				min = other.min;
				includeMin = other.includeMin;
			} else {
				min = this.min;
				includeMin = this.includeMin || other.includeMin;
			}
			if(this.max > other.max) {
				max = this.max;
				includeMax = this.includeMax;
			} else if(this.max < other.max) {
				max = other.max;
				includeMax = other.includeMax;
			} else {
				max = this.max;
				includeMax = this.includeMax || other.includeMax;
			}
			return new Interval[]{new Interval(min, includeMin, max, includeMax)};
		} else {
			//order is used by IntervalCompound
			return new Interval[]{this, other};
		}
	}
	
	public Interval and(Interval other) {
		if(overlapping(other)) {
			float min;
			float max;
			if(this.min < other.min) {
				min = other.min;
			} else {
				min = this.min;
			}
			if(this.max > other.max) {
				max = other.max;
			} else {
				max = this.max;
			}
			return new Interval(min, true, max, true);
		} else {
			return NONE;
		}
	}
	
	public Interval[] not(Interval other) {
		if(overlapping(other)) {
			float oMin = other.min;
			float oMax = other.max;
			if(oMin < min || (oMin == min && includeMin && other.includeMin)) {
				//just one interval returned
				if(oMax > max || (oMax == max && includeMax && other.includeMax)) {
					return new Interval[]{NONE};
				} else {
					return new Interval[]{new Interval(oMax, !other.includeMax, max, includeMax)};
				}
			} else {
				//possible more than one interval returned
				if(oMax > max || (oMax == max && includeMax && other.includeMax)) {
					return new Interval[]{new Interval(min, includeMin, oMin, !other.includeMin)};
				} else {
					//two intervals returned
					return new Interval[]{
							//min
							new Interval(min, includeMin, oMin, !other.includeMin),
							//max
							new Interval(oMax, !other.includeMax, max, includeMax)
					};
				}
			}
		} else {
			return new Interval[]{this};
		}
	}
	
	public boolean overlapping(Interval other) {
		float oMin = other.min;
		float oMax = other.max;
		boolean isMinInside = (oMin > min || (oMin == min && includeMin && other.includeMin)) && (oMin < max || (oMin == max && includeMax && other.includeMin));
		boolean isMaxInside = (oMax > max || (oMax == max && includeMax && other.includeMax)) && (oMax < max || (oMax == max && includeMax && other.includeMax));
		return  isMinInside || isMaxInside || (oMin > min && oMax < max) || (oMin < min && oMax > max);
	}
	
	public boolean touching(Interval other) {
		float oMin = other.getMin();
		float oMax = other.getMax();
		return (min > oMin && max < oMax) || nextTo(oMin, other) || nextTo(oMax, other);
	}
	
	private boolean nextTo(float f, Interval other) {
		return (f >= min && (includeMin || other.includeMax)) && (f <= max && (includeMax || other.includeMin));
	}
	
	public String toString() {
		String left = includeMin ? "[" : "(";
		String right = includeMax ? "]" : ")";
		return min == max ? left + min + right : left + min + "," + max + right;
	}
	
	public boolean equals(Object o) {
		if(o == this) {
			return true;
		} else if(o instanceof Interval) {
			Interval i = (Interval)o;
			return i.min == min && i.includeMin == includeMin && i.max == max && i.includeMax == includeMax;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Interval i) {
		return touching(i) ? (min >= i.min ? (max <= i.max ? 0 : 1) : (max <= i.max ? -1 : 0)) : (min >= i.min ? 1 : -1);
	}
}
