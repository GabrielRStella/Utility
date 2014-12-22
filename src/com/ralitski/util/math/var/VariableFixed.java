package com.ralitski.util.math.var;

import com.ralitski.util.math.Mathable;

/**
 *
 * @author ralitski
 */
public class VariableFixed implements Mathable {
    
    private float value;
    
    public VariableFixed(float value) {
        this.value = value;
    }

    @Override
    public void add(float value) {
        this.value += value;
    }

    @Override
    public void multiply(float value) {
        this.value *= value;
    }

    @Override
    public float value() {
        return value;
    }

}
