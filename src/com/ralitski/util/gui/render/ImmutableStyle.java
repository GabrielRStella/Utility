package com.ralitski.util.gui.render;

import com.ralitski.util.gui.Component;

public class ImmutableStyle implements RenderStyle {
	
	private RenderStyle source;
	
	public ImmutableStyle(RenderStyle source) {
		this.source = source;
	}

	@Override
	public RenderStyle setClassType(String classType) {
		return this;
	}

	@Override
	public String getClassType() {
		return source.getClassType();
	}

	@Override
	public RenderStyle setStyle(String style, Object value) {
		return this;
	}

	@Override
	public <T> T getStyle(Component c, String style) {
		return source.getStyle(c, style);
	}

}
