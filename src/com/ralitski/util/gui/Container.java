package com.ralitski.util.gui;

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
	//control whether the container will resize itself to its layout's recommended size
	void setResizable(boolean resizable);
	boolean isResizable();
	
	//refreshes the state of the container (position components, resize)
	void refresh();
	
	//if the container should render itself, or just children
	boolean doRenderSelf();
	void setRenderSelf(boolean renderSelf);
}
