/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ralitski.util.input;

/**
 *
 * @author ralitski
 */
public interface InputUser {
	
	//mouse clicky
	
    void onMouseClick(int x, int y, long time, int button);
    void onMouseHold(int x, int y, int button, int ticks);
    void onMouseRelease(int x, int y, long time, int button, int ticks);
    void onMouseMove(int x, int y, long time, int dx, int dy);
    
    //wheel
    
    void onMouseWheel(int x, int y, long time, int dWheel);
    
    //cursor movement
    
    void onMouseEnterWindow(int x, int y, long time);
    void onMouseExitWindow(int x, int y, long time);
    
    //keyboard
    
    void onKeyClick(int key, long time, char keyChar);
    void onKeyHold(int key, int ticks);
    void onKeyRelease(int key, long time, char keyChar, int ticks);
}
