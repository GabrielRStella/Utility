package com.ralitski.util.math.graph;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.ralitski.util.math.geom.n.Point;

public class RelatedGraph implements Graph {
	
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
	public Iterable<Edge> getConnected(Node n) {
		return n instanceof RelatedNode ? ((RelatedNode)n) : null;
	}

	@Override
	public Edge getConnection(Node start, Node end) {
		if(start instanceof RelatedNode) {
			RelatedNode r = (RelatedNode)start;
			for(Edge e : r) {
				if(e.getEnd().equals(end)) {
					return e;
				}
			}
		}
		return null;
	}
	
	public RelatedNode addNode(Point position) {
		RelatedNode node = new RelatedNode(position);
		nodes.add(node);
		return node;
	}
	
	public boolean removeNode(Node node) {
		return nodes.remove(node);
	}
	
	/**
	 * Node: order doesn't actually matter.
	 * @param start
	 * @param end
	 */
	public Edge connect(Node start, Node end) {
		if(start instanceof RelatedNode && end instanceof RelatedNode) {
			Metadata shared = new Metadata();
			Edge ret = ((RelatedNode)start).addConnection(end, shared);
			((RelatedNode)end).addConnection(start, shared);
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
		if(start instanceof RelatedNode) {
			((RelatedNode)start).removeConnection(end);
		}
		if(end instanceof RelatedNode) {
			((RelatedNode)end).removeConnection(start);
		}
	}

}
