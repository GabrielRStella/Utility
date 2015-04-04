package tempgeom;


/**
 *
 * @author ralitski
 */
public interface Parametric {
	public float getX(float t);
	public float getY(float t);
	public IntervalCompound getInterval();
}
