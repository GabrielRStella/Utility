package com.ralitski.util.math.geom.d2.func;

import com.ralitski.util.math.expression.Expression;
import com.ralitski.util.math.expression.Expressions;

public abstract class FilterFunction implements Function {
	
	protected Function base;
	
	public FilterFunction(Function base) {
		this.base = base;
	}
    
	public Expression express() {
		return filterExpression(base.express());
	}

	@Override
	public float getY(float x) {
		return filter(base.getY(x));
	}
	
	protected abstract Expression filterExpression(Expression e);
	protected abstract float filter(float y);
	
	public static class AdditiveFunction extends FilterFunction {
		
		private float add;

		public AdditiveFunction(Function base, float add) {
			super(base);
			this.add = add;
		}

		@Override
		protected Expression filterExpression(Expression e) {
			return Expressions.add(e, Expressions.valueOf(add));
		}

		@Override
		protected float filter(float y) {
			return y + add;
		}
		
		public String toString() {
			return "((" + this.base.toString() + ")+" + add + ")";
		}
	}
	
	public static class MultiplyingFunction extends FilterFunction {
		
		private float coefficient;

		public MultiplyingFunction(Function base, float coefficient) {
			super(base);
			this.coefficient = coefficient;
		}

		@Override
		protected Expression filterExpression(Expression e) {
			return Expressions.multiply(e, Expressions.valueOf(coefficient));
		}

		@Override
		protected float filter(float y) {
			return y * coefficient;
		}
		
		public String toString() {
			return "((" + this.base.toString() + ")*" + coefficient + ")";
		}
	}

}
