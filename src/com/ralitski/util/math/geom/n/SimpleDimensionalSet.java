package com.ralitski.util.math.geom.n;

import com.ralitski.util.math.var.VariableFixed;
import com.ralitski.util.math.var.VariableSet;

import java.util.Arrays;

/**
 * this class provides a simple implementation of the DimensionalSet class for
 * use with floating point numbers. While this does function as a Point class,
 * classes such as Vector and HyperPlane extend this class in order to take
 * advantage of its method implementations; thus, this class is abstract, and a
 * separate Point class exists to clarify (and make sure a HyperPlane isn't
 * misused as a simple Point).
 * 
 * @author ralitski
 */
public abstract class SimpleDimensionalSet implements DimensionalSet<Float> {
    
    protected float[] data;
    
    public SimpleDimensionalSet(int...data) {
    	this.data = new float[data.length];
    	for(int i = 0; i < data.length; i++) {
    		this.data[i] = (int)data[i];
    	}
    }
    
    public SimpleDimensionalSet(float...data) {
        this.data = data;
    }

    @Override
    public int dimensions() {
        return data.length;
    }

	public float[] getRaw() {
		return data;
	}
    
    @Override
    public Float get(int index) {
        index--;
        return index < data.length ? data[index] : 0;
    }
    
    @Override
    public void set(int index, Float value) {
        index--;
        if(index < data.length - 1) {
            doSet(index, value);
        } else if(index == data.length - 1) {
            if(value == null || value == 0F) {
                lowerCapacity();
            } else {
                doSet(index, value);
            }
        } else if(value != null) {
            data = Arrays.copyOf(data, index + 1);
            data[index] = value;
        }
    }
    
    private void doSet(int index, Float value) {
        data[index] = value != null ? value : 0F;
    }
    
    private void lowerCapacity() {
        int newSize = data.length;
        boolean resize = false;
        while(data[--newSize] == 0) {
            resize = true;
        }
        if(resize) data = Arrays.copyOf(data, newSize + 2);
    }
    
    public VariableSet getVariableSet() {
        VariableSet set = new VariableSet();
        for(int i = 0; i < data.length; i++) {
            set.add(DimensionVariables.variableOf(i + 1), new VariableFixed(data[i]));
        }
        return set;
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(getClass() == o.getClass()) {
            SimpleDimensionalSet p = (SimpleDimensionalSet)o;
            for(int i = 0; i < p.data.length || i < data.length; i++) {
                float thisData = i >= data.length ? 0 : data[i];
                float otherData = i >= p.data.length ? 0 : p.data[i];
                if(thisData != otherData) return false;
            }
            return true;
        }
        return false;
    }

    //auto-generated
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Arrays.hashCode(this.data);
        return hash;
    }
}
