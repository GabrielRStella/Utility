package com.ralitski.util.math.graph;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.ralitski.util.Metadata;
import com.ralitski.util.math.geom.n.Point;

/**
 * A graph in which nodes at arbitrary positions are stored and linked together like a web.
 * 
 * @author ralitski
 */
public class WebGraph implements Graph, Iterable<Node> {
	
	private List<Node> nodes = new LinkedList<Node>();

	@Override
	public Iterator<Node> iterator() {
		return nodes.iterator();
	}

	@Override
	public Node getClosest(Point p) {
		Node min = null;
		float minDistSquared = Float.MAX_VALUE;
		for(Node n : nodes) {
			if(min == null) {
				min = n;
				continue;
			}
			Point nP = n.getLocation();
			float dist = nP.distanceSquared(p);
			if(dist < minDistSquared) {
				minDistSquared = dist;
				min = n;
			}
		}
		return min;
	}

	@Override
	public Collection<Edge> getConnected(Node n) {
		return n instanceof WebNode ? ((WebNode)n).connections : null;
	}

	@Override
	public Edge getConnection(Node start, Node end) {
		if(start instanceof WebNode) {
			WebNode r = (WebNode)start;
			for(Edge e : r) {
				if(e.getEnd().equals(end)) {
					return e;
				}
			}
		}
		return null;
	}
	
	public WebNode addNode(Point position) {
		WebNode node = new WebNode(position);
		nodes.add(node);
		return node;
	}
	
	public boolean removeNode(Node node) {
		return nodes.remove(node);
	}
	
	/**
	 * Note: order doesn't actually matter.
	 * Connects two Nodes together. They must both be instances of RelatedNode.
	 * @param start The first node
	 * @param end The second node
	 * @return An edge representing the connection between the two nodes (now part of the graph as well)
	 */
	public Edge connect(Node start, Node end) {
		if(start instanceof WebNode && end instanceof WebNode) {
			Metadata shared = new Metadata();
			Edge ret = ((WebNode)start).addConnection(end, shared);
			((WebNode)end).addConnection(start, shared);
			return ret;
		}
		return null;
	}
	
	/**
	 * Node: order doesn't actually matter.
	 * @param start
	 * @param end
	 */
	public void disconnect(Node start, Node end) {
		if(start instanceof WebNode) {
			((WebNode)start).removeConnection(end);
		}
		if(end instanceof WebNode) {
			((WebNode)end).removeConnection(start);
		}
	}

}
