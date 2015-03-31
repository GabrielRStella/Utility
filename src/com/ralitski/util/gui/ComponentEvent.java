package com.ralitski.util.gui;

public class ComponentEvent {
	
	private Component source;
	private String args;
	
	public ComponentEvent(Component source) {
		this(source, null);
	}
	
	public ComponentEvent(Component source, String args) {
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
		return "ComponentEvent[source:" + source + " args:" + args + "]";
	}
}
