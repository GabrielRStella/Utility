package com.ralitski.util.math.expression;

import com.ralitski.util.ArrayUtils;
import com.ralitski.util.math.geom.IntervalCompound;
import com.ralitski.util.math.var.VariableSet;

public class ExponentialExpression extends AbstractExpression {
	
	private Expression base;
	private Expression power;
	
	public ExponentialExpression(Expression numerator, Expression denominator) {
		this.base = numerator;
		this.power = denominator;
	}

    @Override
    public boolean isAbsolute() {
        return base.isAbsolute() && power.isAbsolute();
    }

    @Override
    public float evaluateAbsolute() {
    	if(isAbsolute()) {
    		return (float)Math.pow(base.evaluateAbsolute(), power.evaluateAbsolute());
    	} else {
    		return super.evaluateAbsolute();
    	}
    }

	@Override
	public Expression simplify(VariableSet vars) {
		Expression e1 = base.simplify(vars);
		Expression e2 = power.simplify(vars);
		return new ExponentialExpression(e1, e2);
	}

	@Override
	public boolean hasVariable(String name) {
		return base.hasVariable(name) || power.hasVariable(name);
	}

	@Override
	public Expression[] getChildren() {
		return new Expression[]{base, power};
	}

	@Override
	public String[] getVariables() {
		return ArrayUtils.combine(String.class, base.getVariables(), power.getVariables());
	}

	@Override
	public String toString() {
		return "(" + base + ")^(" + power + ")";
	}

	@Override
	public boolean equals(Object o) {
		if(o == this) {
			return true;
		} else if(o instanceof ExponentialExpression) {
			ExponentialExpression e = (ExponentialExpression)o;
			return e.base.equals(base) && e.power.equals(power);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (base.hashCode() << 16) + (power.hashCode() & 65535);
	}

//	@Override
//	public IntervalCompound getRealInterval(String variable) {
//		//should be all...I guess
//		IntervalCompound base = this.base.getRealInterval(variable).and(power.getRealInterval(variable));
//		return base;
//	}

}
