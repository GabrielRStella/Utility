package com.ralitski.util.math.geom.n;

import com.ralitski.util.doc.TODO;
import com.ralitski.util.math.expression.Expressible;
import com.ralitski.util.math.expression.Expression;
import com.ralitski.util.math.expression.Expressions;

/**
 * an (n-1) dimensional slice of n-dimensional space (eg, a 2D plane in 3D space, or a 3D section of 4D space)
 * 
 * @author ralitski
 */
@TODO("mirroring stuff, etc")
public class HyperPlane extends SimpleDimensionalSet implements Dimensional, Expressible {
    
    /**
     * returns a hyperplane containing of a number of dimensions equal to the
     * number of given points (if the points contain data in those dimensions)
     *
     * @param points
     * @return
     */
    public static HyperPlane get(Point...points) {
        if(points == null || points.length == 0) {
            return null;
        } else if(points.length == 1) {
            return new HyperPlane(points[0].get(1));
        } else if(points.length == 2) {
            return get(Vector.of(points[0], points[1]).crossProduct(), points[0]);
        } else {
            Vector[] toCross = new Vector[points.length - 1];
            Point initial = points[0];
            for (int index = 1; index < points.length; index++) {
                toCross[index - 1] = Vector.of(initial, points[index]);
//                System.out.println(toCross[index - 1]);
            }
            Vector orthogonal = Vector.crossProductOf(toCross);
            return get(orthogonal, initial);
        }
    }
    
    public static HyperPlane get(Vector orthogonal, Point passThrough) {
        float constant = 0;
        float[] data = new float[orthogonal.dimensions()];
        for(int index = 0; index < data.length; index++) {
            float value = orthogonal.data[index];
            data[index] = value;
            float value2 = index < passThrough.dimensions() ? passThrough.data[index] : 0;
            constant -= (value * value2);
        }
        return new HyperPlane(constant, data);
    }
    
    private float constant;
    
    /**
     * 
     * @param constant constant value, ie (Ax + By + Cz + constant = 0)
     * @param data 
     */
    public HyperPlane(float constant, float... data) {
        super(data);
        this.constant = constant;
    }

    @Override
    public int dimensions() {
        return data.length + 1;
    }
    
    public boolean contains(Point point) {
        //Expression e = Expressions.simplifyWithZeros(express(), point.getVariableSet());
        //return e.isAbsolute() && e.evaluateAbsolute() == 0;
        float total = -constant;
        
        for(int index = 0; index < data.length; index++) {
        	total -= point.get(index + 1) * data[index];
        }
        return total == 0;
    }
    
    public boolean contains(Vector vector) {
        return vector.isOrthogonal(toOrthogonalVector());
    }
    
    public Vector toOrthogonalVector() {
        return new Vector(data.clone());
    }

    @Override
    public Expression express() {
        Expression[] expr = new Expression[data.length + 1];
        for(int i = 0; i < data.length; i++) {
            expr[i] = Expressions.multiply(
                    Expressions.valueOf(DimensionVariables.variableOf(i + 1)),
                    Expressions.valueOf(data[i]));
        }
        expr[data.length] = Expressions.valueOf(constant);
        return Expressions.add(expr);
    }
}
