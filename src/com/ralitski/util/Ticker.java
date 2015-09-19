package com.ralitski.util;

public class Ticker {
	
	public static Ticker ticksPerSecond(float ticks) {
		return new Ticker(ticks / 1000F);
	}
	
	public static Ticker ticksPerMinute(float ticks) {
		return new Ticker(ticks / 60000F);
	}
	
	private float ticksPerMillisecond;
	private long prevTime;
	
	public Ticker(float ticksPerMillisecond) {
		this.ticksPerMillisecond = ticksPerMillisecond;
	}
	
	/**
	 * Keeps track of the passage of time
	 * @return The proportion of a tick (or ticks) that have passed since the last call to time()
	 */
	public double time() {
		long time = getTime();
		long delta = time - prevTime; //delta time in milliseconds
		double tick = ticksPerMillisecond * (double)delta;
		prevTime = time;
		return tick;
	}
	
	public long getTime() {
		return System.currentTimeMillis();
	}
}