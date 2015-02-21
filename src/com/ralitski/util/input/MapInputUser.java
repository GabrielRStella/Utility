package com.ralitski.util.input;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ralitski
 */
public class MapInputUser implements InputUser {
    
    private Map<String, InputUser> users;
    
    public MapInputUser() {
        this.users = new HashMap<String, InputUser>();
    }
    
    public boolean isEmpty() {
        return users.isEmpty();
    }
    
    public InputUser set(String key, InputUser user) {
        return users.put(key, user);
    }
    
    public InputUser remove(String key) {
        return users.remove(key);
    }

    @Override
    public void onMouseClick(int x, int y, int button) {
        for(InputUser user : users.values()) {
            user.onMouseClick(x, y, button);
        }
    }

    @Override
    public void onMouseHold(int x, int y, int button, int ticks) {
        for(InputUser user : users.values()) {
            user.onMouseHold(x, y, button, ticks);
        }
    }

    @Override
    public void onMouseRelease(int x, int y, int button, int ticks) {
        for(InputUser user : users.values()) {
            user.onMouseRelease(x, y, button, ticks);
        }
    }

    @Override
    public void onMouseWheel(int x, int y, int dWheel) {
        for(InputUser user : users.values()) {
            user.onMouseWheel(x, y, dWheel);
        }
    }

    @Override
    public void onMouseEnterWindow(int x, int y) {
        for(InputUser user : users.values()) {
            user.onMouseEnterWindow(x, y);
        }
    }

    @Override
    public void onMouseExitWindow(int x, int y) {
        for(InputUser user : users.values()) {
            user.onMouseExitWindow(x, y);
        }
    }

    @Override
    public void onKeyClick(int key, char keyChar) {
        for(InputUser user : users.values()) {
            user.onKeyClick(key, keyChar);
        }
    }

    @Override
    public void onKeyHold(int key, int ticks) {
        for(InputUser user : users.values()) {
            user.onKeyHold(key, ticks);
        }
    }

    @Override
    public void onKeyRelease(int key, char keyChar, int ticks) {
        for(InputUser user : users.values()) {
            user.onKeyRelease(key, keyChar, ticks);
        }
    }

}
