package com.ralitski.util.render.img;

import com.ralitski.util.Ticker;

public class ColorSet {
	private float prevTime;
	private float prevPart;
	private int index;
	private Color[] colors;
	private boolean isSmooth;
	
	private int ticksPerLoop;
	private int ticks;
	
	//if no ticker is assigned, the ColorSet must be updated by calling tick()
	private Ticker ticker;
	
	public ColorSet(Color[] colors, int ticksPerLoop) {
		if(colors.length == 0) throw new IllegalArgumentException("ColorSet must contain at least 1 Color");
		this.colors = colors;
		this.ticksPerLoop = ticksPerLoop;
		isSmooth = true;
	}

	public boolean isSmooth() {
		return isSmooth;
	}

	public void setSmooth(boolean isSmooth) {
		this.isSmooth = isSmooth;
	}

	public int getTicksPerLoop() {
		return ticksPerLoop;
	}

	public void setTicksPerLoop(int ticksPerLoop) {
		this.ticksPerLoop = ticksPerLoop;
	}

	public Ticker getTicker() {
		return ticker;
	}

	public void setTicker(Ticker ticker) {
		this.ticker = ticker;
		ticker.time();
	}
	
	public void tick() {
		ticks++;
	}
	
	public Color next() {
		//OH GOSH IT CALCULATES PARTIAL EVEN IF IT'S NOT USED MUST OPTIMIZE
		float partial;
		if(ticker != null) {
			float f = (float)ticker.time();
			prevTime += f;
			partial = prevTime % 1F;
			prevPart += f;
			int tick = (int)prevPart;
			if(tick > 0) {
				index = (index + tick) % colors.length;
				prevPart = 0;
			} else {
				//?
			}
		} else {
			if(ticks >= ticksPerLoop) {
				int mod = ticks % ticksPerLoop;
				int extra = (ticks - mod) / ticksPerLoop;
				index = (index + extra) % colors.length;
				ticks = mod;
			}
			partial = (float)ticks / (float)ticksPerLoop;
		}
		if(isSmooth) {
			Color current = colors[index];
			Color next = colors[(index + 1) % colors.length];
			return ColorHelper.blend(current, next, 1F - partial);
		} else return colors[index];
	}
}
