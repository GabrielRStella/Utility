package com.ralitski.util.input;

import org.lwjgl.input.Keyboard;

public class Key {

    private int id, keyHoldTime;
    private boolean keyClick, isReleased;

    public Key(int id) {
        this.id = id;
        this.isReleased = true;
    }

    public int getId() {
        return this.id;
    }

    public boolean isClick() {
        return keyClick;
    }

    public void setClick(boolean click) {
        this.keyClick = click;
    }

    public boolean isReleased() {
        return isReleased;
    }

    public void setReleased(boolean isReleased) {
        this.isReleased = isReleased;
    }

    public int getKeyHoldTime() {
        return keyHoldTime;
    }

    public void setKeyHoldTime(int keyHoldTime) {
        this.keyHoldTime = keyHoldTime;
    }

    public void incrementKeyHoldTime() {
        this.keyHoldTime++;
    }

    public void resetKeyHoldTime() {
        this.keyHoldTime = 0;
    }

    public boolean isDown() {
        return Keyboard.isKeyDown(this.id);
    }
}
