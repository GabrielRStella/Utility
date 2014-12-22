package com.ralitski.util;

//Because this project source level is 1.6, java.util.Objects doesn't exist. sadfaec
public class Objects {
	
	public static int hashCode(Object o) {
		return o != null ? o.hashCode() : 0;
	}
}
