package com.ralitski.util;

import org.magicwerk.brownies.collections.GapList;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * A fast implementation of a multi-dimensional sparse array.
 *
 * A sparse array is meant to store arrays with a coordinate system,
 * rather than just as indexes. As a result, less memory is used.
 * 
 * @author foodyling
 */
public class SparseArray<T> implements Iterable<T> {
    private final int dimension;
    private final GapList<Entry<T>> backbone;
    private int modCount;

    public SparseArray(int dimension) {
        this(dimension, 16);
    }

    public SparseArray(int dimension, int initialCapacity) {
        this(dimension, initialCapacity, -1);
    }

    public SparseArray(int dimension, int initialCapacity, int maximumCapacity) {
        this.dimension = dimension;
        this.backbone = new GapList<Entry<T>>(initialCapacity, maximumCapacity);
    }

    public int getDimension() {
        return dimension;
    }

    public T addItem(int[] coordinates, T item) {
        if (coordinates.length == dimension) {
            this.modCount++;
            int index = this.binarySearch(coordinates);
            if (index < backbone.size()) {
                Entry<T> entry = backbone.get(index);
                if (compare(backbone.get(index).coordinates, coordinates) == 0) {
                    return entry.setItem(item);
                } else {
                    backbone.add(index, new Entry<T>(coordinates.clone(), item));
                }
            } else {
                backbone.add(index, new Entry<T>(coordinates.clone(), item));
            }
        } else {
            throw new IllegalArgumentException("Coordinates are not of dimension " + dimension);
        }
        return null;
    }

    public T getItem(int[] coordinates) {
        if (coordinates.length == dimension) {
            int index = this.binarySearch(coordinates);
            if (index < backbone.size()) {
                Entry<T> entry = backbone.get(index);
                if (entry != null && compare(entry.coordinates, coordinates) == 0) {
                    return entry.item;
                }
            }
        } else {
            throw new IllegalArgumentException("Coordinates are not of dimension " + dimension);
        }
        return null;
    }

    public Entry<T> getIndex(int index) {
        return backbone.get(index);
    }

    public T removeItem(int[] coordinates) {
        int index = this.binarySearch(coordinates);
        if (index < backbone.size()) {
            Entry<T> entry = backbone.get(index);
            if (entry != null && compare(entry.coordinates, coordinates) == 0) {
                this.modCount++;
                backbone.remove(index);
                return entry.item;
            }
        }
        return null;
    }

    public boolean contains(int[] coordinates) {
        if (coordinates.length == dimension) {
            int index = this.binarySearch(coordinates);
            if (index < backbone.size()) {
                Entry<T> entry = backbone.get(index);
                return entry != null && compare(entry.coordinates, coordinates) == 0;
            }
        } else {
            throw new IllegalArgumentException("Coordinates are not of dimension " + dimension);
        }
        return false;
    }

    public int size() {
        return backbone.size();
    }

    public void clear() {
        backbone.clear();
    }

    protected int binarySearch(int[] coordinates) {
        int low = -1, high = backbone.size(), guess, compared;
        while (1 < high - low) {
            guess = (low + high) >> 1;
            Entry<T> entry = backbone.get(guess);
            compared = compare(entry.coordinates, coordinates);
            if (compared == -1) {
                high = guess;
            } else if (compared == 1) {
                low = guess;
            } else if (compared == 0) {
                return guess;
            }
        }
        return high;
    }

    private static int compare(int[] coords1, int[] coords2) {
        for (int i = 0; i < coords1.length; i++) {
            if (coords1[i] < coords2[i]) {
                return 1;
            } else if (coords2[i] < coords1[i]) {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new SparseIterator<T>(this);
    }

    public static final class Entry<T> {
        protected final int[] coordinates;
        protected T item;

        public Entry(int[] coordinates, T item) {
            this.coordinates = coordinates;
            this.item = item;
        }

        public int[] getCoordinates() {
            return coordinates.clone();
        }

        public T getItem() {
            return item;
        }

        public T setItem(T item) {
            T oldItem = item;
            this.item = item;
            return oldItem;
        }
    }

    private static final class SparseIterator<T> implements Iterator<T> {
        private final SparseArray<T> sparseArray;
        private final int modCount;
        private int index;

        public SparseIterator(SparseArray<T> sparseArray) {
            this.sparseArray = sparseArray;
            this.modCount = sparseArray.modCount;
        }

        @Override
        public boolean hasNext() {
            return index < sparseArray.backbone.size();
        }

        //note: next() and remove() seem out of sync
        
        @Override
        public T next() {
            if (sparseArray.modCount == modCount) {
                if (hasNext()) {
                    Entry<T> entry = sparseArray.backbone.get(index++);
                    if (entry != null) {
                        return entry.item;
                    } else {
                        return null;
                    }
                } else {
                    throw new IllegalStateException("No next item in iterator.");
                }
            } else {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void remove() {
            if(index > 0) {
            	sparseArray.backbone.remove(--index);
            }
        }
    }
}