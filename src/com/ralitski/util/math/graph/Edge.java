package com.ralitski.util.math.graph;

public interface Edge {
	Node getStart();
	Node getEnd();
	Object getMetadata(int key);
}
