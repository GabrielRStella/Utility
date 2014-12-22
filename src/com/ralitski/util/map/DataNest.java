package com.ralitski.util.map;

/**
 *
 * @author ralitski
 */
public class DataNest<V> implements DeepMapData<V> {
    
    private int depth;
    protected DeepMapData<V> TREE_0;
    protected DeepMapData<V> TREE_1;
    
    public DataNest(int depth) {
        this.depth = depth;
    }

    @Override
    public V get(int id) {
        if((id & 1) == 0) {
            return TREE_0 != null ? TREE_0.get(shift(id)) : null;
        } else {
            return TREE_1 != null ? TREE_1.get(shift(id)) : null;
        }
    }

    @Override
    public void set(int id, V data) {
        if((id & 1) == 0) {
            if(TREE_0 != null) {
                if(data != null) {
                    TREE_0.set(shift(id), data);
                } else {
                    TREE_0 = null;
                }
            } else if(data != null) {
                if(depth == Integer.SIZE - 1) {
                    TREE_0 = new DataNestedValue(data);
                } else {
                    TREE_0 = new DataNest(depth + 1);
                    TREE_0.set(shift(id), data);
                }
            }
        } else {
            if(TREE_1 != null) {
                if(data != null) {
                    TREE_1.set(shift(id), data);
                } else {
                    TREE_1 = null;
                }
            } else if(data != null) {
                if(depth == Integer.SIZE - 1) {
                    TREE_1 = new DataNestedValue(data);
                } else {
                    TREE_1 = new DataNest(depth + 1);
                    TREE_1.set(shift(id), data);
                }
            }
        }
    }
    
    private int shift(int i) {
        return i >>> 1;
    }

}
