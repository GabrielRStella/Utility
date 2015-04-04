package com.ralitski.util.math.expression;

import java.util.Collection;
import java.util.List;

import tempgeom.IntervalCompound;


/**
 *
 * @author ralitski
 */
public class MultiplyingExpression extends AbstractMultiExpression {

    public MultiplyingExpression(Collection<Expression> expressions) {
        super(expressions);
    }

    public MultiplyingExpression(Expression... expressions) {
        super(expressions);
    }
    
//    @Override
//    public void fill(MathableSet set) {
//        for (Expression e : expressions) {
//            if (e.getChildren() != null) {
//                e.fill(set);
//            } else if (e.isAbsolute()) {
//                for (Expression e2 : expressions) {
//                    if (e2 != e && e2 instanceof AdditiveExpression) {
//                        Expression[] sub = ((AdditiveExpression)e2).expressions;
//                        String[] vars = e2.getVariables();
//                        for (String var : vars) {
//                            Mathable math = set.get(var);
//                            if (math != null) {
//                                doFill(math, e.evaluateAbsolute());
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }

    @Override
    protected float baseValue() {
        return 1;
    }

    @Override
    protected float processChange(float value, float change) {
        return value * change;
    }

    @Override
    protected Expression doSimplify(float value, List<Expression> newExpressions) {
        if (value != 1) {
            newExpressions.add(Expressions.valueOf(value));
        }
        return value == 0 ? Expressions.EMPTY_EXPRESSION_0 : (newExpressions.size() == 1 ? newExpressions.get(0) : new MultiplyingExpression(newExpressions));
    }

    @Override
    protected String combiner() {
        return "*";
    }

//    @Override
//    protected void doFill(Mathable math, float value) {
//        math.multiply(value);
//    }
    
    //TODO: stuff
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
//                for(String variable : subSet.get()) {
//                    Mathable math = set.get(variable);
//                    Mathable addMath = subSet.get(variable);
//                    math.add(addMath.value());
//                }
//            } else {
//                variables.addAll(Arrays.asList(e.getVariables()));
//            }
//        }
//        for(String var : variables) {
//            set.get(var).add(absoluteValue);
//        }
//    }
}