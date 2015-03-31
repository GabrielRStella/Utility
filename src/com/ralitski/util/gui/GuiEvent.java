package com.ralitski.util.gui;

public class GuiEvent {
	
	private Gui source;
	
	public GuiEvent(Gui source) {
		this.source = source;
	}

	public Gui getSource() {
		return source;
	}
	
	public String toString() {
		return "GuiEvent[source:" + source + "]";
	}
}
