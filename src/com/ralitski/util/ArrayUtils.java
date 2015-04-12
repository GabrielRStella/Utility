package com.ralitski.util;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArrayUtils {
	
	public static <T> T random(T... array) {
		return random(new Random(), array);
	}
	
	public static <T> T random(Random random, T...array) {
		return array[random.nextInt(array.length)];
	}
    
	/**
	 * returns a copy of the given array without duplicate or null elements
	 * @param type the type of the array
	 * @param array the array to be copied
	 * @return a copy of the given array without duplicate or null elements
	 */
    public static <T> T[] eliminateEquivalentAndNull(Class<T> type, T[] array) {
        if(array == null) return type != null ? (T[]) Array.newInstance(type, 0) : null;
        T[] newArray = (T[]) Array.newInstance(type, array.length);
        int length = 0;
        for(int i = 0; i < array.length; i++) {
            T t = array[i];
            if(t != null && !containsAnyEquivalentWithMax(newArray, length, t)) {
                newArray[length++] = t;
            }
        }
        return Arrays.copyOf(newArray, length);
    }
    
//    public static <T> boolean equals(T[] t1, T[] t2) {
//        if(t1 == null && t2 == null) return true;
//        if(t1 == null || t2 == null) return false;
//        if(t1.length != t2.length) return false;
//        boolean[] equals = new boolean[t1.length];
//        for(int i = 0; i < t1.length; i++) {
//            equals[i] = equivalent(t1, t2);
//        }
//        return isTrue(equals);
//    }
    
    public static <T> T[] combineNoNull(Class<T> type, T[]...originals) {
        T[] newArray = combine(type, originals);
        return newArray == null ? (T[]) Array.newInstance(type, 0) : newArray;
    }
    
    public static <T> T[] combine(Class<T> type, T[]... originals) {
        if(originals.length == 0) return null;
        int newSize = 0;
        for(T[] t : originals) if(t != null) newSize += t.length;
        T[] newArray = (T[]) Array.newInstance(type, newSize);
        int index = 0;
        for(int i = 0; i < originals.length; i++) {
            T[] array = originals[i];
            if(array != null) {
                for (int j = 0; j < array.length; j++) {
                    newArray[index++] = array[j];
                }
            }
        }
        return newArray;
    }
    
    public static float[] combine(float[]... originals) {
        int newSize = 0;
        if(originals.length == 0) return new float[0];
        for(float[] t : originals) newSize += t.length;
        float[] newArray = Arrays.copyOf(originals[0], newSize);
        int index = originals[0].length;
        for(int i = 1; i < originals.length; i++) {
            float[] array = originals[i];
            for(int j = 0; j < array.length; j++) {
                newArray[index++] = array[j];
            }
        }
        return newArray;
    }
    
    //used for matrices
    public static <T> Object[][] flip(T[][] table) {
        int xSize = table.length;
        int ySize = table.length > 0 ? table[0].length : (xSize = 0);
        Object[][] result = new Object[ySize][xSize];
        for(int x = 0; x < xSize; x++) {
            for(int y = 0; y < ySize; y++) {
                result[y][x] = table[x][y];
            }
        }
        return result;
    }
    
    public static float[][] flip(float[][] table) {
        int xSize = table.length;
        int ySize = table.length > 0 ? table[0].length : (xSize = 0);
        float[][] result = new float[ySize][xSize];
        for(int x = 0; x < xSize; x++) {
            for(int y = 0; y < ySize; y++) {
                result[y][x] = table[x][y];
            }
        }
        return result;
    }
    
    public static <T extends Enum> T valueOf(Class<T> clazz, String value) {
        try {
            Method m = clazz.getDeclaredMethod("valueOf", String.class);
            Object o = m.invoke(null, value);
            return (T)o;
        } catch (Exception ex) {
            Logger.getLogger(ArrayUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static <T extends Enum> T[] values(Class<T> clazz) {
        try {
            Method m = clazz.getDeclaredMethod("values");
            Object o = m.invoke(null);
            return (T[])o;
        } catch (Exception ex) {
            Logger.getLogger(ArrayUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static <T extends Enum> T cycle(T t) {
        if(t == null) return null;
        Enum[] values = values(t.getClass());
        int i = cycleIndex(t, values);
        return i == -1 ? null : (T)values[i];
    }
    
    public static <T extends Enum> int cycleIndex(T t) {
        Enum[] values = values(t.getDeclaringClass());
        return cycleIndex(t, values);
    }
    
    private static <T extends Enum> int cycleIndex(T t, Enum[] values) {
        if(t == null) return 0;
        if(values != null) {
            for(int i = 0; i < values.length; i++) {
                if(values[i] == t) {
                    int index = (i + 1) % values.length;
                    return index;
                }
            }
        }
        return -1;
    }
    
    public static <T extends Enum> int ordinal(T t) {
        try {
            Method m = t.getClass().getMethod("ordinal");
            Object o = m.invoke(t);
            return ((Integer)o).intValue();
        } catch (Exception ex) {
            Logger.getLogger(ArrayUtils.class.getName()).log(Level.SEVERE, "could not find ordinal of " + t.name(), ex);
        }
        return -1;
    }
    
    public static <T> boolean containsAnyOf(T[] array, T... values) {
        for (T value : values) {
            for (T item : array) {
                if (item == value) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static <T> boolean containsAllOf(T[] array, T... values) {
        boolean[] ret = new boolean[values.length];
        for (int i = 0; i < values.length; i++) {
            ret[i] = false; //initialize it
            for (T item : array) {
                if (item == values[i]) {
                    ret[i] = true;
                    break;
                }
            }
        }
        return isTrue(ret);
    }

    public static <T> boolean containsAnyEquivalent(T[] array, T... values) {
        for (T value : values) {
            for (T t : array) {
                if (equivalent(value, t)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <T> boolean containsAnyEquivalentWithMax(T[] array, int max, T... values) {
        for(int index = 0; index < Math.min(array.length, max); index++) {
            T value = array[index];
            for(T t : values) {
                if(equivalent(value, t)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <T> boolean containsAllEquivalentOf(T[] array, T... values) {
        boolean[] ret = new boolean[values.length];
        for (int i = 0; i < values.length; i++) {
            ret[i] = false; //initialize it
            for (T item : array) {
                if (equivalent(values[i], item)) {
                    ret[i] = true;
                    break;
                }
            }
        }
        return isTrue(ret);
    }

    public static <T> boolean equals(T item, T... values) {
        for (T value : values) {
            if (item == value) {
                return true;
            }
        }
        return false;
    }

	public static <T> boolean contains(T value, T...array) {
        for (T t : array) {
            if (equivalent(t, value)) {
                return true;
            }
        }
        return false;
    }
    
    public static <T> boolean equivalent(T first, T second) {
        return first == second || (first != null && second != null && first.equals(second));
    }
    
    public static boolean isTrue(boolean[] flagArray) {
        for (boolean flag : flagArray) {
            if(!flag) {
                return false;
            }
        }
        return true;
    }
    
    public static <T> Iterable<T> getIterable(T...t) {
        return new ArrayIterable<T>(t.length, t);
    }
    
    public static <T> Iterable<T> getIterable(int len, T...t) {
        return new ArrayIterable<T>(len, t);
    }
    
    public static <T> Iterator<T> getIterator(T...t) {
        return new ArrayIterator<T>(t.length, t);
    }
    
    public static <T> Iterator<T> getIterator(int len, T...t) {
        return new ArrayIterator<T>(len, t);
    }
    
    public static <T> Iterable<T> getLoopIterable(T...t) {
    	return getLoopIterable(t.length, t);
    }
    
    public static <T> Iterable<T> getLoopIterable(int length, T...t) {
        return new ArrayLoopIterable<T>(t.length, t);
    }
    
    public static <T> Iterator<T> getLoopIterator(T...t) {
        return getLoopIterator(t.length, t);
    }
    
    public static <T> Iterator<T> getLoopIterator(int length, T...t) {
        return new ArrayLoopIterator<T>(t.length, t);
    }
    
    private static class ArrayIterable<T> implements Iterable<T> {
    	
    	private int len;
    	private T[] t;
    	
    	private ArrayIterable(int len, T...t) {
    		this.len = len;
    		this.t = t;
    	}

		@Override
		public Iterator<T> iterator() {
			return new ArrayIterator<T>(len, t);
		}
    	
    }
    
    private static class ArrayIterator<T> implements Iterator<T> {
        
    	private int len;
        private T[] t;
        private int nextIndex;
        
        private ArrayIterator(int len, T...t) {
        	this.len = len;
            this.t = t;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < t.length && nextIndex < len;
        }

        @Override
        public T next() {
            return t[nextIndex++];
        }

        @Override
        public void remove() {
            //I don't particularly like doing this.
            //t[nextIndex] = null;
        }
        
    }
    
    private static class ArrayLoopIterable<T> implements Iterable<T> {
    	
    	private int len;
    	private T[] t;
    	
    	private ArrayLoopIterable(int len, T...t) {
    		this.len = len;
    		this.t = t;
    	}

		@Override
		public Iterator<T> iterator() {
			return new ArrayLoopIterator<T>(len, t);
		}
    	
    }
    
    private static class ArrayLoopIterator<T> implements Iterator<T> {
        
    	private int len;
        private T[] t;
        private int nextIndex;
        
        private ArrayLoopIterator(int len, T...t) {
        	this.len = len;
            this.t = t;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public T next() {
        	T next = t[nextIndex++];
        	nextIndex %= len;
            return next;
        }

        @Override
        public void remove() {
        }
        
    }
}