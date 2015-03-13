package com.ralitski.util.input;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ralitski
 */
public class InputMonitor {

    //filters and feeds input to this
    private InputUser user;
    private InputFeed input;
    private boolean[] mouseClick, mouseReleased;
    private int[] mouseHoldTime;
    private Map<Integer, Key> keyboard;
    private boolean wasMouseInWindow; //was cursor in window previous tick?

    public InputMonitor(InputUser user, InputFeed feed) {
    	this.input = feed;
        this.user = user;
        int c = feed.getMouseButtonCount();
        mouseClick = new boolean[c];
        mouseHoldTime = new int[c];
        mouseReleased = new boolean[c];
        for (int i = 0; i < mouseReleased.length; i++) {
            mouseReleased[i] = true;
        }

        keyboard = new HashMap<Integer, Key>();
    }

    public InputUser getUser() {
        return user;
    }

    public void setUser(InputUser user) {
        this.user = user;
    }

    public int getMouseX() {
        return input.getMouseX();
    }

    public int getMouseY() {
        return input.getMouseY();
    }

    public boolean isMouseInWindow() {
        return input.isMouseInsideWindow();
    }

    public boolean isButtonDown(int btn) {
        return input.isMouseButtonDown(btn);
    }

    public boolean isKeyDown(int key) {
        return input.isKeyDown(key);
    }

    private Key getKey(int id) {
        Key k = keyboard.get(id);
        if (k == null) {
            keyboard.put(id, k = new Key(id));
        }
        return k;
    }

    public void update() //only update that needs to be called ever
    {
        
        while(input.mouseNext()) {
            handleMouseInput();
        }
        while(input.keyNext()) {
            handleKeyboardInput();
        }
        
        if (isMouseInWindow() != wasMouseInWindow) {
            if (wasMouseInWindow) {
                user.onMouseExitWindow(getMouseX(), getMouseY(), input.getTime());
            } else {
                user.onMouseEnterWindow(getMouseX(), getMouseY(), input.getTime());
            }
        }
        wasMouseInWindow = isMouseInWindow();

        for (int btn = 0; btn < mouseClick.length; btn++) {
            if (input.isMouseButtonDown(btn) && !mouseReleased[btn]) {
                mouseHoldTime[btn]++;
                user.onMouseHold(input.getMouseX(), input.getMouseY(), btn, mouseHoldTime[btn]);
            }
        }

        for (Key k : keyboard.values()) {
            if (k.isDown() && !k.isReleased()) {
                k.incrementKeyHoldTime();
                user.onKeyHold(k.getId(), k.getKeyHoldTime());
            }
        }
    }

    public void handleMouseInput() {
        int btn = input.getMouseEventButton();
        boolean state = input.getMouseEventButtonState();
        int x = input.getMouseEventX();
        int y = input.getMouseEventY();
        long time = input.getKeyEventTime();

        //test for click, hold, release
        if (btn >= 0) {
            if (state) {
                if (!mouseClick[btn] && mouseReleased[btn]) {
                    mouseClick[btn] = true;
                    mouseReleased[btn] = false;
                    user.onMouseClick(x, y, time, btn);
                }
                mouseReleased[btn] = false;
            } else {
                if (!mouseReleased[btn]) {
                    user.onMouseRelease(x, y, time, btn, mouseHoldTime[btn]);
                }
                mouseClick[btn] = false;
                mouseHoldTime[btn] = 0;
                mouseReleased[btn] = true;
            }
        } else {
            //wheel
            int wheel = input.getMouseEventDWheel();
            if (wheel != 0) {
                user.onMouseWheel(x, y, time, wheel);
            } else {
            	int dx = input.getMouseEventDX();
            	int dy = input.getMouseEventDY();
            	user.onMouseMove(x, y, time, dx, dy);
            }
        }

    }

    public void handleKeyboardInput() {
        
        int key = input.getKeyEventKey();
        char keyChar = input.getKeyEventCharacter();
        boolean state = input.getKeyEventState();
        long time = input.getKeyEventTime();
        Key k = getKey(key);
        //test for click, hold, release
        if (state) {
            if (!k.isClick() && k.isReleased()) {
                k.setClick(true);
                user.onKeyClick(key, time, keyChar);
            }
            k.setReleased(false);
        } else {
            if (!k.isReleased()) {
                user.onKeyRelease(key, time, keyChar, k.getKeyHoldTime());
            }
            k.setClick(false);
            k.resetKeyHoldTime();
            k.setReleased(true);
        }
    }
}
