package com.ralitski.util.math.graph;

public class SimpleEdge implements Edge {
	
	private Node source;
	private Node dest;
	
	private Metadata data;

	public SimpleEdge(RelatedNode source, Node dest) {
		this(source, dest, new Metadata());
	}

	public SimpleEdge(RelatedNode source, Node dest, Metadata data) {
		this.source = source;
		this.dest = dest;
	}

	@Override
	public Node getStart() {
		return source;
	}

	@Override
	public Node getEnd() {
		return dest;
	}

	@Override
	public Object getMetadata(int key) {
		return data.getData(key);
	}

}
