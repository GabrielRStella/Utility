package com.ralitski.util.math.expression;

import tempgeom.IntervalCompound;

import com.ralitski.util.math.geom.d2.func.Function;
import com.ralitski.util.math.var.VariableSet;

/**
 *
 * @author ralitski
 */
public class FunctionExpression extends AbstractExpression {
    
    private Function function;
    private Expression variable;
    
    public FunctionExpression(Function function) {
        this(function, new RelativeExpression("x"));
    }
    
    public FunctionExpression(Function function, Expression expr) {
        this.function = function;
        this.variable = expr;
    }
    
    @Override
    public String toString() {
        return function.toString().replaceAll("x", "(" + variable.toString() + ")");
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(o instanceof FunctionExpression) {
            FunctionExpression expr = (FunctionExpression)o;
            return this.function.equals(expr.function) && this.variable.equals(expr.variable);
        } else if(o instanceof Function) {
            return this.function.equals(o);
        } else return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + function.hashCode();
        hash = 37 * hash + variable.hashCode();
        return hash;
    }

    @Override
    public Expression simplify(VariableSet vars) {
        Expression e = variable.simplify(vars);
        if(e.isAbsolute()) {
            return Expressions.valueOf(function.getY(e.evaluateAbsolute()));
        } else {
            return this;
        }
    }

    @Override
    public boolean hasVariable(String name) {
        return variable.hasVariable(name);
    }

    @Override
    public Expression[] getChildren() {
        return new Expression[]{variable};
    }

    @Override
    public String[] getVariables() {
        return variable.getVariables();
    }

//	@Override
//	public IntervalCompound getRealInterval(String variable) {
//		IntervalCompound base = this.variable.getRealInterval(variable);
//		return base.and(function.getRealInterval());
//	}
    
}
