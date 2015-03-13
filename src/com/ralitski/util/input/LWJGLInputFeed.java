package com.ralitski.util.input;

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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getMouseEventButtonState() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getMouseEventX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMouseEventY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMouseEventDWheel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMouseEventDX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMouseEventDY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getMouseEventTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean keyNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isKeyDown(int key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getKeyEventKey() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public char getKeyEventCharacter() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getKeyEventState() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getKeyEventTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getTime() {
		// TODO Auto-generated method stub
		return 0;
	}

}
