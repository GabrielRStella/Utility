package com.ralitski.util.math.graph;

import com.ralitski.util.ArrayUtils;
import com.ralitski.util.Metadata;
import com.ralitski.util.counter.Counter;
import com.ralitski.util.math.geom.n.Dimensional;
import com.ralitski.util.math.geom.n.Point;

public class GridGraph implements Graph {
	
	private GridGraphWorld world;
	private float nodeSpace;
	private float clearSpace;
	private boolean connectEdges;
	
	public GridGraph(GridGraphWorld world) {
		this.world = world;
		this.nodeSpace = 1;
		this.clearSpace = 1;
	}

	public float getNodeSpace() {
		return nodeSpace;
	}

	public void setNodeSpace(float nodeSpace) {
		this.nodeSpace = nodeSpace;
	}

	public float getClearSpace() {
		return clearSpace;
	}

	public void setClearSpace(float clearSpace) {
		this.clearSpace = clearSpace;
	}

	public boolean isConnectEdges() {
		return connectEdges;
	}

	public void setConnectEdges(boolean connectEdges) {
		this.connectEdges = connectEdges;
	}

	@Override
	public Node getClosest(Point p) {
		int dim = world.dimensions();
		int dim2 = p.dimensions();
		p = p.clone();
		for(int i = 0; i < dim2; i++) {
			int d = i + 1;
			float f = i >= dim ? 0 : p.get(d) % (float)nodeSpace;
			p.set(d, f);
		}
		return world.isNode(p, clearSpace) ? new SimpleNode(p, world.getNodeMetadata(null, p)) : expand(p);//crap do I have to recursively scan now? uuuugh...maybe make it spiral outwards somehow. but what is an n-dimensional spiral? D:
	}
	
	//TODO: somehow get the closest point by expanding out a bunch
	//the given point is already aligned to the coordinates of the grid
	private Node expand(Point p) {
		
	}

	@Override
	public Iterable<Edge> getConnected(Node n) {
		int dim = world.dimensions();
		if(connectEdges) {
			int nodeCount = (int)(Math.pow(3, dim)) - 1;
			Edge[] edges = new Edge[nodeCount];
			int index = 0;
			Counter c = new Counter(dim, 2);
			int[] i = c.get();
			while(c.hasNext()) {
				float[] floats = new float[dim];
				for(int j = 0; j < dim; j++) {
					floats[j] = (float)(i[j] - 1) * nodeSpace;
				}
				Point point = new Point(floats);
				if(world.isNode(point, clearSpace)) {
					Node node = new SimpleNode(point, world.getNodeMetadata(n, point));
					edges[index++] = new Edge(n, node, world.getEdgeMetadata(n, node));
				}
				c.next();
			}
			return ArrayUtils.getIterable(index, edges);
		} else {
			//TODO muh
			int nodeCount = dim * 2;
			Edge[] edges = new Edge[nodeCount];
			int index = 0;
			for(int i = 0; i < dim; i++) {
				int dim2 = i + 1;
				//add space
				Point point = n.getLocation().clone();
				point.set(dim2, point.get(dim2) + nodeSpace);
				if(world.isNode(point, clearSpace)) {
					Node node = new SimpleNode(point, world.getNodeMetadata(n, point));
					edges[index++] = new Edge(n, node, world.getEdgeMetadata(n, node));
				}
				//subtract space
				point = n.getLocation().clone();
				point.set(dim2, point.get(dim2) - nodeSpace);
				if(world.isNode(point, clearSpace)) {
					Node node = new SimpleNode(point, world.getNodeMetadata(n, point));
					edges[index++] = new Edge(n, node, world.getEdgeMetadata(n, node));
				}
			}
			return ArrayUtils.getIterable(index, edges);
		}
	}

	@Override
	public Edge getConnection(Node start, Node end) {
		float distSquared = Edge.distance(start, end);
		float maxDist = clearSpace * clearSpace;
		if(connectEdges) maxDist *= 2F; //root2squared(4me)
		if(distSquared <= maxDist) {
			return new Edge(start, end, world.getEdgeMetadata(start, end));
		} else return null;
	}
	
	public static interface GridGraphWorld extends Dimensional {
		boolean isNode(Point point, float clearSpace);
		
		/**
		 * Called to retrieve a (possibly reused) Metadata to be used for the new Node constructed at the given Point. The first Node argument is used when this method is called by  getConnected(Node), with the Node passed to this method being the Node passed to getConnected(Node). When called from getClosest(Point), the Node will be null.
		 * @param n The source Node, if applicable
		 * @param point The location of the new Node
		 * @return A Metadata object to be stored within the Node created after this method call
		 */
		Metadata getNodeMetadata(Node n, Point point);

		//the same metadata may be reused
		Metadata getEdgeMetadata(Node start, Node end);
	}

}
