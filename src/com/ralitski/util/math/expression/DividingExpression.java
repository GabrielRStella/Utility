package com.ralitski.util.math.expression;

import tempgeom.IntervalCompound;

import com.ralitski.util.ArrayUtils;
import com.ralitski.util.math.var.VariableSet;

public class DividingExpression extends AbstractExpression {
	
	private Expression numerator;
	private Expression denominator;
	
	public DividingExpression(Expression numerator, Expression denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

    @Override
    public boolean isAbsolute() {
        return numerator.isAbsolute() && denominator.isAbsolute();
    }

    @Override
    public float evaluateAbsolute() {
    	if(isAbsolute()) {
    		return numerator.evaluateAbsolute() / denominator.evaluateAbsolute();
    	} else {
    		return super.evaluateAbsolute();
    	}
    }

	@Override
	public Expression simplify(VariableSet vars) {
		Expression e1 = numerator.simplify(vars);
		Expression e2 = denominator.simplify(vars);
		return new DividingExpression(e1, e2);
	}

	@Override
	public boolean hasVariable(String name) {
		return numerator.hasVariable(name) || denominator.hasVariable(name);
	}

	@Override
	public Expression[] getChildren() {
		return new Expression[]{numerator, denominator};
	}

	@Override
	public String[] getVariables() {
		return ArrayUtils.combine(String.class, numerator.getVariables(), denominator.getVariables());
	}

	@Override
	public String toString() {
		return "(" + numerator + ")/(" + denominator + ")";
	}

	@Override
	public boolean equals(Object o) {
		if(o == this) {
			return true;
		} else if(o instanceof DividingExpression) {
			DividingExpression e = (DividingExpression)o;
			return e.numerator.equals(numerator) && e.denominator.equals(denominator);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (numerator.hashCode() << 16) + (denominator.hashCode() & 65535);
	}

//	@Override
//	public IntervalCompound getRealInterval(String variable) {
//		IntervalCompound base = numerator.getRealInterval(variable).and(denominator.getRealInterval(variable));
//		if(denominator.hasVariable(variable)) {
//			//get rid of the inputs that result in a 0 in denominator
//		}
//		return base;
//	}

}
