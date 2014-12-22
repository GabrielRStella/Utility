package com.ralitski.util.math.geom.d2.func;

import java.util.Set;

/**
 *
 * @author ralitski
 */
public interface Graph2d<T> {
    public Set<T> getY(float x);
    public Graph2d<T> inverse();
}
