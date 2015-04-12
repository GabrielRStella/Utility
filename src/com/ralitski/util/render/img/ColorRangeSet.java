package com.ralitski.util.render.img;

import java.util.Iterator;
import java.util.Random;

import com.ralitski.util.Ticker;

public class ColorRangeSet {
	private ColorRange[] range;
	private boolean isSmoothGradient;
	private IterableColorSet set;
	private Random random;
	
	public ColorRangeSet(ColorRange[] range, int ticksPerLoop) {
		this.range = range;
		random = new Random();
		set = new IterableColorSet(new ColorIter(), ticksPerLoop);
	}

	public boolean isSmooth() {
		return set.isSmooth();
	}

	public void setSmooth(boolean isSmooth) {
		set.setSmooth(isSmooth);
	}

	public boolean isSmoothGradient() {
		return isSmoothGradient;
	}

	public void setSmoothGradient(boolean isSmoothGradient) {
		this.isSmoothGradient = isSmoothGradient;
	}

	public ColorRange[] getRange() {
		return range;
	}

	public void setRange(ColorRange[] range) {
		this.range = range;
	}

	public int getTicksPerLoop() {
		return set.getTicksPerLoop();
	}

	public void setTicksPerLoop(int ticksPerLoop) {
		set.setTicksPerLoop(ticksPerLoop);
	}

	public Ticker getTicker() {
		return set.getTicker();
	}

	public void setTicker(Ticker ticker) {
		set.setTicker(ticker);
	}
	
	public void tick() {
		set.tick();
	}
	
	public Color next() {
		return set.next();
	}
	
	private class ColorIter implements Iterator<Color> {
		
		private int index;

		@Override
		public boolean hasNext() {
			return true;
		}

		@Override
		public Color next() {
			Color c = isSmoothGradient ? range[index].createSmooth(random) : range[index].createRough(random);
			index = (index + 1) % range.length;
			return c;
		}

		@Override
		public void remove() {
		}
		
	}
	
	
}
