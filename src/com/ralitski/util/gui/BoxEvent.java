package com.ralitski.util.gui;

public class BoxEvent {

	public static final int AXIS_X = 1;
	public static final int AXIS_Y = 2;

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

	public int getTranslateX() {
		if(eventType.isMovement() && eventType.getAxis() == AXIS_X) {
			return newValue - prevValue;
		} else return 0;
	}

	public int getTranslateY() {
		if(eventType.isMovement() && eventType.getAxis() == AXIS_Y) {
			return newValue - prevValue;
		} else return 0;
	}

	public static enum BoxEventType {
		SET_MINX(true, AXIS_X),
		SET_MINY(true, AXIS_Y),
		SET_MAXX(false, AXIS_X),
		SET_MAXY(false, AXIS_Y),
		SET_WIDTH(false, AXIS_X),
		SET_HEIGHT(false, AXIS_Y),
		SET_CENTERX(true, AXIS_X),
		SET_CENTERY(true, AXIS_Y),
		SET_X(true, AXIS_X),
		SET_Y(true, AXIS_Y),
		TRANSLATE_X(true, AXIS_X),
		TRANSLATE_Y(true, AXIS_Y);
		
		private boolean isMovement;
		private int axis;
		
		BoxEventType(boolean isMovement, int axis) {
			this.isMovement = isMovement;
			this.axis = axis;
		}
		
		public boolean isMovement() {
			return isMovement;
		}
		
		public int getAxis() {
			return axis;
		}
	}
}
