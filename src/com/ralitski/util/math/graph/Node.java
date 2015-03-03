package com.ralitski.util.math.graph;

import com.ralitski.util.math.geom.n.Point;

/**
 * Nodes store position and related metadata.
 * 
 * @author ralitski
 */
public interface Node {
	
	Point getLocation();
	Object getMetadata(int key);
}
