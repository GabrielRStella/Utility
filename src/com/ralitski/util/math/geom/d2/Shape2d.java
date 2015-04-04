package com.ralitski.util.math.geom.d2;

public interface Shape2d {
	
	int POINT_OUTSIDE = 1;
	int POINT_ON = 2;
	int POINT_INSIDE = 4;
	
	public Point2d getPosition();
	public void setPosition(Point2d position);
	public int getState(Point2d point);
	public Point2d getClosestPoint(Point2d point);
	public float getArea();
}
