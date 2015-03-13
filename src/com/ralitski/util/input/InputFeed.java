package com.ralitski.util.input;

public interface InputFeed {

	boolean mouseNext();

	int getMouseButtonCount();
	boolean isMouseButtonDown(int btn);
	int getMouseX();
	int getMouseY();
	boolean isMouseInsideWindow();

	int getMouseEventButton();
	boolean getMouseEventButtonState();
	int getMouseEventX();
	int getMouseEventY();
	int getMouseEventDWheel();
	int getMouseEventDX();
	int getMouseEventDY();
	long getMouseEventTime();

	boolean keyNext();

	boolean isKeyDown(int key);

	int getKeyEventKey();
	char getKeyEventCharacter();
	boolean getKeyEventState();
	long getKeyEventTime();

	/**
	 * The current time, according to the system this InputFeed is using.
	 * @return The current time, in whichever units this InputFeed uses (same relative start as getKeyEventTime())
	 */
	long getTime();

	/*int getEventButton
boolean getEventButtonState
int getEventDWheel
int getEventDX
int getEventDY
int getEventNanoseconds
int getEventX
int getEventY
	 */
}
