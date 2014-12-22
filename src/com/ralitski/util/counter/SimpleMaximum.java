package com.ralitski.util.counter;


public class SimpleMaximum implements Maximum {
	
	private int max;
	
	public SimpleMaximum(int max) {
		this.max = max;
	}

	@Override
	public int get(int index) {
		return max;
	}

}
