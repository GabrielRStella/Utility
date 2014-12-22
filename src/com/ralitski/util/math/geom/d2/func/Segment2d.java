package com.ralitski.util.math.geom.d2.func;

import com.ralitski.util.math.geom.Interval;
import com.ralitski.util.math.geom.IntervalCompound;
import com.ralitski.util.math.geom.d2.Point2d;


/**
 *
 * @author ralitski
 */
public class Segment2d extends Line2d {

    static Segment2d betweenX(Line2d line, float start, float end) {
        Segment2d ret = new Segment2d(line);
        ret.startX = start;
        ret.endX = end;
        ret.startY = line.getY(start);
        ret.endY = line.getY(end);
        return ret;
    }

    static Segment2d betweenY(Line2d line, float start, float end) {
        Segment2d ret = new Segment2d(line);
        ret.startY = start;
        ret.endY = end;
        ret.startX = line.getY(start);
        ret.endX = line.getY(end);
        return ret;
    }
    
    private Line2d line;
    private float startX;
    private float endX;
    private float startY;
    private float endY;
    
    private Segment2d(Line2d line) {
        this.line = line;
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
        return x >= startX && x <= endX;
    }
    
    private boolean inYBounds(float y) {
        return y >= startY && y <= endY;
    }

//	@Override
//	public IntervalCompound getRealInterval() {
//		return new IntervalCompound(new Interval(startX, true, endX, true));
//	}
}
