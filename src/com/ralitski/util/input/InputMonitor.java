package com.ralitski.util.input;

import java.util.HashMap;
import java.util.Map;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 *
 * @author ralitski
 */
public class InputMonitor {

    //filters and feeds input to this
    private InputUser user;
    private boolean[] mouseClick, mouseReleased;
    private int[] mouseHoldTime;
    private Map<Integer, Key> keyboard;
    private boolean wasMouseInWindow; //was cursor in window previous tick?

    public InputMonitor(InputUser user) {
        this.user = user;
        int c = Mouse.getButtonCount();
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
        return Mouse.getX();
    }

    public int getMouseY() {
        return Mouse.getY();
    }

    public boolean isMouseInWindow() {
        return Mouse.isInsideWindow();
    }

    public boolean isButtonDown(int btn) {
        return Mouse.isButtonDown(btn);
    }

    public boolean isKeyDown(int key) {
        return Keyboard.isKeyDown(key);
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
        
        while(Mouse.next()) {
            handleMouseInput();
        }
        while(Keyboard.next()) {
            handleKeyboardInput();
        }
        
        if (isMouseInWindow() != wasMouseInWindow) {
            if (wasMouseInWindow) {
                user.onMouseExitWindow(getMouseX(), getMouseY());
            } else {
                user.onMouseEnterWindow(getMouseX(), getMouseY());
            }
        }
        wasMouseInWindow = isMouseInWindow();

        for (int btn = 0; btn < mouseClick.length; btn++) {
            if (Mouse.isButtonDown(btn) && !mouseReleased[btn]) {
                mouseHoldTime[btn]++;
                user.onMouseHold(Mouse.getX(), Mouse.getY(), btn, mouseHoldTime[btn]);
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
        int btn = Mouse.getEventButton();
        boolean state = Mouse.getEventButtonState();
        int x = Mouse.getEventX();
        int y = Mouse.getEventY();

        //test for click, hold, release
        if (btn >= 0) {
            if (state) {
                if (!mouseClick[btn] && mouseReleased[btn]) {
                    mouseClick[btn] = true;
                    mouseReleased[btn] = false;
                    user.onMouseClick(x, y, btn);
                }
                mouseReleased[btn] = false;
            } else {
                if (!mouseReleased[btn]) {
                    user.onMouseRelease(x, y, btn, mouseHoldTime[btn]);
                }
                mouseClick[btn] = false;
                mouseHoldTime[btn] = 0;
                mouseReleased[btn] = true;
            }
        }
        //wheel
        int wheel = Mouse.getEventDWheel();
        if (wheel != 0) {
            user.onMouseWheel(x, y, wheel);
        }

    }

    public void handleKeyboardInput() {
        
        int key = Keyboard.getEventKey();
        char keyChar = Keyboard.getEventCharacter();
        boolean state = Keyboard.getEventKeyState();
        Key k = getKey(key);
        //test for click, hold, release
        if (state) {
            if (!k.isClick() && k.isReleased()) {
                k.setClick(true);
                user.onKeyClick(key, keyChar);
            }
            k.setReleased(false);
        } else {
            if (!k.isReleased()) {
                user.onKeyRelease(key, keyChar, k.getKeyHoldTime());
            }
            k.setClick(false);
            k.resetKeyHoldTime();
            k.setReleased(true);
        }
    }
}
