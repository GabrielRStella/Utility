package com.ralitski.util.gui;

public class GuiEvent {
	
	private Component source;
	private String args;
	
	public GuiEvent(Component source) {
		this(source, null);
	}
	
	public GuiEvent(Component source, String args) {
		this.source = source;
		this.args = args;
	}

	public Component getSource() {
		return source;
	}

	public String getArgs() {
		return args;
	}
	
	public String toString() {
		return "[source:" + source + " args:" + args + "]";
	}
}
