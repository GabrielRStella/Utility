/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ralitski.util.input;

import com.ralitski.util.input.event.KeyEvent;
import com.ralitski.util.input.event.MouseEvent;

/**
 *
 * @author ralitski
 */
public interface InputUser {
	
	void onMouseEvent(MouseEvent event);
    void onKeyEvent(KeyEvent event);
}
