package com.ralitski.util.render.img;

import java.util.Iterator;

import com.ralitski.util.Ticker;

public class IterableColorSet {
	private float prevTime;
	private float prevPart;
	private Color current;
	private Color next;
	private Iterator<Color> colors;
	private boolean isSmooth;
	
	private int ticksPerLoop;
	private int ticks;
	
	//if no ticker is assigned, the ColorSet must be updated by calling tick()
	private Ticker ticker;
	
	public IterableColorSet(Iterator<Color> colors, int ticksPerLoop) {
		this.colors = colors;
		this.ticksPerLoop = ticksPerLoop;
		isSmooth = true;
		current = colors.next();
		next = colors.next();
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
			int tick = (int)Math.floor(prevPart);
			if(tick > 0) {
				doNext();
				prevPart = 0;
				partial = 0;
			}
		} else {
			if(ticks >= ticksPerLoop) {
				int mod = ticks % ticksPerLoop;
				int extra = (ticks - mod) / ticksPerLoop;
				while(extra-- > 0) doNext();
				ticks = mod;
			}
			partial = (float)ticks / (float)ticksPerLoop;
		}
		if(isSmooth) {
			return ColorHelper.blend(current, next, 1F - partial);
		} else return current;
	}
	
	private void doNext() {
		current = next;
		next = colors.next();
	}
}
