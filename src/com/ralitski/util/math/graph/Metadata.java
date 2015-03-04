package com.ralitski.util.math.graph;

import java.util.HashMap;
import java.util.Map;

public class Metadata {
	private Map<Integer, Object> metadata = new HashMap<Integer, Object>();
	
	public void setData(int i, Object o) {
		metadata.put(i, o);
	}
	
	public Object getData(int i) {
		return metadata.get(i);
	}

	public boolean hasData(int key) {
		return metadata.containsKey(key);
	}
	
	public String toString() {
		return metadata.toString();
	}
}
