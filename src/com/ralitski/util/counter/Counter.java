package com.ralitski.util.counter;


public class Counter {
	
	private int[] values;
	private Maximum max;
	
	public Counter(int length, int max) {
		this(length, new SimpleMaximum(max));
	}
	
	public Counter(int[] max) {
		this(max.length, new ArrayMaximum(max));
	}
	
	public Counter(int length, Maximum max) {
		this.values = new int[length];
		this.max = max;
	}
	
	public int[] get() {
		return values;
	}
	
	public boolean hasNext() {
		try {
			int index = 0;
			while(values[index] == max.get(index)) {
				index++;
			}
			return true;
		} catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	public boolean next() {
		try {
			int index = 0;
			while(values[index] == max.get(index)) {
				index++;
			}
			values[index]++;
			if(index > 0) {
				for(int i = 0; i < index; i++) {
					values[i] = 0;
				}
			}
			return true;
		} catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
}
