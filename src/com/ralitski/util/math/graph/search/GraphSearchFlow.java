package com.ralitski.util.math.graph.search;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.ralitski.util.math.graph.Edge;
import com.ralitski.util.math.graph.Graph;
import com.ralitski.util.math.graph.Node;

public class GraphSearchFlow implements GraphSearch {
	
	private boolean stopOnEnd;
	private SearchMode mode;
	
	private boolean keepSources = false;
	private Map<Node, Node> sources;
	private Node prevSource;
	
	public GraphSearchFlow(SearchMode mode) {
		this(mode, true);
	}
	
	public GraphSearchFlow(SearchMode mode, boolean stopOnEnd) {
		this.mode = mode;
		this.stopOnEnd = stopOnEnd;
		sources = new HashMap<Node, Node>();
	}
	
	public boolean doStopOnEnd() {
		return stopOnEnd;
	}
	
	public void setStopOnEnd(boolean stopOnEnd) {
		this.stopOnEnd = stopOnEnd;
	}
	
	public SearchMode getMode() {
		return mode;
	}
	
	public void setMode(SearchMode mode) {
		this.mode = mode;
	}
	
	public boolean doKeepSources() {
		return this.keepSources;
	}
	
	public void setKeepSources(boolean keepSources) {
		this.keepSources = keepSources;
	}
	
	public void clearSources() {
		sources.clear();
	}

	@Override
	public LinkedList<Node> getPath(Graph graph, Node start, Node end) {
		Queue<WeightedNode> frontier = mode.prioritize() ? new PriorityQueue<WeightedNode>() : new LinkedList<WeightedNode>();
		WeightedNode dStart = new WeightedNode(start, 0);
		frontier.add(dStart);
		
		boolean keptSource = true;
		if(!keepSources || !start.equals(prevSource)) {
			sources.clear();
			keptSource = false;
		}
		sources.put(start, null);
		
		Map<Node, Float> costs;
		if(mode.useCost()) {
			costs = new HashMap<Node, Float>();
			costs.put(start, 0F);
		} else costs = null;
		
		//stitch together graph and flow directions
		while(!frontier.isEmpty()) {
			WeightedNode current = frontier.poll();
			if(stopOnEnd && (current.node.equals(end) || (keptSource && sources.containsKey(end)))) {
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
				float priority = getPriority(graph, start, end, e, newCost);
				WeightedNode dNext = new WeightedNode(next, priority);
				if(!sources.containsKey(next) || canForce) {
					frontier.add(dNext);
					sources.put(next, current.node);
					if(costs != null) costs.put(next, newCost);
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
	
	//can be overridden by subclasses
	public float getPriority(Graph graph, Node start, Node end, Edge current, float cost) {
		return mode.getPriority(graph, start, end, current, cost);
	}
	
	private class WeightedNode implements Comparable<WeightedNode> {
	  private Node node;
	  private float cost;
	  
	  public WeightedNode(Node n, float cost) {
	    node = n;
	    this.cost = cost;
	  }
	  
	  public int compareTo(WeightedNode n) {
	    return (int)(cost - n.cost);
	  }
	  
	  public boolean equals(Object o) {
		  return super.equals(o) ? true : o instanceof WeightedNode ? ((WeightedNode)o).node.equals(node) : node.equals(o);
	  }
	  
	  public String toString() {
		  return "[" + node.toString() + ", " + cost + "]";
	  }
	}
	
	public static enum SearchMode {
		BREADTH_FIRST(false, false),
		DIJKSTRA(true, true),
		ASTAR(true, true){
			public float getPriority(Graph graph, Node start, Node end, Edge current, float cost) {
				return cost + Edge.distance(current.getEnd(), end);
			}
		},
		GREEDY(true, false){
			public float getPriority(Graph graph, Node start, Node end, Edge current, float cost) {
				return Edge.distance(current.getEnd(), end);
			}
		};
		
		private boolean priority;
		private boolean useCost;
		
		SearchMode(boolean priority, boolean useCost) {
			this.priority = priority;
			this.useCost = useCost;
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
