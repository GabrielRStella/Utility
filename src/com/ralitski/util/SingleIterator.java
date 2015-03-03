package com.ralitski.util;

import java.util.Iterator;

public class SingleIterator<T> implements Iterator<T> {
	
	private T t;
	private boolean go;
	
	public SingleIterator(T t) {
		this.t = t;
	}

	@Override
	public boolean hasNext() {
		return go;
	}

	@Override
	public T next() {
		boolean flag = go;
		go = false;
		return flag ? t : null;
	}

	@Override
	public void remove() {
		//no
	}

}
