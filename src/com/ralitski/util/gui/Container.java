package com.ralitski.util.gui;

import java.util.List;

public interface Container extends Component {
	void add(Component c);
	void add(Component c, String layout);
	void remove(Component c);
	List<Component> getComponents();
	
	//refreshes the state of the container (position components, resize)
	void refresh();
	
	//if the container should render itself, or just children
	boolean doRenderSelf();
	void setRenderSelf(boolean renderSelf);
}
