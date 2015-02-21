package com.ralitski.util.render;

import static org.lwjgl.opengl.GL11.*;

/**
 * GL11 blend functions. see https://www.opengl.org/sdk/docs/man/html/glBlendFunc.xhtml
 * @author ralitski
 *
 */
public enum BlendFunc {
	
	/**
	 * (0, 0, 0, 0)
	 */
	ZERO(GL_ZERO),
	
	/**
	 * (1, 1, 1, 1)
	 */
	ONE(GL_ONE),
	
	/**
	 * 
	 */
	SRC_COLOR(GL_SRC_COLOR),
	
	/**
	 * 
	 */
	ONE_MINUS_SRC_COLOR(GL_ONE_MINUS_SRC_COLOR),
	
	/**
	 * 
	 */
	DST_COLOR(GL_DST_COLOR),
	
	/**
	 * 
	 */
	ONE_MINUS_DST_COLOR(GL_ONE_MINUS_DST_COLOR),
	
	/**
	 * 
	 */
	SRC_ALPHA(GL_SRC_ALPHA),
	
	/**
	 * 
	 */
	ONE_MINUS_SRC_ALPHA(GL_ONE_MINUS_SRC_ALPHA),
	
	/**
	 * 
	 */
	DST_ALPHA(GL_DST_ALPHA),
	
	/**
	 * 
	 */
	ONE_MINUS_DST_ALPHA(GL_ONE_MINUS_DST_ALPHA),
	
	/**
	 * 
	 */
	CONSTANT_COLOR(GL_CONSTANT_COLOR),
	
	/**
	 * 
	 */
	ONE_MINUS_CONSTANT_COLOR(GL_ONE_MINUS_CONSTANT_COLOR),
	
	/**
	 * 
	 */
	CONSTANT_ALPHA(GL_CONSTANT_ALPHA),
	
	/**
	 * 
	 */
	ONE_MINUS_CONSTANT_ALPHA(GL_ONE_MINUS_CONSTANT_ALPHA);
	
	private int glCode;
	
	BlendFunc(int code) {
		glCode = code;
	}
	
	public int glCode() {
		return glCode;
	}
	
	//source = values being drawn
	public static BlendFunc autoSrc() {
		return ONE;
	}
	
	//destination = values already there
	public static BlendFunc autoDest() {
		return ZERO;
	}
}
