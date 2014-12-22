package com.ralitski.util.math.expression;

import com.ralitski.util.math.geom.Interval;
import com.ralitski.util.math.geom.IntervalCompound;
import com.ralitski.util.math.var.VariableSet;

/**
 *
 * @author ralitski
 */
public class AbsoluteExpression implements Expression {

    private float value;

    public AbsoluteExpression(float value) {
        this.value = value;
    }

    @Override
    public boolean isAbsolute() {
        return true;
    }

    @Override
    public float evaluateAbsolute() {
        return value;
    }

    @Override
    public Expression simplify(VariableSet vars) {
        return this;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AbsoluteExpression) {
            AbsoluteExpression expr = (AbsoluteExpression) o;
            return expr.value == value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Float.floatToIntBits(this.value);
        return hash;
    }

    @Override
    public boolean hasVariable(String name) {
        return false;
    }

//    @Override
//    public void fill(MathableSet set) {
//    }

    @Override
    public Expression[] getChildren() {
        return null;
    }

    @Override
    public String[] getVariables() {
        return null;
    }

//	@Override
//	public IntervalCompound getRealInterval(String variable) {
//		return IntervalCompound.ALL;
//	}
}
