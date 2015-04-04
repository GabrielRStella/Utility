package com.ralitski.util.math.geom.d2.func;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.ralitski.util.doc.TODO;
import com.ralitski.util.math.expression.Expression;
import com.ralitski.util.math.expression.FunctionExpression;

public class ReflectingFunction implements Function {
	
	private Object host;
	private Method func;
	
	public ReflectingFunction(Class<?> type, String func) throws SecurityException, NoSuchMethodException {
		this(null, type, func);
	}
	
	public ReflectingFunction(Object host, Class<?> type, String func) throws SecurityException, NoSuchMethodException {
		Method m;
		try {
			m = type.getDeclaredMethod(func, float.class);
		} catch (Exception e) {
			m = type.getDeclaredMethod(func, double.class);
		}
		this.host = host;
		this.func = m;
	}
	
	public ReflectingFunction(Object host, Method func) {
		this.host = host;
		this.func = func;
	}

	@Override
	public float getY(float x) {
		try {
			//Object o = func.invoke(host, x);
			float f = ((Number)func.invoke(host, x)).floatValue();
			return f;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	@TODO("not this")
	public Expression express() {
		//throw new UnsupportedOperationException("Unable to determin expression equivalent of reflected function");
		return new FunctionExpression(this);
	}
	
	public String toString() {
		return func.getName() + "(x)";
	}

//	@Override
//	public IntervalCompound getRealInterval() {
//		return IntervalCompound.ALL;
//	}

}
