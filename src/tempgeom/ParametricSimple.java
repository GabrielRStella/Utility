package tempgeom;

import com.ralitski.util.math.geom.d2.func.Function;

public class ParametricSimple implements Parametric {
	
	private Function func;
	private IntervalCompound interval;
	
//	public ParametricSimple(Function func) {
//		this(func, func.getRealInterval());
//	}
	
	public ParametricSimple(Function func, IntervalCompound interval) {
		this.func = func;
		this.interval = interval;
	}

	@Override
	public float getX(float t) {
		return t;
	}

	@Override
	public float getY(float t) {
		return func.getY(t);
	}

	@Override
	public IntervalCompound getInterval() {
		return interval;
	}

}
