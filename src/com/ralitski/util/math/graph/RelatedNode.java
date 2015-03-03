package com.ralitski.util.math.graph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.ralitski.util.math.geom.n.Point;

public class RelatedNode implements Node, Iterable<Edge> {
	
	private Point position;
	private List<Edge> connections = new LinkedList<Edge>();
	
	public RelatedNode(Point position) {
		this.position = position;
	}
	
	//connection methods should be used through RelatedGraph
	
	Edge addConnection(Node n, Metadata data) {
		Edge e = new SimpleEdge(this, n, data);
		connections.add(e);
		return e;
	}
	
	void removeConnection(Node n) {
		connections.remove(n);
	}

	@Override
	public Point getLocation() {
		return position;
	}

	@Override
	public Iterator<Edge> iterator() {
		return connections.iterator();
	}

	@Override
	public Object getMetadata(int key) {
		return null;
	}
	
	public boolean equals(Object o) {
		return super.equals(o) ? true : (o instanceof Node ? ((Node)o).getLocation().equals(position) : (o instanceof Point ? ((Point)o).equals(position) : false));
	}

}
