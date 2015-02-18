package com.ralitski.util.gui.render;

import java.util.HashMap;
import java.util.Map;

import com.ralitski.util.gui.Component;

public class CascadingRenderStyle implements RenderStyle {
	
	private CascadingRenderStyles styles;
	
	private String parentType;
	private String classType;
	private Map<String, Object> values;
	
	public CascadingRenderStyle(CascadingRenderStyles styles) {
		this.styles = styles;
		values = new HashMap<String, Object>();
	}

	public RenderStyle setParentClassType(String parentType) {
		this.parentType = parentType;
		return this;
	}

	public String getParentClassType() {
		return parentType;
	}

	@Override
	public RenderStyle setClassType(String classType) {
		styles.removeClassType(this);
		this.classType = classType;
		styles.addClassType(this, parentType);
		return this;
	}

	@Override
	public String getClassType() {
		return classType;
	}

	@Override
	public RenderStyle setStyle(String style, Object value) {
		values.put(style, value);
		return this;
	}

	@Override
	public Object getStyle(Component c, String style) {
		Object o = values.get(style);
		if(o != null) {
			return o;
		}
		if(parentType != null) {
			RenderStyle r = styles.getRenderStyle(parentType);
			return r.getStyle(c, style);
		}
		if(c.getParent() != null) {
			return getStyle(c.getParent(), style);
		}
		return null;
	}
	
}
