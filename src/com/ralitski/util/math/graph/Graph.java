package com.ralitski.util.math.graph;

import com.ralitski.util.math.geom.n.Point;

public interface Graph extends Iterable<Node> {
	Node getClosest(Point p);
	
	/**
	 * Returns an iterator of Edges connecting the specified Node to other Nodes. For each returned Edge, the Start Node should be the specified Node.
	 * @param n the Node to be searched
	 * @return An Iterator over all connected nodes
	 */
	Iterable<Edge> getConnected(Node n);
	
	Edge getConnection(Node start, Node end);
}
