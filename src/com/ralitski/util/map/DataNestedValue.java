package com.ralitski.util.map;

/**
 *
 * @author ralitski
 */
public class DataNestedValue<V> implements DeepMapData<V> {
    
    private V value;
    
    public DataNestedValue(V value) {
        this.value = value;
    }

    //id should be 0 by this time no matter what
    
    @Override
    public V get(int id) {
        return value;
    }

    @Override
    public void set(int id, V value) {
        this.value = value;
    }

}
