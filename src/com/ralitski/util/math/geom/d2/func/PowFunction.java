package com.ralitski.util.math.geom.d2.func;

import com.ralitski.util.math.expression.Expression;
import com.ralitski.util.math.expression.Expressions;

public class PowFunction implements Function {
	
	private Function base;
	private float power;
	
	public PowFunction(Function base) {
		//default
		this(base, 2);
	}
	
	public PowFunction(Function base, float power) {
		this.base = base;
		this.power = power;
	}

	@Override
	public Expression express() {
		return Expressions.pow(base.express(), Expressions.valueOf(power));
	}

	@Override
	public float getY(float x) {
		return (float)Math.pow(base.getY(x), power);
	}
	
	public String toString() {
		return "(" + base + ")^(" + power + ")";
	}

}
