package com.ralitski.util.render.camera;

import com.ralitski.util.input.InputUser;

/**
 *
 * @author ralitski
 */
public interface Camera extends InputUser {
    
    void update();
    /*
     * TODO: this will be the position and rotation and such of the user (or
     * whatever the current view is), and a separate RenderCamera will translate
     * it to opengl stuff.
     */
    
    //position, rotation

    void setX(float x);
    void setY(float y);
    void setZ(float z);
    float getX();
    float getY();
    float getZ();
    float getYaw();
    float getPitch();
    float getRoll();
    
    //input inherited from InputUser
    
//    void moveForward(float forward);
//    /**
//     * positive strafe should move camera to the right.
//     * @param strafe 
//     */
//    void moveStrafe(float strafe);
//    void jump();
}
