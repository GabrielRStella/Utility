package com.ralitski.util.gui.render;

import com.ralitski.util.gui.Component;

public interface RenderStyle {
	RenderStyle setClassType(String classType);
	String getClassType();
	
	RenderStyle setStyle(String style, Object value);
	Object getStyle(Component c, String style);
}
