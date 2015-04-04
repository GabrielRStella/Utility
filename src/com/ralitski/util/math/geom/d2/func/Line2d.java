package com.ralitski.util.math.geom.d2.func;

import com.ralitski.util.math.expression.Expression;
import com.ralitski.util.math.expression.Expressions;
import com.ralitski.util.math.geom.d2.Point2d;


/**
 *
 * @author ralitski
 */
public class Line2d implements FunctionInverse {
    
    private float slope;
    private float yIntercept;
    
    Line2d() {
        //empty constructor for use by segment and ray
    }
    
    public Line2d(Point2d pointOne, Point2d pointTwo) {
        this.slope = (pointTwo.getY() - pointOne.getY()) / (pointTwo.getX() - pointOne.getX());
        this.yIntercept = (-slope * pointOne.getX()) + pointOne.getY();
    }
    
    public Line2d(Point2d point, float slope) {
        this.slope = slope;
        this.yIntercept = (-slope * point.getX()) + point.getY();
    }
    
    public Line2d(float slope, float yIntercept) {
    	this.slope = slope;
    	this.yIntercept = yIntercept;
    }
    
    public float getSlope() {
        return slope;
    }
    
    public void setSlope(float slope) {
        this.slope = slope;
    }
    
    public float getYIntercept() {
        return yIntercept;
    }
    
    public void setYIntercept(float yIntercept) {
        this.yIntercept = yIntercept;
    }
    
    @Override
    public float getX(float y) {
        return (y - yIntercept) / slope;
    }

    @Override
    public float getY(float x) {
        return slope * x + yIntercept;
    }
    
    public boolean contains(float x, float y) {
        return getY(x) == y;
    }
    
    public boolean contains(Point2d p) {
        return getY(p.getX()) == p.getY();
    }
    
    public Segment2d getBetweenX(float start, float end) {
        return Segment2d.betweenX(this, start, end);
    }
    
    public Segment2d getBetweenY(float start, float end) {
        return Segment2d.betweenY(this, start, end);
    }
    
    /**
     * 
     * @param start
     * @param direction true for ray extending in positive direction
     * @return 
     */
    public Ray2d getfromX(float start, boolean direction) {
        return Ray2d.fromX(this, start, direction);
    }
    
    /**
     * 
     * @param start
     * @param direction true for ray extending in positive direction
     * @return 
     */
    public Ray2d getfromY(float start, boolean direction) {
        return Ray2d.fromY(this, start, direction);
    }

    @Override
    public FunctionInverse inverse() {
        return new Line2d(new Point2d(yIntercept, 0), 1F / slope);
    }
    
    public Point2d intercept(Line2d other) {
    	//TODO: coincident lines
    	float x = (yIntercept - other.yIntercept) / (other.slope - slope);
    	float y = getY(x);
    	return new Point2d(x, y);
    }
    
    public Line2d getPerpendicularThrough(Point2d p) {
    	return new Line2d(p, -1F / slope);
    }
    
    public Line2d getParallelThrough(Point2d p) {
    	return new Line2d(p, slope);
    }
    
    public float getDistanceTo(Point2d p) {
    	Line2d temp = getPerpendicularThrough(p);
    	return p.length(intercept(temp));
    }
    
    @Override
    public String toString() {
        //DecimalFormat format = new DecimalFormat("#.##");
        //return "y=" + format.format(slope) + "x+" + format.format(yIntercept);
        return "y=" + slope + "x+" + yIntercept;
    }

	@Override
	public Expression express() {
		return Expressions.add(
				Expressions.multiply(
						Expressions.valueOf('x'),
						Expressions.valueOf(slope)
						),
				Expressions.valueOf(yIntercept)
				);
	}
	
//	public Parametric getParametric() {
//		return new ParametricSimple(this, IntervalCompound.ALL);
//	}

	/*
	@Override
	public IntervalCompound getRealInterval() {
		return IntervalCompound.ALL;
	}
	 */
}
