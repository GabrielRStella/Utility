package com.ralitski.util.input;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class LWJGLInputFeed implements InputFeed {

	@Override
	public boolean mouseNext() {
		return Mouse.next();
	}

	@Override
	public int getMouseButtonCount() {
		return Mouse.getButtonCount();
	}

	@Override
	public boolean isMouseButtonDown(int btn) {
		return Mouse.isButtonDown(btn);
	}

	@Override
	public int getMouseX() {
		return Mouse.getX();
	}

	@Override
	public int getMouseY() {
		return Mouse.getY();
	}

	@Override
	public boolean isMouseInsideWindow() {
		return Mouse.isInsideWindow();
	}

	@Override
	public int getMouseEventButton() {
		return Mouse.getEventButton();
	}

	@Override
	public boolean getMouseEventButtonState() {
		return Mouse.getEventButtonState();
	}

	@Override
	public int getMouseEventX() {
		return Mouse.getEventX();
	}

	@Override
	public int getMouseEventY() {
		return Mouse.getEventY();
	}

	@Override
	public int getMouseEventDWheel() {
		return Mouse.getEventDWheel();
	}

	@Override
	public int getMouseEventDX() {
		return Mouse.getEventDX();
	}

	@Override
	public int getMouseEventDY() {
		return Mouse.getEventDY();
	}

	@Override
	public long getMouseEventTime() {
		return Mouse.getEventNanoseconds();
	}

	@Override
	public boolean keyNext() {
		return Keyboard.next();
	}

	@Override
	public boolean isKeyDown(int key) {
		return Keyboard.isKeyDown(key);
	}

	@Override
	public int getKeyEventKey() {
		return Keyboard.getEventKey();
	}

	@Override
	public char getKeyEventCharacter() {
		return Keyboard.getEventCharacter();
	}

	@Override
	public boolean getKeyEventState() {
		return Keyboard.getEventKeyState();
	}

	@Override
	public long getKeyEventTime() {
		return Keyboard.getEventNanoseconds();
	}

	//multiply by 1E9 to get nano instead of seconds
	@Override
	public long getTime() {
		return (Sys.getTime() * 1000000000) / Sys.getTimerResolution();
	}

}
