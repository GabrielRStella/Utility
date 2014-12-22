package com.ralitski.util.math.geom.d2.func;

import com.ralitski.util.math.geom.Interval;
import com.ralitski.util.math.geom.IntervalCompound;
import com.ralitski.util.math.geom.d2.Point2d;


/**
 *
 * @author ralitski
 */
public class Ray2d extends Line2d {

    static Ray2d fromX(Line2d line, float start, boolean direction) {
        Ray2d ret = new Ray2d(line, direction);
        ret.startX = start;
        ret.startY = line.getY(start);
        return ret;
    }

    static Ray2d fromY(Line2d line, float start, boolean direction) {
        Ray2d ret = new Ray2d(line, direction);
        ret.startY = start;
        ret.startX = line.getY(start);
        return ret;
    }
    
    private Line2d line;
    private float startX;
    private float startY;
    //true for positive, false for negative
    private boolean direction;
    
    private Ray2d(Line2d line, boolean direction) {
        this.line = line;
        this.direction = direction;
    }
    
    @Override
    public float getX(float y) {
        return inYBounds(y) ? line.getX(y) : Float.NaN;
    }

    @Override
    public float getY(float x) {
        return inXBounds(x) ? line.getY(x) : Float.NaN;
    }
    
    @Override
    public boolean contains(float x, float y) {
        return inXBounds(x) ? line.getY(x) == y : false;
    }
    
    @Override
    public boolean contains(Point2d p) {
        return inXBounds(p.getX()) ? line.getY(p.getX()) == p.getY() : false;
    }
    
    private boolean inXBounds(float x) {
        return direction ? x >= startX : x <= startX;
    }
    
    private boolean inYBounds(float y) {
        return direction ? y >= startY : y <= startY;
    }

//	@Override
//	public IntervalCompound getRealInterval() {
//		return direction ? new IntervalCompound(new Interval(startX, true, Float.POSITIVE_INFINITY, false)) : new IntervalCompound(new Interval(Float.NEGATIVE_INFINITY, false, startX, true));
//	}
}
