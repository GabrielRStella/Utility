package com.ralitski.util.math.expression;

import java.util.Collection;
import java.util.List;

import tempgeom.IntervalCompound;


/**
 *
 * @author ralitski
 */
public class AdditiveExpression extends AbstractMultiExpression {

    public AdditiveExpression(Collection<Expression> expressions) {
        super(expressions);
    }

    public AdditiveExpression(Expression... expressions) {
        super(expressions);
    }

    @Override
    protected float baseValue() {
        return 0;
    }

    @Override
    protected float processChange(float value, float change) {
        return value + change;
    }

    @Override
    protected Expression doSimplify(float value, List<Expression> newExpressions) {
        if (value != 0) {
            newExpressions.add(Expressions.valueOf(value));
        }
        return newExpressions.size() == 1 ? newExpressions.get(0) : new AdditiveExpression(newExpressions);
    }

    @Override
    protected String combiner() {
        return "+";
    }

//    @Override
//    protected void doFill(Mathable math, float value) {
//        math.add(value);
//    }
    
//    @Override
//    public void fill(MathableSet set) {
//        float absoluteValue = baseValue();
//        Map<String, Integer> variables = new HashMap<>();
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
////                variables.addAll(Arrays.asList(e.getVariables()));
//                for(String var : e.getVariables()) {
//                    Integer i = variables.get(var);
//                    int i2 = i != null ? i.intValue() : 0;
//                    variables.put(var, i2 + 1);
//                }
//            }
//        }
//        for(String var : variables.keySet()) {
//            Mathable math = set.get(var);
//            if(math != null) {
//                math.multiply(variables.get(var));
//                math.add(absoluteValue);
//            }
//        }
//    }
}
