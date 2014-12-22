package com.ralitski.util.math.expression;

import com.ralitski.util.math.var.VariableSet;

/**
 *
 * @author ralitski
 */
public interface Expression {
    boolean isAbsolute();
    float evaluateAbsolute();
    
    Expression simplify(VariableSet vars);
//    void fill(MathableSet set);
    
    boolean hasVariable(String name);
    Expression[] getChildren();
    String[] getVariables();
    
    //public IntervalCompound getRealInterval(String variable);
}
