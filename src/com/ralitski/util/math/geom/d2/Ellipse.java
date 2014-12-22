package com.ralitski.util.math.geom.d2;

import com.ralitski.util.math.expression.Expression;
import com.ralitski.util.math.expression.Expressions;
import com.ralitski.util.math.geom.Geometry;
import com.ralitski.util.math.var.VariableSet;


public class Ellipse implements Shape {

	private Point2d focus1;
	private Point2d focus2;
	private float fc;
	
	public Ellipse(Point2d focus1, Point2d focus2, Point2d pointOnEdge)
	{
		this(focus1, focus2, focus1.length(focus2) + focus1.length(pointOnEdge));
	}
	
	public Ellipse(Point2d focus1, Point2d focus2, float focalConstant)
	{
		this.focus1 = focus1;
		this.focus2 = focus2;
		this.fc = focalConstant;
	}
	
	public float getArea()
	{
		Point2d center = Point2d.midpoint(this.focus1, this.focus2);
		float c = focus1.length(center);
		float a = fc / 2F;
		float b = (float)Math.sqrt(a * a - c * c);
		return (float)Math.PI * a * b;
	}

	@Override
	public Point2d getPosition() {
		return Point2d.midpoint(this.focus1, this.focus2);
	}

	@Override
	public void setPosition(Point2d position) {
		Point2d offset = position.clone();
		offset.translate(getPosition().negative());
		this.focus1.translate(offset);
		this.focus2.translate(offset);
	}

	@Override
	public PointState getState(Point2d point) {
		float d = focus1.length(point) + focus2.length(point);
		if(d > this.fc) return PointState.OUTSIDE;
		if(d < this.fc) return PointState.INSIDE;
		return PointState.ON;
	}

	@Override
	public Expression express() {
		Point2d center = Point2d.midpoint(this.focus1, this.focus2);
		float theta = Geometry.getReferenceAngleRadians(focus2.rotationRadians(focus1));
		float c = focus1.length(center);
		float a = fc / 2F;
		float b = (float)Math.sqrt(a * a - c * c);
		float aSquared = a * a;
		float bSquared = b * b;
		Expression cosTheta = Expressions.valueOf((float)Math.cos(theta));
		Expression sinTheta = Expressions.valueOf((float)Math.sin(theta));
		//account for rotation
		//((x*cos(v)+y*sin(v))^2)/(a^2)+((x*sin(v)-y*cos(v))^2)/(b^2) = 1
		Expression x = Expressions.valueOf('x');
		Expression y = Expressions.valueOf('y');
		return Expressions.add(
				Expressions.divide(
						Expressions.square(
								Expressions.add(
										Expressions.multiply(x, cosTheta),
										Expressions.multiply(y, sinTheta),
										Expressions.valueOf(-center.getX())
										)
								),
						Expressions.valueOf(aSquared)
						),
				Expressions.divide(
						Expressions.square(
								Expressions.add(
										Expressions.multiply(x, sinTheta),
										Expressions.negative(Expressions.multiply(y, cosTheta)),
										Expressions.valueOf(center.getY())
										)
								),
						Expressions.valueOf(bSquared)
						),
				Expressions.valueOf(-1)
				);
	}
	
	public String toString() {
		//easy peasy
		return express().simplify(VariableSet.EMPTY_SET).toString();
	}
}
