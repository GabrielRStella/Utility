package com.ralitski.util.render;

import org.lwjgl.opengl.GL11;

public enum DepthFunc {
	
	/**
	 * Never passes.
	 */
	GL_NEVER(GL11.GL_NEVER),
	
	/**
	 * Passes if the incoming depth value is less than the stored depth value.
	 */
	GL_LESS(GL11.GL_LESS),
	
	/**
	 * Passes if the incoming depth value is equal to the stored depth value.
	 */
	GL_EQUAL(GL11.GL_EQUAL),
	
	/**
	 * Passes if the incoming depth value is less than or equal to the stored depth value.
	 */
	GL_LEQUAL(GL11.GL_LEQUAL),
	
	/**
	 * Passes if the incoming depth value is greater than the stored depth value.
	 */
	GL_GREATER(GL11.GL_GREATER),
	
	/**
	 * Passes if the incoming depth value is not equal to the stored depth value.
	 */
	GL_NOTEQUAL(GL11.GL_NOTEQUAL),
	
	/**
	 * Passes if the incoming depth value is greater than or equal to the stored depth value.
	 */
	GL_GEQUAL(GL11.GL_GEQUAL),
	
	/**
	 * Always passes.
	 */
	GL_ALWAYS(GL11.GL_ALWAYS);
	
	private int glCode;
	
	DepthFunc(int i) {
		glCode = i;
	}
	
	public int glCode() {
		return glCode;
	}
	
	public static DepthFunc auto() {
		return GL_LESS;
	}

}
