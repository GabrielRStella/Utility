package tempgeom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ralitski.util.ArrayUtils;
import com.ralitski.util.doc.TODO;

public class IntervalCompound {

	public static final IntervalCompound ALL = new IntervalCompound(Interval.ALL);
	public static final IntervalCompound NONE = new IntervalCompound(Interval.NONE);
	public static final IntervalCompound RADIANS = new IntervalCompound(Interval.RADIANS);
	public static final IntervalCompound DEGREES = new IntervalCompound(Interval.DEGREES);
	public static final IntervalCompound POSITIVE = new IntervalCompound(Interval.POSITIVE);
	public static final IntervalCompound POSITIVE_OR_ZERO = new IntervalCompound(Interval.POSITIVE_OR_ZERO);
	public static final IntervalCompound NEGATIVE = new IntervalCompound(Interval.NEGATIVE);
	public static final IntervalCompound NEGATIVE_OR_ZERO = new IntervalCompound(Interval.NEGATIVE_OR_ZERO);
	
	private Interval[] intervals;
	private Interval min;
	private Interval max;

	public IntervalCompound(Interval...intervals) {
		this.intervals = intervals;
		float min = Float.POSITIVE_INFINITY;
		float max = Float.NEGATIVE_INFINITY;
		for(Interval i : intervals) {
			if(i.getMin() < min) {
				this.min = i;
				min = i.getMin();
			} else if(i.getMax() > max) {
				this.max = i;
				max = i.getMax();
			}
		}
	}
	
	public Interval[] get() {
		return intervals;
	}
	
	public float getMin() {
		return min.getMin();
	}
	
	public float getMax() {
		return max.getMax();
	}
	
	public boolean includeMin() {
		return min.includeMin();
	}
	
	public boolean includeMax() {
		return max.includeMax();
	}
	
	public boolean includes(float f) {
		for(Interval i : intervals) if(i.includes(f)) return true;
		return false;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < intervals.length; i++) {
			if(i > 0) s.append("U");
			s.append(intervals[i].toString());
		}
		return s.toString();
	}
	
	//shift
	
	public IntervalCompound shift(float shift) {
		Interval[] intervals = new Interval[this.intervals.length];
		for(int i = 0; i < intervals.length; i++) {
			intervals[i] = this.intervals[i].shift(shift);
		}
		return new IntervalCompound(intervals);
	}
	
	//and
	
	public IntervalCompound and(IntervalCompound i) {
		return and(i.intervals);
	}
	
	public IntervalCompound and(Interval[] i) {
		IntervalCompound temp = this; //so meta
		for(Interval i2 : i) {
			temp = temp.and(i2);
		}
		return temp;
	}
	
	public IntervalCompound and(Interval i) {
		Interval[] intervals = new Interval[this.intervals.length];
		for(int j = 0; j < intervals.length; j++) {
			intervals[j] = this.intervals[j].and(i);
		}
		return new IntervalCompound(intervals);
	}
	
	//or
	
	public IntervalCompound or(IntervalCompound i) {
		return or(i.intervals);
	}
	
	public IntervalCompound or(Interval[] i) {
		IntervalCompound temp = this; //so meta
		for(Interval i2 : i) {
			temp = temp.or(i2);
		}
		return temp;
	}
	
	@TODO
	public IntervalCompound or(Interval i) {
		List<Interval> intervals = new ArrayList<Interval>(this.intervals.length);
		for(int j = 0; j < this.intervals.length; j++) {
			intervals.addAll(Arrays.asList(this.intervals[j].or(i)));
		}
		return new IntervalCompound(intervals.toArray(new Interval[intervals.size()]));
	}
	
	//not
	
	public IntervalCompound not(IntervalCompound i) {
		return not(i.intervals);
	}
	
	public IntervalCompound not(Interval[] i) {
		IntervalCompound temp = this; //so meta
		for(Interval i2 : i) {
			temp = temp.not(i2);
		}
		return temp;
	}

	@TODO
	public IntervalCompound not(Interval i) {
		List<Interval> intervals = new ArrayList<Interval>(this.intervals.length);
		for(int j = 0; j < this.intervals.length; j++) {
			intervals.addAll(Arrays.asList(this.intervals[j].not(i)));
		}
		return new IntervalCompound(intervals.toArray(new Interval[intervals.size()]));
	}
	
	//
	
	/*
	 * [-3.0,2.0)U(-5.0,-2.0)U[4.0,6.0]
	 * (-5.0,2.0)U(-5.0,-2.0)
	 */
	@TODO("fix this")
	public IntervalCompound simplify() {
		Interval[] result = intervals.clone();
		for(int i = 0; i < result.length; i++) {
			for(int j = i + 1; j < result.length; j++) {
				Interval i1 = result[i];
				Interval i2 = result[j];
				if(i1 == null || i2 == null) continue;
				Interval[] in = i1.or(i2);
				result[i] = in[0];
				result[j] = (in.length > 1) ? in[1] : null;
			}
		}
		return new IntervalCompound(ArrayUtils.eliminateEquivalentAndNull(Interval.class, result));
	}
	
	public IntervalCompound sort() {
		Interval[] intervals = this.intervals.clone();
		Arrays.sort(intervals);
		return new IntervalCompound(intervals);
	}

}
