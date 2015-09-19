package com.ralitski.util.gui;

public class BoxEvent {
	
	private Box source;
	private BoxEventType eventType;
	private int prevValue;
	private int newValue;
	
	public BoxEvent(Box source, BoxEventType type, int prevValue, int newValue) {
		this.source = source;
		this.eventType = type;
		this.prevValue = prevValue;
		this.newValue = newValue;
	}
	
	public Box getSource() {
		return source;
	}
	
	public BoxEventType getEventType() {
		return eventType;
	}

	public int getPrevValue() {
		return prevValue;
	}

	public int getNewValue() {
		return newValue;
	}

	public static enum BoxEventType {
		SET_MINX,
		SET_MINY,
		SET_MAXX,
		SET_MAXY,
		SET_WIDTH,
		SET_HEIGHT,
		SET_CENTERX,
		SET_CENTERY,
		SET_X,
		SET_Y,
		TRANSLATE_X,
		TRANSLATE_Y
	}
}
