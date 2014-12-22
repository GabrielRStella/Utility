package com.ralitski.util.math.geom.d2;

import com.ralitski.util.math.geom.Interval;
import com.ralitski.util.math.geom.IntervalCompound;

/**
 *
 * @author ralitski
 */
public interface Parametric {
	public float getX(float t);
	public float getY(float t);
	public IntervalCompound getInterval();
}
