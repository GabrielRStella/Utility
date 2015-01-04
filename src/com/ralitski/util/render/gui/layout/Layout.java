package com.ralitski.util.render.gui.layout;

import java.awt.Dimension;

import com.ralitski.util.render.gui.Box;
import com.ralitski.util.render.gui.Component;

//TODO:
//http://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
//http://docs.oracle.com/javase/7/docs/api/javax/swing/BoxLayout.html
//http://docs.oracle.com/javase/7/docs/api/java/awt/CardLayout.html
//http://docs.oracle.com/javase/7/docs/api/javax/swing/plaf/basic/DefaultMenuLayout.html
//http://docs.oracle.com/javase/7/docs/api/java/awt/FlowLayout.html
//http://docs.oracle.com/javase/7/docs/api/java/awt/GridBagLayout.html
//http://docs.oracle.com/javase/7/docs/api/java/awt/GridLayout.html
//http://docs.oracle.com/javase/7/docs/api/javax/swing/GroupLayout.html
public interface Layout {
	//layout string may be empty or null
	void addComponent(Component c, String layout);
	void removeComponent(Component c);
	void refresh(Box window);
	Dimension getMinimumSize();
}
