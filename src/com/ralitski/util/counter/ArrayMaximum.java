package com.ralitski.util.counter;


public class ArrayMaximum implements Maximum {
	
	private int[] max;
	
	public ArrayMaximum(int[] max) {
		this.max = max;
	}
	
	@Override
	public int get(int index) {
		return index < max.length ? (index < 0 ? 0 : max[index]) : 0;
	}

}
