package com.ralitski.util.math.graph.path;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.ralitski.util.SingleIterator;
import com.ralitski.util.math.graph.Edge;
import com.ralitski.util.math.graph.Graph;
import com.ralitski.util.math.graph.Node;

public class PathFinderBreadthFirst implements PathFinder {
	
	private boolean stopOnEnd;
	
	public PathFinderBreadthFirst() {
		this(true);
	}
	
	public PathFinderBreadthFirst(boolean stopOnEnd) {
		this.stopOnEnd = stopOnEnd;
	}

	@Override
	public Iterator<Iterable<Node>> getPaths(Graph graph, Node start, Node end) {
		return new SingleIterator<Iterable<Node>>(getPath(graph, start, end));
	}

	public Iterable<Node> getPath(Graph graph, Node start, Node end) {
		LinkedList<Node> frontier = new LinkedList<Node>();
		frontier.add(start);
		Map<Node, Node> sources = new HashMap<Node, Node>();
		sources.put(start, null);
		//stitch together graph and flow directions
		while(!frontier.isEmpty()) {
			Node current = frontier.removeLast();
			if(stopOnEnd && current.equals(end)) break;
			for(Edge e : graph.getConnected(current)) {
				Node next = e.getEnd();
				if(!sources.containsKey(next)) {
					frontier.add(next);
					sources.put(next, current);
				}
			}
		}
		//construct path
		Node current = end;
		LinkedList<Node> path = new LinkedList<Node>();
		path.add(current);
		while(!current.equals(start)) {
			current = sources.get(current);
			path.add(current);
		}
		return path;
	}

}
