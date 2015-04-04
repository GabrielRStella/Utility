package com.ralitski.util.math.expression;

import com.ralitski.util.ArrayUtils;
import com.ralitski.util.math.var.VariableSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import tempgeom.IntervalCompound;

/**
 *
 * @author ralitski
 */
public abstract class AbstractMultiExpression extends AbstractExpression {

    protected Expression[] expressions;
    protected boolean absolute;

    protected AbstractMultiExpression(Collection<Expression> expressions) {
        this(expressions.toArray(new Expression[expressions.size()]));
    }

    protected AbstractMultiExpression(Expression... expressions) {
        this.expressions = expressions;
        genAbsolute();
    }

    private void genAbsolute() {
        for (Expression e : expressions) {
            if (!e.isAbsolute()) {
                return;
            }
        }
        absolute = true;
    }

    @Override
    public boolean isAbsolute() {
        return absolute;
    }

    @Override
    public float evaluateAbsolute() {
        if (absolute) {
            float value = 0;
            for (Expression e : expressions) {
                value += e.evaluateAbsolute();
            }
            return value;
        } else {
            //throw error
            return super.evaluateAbsolute();
        }
    }

    @Override
    public Expression simplify(VariableSet vars) {
        float value = baseValue();
        List<Expression> newExpressions = new ArrayList<Expression>(expressions.length);
//            System.out.println(getClass().getSimpleName());
        for (Expression e : expressions) {
            Expression e2 = e.simplify(vars);
//            System.out.println(getDepth() + e2);
//            System.out.println(getClass().getSimpleName().substring(0, 1) + " " + e2.getClass().getSimpleName().substring(0, 1));
            if (e2.isAbsolute()) {
                value = processChange(value, e2.evaluateAbsolute());
            } else if (e2 instanceof AbstractMultiExpression && getClass() == e2.getClass()) {
//                System.out.println("ehreuihr");
                newExpressions.addAll(Arrays.asList(((AbstractMultiExpression) e2).expressions));
            } else {
                newExpressions.add(e2);
            }
        }
        if (newExpressions.isEmpty()) {
            return Expressions.valueOf(value);
        } else {
            Expression ret = doSimplify(value, newExpressions);
//            System.out.println(getDepth() + "  " + ret);
            return ret;
        }
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        int i = 0;
        for (Expression e : expressions) {
            if (i++ > 0) {
                build.append(combiner());
            }
            build.append("(").append(e.toString()).append(")");
        }
        return build.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AbstractMultiExpression) {
            AbstractMultiExpression expr = (AbstractMultiExpression) o;
            if (expr.absolute == absolute && expr.expressions.length == expressions.length && getClass() == expr.getClass()) {
                return ArrayUtils.containsAllEquivalentOf(expressions, expr.expressions);
//                for (int i = 0; i < expressions.length; i++) {
//                    Expression e1 = expressions[i];
//                    Expression e2 = expr.expressions[i];
//                    if (!((e1 == e2) || (e1 != null && e2 != null && e1.equals(e2)))) {
//                        return false;
//                    }
//                }
//                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Arrays.deepHashCode(this.expressions);
        hash = 97 * hash + (this.absolute ? 1 : 0);
        return hash;
    }

    @Override
    public boolean hasVariable(String name) {
        for (Expression e : expressions) {
            if (e.hasVariable(name)) {
                return true;
            }
        }
        return false;
    }

//        @Override
//        public ExpressionSet split(VariableSet vars) {
//            ExpressionSet set = new ExpressionSet();
//            for(Expression e : expressions) {
//                ExpressionSet splitSet = e.split(vars);
//                for(String var : splitSet.get()) {
//                    Expression pre = set.get(var);
//                    Expression add;
//                    if(pre != null) {
//                        add = new AdditiveExpression(pre, splitSet.get(var));
//                    } else {
//                        add = splitSet.get(var);
//                    }
//                    set.add(var, add);
//                }
//            }
//            return set;
//        }
    
//    @Override
//    public void fill(MathableSet set) {
//        float absoluteValue = baseValue();
//        List<String> variables = new ArrayList<>();
//        for(Expression e : expressions) {
//            if(e.isAbsolute()) {
//                absoluteValue = processChange(absoluteValue, e.evaluateAbsolute());
//            } else if(e instanceof AbstractMultiExpression) {
//                DynamicMathableSet subSet = new DynamicMathableSet();
//                e.fill(subSet);
//            } else {
//                //relative expression
//            }
//        }
////        for (Expression e : expressions) {
////            if (e.getChildren() != null) {
////                e.fill(set);
////            } else if (e.isAbsolute()) {
////                //add
//////                System.out.println("filling: " + e);
////                for (Expression e2 : expressions) {
////                    if (e2 != e && e2.getChildren() == null) {
//////                        System.out.println(" filling sub: " + e2);
////                        String[] vars = e2.getVariables();
////                        for (String var : vars) {
//////                            System.out.println("  filling var: " + var);
////                            Mathable math = set.get(var);
////                            if (math != null) {
//////                                System.out.println("   filling math: " + math);
////                                doFill(math, e.evaluateAbsolute());
//////                                System.out.println("   ... " + math);
////                            }
////                        }
////                    }
////                }
////            }
////        }
//    }

    @Override
    public Expression[] getChildren() {
        return expressions;
    }

    @Override
    public String[] getVariables() {
        String[][] vars = new String[expressions.length][];
        for (int i = 0; i < expressions.length; i++) {
            Expression e = expressions[i];
            vars[i] = e.getVariables();
        }
        return ArrayUtils.eliminateEquivalentAndNull(String.class, ArrayUtils.combine(String.class, vars));
    }

//	@Override
//	public IntervalCompound getRealInterval(String variable) {
//		IntervalCompound base = IntervalCompound.ALL;
//		for(Expression e : expressions) base = base.and(e.getRealInterval(variable));
//		return base;
//	}
    
    protected abstract float baseValue();
    protected abstract float processChange(float value, float change);
    protected abstract Expression doSimplify(float absolute, List<Expression> newExpressions);
    protected abstract String combiner();
//    protected abstract void doFill(Mathable math, float value);
}
