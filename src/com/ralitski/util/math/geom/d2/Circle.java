package com.ralitski.util.math.geom.d2;

import java.util.Random;

import com.ralitski.util.math.expression.Expressible;
import com.ralitski.util.math.expression.Expression;
import com.ralitski.util.math.expression.Expressions;
import com.ralitski.util.math.geom.IntervalCompound;

public class Circle implements Shape2d, Expressible {

	public Point2d center;
	public float radius;
	
	public Circle(float r)
	{
		this(0, 0, r);
	}
	
	public Circle(float x, float y, float r)
	{
		this(new Point2d(x, y), r);
	}
	
	public Circle(Point2d p, float r)
	{
		this.center = p;
		this.radius = r;
	}
	
	/*
	 * returns the state of a point relative to the circle: interior, exterior, or on the circle.
	 * @param p: point which will be compared
	 * @return: state of point; -1 inside circle, 0 on circle, 1 outside circle
	 */
	public PointState getState(Point2d p)
	{
		return getState(p.getX(), p.getY());
	}

	public PointState getState(float x, float y)
	{
		float d = center.length(x, y);
		if(d > this.radius) return PointState.OUTSIDE;
		if(d < this.radius) return PointState.INSIDE;
		return PointState.ON;
	}

	@Override
	public Point2d getClosestPoint(Point2d point) {
		return null;
	}
	
	public float circumfrence()
	{
		return (float) (Math.PI * this.radius * 2F);
	}
	
	public float getArea()
	{
		return (float) (Math.PI * this.radius * this.radius);
	}
	
	public float getArea(float sector)
	{
		float ratio = sector / 360F;
		return this.getArea() * ratio;
	}
	
	public Point2d randomPoint()
	{
		return this.randomPoint(true);
	}
	
	public Point2d randomPoint(boolean edge)
	{
		float d = this.radius;
		if(!edge) d -= 0.00000000001F; //close enough
		Random r = new Random();
		float dist = d * r.nextFloat();
		float angle = 360F * r.nextFloat();
		return Point2d.fromComplex(dist, angle);
	}
	
	public Point2d[] split(int points)
	{
		return this.split(points, 0);
	}

	//gives points around the circle
	public Point2d[] split(int points, float degree)
	{
		Point2d[] pointList = new Point2d[points];
		float partialDegree = 360F/(float)points;
		for(int i = 0; i < points; i++)
		{
			Point2d p = Point2d.fromComplex(this.radius, partialDegree * i + degree);
			p.translate(this.center);
			pointList[i] = p;
		}
		return pointList;
	}
	
	public Point2d[] split(float distance)
	{
		return this.split(distance, 0);
	}
	
	public Point2d[] split(float distance, float degree)
	{
		int points = (int) Math.ceil(this.circumfrence() / distance);
		return split(points, degree);
	}

	@Override
	public Point2d getPosition() {
		return this.center;
	}

	@Override
	public void setPosition(Point2d position) {
		this.center = position;
	}

	@Override
	public Expression express() {
		Expression x = Expressions.valueOf('x');
		Expression y = Expressions.valueOf('y');
		return Expressions.add(
				Expressions.multiply(x, x),
				Expressions.multiply(y, y),
				Expressions.valueOf(-(radius * radius))
				);
	}
	
//	public Parametric getParametric() {
//		return new CircleParametric();
//	}
//	
//	public Parametric getParametricRadians() {
//		return new CircleParametricRadians();
//	}
//	
//	private class CircleParametric implements Parametric {
//
//		@Override
//		public float getX(float t) {
//			return (float)Math.cos(Math.toRadians(t)) * radius + center.getX();
//		}
//
//		@Override
//		public float getY(float t) {
//			return (float)Math.sin(Math.toRadians(t)) * radius + center.getY();
//		}
//
//		@Override
//		public IntervalCompound getInterval() {
//			return IntervalCompound.DEGREES;
//		}
//	}
//	
//	private class CircleParametricRadians implements Parametric {
//
//		@Override
//		public float getX(float t) {
//			return (float)Math.cos(t) * radius + center.getX();
//		}
//
//		@Override
//		public float getY(float t) {
//			return (float)Math.sin(t) * radius + center.getY();
//		}
//
//		@Override
//		public IntervalCompound getInterval() {
//			return IntervalCompound.RADIANS;
//		}
//	}
}
