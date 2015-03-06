package com.ralitski.util.math.graph.search;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

import com.ralitski.util.math.graph.Edge;
import com.ralitski.util.math.graph.Graph;
import com.ralitski.util.math.graph.Node;

public class GraphSearchFlow implements GraphSearch {
	
	private boolean stopOnEnd;
	private Mode mode;
	
	public GraphSearchFlow(Mode mode) {
		this(mode, true);
	}
	
	public GraphSearchFlow(Mode mode, boolean stopOnEnd) {
		this.mode = mode;
		this.stopOnEnd = stopOnEnd;
	}

	@Override
	public LinkedList<Node> getPath(Graph graph, Node start, Node end) {
		Queue<DijkstraNode> frontier = mode.prioritize() ? new PriorityQueue<DijkstraNode>() : new LinkedList<DijkstraNode>();
		DijkstraNode dStart = new DijkstraNode(start, 0);
		frontier.add(dStart);
		
		Map<Node, Node> sources = new HashMap<Node, Node>();
		sources.put(start, null);
		
		Map<Node, Float> costs;
		if(mode.useCost()) {
			costs = new HashMap<Node, Float>();
			costs.put(start, 0F);
		} else costs = null;
		
		//stitch together graph and flow directions
		while(!frontier.isEmpty()) {
			DijkstraNode current = frontier.poll();
			if(stopOnEnd && current.node.equals(end)) {
				break;
			}
			for(Edge e : graph.getConnected(current.node)) {
				Node next = e.getEnd();
				float newCost = 0;
				boolean canForce = false;
				if(mode.useCost()) {
					newCost = current.cost + getCost(graph, e);
					float cost = costs.containsKey(next) ? costs.get(next) : newCost;
					canForce = newCost < cost;
				}
				float priority = mode.getPriority(graph, start, end, e, newCost);
				DijkstraNode dNext = new DijkstraNode(next, priority);
				if(!sources.containsKey(next) || canForce) {
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
		float f = e.getLength();
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
	
	public static enum Mode {
		BREADTH_FIRST(false, false, false),
		DIJKSTRA(true, true, false),
		ASTAR(true, true, true){
			public float getPriority(Graph graph, Node start, Node end, Edge current, float cost) {
				return cost + current.getEnd().distance(end);
			}
		},
		GREEDY(true, false, true)
			public float getPriority(Graph graph, Node start, Node end, Edge current, float cost) {
				return current.getEnd().distance(end);
			};
		
		private boolean priority;
		private boolean useCost;
		private boolean useHeuristic;
		
		Mode(boolean priority, boolean useCost, boolean useHeuristic) {
			this.priority = priority;
			this.useCost = useCost;
			this.useHeuristic = useHeuristic;
		}
		
		public boolean prioritize() {
			return priority;
		}
		
		public boolean useCost() {
			return useCost;
		}
		
		public float getPriority(Graph graph, Node start, Node end, Edge current, float cost) {
			return cost;
		}
	}

}
