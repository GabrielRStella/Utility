package com.ralitski.util.math.var;

import com.ralitski.util.math.Mathable;

import java.util.concurrent.Callable;

/**
 *
 * @author ralitski
 */
public class VariableReference implements Mathable {
    
    private float added;
    private float multiplied;
    private Callable<Float> value;
    
    public VariableReference(Callable<Float> value) {
        if(value == null) {
            throw new IllegalArgumentException("Variable Reference callable value can not be null.");
        }
        this.value = value;
    }
    
    @Override
    public float value() {
        try {
            return (value.call() + added) * multiplied;
        } catch (Exception ex) {
            return Float.NaN;
        }
    }

    @Override
    public void add(float value) {
        added += value;
    }

    @Override
    public void multiply(float value) {
        multiplied *= value;
    }
}
