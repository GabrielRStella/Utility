package com.ralitski.util.io.nbt;

public class TagDataSet {

	public String tagName;
	public byte tagID;
	public boolean tagLoad;
	
	public TagDataSet(String s, byte b, boolean flag)
	{
		this.tagName = s;
		this.tagID = b;
		this.tagLoad = flag;
	}
}
