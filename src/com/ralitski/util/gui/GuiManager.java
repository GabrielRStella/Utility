package com.ralitski.util.gui;

import com.ralitski.util.input.InputUser;
import com.ralitski.util.input.event.KeyEvent;
import com.ralitski.util.input.event.MouseEvent;

public class GuiManager implements InputUser {
	
	private GuiOwner owner;
	private Gui currentScreen;
	
	public GuiManager(GuiOwner owner) {
		this.owner = owner;
	}
	
	public GuiOwner getGuiOwner() {
		return owner;
	}

	public Gui getCurrentScreen() {
		return currentScreen;
	}

	public void closeScreen() {
		currentScreen.onClose(true);
		if(currentScreen.getParent() != null) {
			currentScreen = currentScreen.getParent();
			currentScreen.onOpen(true);
		} else {
			owner.onTopLevelGuiClose();
		}
	}
	
	public void openScreen(Gui screen) {
		if(currentScreen != null) {
			currentScreen.onClose(screen.isChildOf(currentScreen));
		}
		screen.onOpen(false);
		currentScreen = screen;
	}
	
	public int getWindowWidth() {
		return owner.getWidth();
	}
	
	public int getWindowHeight() {
		return owner.getHeight();
	}
	
	public Box getWindow() {
		return new Box(0, 0, getWindowWidth(), getWindowHeight());
	}
	
	public void render2d(float partial) {
		if(currentScreen != null) currentScreen.render2d(partial);
	}
	
	public void render3d(float partial) {
		if(currentScreen != null) currentScreen.render3d(partial);
	}

	@Override
	public void onMouseEvent(MouseEvent event) {
		if(currentScreen != null) currentScreen.onMouseEvent(event);
	}

	@Override
	public void onKeyEvent(KeyEvent event) {
		if(currentScreen != null) currentScreen.onKeyEvent(event);
	}
	
	public void update() {
		if(currentScreen != null) currentScreen.update();
	}
}
