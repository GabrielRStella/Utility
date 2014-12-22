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
    void onMouseClick(int x, int y, int button);
    void onMouseHold(int x, int y, int button, int ticks);
    void onMouseRelease(int x, int y, int button, int ticks);
    
    void onMouseWheel(int x, int y, int dWheel);
    
    void onMouseEnterWindow(int x, int y);
    void onMouseExitWindow(int x, int y);
    
    void onKeyClick(int key, char keyChar);
    void onKeyHold(int key, int ticks);
    void onKeyRelease(int key, char keyChar, int ticks);
}
