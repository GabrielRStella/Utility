package com.ralitski.util.math.graph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.ralitski.util.math.geom.n.Point;

/**
 * A Node implementation that stores references to the Nodes connected to it. Specially designed for use with WebGraph; useless in other cases, since WebGraph has access to its package-level methods.
 * 
 * @author ralitski
 */
public class WebNode implements Node, Iterable<Edge> {
	
	private Point position;
	private List<Edge> connections = new LinkedList<Edge>();
	private Metadata data;
	
	public WebNode(Point position) {
		this.position = position;
	}
	
	//connection methods should be used through RelatedGraph
	
	Edge addConnection(Node n, Metadata data) {
		Edge e = new Edge(this, n, data);
		connections.add(e);
		return e;
	}
	
	void removeConnection(Node n) {
		connections.remove(n);
	}
	
	//public stuff

	public Metadata getMetadata() {
		return data;
	}

	public void setMetadata(Metadata data) {
		this.data = data;
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
		return data != null ? data.getData(key) : null;
	}
	
	public boolean equals(Object o) {
		return super.equals(o) ? true : (o instanceof Node ? ((Node)o).getLocation().equals(position) : (o instanceof Point ? ((Point)o).equals(position) : false));
	}

}
