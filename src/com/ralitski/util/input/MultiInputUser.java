package com.ralitski.util.input;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ralitski
 */
public class MultiInputUser implements InputUser {
    
    private List<InputUser> users;
    
    public MultiInputUser() {
        this.users = new LinkedList<InputUser>();
    }
    
    public boolean isEmpty() {
        return users.isEmpty();
    }
    
    public boolean add(InputUser user) {
        return users.add(user);
    }
    
    public boolean remove(InputUser user) {
        return users.remove(user);
    }

    @Override
    public void onMouseClick(int x, int y, int button) {
        for(InputUser user : users) {
            user.onMouseClick(x, y, button);
        }
    }

    @Override
    public void onMouseHold(int x, int y, int button, int ticks) {
        for(InputUser user : users) {
            user.onMouseHold(x, y, button, ticks);
        }
    }

    @Override
    public void onMouseRelease(int x, int y, int button, int ticks) {
        for(InputUser user : users) {
            user.onMouseRelease(x, y, button, ticks);
        }
    }

    @Override
    public void onMouseWheel(int x, int y, int dWheel) {
        for(InputUser user : users) {
            user.onMouseWheel(x, y, dWheel);
        }
    }

    @Override
    public void onMouseEnterWindow(int x, int y) {
        for(InputUser user : users) {
            user.onMouseEnterWindow(x, y);
        }
    }

    @Override
    public void onMouseExitWindow(int x, int y) {
        for(InputUser user : users) {
            user.onMouseExitWindow(x, y);
        }
    }

    @Override
    public void onKeyClick(int key, char keyChar) {
        for(InputUser user : users) {
            user.onKeyClick(key, keyChar);
        }
    }

    @Override
    public void onKeyHold(int key, int ticks) {
        for(InputUser user : users) {
            user.onKeyHold(key, ticks);
        }
    }

    @Override
    public void onKeyRelease(int key, char keyChar, int ticks) {
        for(InputUser user : users) {
            user.onKeyRelease(key, keyChar, ticks);
        }
    }

}
