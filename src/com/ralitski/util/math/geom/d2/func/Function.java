package com.ralitski.util.math.geom.d2.func;

import com.ralitski.util.math.expression.Expressible;
import com.ralitski.util.math.geom.IntervalCompound;

/**
 *
 * @author ralitski
 */
public interface Function extends Expressible {
    float getY(float x);
}
