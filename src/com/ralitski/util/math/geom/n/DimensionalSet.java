package com.ralitski.util.math.geom.n;

/**
 *
 * @author ralitski
 */
public interface DimensionalSet<T> extends Dimensional {
    T get(int dimension);
    void set(int dimension, T value);
}
