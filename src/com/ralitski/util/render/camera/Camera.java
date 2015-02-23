package com.ralitski.util.render.camera;

import com.ralitski.util.math.geom.d3.Orientation3d;
import com.ralitski.util.math.geom.d3.Point3d;

/**
 *
 * @author ralitski
 */
public interface Camera {
    /*
     * TODO: this will be the position and rotation and such of the user (or
     * whatever the current view is), and a separate RenderCamera will translate
     * it to opengl stuff.
     */
    
    //position, rotation

    Point3d getPosition();
    Orientation3d getOrientation();
}
