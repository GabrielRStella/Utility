package com.ralitski.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ralitski
 */
public class MapList<K, V> {
    
    private Map<K, List<V>> map;
    
    public MapList() {
        this.map = new HashMap<K, List<V>>();
    }
    
    private List<V> generateList() {
    	return new ArrayList<V>();
    }
    
    public boolean contains(K k, V v) {
    	List<V> list = this.get(k);
    	return list.contains(v);
    	/*
    	for(V v2 : list) {
    		if(v.equals(v2)) return true;
    	}
    	return false;
    	 */
    }
    
    public int size() {
    	int ret = 0;
    	
    	for(List<V> list : this.map.values()) {
    		ret += list.size();
    	}
    	
    	return ret;
    }
    
    public void copy(MapList<K, V> mapList) {
    	for(K k : mapList.keySet()) {
    		List<V> list = get(k);
    		for(V v : mapList.get(k)) {
    			list.add(v);
    		}
    	}
    }
    
    public void clear() {
    	this.map.clear();
    }
    
    public Map<K, List<V>> get() {
        return this.map;
    }
    
    public Set<K> keySet() {
        return this.map.keySet();
    }
    
    public void add(K key) {
        this.map.put(key, generateList());
    }
    
    public boolean add(K key, V value) {
        List<V> values = this.map.get(key);
        if(values == null) {
            this.map.put(key, values = generateList());
        }
        return values.add(value);
    }
    
    public List<V> get(K key) {
        List<V> values = this.map.get(key);
        if(values == null) {
            this.map.put(key, values = generateList());
        }
        return values;
    }
    
    public V get(K key, int index) {
        List<V> values = this.map.get(key);
        if(values == null) {
            this.map.put(key, values = generateList());
            return null;
        }
        return values.get(index);
    }
    
    public boolean remove(K key, V value) {
        List<V> values = this.map.get(key);
        if(values == null) {
            this.map.put(key, values = generateList());
        }
        return values.remove(value);
    }

	public boolean containsKey(K key) {
		return map.containsKey(key);
	}
}
