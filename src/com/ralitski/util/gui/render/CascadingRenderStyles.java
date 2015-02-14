package com.ralitski.util.gui.render;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CascadingRenderStyles {
	
	private Map<String, String> parentTypes = new HashMap<String, String>();
	private Map<String, RenderStyle> styles = new HashMap<String, RenderStyle>();

	public String getParentClassType(String classType) {
		return parentTypes.get(classType);
	}

	public void addClassType(RenderStyle style, String parentType) {
		styles.put(style.getClassType(), style);
		parentTypes.put(style.getClassType(), parentType);
	}

	public void removeClassType(RenderStyle style) {
		String s = style.getClassType();
		styles.remove(s);
		doRemoveClass(s);
	}
	
	private void doRemoveClass(String s) {
		List<String> types = new LinkedList<String>();
		for(Entry<String, String> e : parentTypes.entrySet()) {
			//find children
			if(e.getValue().equals(s)) {
				types.add(e.getKey());
			}
		}
		//remove children
		for(String type : types) {
			parentTypes.remove(type);
			//remove grandchildren
			doRemoveClass(type);
		}
	}

	public RenderStyle getRenderStyle(String classType) {
		return styles.get(classType);
	}

}
