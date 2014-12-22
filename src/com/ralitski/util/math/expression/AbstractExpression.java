package com.ralitski.util.math.expression;

/**
 *
 * @author ralitski
 */
public abstract class AbstractExpression implements Expression {

    @Override
    public boolean isAbsolute() {
        return false;
    }

    @Override
    public float evaluateAbsolute() {
        throw new UnsupportedOperationException("Absolute evaluation called on non-absolute expression.");
    }

    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();
}