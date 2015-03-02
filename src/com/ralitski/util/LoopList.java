package com.ralitski.util;

import java.util.Iterator;

/**
 * A super cool list type that will store a finite number of values in an array, looping through old values. Good for tracking position.
 * 
 * @author ralitski
 *
 * @param <E> The data type of this list
 */
public class LoopList<E> implements Iterable<E> {

	private Object[] values;
	private int index = 0;
	
	public LoopList(E[] values)
	{
		this.values = values;
	}
	
	public LoopList(int size)
	{
		this.values = new Object[size];
	}
	
	private void shift()
	{
		index++;
		if(index >= values.length) index %= values.length; 
	}
	
	private void shift(int i)
	{
		index = i % values.length;
	}
	
	/**
	 * Used internally to keep track of position offset, but can be used externally to interact directly with the stored array using get(wanted index - offsetIndex(wantedIndex))
	 * @param i the index, relative to the LoopList's current position
	 * @return
	 */
	public int offsetIndex(int i)
	{
		return (i + this.index) % this.values.length;
	}
	
	/**
	 * Append a value to the list and shift the current position by one.
	 * @param value The value to be appended
	 */
	public void add(E value) {
		values[index] = value;
		this.shift();
	}
	
	/**
	 * Sets the value at a certain position in the list.
	 * @param index The index, relative to the LoopList's position, to be changed
	 * @param value new value to be set at the specified position
	 * @return The previous value at that space
	 */
	@SuppressWarnings("unchecked")
	public E set(int index, E value) {
		int i = this.offsetIndex(index);
		E e = (E)this.values[i];
		this.values[i] = value;
		return e;
	}
	
	@SuppressWarnings("unchecked")
	public E get(int index) {
		return (E)this.values[this.offsetIndex(index)];
	}
	
	public boolean isEmpty() {
		for(int n = 0; n < this.values.length; n++)
		{
			if(this.values[n] != null) return false;
		}
		return true;
	}

	public void clear() {
		this.values = new Object[this.values.length];
		this.shift(0);
	}
	
	public int size() {
		return this.values.length;
	}
	
	/**
	 * Returns an iterator over this LoopList. The iteration begins at the LoopList's relative start.
	 */
	@Override
	public Iterator<E> iterator() {
		return new LoopListIterator();
	}
	
	private class LoopListIterator implements Iterator<E> {
		
		private int iterIndex;

		@Override
		public boolean hasNext() {
			return iterIndex < values.length;
		}

		@Override
		public E next() {
			return get(iterIndex++);
		}

		@Override
		public void remove() {
			set(iterIndex - 1, null);
		}
		
	}

}
