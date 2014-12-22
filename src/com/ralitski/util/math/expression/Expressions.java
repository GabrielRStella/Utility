package com.ralitski.util.math.expression;

import com.ralitski.util.math.geom.d2.func.Function;
import com.ralitski.util.math.geom.n.DimensionVariables;
import com.ralitski.util.math.var.Variable;
import com.ralitski.util.math.var.VariableFixed;
import com.ralitski.util.math.var.VariableSet;
import com.ralitski.util.ArrayUtils;
import com.ralitski.util.doc.TODO;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author ralitski
 */
public class Expressions {
    
    public static final Expression EMPTY_EXPRESSION_0 = new AbsoluteExpression(0);
    public static final Expression EMPTY_EXPRESSION_1 = new AbsoluteExpression(1);
    
    //==========================================================================
    //-----VALUE OF-------------------------------------------------------------
    //==========================================================================
    
    public static Expression valueOf(Object value) {
        if(value == null) return null;
        if(value instanceof Float) {
            return valueOf(((Float)value).floatValue());
        } else if(value instanceof Character) {
            return valueOf(((Character)value).charValue());
        } else if(value instanceof String) {
            return valueOf((String)value);
        } else if(value instanceof Expression) {
            return valueOf((Expression)value);
        }
        return null;
    }
    
    public static Expression valueOf(float value) {
        return new AbsoluteExpression(value);
    }
    
    public static Expression valueOf(char c) {
    	return new RelativeExpression("" + c);
    }
    
    //TODO: parse an actual expression out of this
    public static Expression valueOf(String str) {
        return parse(str);
    }
    
    public static Expression valueOf(Expression expr) {
        return expr.simplify(VariableSet.EMPTY_SET);
    }
    
    public static Expression valueOf(Function function) {
        return new FunctionExpression(function);
    }
    
    public static Expression valueOf(Function function, String variable) {
        return new FunctionExpression(function, new RelativeExpression(variable));
    }
    
    public static Expression valueOf(Function function, Expression variable) {
        return new FunctionExpression(function, variable);
    }
    
    //==========================================================================
    //-----SOLVE----------------------------------------------------------------
    //==========================================================================
    
    public static Expression solve(Expression e, String variable) {
        if(variable == null) return e;
        String[] vars = e.getVariables();
        VariableSet varSet = new VariableSet();
        for(String s : vars) {
            if(!variable.equals(s)) {
                varSet.add(s, new VariableFixed(0));
            }
        }
        return e.simplify(varSet);
    }
    
    /**
     * simplifies the given expression, replacing variables not already in the VariableSet with 0.
     * @param e expression to simplify
     * @param nonZeroVars variables to not be 0
     * @return simplified expression
     */
    public static Expression simplifyWithZeros(Expression e, VariableSet nonZeroVars) {
        return simplifyWithReplacement(e, nonZeroVars, 0);
    }
    
    /**
     * simplifies the given expression, replacing variables not already in the
     * VariableSet with 0.
     *
     * @param e expression to simplify
     * @param nonZeroVars variables to not be 0
     * @param zeroVar the number to be substituted for other variables
     * @return simplified expression
     */
    public static Expression simplifyWithReplacement(Expression e, VariableSet nonZeroVars, float zeroVar) {
        VariableSet realVars = nonZeroVars.clone();
        if(realVars == null) {
            realVars = new VariableSet();
            for(Entry<String, Variable> entry : nonZeroVars.getAll()) {
                realVars.add(entry.getKey(), entry.getValue());
            }
        }
        if(e != null) {
            String[] vars = e.getVariables();
            if (vars != null) {
                Set<String> setVars = realVars.get();
                Variable zeroVariable = new VariableFixed(zeroVar);
                for(String var : vars) {
                    if(!setVars.contains(var)) {
                        realVars.add(var, zeroVariable);
                    }
                }
            }
        }
        return e.simplify(realVars);
    }
    
    /**
     * solves for the value of the variables when multiplied together.
     *
     * @param e expression to be solved/simplified
     * @param variables the variables whose multiplied value will be returned
     * @return an expression which contains only terms absolute or relating to
     * the multiplication of the given variables, eg (x + xy + y) would return
     * (xy)
     */
    @Deprecated
    @TODO
    public static Expression solve(Expression e, String...variables) {
        if(variables == null) return e;
        //
        Expression[] subExpressions = new Expression[variables.length + 1];
        for(int i = 0; i < variables.length; i++) {
            subExpressions[i] = negative(solve(e, variables[i]));
        }
        String[] vars = e.getVariables();
        VariableSet varSet = new VariableSet();
        for(String s : vars) {
            if(ArrayUtils.containsAnyEquivalent(variables, s)) {
                varSet.add(s, new VariableFixed(1));
            } else {
                varSet.add(s, new VariableFixed(0));
            }
        }
        subExpressions[variables.length] = e.simplify(varSet);
        return add(subExpressions);
    }
    
    //==========================================================================
    //-----RANDOM---------------------------------------------------------------
    //==========================================================================
    
    public static Expression random() {
        Random rand = new Random();
        return random(rand, 0);
    }
    
    private static Expression random(Random rand, int deep) {
        int i = rand.nextInt(4);
        int MAX_DEEP = 5;
        if(i == 0) {
            return new AbsoluteExpression((rand.nextFloat() * 2f - 1f) / rand.nextFloat());
        } else if(i == 1) {
            return new RelativeExpression(DimensionVariables.variableOf(rand.nextInt(10)));
        } else if(i == 2 && deep < MAX_DEEP) {
            return add(randomArray(rand, deep));
        } else if(deep < MAX_DEEP) {
            return multiply(randomArray(rand, deep));
        } else {
            return EMPTY_EXPRESSION_1;
        }
    }
    
    private static Expression[] randomArray(Random rand, int deep) {
        int length = rand.nextInt(3) + 2;
        Expression[] expr = new Expression[length];
        for(int j = 0; j < length; j++) {
            expr[j] = random(rand, deep + 1);
        }
        return expr;
    }
    
    //==========================================================================
    //-----MISC OPERATIONS------------------------------------------------------
    //==========================================================================
    
    public static Expression square(Expression e) {
    	return multiply(e, e);
    }
    
    public static Expression negative(Expression expression) {
        return multiply(valueOf(-1), expression);
    }
    
    public static Expression add(Expression...e) {
        return new AdditiveExpression(e);
    }
    
    public static Expression add(Collection<Expression> e) {
        return new AdditiveExpression(e);
    }

	public static Expression subtract(Expression e1, Expression e2) {
		return add(e1, negative(e2));
	}
    
    public static Expression multiply(Expression...e) {
        return new MultiplyingExpression(e);
    }
    
    public static Expression multiply(Collection<Expression> e) {
        return new MultiplyingExpression(e);
    }
    
    public static Expression divide(Expression e1, Expression e2) {
    	return new DividingExpression(e1, e2);
    }
    
    public static Expression pow(Expression e1, Expression e2) {
    	return new ExponentialExpression(e1, e2);
    }
    
    //==========================================================================
    //-----PARSE----------------------------------------------------------------
    //==========================================================================
    
    @TODO
    public static Expression parse(String s) {
        return null;
    }
}
