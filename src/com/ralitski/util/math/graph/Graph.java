package com.ralitski.util.math.graph;

import com.ralitski.util.math.geom.n.Point;

/**
 * A Graph, in the sense of Graph Theory - a set of nodes linked by edges.
 * Note: The Graph interface supplies no methods for changing the Graph. Interaction is implementation-dependent.
 * 
 * @author ralitski
 */
public interface Graph {
	Node getClosest(Point p);
	
	/**
	 * Returns an iterator of Edges connecting the specified Node to other Nodes. For each returned Edge, the Start Node should be the specified Node.
	 * @param n the Node to be searched
	 * @return An Iterator over all connected nodes
	 */
	Iterable<Edge> getConnected(Node n);
	
	Edge getConnection(Node start, Node end);
}
