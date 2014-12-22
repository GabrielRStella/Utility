package com.ralitski.util.render.model;

import java.io.*;
import org.lwjgl.util.vector.Vector3f;


public class ObjModelLoader {

	public static Model load(File f) throws FileNotFoundException, IOException
	{
		Model m = new Model();
		
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line;
		while((line = reader.readLine()) != null)
		{
			boolean hasNormals = true;
			String vertex = "v ";
			String normal = "vn ";
			String face = "f ";
			if(line.startsWith(vertex))
			{
				String[] s = line.split(" ");
				int off = hasNormals ? 0 : 1;
				float x = Float.valueOf(s[off + 1]);
				float y = Float.valueOf(s[off + 2]);
				float z = Float.valueOf(s[off + 3]);
				m.vertices.add(new Vector3f(x, y, z));
			}
			else if(line.startsWith(normal))
			{
				String[] s = line.split(" ");
				int off = hasNormals ? 0 : 1;
				float x = Float.valueOf(s[off + 1]);
				float y = Float.valueOf(s[off + 2]);
				float z = Float.valueOf(s[off + 3]);
				m.normals.add(new Vector3f(x, y, z));
			}
			else if(line.startsWith(face))
			{
				if(hasNormals)
				{
					String[] s = line.split(" ");
					String[] xs = s[1].split("/");
					String[] ys = s[2].split("/");
					String[] zs = s[3].split("/");
					Vector3f vertexIndices = new Vector3f(Float.valueOf(xs[0]), Float.valueOf(ys[0]), Float.valueOf(zs[0]));
					Vector3f normalIndices = new Vector3f(Float.valueOf(xs[2]), Float.valueOf(ys[2]), Float.valueOf(zs[2]));
					Face fa = new Face(vertexIndices, normalIndices);
					m.faces.add(fa);
				} else
				{
					String[] s = line.split(" ");
					float x = Float.valueOf(s[1]);
					float y = Float.valueOf(s[2]);
					float z = Float.valueOf(s[3]);
					Face fa = new Face(new Vector3f(x, y, z));
					m.faces.add(fa);
				}
			}
		}
		reader.close();
		return m;
	}
}
