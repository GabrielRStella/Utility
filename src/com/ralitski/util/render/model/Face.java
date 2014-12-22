package com.ralitski.util.render.model;

import org.lwjgl.util.vector.Vector3f;

public class Face {
	public Vector3f vertex = new Vector3f();
	public Vector3f normal = new Vector3f();
	public boolean normals;

	public Face(Vector3f v1, Vector3f v2)
	{
		this(v1, v2, true);
	}

	public Face(Vector3f v)
	{
		this(v, new Vector3f(0.0F, 0.0F, 0.0F), false);
	}
	
	public Face(Vector3f v1, Vector3f v2, boolean flag)
	{
		this.vertex = v1;
		this.normal = v2;
		this.normals = flag;
	}
}
