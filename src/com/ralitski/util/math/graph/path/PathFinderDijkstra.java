package com.ralitski.util.math.graph.path;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import com.ralitski.util.SingleIterator;
import com.ralitski.util.math.graph.Edge;
import com.ralitski.util.math.graph.Graph;
import com.ralitski.util.math.graph.Node;

public class PathFinderDijkstra implements PathFinder {
	
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
	NavigableSet<DijkstraNode> frontier = new TreeSet<DijkstraNode>();
	frontier.add(new DijkstraNode(start, 0));
	
	Map<Node, Node> sources = new HashMap<Node, Node>();
		sources.put(start, null);
		//stitch together graph and flow directions
		while(!frontier.isEmpty()) {
			DijkstraNode current = frontier.pollLast();
			if(stopOnEnd && current.equals(end)) break;
			for(Edge e : graph.getConnected(current)) {
				Node next = e.getEnd();
				float newCost = current.cost + getCost(e);
				if(!sources.containsKey(next) | ) {
					frontier.add(next);
					sources.put(next, current);
				}
			}
		}
	/*
came_from = {}
cost_so_far = {}
came_from[start] = None
cost_so_far[start] = 0

while not frontier.empty():
   current = frontier.get()
   
   for next in graph.neighbors(current):
      new_cost = cost_so_far[current] + graph.cost(current, next)
      if next not in cost_so_far or new_cost < cost_so_far[next]:
         cost_so_far[next] = new_cost
         priority = new_cost
         frontier.put(next, priority)
         came_from[next] = current
	*/
	return null;
	}
		//construct path
		Node current = end;
		LinkedList<Node> path = new LinkedList<Node>();
		path.add(current);
		while(!current.equals(start)) {
			current = sources.get(current);
			path.add(current);
		}
	
	private class DijkstraNode implements Comparable<DijkstraNode> {
	  private Node node;
	  private float cost;
	  
	  public DijkstraNode(Node n, float cost) {
	    node = n;
	    this.cost = cost;
	  }
	  
	  public int compareTo(DijkstraNode n) {
	    return n.cost - cost;
	  }
	}

}
