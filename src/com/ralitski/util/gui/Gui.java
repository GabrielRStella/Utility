package com.ralitski.util.gui;

import com.ralitski.util.render.img.Color;

public class Gui {
	
	//used to darken lower-level gui screens (parent gui rendered behind current gui)
	private static final Color FILM = new Color(100, 100, 100, 100);
	
	private GuiManager owner;
	private Gui parent;
	private Component topLevel;
	
	public Gui(GuiManager owner, Component topLevel) {
		this.owner = owner;
		this.topLevel = topLevel;
	}
	
	public Gui(Gui parent, Component topLevel) {
		this.parent = parent;
		this.topLevel = topLevel;
	}
	
	public GuiManager getOwner() {
		return owner != null ? owner : parent.getOwner();
	}
	
	public Gui getParent() {
		return parent;
	}
	
	public boolean isChildOf(Gui parent) {
		Gui g = this;
		while(g.getParent() != null) {
			g = g.getParent();
			if(g == parent) return true;
		}
		return false;
	}
	
	public void close() {
		GuiManager owner = getOwner();
		if(owner.getCurrentScreen() == this) {
			owner.closeScreen();
		}
	}
	
	public void open(Gui child) {
		GuiManager owner = getOwner();
		owner.openScreen(child);
	}
	
	public boolean renderParent() {
		return true;
	}
	
	public void render2d() {
		GuiManager manager = getOwner();
		GuiOwner render = manager.getGuiOwner();
		if(parent != null && renderParent()) {
			parent.render2d();
			render.drawBox(manager.getWindow(), FILM);
		}
		if(topLevel != null) topLevel.render(render);
	}
	
	//optional 3d rendering
	public void render3d() {}
	
	//optional stuff to let the gui do whatever it needs to
	public void onOpen(boolean reentry) {}
	public void onClose(boolean exit) {}
}
