package com.ralitski.util.math.graph.search;

import com.ralitski.util.math.graph.Graph;
import com.ralitski.util.math.graph.Node;

public interface GraphSearch {
	
	/**
	 * Metadata key for a multiplier on the "weight" of an edge. Note that this is interpreted similarly to length; a higher value will make an edge less favorable.
	 */
	public static final int EDGE_WEIGHT = 0;
	
	/**
	 * Looks for the "best" path from one node to the other. The returned Iterable should return an Iterator with the specified start Node as the first element and the specified end Node as the last element. The Iterable may be null if no path is found.
	 * @param graph The graph to look for paths in
	 * @param start The starting Node
	 * @param end The ending Node
	 * @return An Iterable of Nodes representing a path between the two specified Nodes
	 */
	Iterable<Node> getPath(Graph graph, Node start, Node end);
}
