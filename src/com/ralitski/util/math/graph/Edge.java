package com.ralitski.util.math.graph;

public class Edge {
	
	public static float distance(Node start, Node end) {
		return start.getLocation().distance(end.getLocation());
	}
	
	private Node source;
	private Node dest;
	
	private Metadata data;

	public Edge(WebNode source, Node dest) {
		this(source, dest, null);
	}

	public Edge(WebNode source, Node dest, Metadata data) {
		this.source = source;
		this.dest = dest;
		this.data = data;
	}

	public Node getStart() {
		return source;
	}

	public Node getEnd() {
		return dest;
	}

	public Metadata getMetadata() {
		return data;
	}

	public void setMetadata(Metadata data) {
		this.data = data;
	}

	public Object getMetadata(int key) {
		return data != null ? (data.hasData(key) ? data.getData(key) : null) : null;
	}
	
	public String toString() {
		return "[" + source + ":" + dest + ", Meta:" + data + "]";
	}

}
