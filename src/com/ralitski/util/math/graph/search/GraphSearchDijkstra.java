package com.ralitski.util.math.graph.search;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

import com.ralitski.util.math.graph.Edge;
import com.ralitski.util.math.graph.Graph;
import com.ralitski.util.math.graph.Node;

public class GraphSearchDijkstra implements GraphSearch {
	
	private boolean stopOnEnd;
	
	public GraphSearchDijkstra() {
		this(true);
	}
	
	public GraphSearchDijkstra(boolean stopOnEnd) {
		this.stopOnEnd = stopOnEnd;
	}

	@Override
	public LinkedList<Node> getPath(Graph graph, Node start, Node end) {
		PriorityQueue<DijkstraNode> frontier = new PriorityQueue<DijkstraNode>();
		DijkstraNode dStart = new DijkstraNode(start, 0);
		frontier.add(dStart);
		
		Map<Node, Node> sources = new HashMap<Node, Node>();
		sources.put(start, null);
		
		Map<Node, Float> costs = new HashMap<Node, Float>();
		costs.put(start, 0F);
		
		//stitch together graph and flow directions
		while(!frontier.isEmpty()) {
			DijkstraNode current = frontier.poll();
			if(stopOnEnd && current.node.equals(end)) {
				break;
			}
			for(Edge e : graph.getConnected(current.node)) {
				Node next = e.getEnd();
				float newCost = current.cost + getCost(graph, e);
				float cost = costs.containsKey(next) ? costs.get(next) : newCost;
				DijkstraNode dNext = new DijkstraNode(next, newCost);
				if(!sources.containsKey(next) || newCost < cost) {
					frontier.add(dNext);
					sources.put(next, current.node);
					costs.put(next, newCost);
				}
			}
		}
		//construct path
		Node current = end;
		LinkedList<Node> path = new LinkedList<Node>();
		path.add(current);
		while(!current.equals(start)) {
			current = sources.get(current);
			if(current == null) {
				System.out.println("No path");
				return null;
			}
			path.add(current);
		}
		return path;
	}
	
	public float getCost(Graph g, Edge e) {
		Object o = e.getMetadata(EDGE_WEIGHT);
		float f = e.getStart().getLocation().distance(e.getEnd().getLocation());
		if(o != null && o instanceof Float) {
			f *= (Float)o;
		}
		return f;
	}
	
	private class DijkstraNode implements Comparable<DijkstraNode> {
	  private Node node;
	  private float cost;
	  
	  public DijkstraNode(Node n, float cost) {
	    node = n;
	    this.cost = cost;
	  }
	  
	  public int compareTo(DijkstraNode n) {
	    return (int)(cost - n.cost);
	  }
	  
	  public boolean equals(Object o) {
		  return super.equals(o) ? true : o instanceof DijkstraNode ? ((DijkstraNode)o).node.equals(node) : node.equals(o);
	  }
	  
	  public String toString() {
		  return "[" + node.toString() + ", " + cost + "]";
	  }
	}

}