/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ralitski.util.math.geom.d2.func;

/**
 *
 * @author ralitski
 */
public interface FunctionInverse extends Function {
    float getX(float y);
    FunctionInverse inverse();
}
