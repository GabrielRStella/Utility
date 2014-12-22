package com.ralitski.util;

import org.lwjgl.Sys;

public class MinuteTimer implements Timer {

	//number of timer ticks per second. this timer uses milliseconds.
	public int ticksPerSecond = 1000;

	//number of timer ticks per minute.
	public int ticksPerMinute = 60000;
	
	//number of ticks per minute the timer returns
	public int ticks;
	
	//time (in milliseconds) at last measurement
	public long lastTime;
	
	//number of system ticks per timer tick
	public float timerTicks;
	
	public MinuteTimer(int i)
	{
		this.ticks = i;
		this.timerTicks = (float)this.ticksPerMinute / (float)this.ticks;
	}
	
	public boolean update()
	{
		return this.update(this.time());
	}
	
	public boolean update(float f)
	{
		return f >= 1.0F;
	}
	
	public float time()
	{
		long time = this.getTime();
		long delta = time - this.lastTime;
		//System.out.println("time " + time + " last " + lastTime + "\n" + "delta " + delta + "\n" + "timerticks" + timerTicks);
		if(delta >= this.timerTicks)
		{
			this.lastTime = time;
		}
		return delta / this.timerTicks;
	}
	
	public long getTime()
	{
		return Sys.getTime() * this.ticksPerSecond / Sys.getTimerResolution();
	}
}
