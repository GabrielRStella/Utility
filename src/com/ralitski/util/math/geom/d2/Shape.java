package com.ralitski.util.math.geom.d2;

import com.ralitski.util.math.expression.Expressible;


public interface Shape extends Expressible {
	
	public Point2d getPosition();
	public void setPosition(Point2d position);
	public PointState getState(Point2d point);
	public float getArea();
}
