package com.ralitski.util.math.geom;

/**
 *
 * @author ralitski
 */
public interface InterceptFinder<T1, T2, I> {
    public I intercept(T1 obj1, T2 obj2);
}
