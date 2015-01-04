package com.ralitski.util.render.gui;

import java.util.List;

public interface Container extends Component {
	void add(Component c);
	void add(Component c, String layout);
	void remove(Component c);
	List<Component> getComponents();
	
	int getMinWidth();
	void setMinWidth(int width);
	int getMinHeight();
	void setMinHeight(int height);
	void setResizable(boolean resizable);
	boolean isResizable();
	
	//refreshes the state of the container (position components, resize)
	void refresh();
}
