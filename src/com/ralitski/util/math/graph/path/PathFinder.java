package com.ralitski.util.math.graph.path;

import java.util.Iterator;

import com.ralitski.util.math.graph.Graph;
import com.ralitski.util.math.graph.Node;

public interface PathFinder {
	
	/**
	 * Looks for various paths from one node to the other. The returned Iterator should generate new paths on-demand, not necessarily in any particular order. A call to remove() on the returned Iterator signals the end of its lifecycle, and it may then clear its memory.
	 * @param graph The graph to look for paths in
	 * @param start The starting Node
	 * @param end The ending Node
	 * @return An on-demand Iterator over possible paths, generated in no particular order, not necessarily of infinite size
	 */
	Iterator<Iterable<Node>> getPaths(Graph graph, Node start, Node end);
}
