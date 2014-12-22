package com.ralitski.util.counter;

public class IncreasingMaximum implements Maximum {
	
	private static final IncreasingMaximum instance = new IncreasingMaximum();
	
	public static IncreasingMaximum get() {
		return instance;
	}
	
	private IncreasingMaximum() {}

	@Override
	public int get(int index) {
		return index + 1;
	}

}
