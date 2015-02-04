package com.ralitski.util.math.expression;

import com.ralitski.util.math.geom.IntervalCompound;
import com.ralitski.util.math.var.Variable;
import com.ralitski.util.math.var.VariableSet;

/**
 *
 * @author ralitski
 */
public class RelativeExpression extends AbstractExpression {

    private String variable;
    private boolean simplify;

    public RelativeExpression(String variable) {
        this(variable, true);
    }

    public RelativeExpression(String variable, boolean simplify) {
        this.variable = variable;
        this.simplify = simplify;
    }

    @Override
    public Expression simplify(VariableSet vars) {
        if (simplify) {
            Variable var = vars.get(variable);
            if (var != null) {
                return Expressions.valueOf(var.value());
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof RelativeExpression) {
            RelativeExpression expr = (RelativeExpression) o;
            return expr.variable.equals(variable) && expr.simplify == simplify;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (variable.hashCode() << 1) + ((this.simplify ? 1 : 0));
    }

    @Override
    public boolean hasVariable(String name) {
        return variable.equals(name);
    }

//        @Override
//        public ExpressionSet split(VariableSet vars) {
//            Variable var = vars.get(variable);
//            if(var != null) {
//                return new ExpressionSet().add(variable, this);
//            }
//            return ExpressionSet.EMPTY_SET;
//        }
    
//    @Override
//    public void fill(MathableSet set) {
//    }

    @Override
    public Expression[] getChildren() {
        return null;
    }

    @Override
    public String[] getVariables() {
        return new String[]{variable};
    }

//	@Override
//	public IntervalCompound getRealInterval(String variable) {
//		return IntervalCompound.ALL;
//	}
}