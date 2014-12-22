package com.ralitski.util.input;

public abstract class AbstractInputUser implements InputUser {

	@Override
	public void onMouseClick(int x, int y, int button) {
	}

	@Override
	public void onMouseHold(int x, int y, int button, int ticks) {
	}

	@Override
	public void onMouseRelease(int x, int y, int button, int ticks) {
	}

	@Override
	public void onMouseWheel(int x, int y, int dWheel) {
	}

	@Override
	public void onMouseEnterWindow(int x, int y) {
	}

	@Override
	public void onMouseExitWindow(int x, int y) {
	}

	@Override
	public void onKeyClick(int key, char keyChar) {
	}

	@Override
	public void onKeyHold(int key, int ticks) {
	}

	@Override
	public void onKeyRelease(int key, char keyChar, int ticks) {
	}

}
