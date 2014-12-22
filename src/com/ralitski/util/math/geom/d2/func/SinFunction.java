package com.ralitski.util.math.geom.d2.func;

import com.ralitski.util.math.expression.Expression;
import com.ralitski.util.math.expression.FunctionExpression;

public class SinFunction implements Function {

	public static final SinFunction INSTANCE = new SinFunction();
	public static final Function INSTANCE_2 = new PowFunction(INSTANCE);
	
	private SinFunction() {}

	@Override
	public Expression express() {
		//cheatz lel
		return new FunctionExpression(this);
	}

	@Override
	public float getY(float x) {
		return (float)Math.sin(x);
	}
	
	public String toString() {
		return "sin(x)";
	}

}
