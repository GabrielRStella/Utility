package com.ralitski.util;

public interface Timer {

	public boolean update();
	public boolean update(float f);
	public float time();
	public long getTime();
}