package com.ralitski.util.render.img;

import java.util.Random;

import com.ralitski.util.render.img.Color;

public class ColorRange {
	
	private Color min;
	private Color max;
	
	public ColorRange(Color min, Color max) {
		this.min = min;
		this.max = max;
	}
	
	public Color createSmooth(Random random) {
		float grad = random.nextFloat();
		return ColorHelper.blend(min, max, grad);
	}
	
	public Color getMin() {
		return min;
	}

	public void setMin(Color min) {
		this.min = min;
	}

	public Color getMax() {
		return max;
	}

	public void setMax(Color max) {
		this.max = max;
	}

	public Color createRough(Random random) {
		float grad = random.nextFloat();
		float grad2 = 1F - grad;
		float r = min.getRedFloat() * grad + max.getRedFloat() * grad2;
		grad = random.nextFloat();
		grad2 = 1F - grad;
		float g = min.getGreenFloat() * grad + max.getGreenFloat() * grad2;
		grad = random.nextFloat();
		grad2 = 1F - grad;
		float b = min.getBlueFloat() * grad + max.getBlueFloat() * grad2;
		grad = random.nextFloat();
		grad2 = 1F - grad;
		float a = min.getAlphaFloat() * grad + max.getAlphaFloat() * grad2;
		return new Color(r, g, b, a);
	}
}
