package com.ralitski.util.math.graph;

import com.ralitski.util.Metadata;
import com.ralitski.util.math.geom.n.Point;

public class SimpleNode implements Node {
	
	private Point location;
	private Metadata metadata;
	
	public SimpleNode(Point location) {
		this(location, new Metadata());
	}

	public SimpleNode(Point location, Metadata metadata) {
		this.location = location;
		this.metadata = metadata;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	@Override
	public Point getLocation() {
		return location;
	}

	@Override
	public Object getMetadata(int key) {
		return metadata.getData(key);
	}

}
