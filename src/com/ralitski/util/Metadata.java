package com.ralitski.util;

import java.util.HashMap;
import java.util.Map;

/**
 * A very minimalist Metadata storage class. There may be a better way to do this (ie without Integer generics / Map; maybe using array), but meh...
 * 
 * @author ralitski
 */
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
