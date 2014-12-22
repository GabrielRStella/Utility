package com.ralitski.util.map;

import java.util.Map.Entry;

/**
 *
 * @author ralitski
 */
public interface DeepMapData<V> {
    
    V get(int id);
    void set(int id, V data);
}
