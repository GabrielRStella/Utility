package com.ralitski.util.map;

import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.Map.Entry;

import com.ralitski.util.Identifier;
import com.ralitski.util.Identifiers;

/**
 * Experimenting with a faster map. Ended up being much slower than HashMap. :(
 * 
 * @author ralitski
 */
public class FastMap<K, V> extends DataNest<Entry<K, V>> implements Iterable<Entry<K, V>> {
    
    public static <K, V> FastMap<K, V> withHashCodeIdentifier() {
        return new FastMap<K, V>(new Identifiers.HashcodeIdentifier());
    }
    
    public static <K, V> FastMap<K, V> withReflectingIdentifier() {
        return new FastMap<K, V>(new Identifiers.ReflectingIdentifier());
    }
    
    private Identifier<? super K> identifier;
    
    private int size;
    
    public FastMap(Identifier<? super K> identifier) {
        super(0);
        this.identifier = identifier;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public V get(K key) {
        try {
            K realKey = (K)key;
            Entry<K, V> value = get(identifier.id(realKey));
            return value != null ? value.getValue() : null;
        } catch(Exception e) {
            return null;
        }
    }

    public V put(K key, V value) {
        V oldValue = get(key);
        if(oldValue == null && value != null) {
            size++;
        } else if(oldValue != null && value == null) {
            size--;
        }
        set(identifier.id(key), new SimpleEntry<K, V>(key, value));
        return oldValue;
    }

    public V remove(K key) {
    	return put(key, null);
    }

    public void clear() {
        this.TREE_0 = null;
        this.TREE_1 = null;
    }
    
    public Iterator<Entry<K, V>> iterator() {
    	return new FastMapIterator();
    }
    
    private class FastMapIterator implements Iterator<Entry<K, V>> {
    	
    	private FastMapIteratorInternal internal = new FastMapIteratorInternal();
    	private Entry<K, V> previous;
    	private Entry<K, V> next;
    	private boolean started;

		@Override
		public boolean hasNext() {
			return !started || next != null;
		}

		@Override
		public Entry<K, V> next() {
			started = true;
			previous = next;
			next = internal.next();
			return next;
		}

		@Override
		public void remove() {
			if(previous != null) {
				FastMap.this.remove(previous.getKey());
			} else {
				throw new IllegalStateException("FastMap iterator hasn't started yet");
			}
		}
    	
    }
    
    private class FastMapIteratorInternal {

    	private DeepMapData<Entry<K, V>> prevSource = FastMap.this;
    	private DeepMapData<Entry<K, V>> source = FastMap.this;
    	
    	public Entry<K, V> next() {
    		if(source instanceof DataNest) {
    			DataNest<Entry<K, V>> nest = (DataNest<Entry<K, V>>)source;
    			DeepMapData<Entry<K, V>> TREE_0 = nest.TREE_0;
    			DeepMapData<Entry<K, V>> TREE_1 = nest.TREE_1;
    			if(TREE_0 != null) {
    	    		prevSource = source;
    				source = TREE_0;
    				//mmm recursion
    				return next();
    			} else if(TREE_1 != null) {
    				prevSource = source;
    				source = TREE_1;
    				return next();
    			} else {
    				source = prevSource;
    				
    				//switch to next branch
    	    		if(source instanceof DataNest) {
    	    			DataNest<Entry<K, V>> nest2 = (DataNest<Entry<K, V>>)source;
    	    			source = nest2.TREE_1;
    	    		}
    				return next();
    			}
    		} else {
    			DataNestedValue<Entry<K, V>> nest = (DataNestedValue<Entry<K, V>>)source;
    			Entry<K, V> value = nest.get(0);
    			if(value != null) {
    				source = prevSource;
    				
    				//switch to next branch
    	    		if(source instanceof DataNest) {
    	    			DataNest<Entry<K, V>> nest2 = (DataNest<Entry<K, V>>)source;
    	    			source = nest2.TREE_1;
    	    		}
    	    		
    				return value;
    			} else {
    				//...well crap
    				return null;
    			}
    		}
    	}
    }

}