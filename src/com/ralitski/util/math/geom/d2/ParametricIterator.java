package com.ralitski.util.math.geom.d2;

import java.util.Iterator;

import com.ralitski.util.math.geom.Interval;
import com.ralitski.util.math.geom.IntervalCompound;


public class ParametricIterator implements Iterator<Point2d> {
	
	private float t;
	private float tStep;
	private Parametric graph;
	private IntervalCompound interval;
	private int intervalCount;
	
	public ParametricIterator(Parametric graph, float t, float tStep) {
		this.t = t;
		this.tStep = tStep;
		this.graph = graph;
		this.interval = graph.getInterval().simplify().sort();
	}

	public float getParameter() {
		return t;
	}

	public void setParameter(float step) {
		t = step;
	}

	public float getParameterStep() {
		return tStep;
	}

	public void setParameterStep(float step) {
		this.tStep = step;
	}
	
	public boolean hasNext() {
		return interval.includes(t) || intervalCount < interval.get().length;
	}

	public Point2d next() {
		Point2d p;
		try {
			p = new Point2d(graph.getX(t), graph.getY(t));
		} catch(Exception e) {
			p = null;
		}
		t += tStep;
		if(!interval.includes(t)) {
			if(intervalCount < interval.get().length) {
				Interval i = interval.get()[intervalCount];
				//might end up borking stuff. :[
				t = i.getMin();
				//if(!i.includeMin()) t += 0.0000001F; //just for stuff
			}
			intervalCount++;
		}
		return p;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Can't remove points from a graph!");
	}

}
