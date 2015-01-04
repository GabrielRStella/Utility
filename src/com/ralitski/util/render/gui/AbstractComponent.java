package com.ralitski.util.render.gui;

public abstract class AbstractComponent implements Component {
	
	protected Gui gui;
	private int id = -1;
	
	public AbstractComponent(Gui gui) {
		this.gui = gui;
	}

	@Override
	public Gui getGui() {
		return gui;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void setSelected(boolean selected) {
		if(!isSelectable()) {
			throw new UnsupportedOperationException("Can't be selected! (" + getClass().getSimpleName() + ")");
		}
	}

}
