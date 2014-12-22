package com.ralitski.util.math.geom.d3.transform;

import com.ralitski.util.math.geom.d3.Vector3d;

public interface Transformation3d {
	Vector3d transform(Vector3d v);
}
