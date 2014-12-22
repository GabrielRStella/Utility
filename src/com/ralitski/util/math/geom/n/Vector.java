package com.ralitski.util.math.geom.n;

import com.ralitski.util.math.Mathable;
import com.ralitski.util.math.EMatrix;
import com.ralitski.util.math.expression.Expressible;
import com.ralitski.util.math.expression.Expression;
import com.ralitski.util.math.expression.Expressions;
import com.ralitski.util.math.expression.RelativeExpression;
import com.ralitski.util.math.var.VariableFixed;
import com.ralitski.util.math.var.VariableSet;

import java.util.Arrays;

/**
 *
 * @author ralitski
 */
public class Vector extends SimpleDimensionalSet implements Mathable, Expressible {
    
    /**
     * parses a vector from an expression representing the addition of scaled
     * unit vectors.
     *
     * @param expression the expression to parse
     * @return a vector with values taken from the given expression, or an empty
     * vector if the expression does not represent a vector
     */
    public static Vector parse(Expression expression) {
        String[] vars = expression.getVariables();
        if(vars != null && vars.length > 0) {
            int highest = 0;
            int[] dimensions = new int[vars.length];
            for(int i = 0; i < vars.length; i++) {
                int j = DimensionVariables.dimensionOf(vars[i]);
                highest = Math.max(highest, j);
                dimensions[i] = j;
            }
            float[] vec = new float[highest];
            for(int i = 0; i < dimensions.length; i++) {
                int index = dimensions[i] - 1;
                String var = vars[i];
                vec[index] = Expressions.solve(expression, var)
                        .simplify(new VariableSet().add(var, new VariableFixed(1)))
                        .evaluateAbsolute();
            }
            return new Vector(vec);
        }
        return empty();
    }
    
    public static Vector crossProductOf(Vector... others) {
        int length = others != null ? others.length : 0;
        int length2 = length + 1;
        Expression[][] matrixTable = new Expression[length2][length2];
        
        matrixTable[0] = totalUnitVector(length + 1).toExpression(length2);
        if(length > 0) {
            for (int i = 1; i < matrixTable.length; i++) {
                matrixTable[i] = others[i - 1].toExpression(length2);
            }
        }
        EMatrix matrix = new EMatrix(matrixTable);
//        System.out.println(matrix);
        Expression determinant = matrix.determinant();
        determinant = determinant.simplify(VariableSet.EMPTY_SET);
//        System.out.println(determinant);
        return parse(determinant);
    }
    
    public static Vector of(Point initial, Point terminal) {
        int size = Math.max(initial.dimensions(), terminal.dimensions());
        float[] data = new float[size];
        for(int index = 0; index < size; index++) {
            float terminalFloat = index < terminal.dimensions() ? terminal.data[index] : 0;
            float initialFloat = index < initial.dimensions() ? initial.data[index] : 0;
            data[index] = terminalFloat - initialFloat;
        }
//        System.out.println(initial);
//        System.out.println(terminal);
//        System.out.println(new Vector(data));
        return new Vector(data);
    }
    
    public static Vector empty() {
        return new Vector();
    }
    
    public static Vector unit(int dimension) {
        float[] data = new float[dimension];
        data[dimension - 1] = 1;
        return new Vector(data);
    }
    
    /**
     * returns a vector with a value of 1 in every dimension up to (and
     * including) the specified dimension.
     *
     * @param dimensions the number of dimensions to be included in the returned
     * total unit vector
     * @return a total unit vector, special for use in cross-product matrices
     * (overrides {@link #toExpression(int) })
     */
    public static Vector totalUnitVector(int dimensions) {
        float[] data = new float[dimensions];
        for(int i = 0; i < dimensions; i++) {
            data[i] = 1;
        }
        return new TotalUnitVector(data);
    }
    
    //==========================================================================
    //-----OBJECT---------------------------------------------------------------
    //==========================================================================
    
    private float magnitude;
    private boolean magnitudeDirty = true;
    
    public Vector(float...data) {
        super(data);
    }
    
    //==========================================================================
    //-----CHANGE---------------------------------------------------------------
    //==========================================================================
    
    //TODO: angles, ye
    
    @Override
    public Float get(int index) {
        this.magnitudeDirty = true;
        return super.get(index);
    }
    
    @Override
    public void set(int index, Float value) {
        this.magnitudeDirty = true;
        super.set(index, value);
    }
    
    public Vector crossProduct(Vector... others) {
        if(others == null || others.length == 0) {
            return crossProductOf(this);
        } else {
            int length = others != null ? others.length : 0;
            int length2 = length + 2;
            Expression[][] matrixTable = new Expression[length2][length2];

            matrixTable[0] = totalUnitVector(length + 2).toExpression(length2);
            matrixTable[1] = toExpression(length2);
            if (length > 0) {
                for (int i = 2; i < matrixTable.length; i++) {
                    matrixTable[i] = others[i - 2].toExpression(length2);
                }
            }
            EMatrix matrix = new EMatrix(matrixTable);
            Expression determinant = matrix.determinant();
            determinant = determinant.simplify(VariableSet.EMPTY_SET);
//        System.out.println(determinant);
            return parse(determinant);
            
        }
    }
    
    public float dot(Vector other) {
        int length = Math.min(data.length, other.data.length);
        float dot = 0;
        for(int i = 0; i < length; i++) {
            dot += data[i] * other.data[i];
        }
        return dot;
    }
    
    public void add(Vector other) {
        float[] tempData = other.data;
        if(tempData.length > data.length) {
            data = Arrays.copyOf(data, tempData.length);
        }
        for(int i = 0; i < data.length; i++) {
            float add = i < tempData.length ? tempData[i] : 0;
            data[i] += add;
            magnitudeDirty |= (add != 0);
        }
    }
    
    public float magnitude() {
        if(magnitudeDirty) {
            float newMagnitude = 0F;
            for(float f : data) {
                newMagnitude += f * f;
            }
            this.magnitude = (float)Math.sqrt(newMagnitude);
            magnitudeDirty = false;
            return this.magnitude;
        } else {
            return magnitude;
        }
    }
    
    /**
     * gives the angle between the two vectors.
     * @param other vector to compare to
     * @return the angle (in radians) between the two vectors
     */
    public float angleTo(Vector other) {
        float dot = this.dot(other);
        float div = this.magnitude() * other.magnitude();
        if(div == 0) return 0;
        float cosAngle = dot / div;
        return (float)Math.acos(cosAngle);
    }
    
    //==========================================================================
    //-----EXPRESSIONS AND MATRICES---------------------------------------------
    //==========================================================================
    
    public Expression[] toExpression() {
        return toExpression(data.length);
    }
    
    public Expression[] toExpression(int size) {
        Expression[] result = new Expression[size];
        for(int i = 0; i < result.length; i++) {
            result[i] = Expressions.valueOf(i < data.length ? data[i] : 0);
        }
        return result;
    }
    
    public EMatrix toMatrix() {
        return toMatrix(data.length);
    }
    
    public EMatrix toMatrix(int size) {
        Expression[][] matrix = new Expression[][]{
            this.toExpression(size)
        };
        return new EMatrix(matrix);
    }
    
    //==========================================================================
    //-----MISC-----------------------------------------------------------------
    //==========================================================================

    public boolean isParallel(Vector other) {
        float[] otherData = other.data;
        if(otherData.length != data.length) return false;
        float scalar = this.magnitude() / other.magnitude();
        for(int i = 0; i < data.length; i++) {
            if(data[i] / scalar != otherData[i]) return false;
        }
        return true;
    }
    
    public boolean isOrthogonal(Vector other) {
        return this.dot(other) == 0;
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o instanceof Vector) {
            Vector v = (Vector)o;
            for(int i = 0; i < v.data.length || i < data.length; i++) {
                float thisData = i >= data.length ? 0 : data[i];
                float otherData = i >= v.data.length ? 0 : v.data[i];
                if(thisData != otherData) return false;
            }
        }
        return false;
    }

    //autogenerated
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Arrays.hashCode(this.data);
        return hash;
    }
    
    @Override
    public String toString() {
        StringBuilder build = new StringBuilder("<");
        for(int i = 0; i < data.length; i++) {
            if(i > 0) build.append(", ");
            build.append(data[i]);
        }
        return build.append(">").toString();
    }

    /**
     * adds the specified value to the magnitude of the vector (appropriately
     * scaling all dimensional values)
     * @param value 
     */
    @Override
    public void add(float value) {
        float oldMag = magnitude();
        float newMag = oldMag + value;
        float mod = newMag / oldMag;
        for(int i = 0; i < data.length; i++) {
            data[i] *= mod;
        }
        magnitudeDirty = true;
    }

    @Override
    public void multiply(float value) {
        float oldMag = magnitude();
        float newMag = value * oldMag;
        float mod = value;
        for(int index = 0; index < data.length; index++) {
            data[index] *= mod;
        }
        magnitude = newMag;
        magnitudeDirty = false;
    }

    @Override
    public float value() {
        return magnitude();
    }

    @Override
    public Expression express() {
        Expression[] expr = new Expression[data.length];
        for(int i = 0; i < expr.length; i++) {
            expr[i] = Expressions.multiply(
                    Expressions.valueOf(DimensionVariables.variableOf(i + 1)),
                    Expressions.valueOf(data[i])
                    );
        }
        return Expressions.add(expr);
    }
    
    //==========================================================================
    //-----INTERNAL CLASSES-----------------------------------------------------
    //==========================================================================
    
    private static class TotalUnitVector extends Vector {
        
        private TotalUnitVector(float... data) {
            super(data);
        }

        @Override
        public Expression[] toExpression(int size) {
            Expression[] result = new Expression[size];
            for (int i = 0; i < result.length; i++) {
                result[i] = new RelativeExpression(DimensionVariables.variableOf(i + 1));
            }
            //System.out.println(Arrays.deepToString(result));
            return result;
        }
    }

}
