package com.ralitski.util.gui.render;

public class RenderListState {
	
	private boolean dirty = true;

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
}
