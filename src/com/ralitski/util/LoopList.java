package com.ralitski.util;

public class LoopList<E> {

	public E[] values;
	int index = 0;
	
	public LoopList(E[] values)
	{
		this.values = values;
	}
	
	@SuppressWarnings("unchecked")
	public LoopList(int size)
	{
		this.values = (E[])new Object[size];
	}
	
	private void shift()
	{
		this.index++;
		if(this.index >= this.values.length) this.index %= this.values.length; 
	}
	
	private void shift(int i)
	{
		this.shift(i, false);
	}
	
	private void shift(int i, boolean flag)
	{
		this.index = i;
		if(flag) this.index++;
		if(this.index >= this.values.length) this.index %= this.values.length;
	}
	
	public int offsetIndex(int i)
	{
		return (i + this.index) % this.values.length;
	}
	
	public boolean add(E value) {
		values[index] = value;
		this.shift();
		return true;
	}
	
	public E set(int index, E value) {
		int i = this.offsetIndex(index);
		E e = this.values[i];
		this.values[i] = value;
		return e;
	}
	
	public E get(int index) {
		return this.values[this.offsetIndex(index)];
	}
	
	public boolean isEmpty() {
		for(int n = 0; n < this.values.length; n++)
		{
			if(this.values[n] != null) return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public void clear() {
		this.values = (E[])new Object[this.values.length];
		this.shift(0);
	}
	
	public int size() {
		return this.values.length;
	}

}
