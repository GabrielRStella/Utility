package com.ralitski.util.math.geom.d2;

public class Ring implements Shape2d {
	
	private Point2d position;
	private Circle inside;
	private Circle outside;
	
	public Ring(float innerRadius, float outerRadius) {
		this(Point2d.origin(), innerRadius, outerRadius);
	}

	public Ring(Point2d point, float innerRadius, float outerRadius) {
		this.position = point;
		inside = new Circle(point, innerRadius);
		outside = new Circle(point, outerRadius);
	}
	
	public Circle getInside() {
		return inside;
	}
	
	public Circle getOutside() {
		return outside;
	}

	@Override
	public Point2d getPosition() {
		return position;
	}

	@Override
	public void setPosition(Point2d position) {
		//automatically change center of Circles as well
		this.position.setX(position.getX());
		this.position.setY(position.getY());
	}

	@Override
	public int getState(Point2d point) {
		int outerState = outside.getState(point);
		int innerState = inside.getState(point);
		if(outerState == POINT_ON || innerState == POINT_ON) return POINT_ON;
		if(innerState == POINT_OUTSIDE && outerState == POINT_INSIDE) return POINT_INSIDE;
		return POINT_OUTSIDE;
	}

	@Override
	public Point2d getClosestPoint(Point2d point) {
		int state = getState(point);
		if(state == POINT_ON || state == POINT_INSIDE) return point;
		float dist = point.length(position);
		point = point.clone();
		point.setLength(dist < inside.getRadius() ? inside.getRadius() : outside.getRadius());
		return point;
	}

	@Override
	public float getArea() {
		return outside.getArea() - inside.getArea();
	}

}
