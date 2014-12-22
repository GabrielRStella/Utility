package com.ralitski.util.math.geom.d3;

/**
 * not a surface in the typical mathematical sense;
 * this should only be for flat shapes (triangles, squares, polygons),
 * not curved.
 * 
 * @author ralitski
 */
public interface Surface3d extends Shape3d {
	Plane getPlane();
}
