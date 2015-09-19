package com.ralitski.util.gui;

public class BoxEvent {

	private Box source;
	private BoxEventType eventType;
	/**
	 * The previous value associated with the given field. Note that if the
	 * BoxEventType is a translation, prevValue = 0
	 */
	private int prevValue;
	/**
	 * The new value associated with the given field (set after the event is
	 * called). Note that if the BoxEventType is a translation, newValue = the
	 * translation amount.
	 */
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
		SET_MINX(true),
		SET_MINY(true),
		SET_MAXX(false),
		SET_MAXY(false),
		SET_WIDTH(false),
		SET_HEIGHT(false),
		SET_CENTERX(true),
		SET_CENTERY(true),
		SET_X(true),
		SET_Y(true),
		TRANSLATE_X(true),
		TRANSLATE_Y(true);
		
		private boolean isMovement;
		
		BoxEventType(boolean isMovement) {
			this.isMovement = isMovement;
		}
		
		public boolean isMovement() {
			return isMovement;
		}
	}
}
