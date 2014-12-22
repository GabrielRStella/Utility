package com.ralitski.util.math.geom.d2.func;

import com.ralitski.util.math.expression.Expression;
import com.ralitski.util.math.expression.FunctionExpression;

public class CosFunction implements Function {
	
	public static final CosFunction INSTANCE = new CosFunction();
	public static final Function INSTANCE_2 = new PowFunction(INSTANCE);
	
	private CosFunction() {}

	@Override
	public Expression express() {
		//cheatz lel
		return new FunctionExpression(this);
	}

	@Override
	public float getY(float x) {
		return (float)Math.cos(x);
	}
	
	public String toString() {
		return "cos(x)";
	}

}
