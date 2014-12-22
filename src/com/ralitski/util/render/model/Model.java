package com.ralitski.util.render.model;

import java.util.List;
import java.util.ArrayList;
import org.lwjgl.util.vector.Vector3f;


public class Model {

public List<Vector3f> vertices = new ArrayList<Vector3f>();
public List<Vector3f> normals = new ArrayList<Vector3f>();
public List<Face> faces = new ArrayList<Face>();

public Model()
{
	
}
}

