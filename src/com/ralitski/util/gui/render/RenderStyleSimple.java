package com.ralitski.util.gui.render;

import java.util.HashMap;
import java.util.Map;

import com.ralitski.util.gui.Component;

public class RenderStyleSimple implements RenderStyle {
	
	private String type;
	private Map<String, Object> styles = new HashMap<String, Object>();

	@Override
	public RenderStyle setClassType(String classType) {
		type = classType;
		return this;
	}

	@Override
	public String getClassType() {
		return type;
	}

	@Override
	public RenderStyle setStyle(String style, Object value) {
		styles.put(style, value);
		return this;
	}

	@Override
	public Object getStyle(Component c, String style) {
		return styles.get(style);
	}

}
