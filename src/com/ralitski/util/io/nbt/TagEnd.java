package com.ralitski.util.io.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.util.Arrays;
import java.util.Collection;

@SuppressWarnings("rawtypes")
public class TagEnd extends DataBase {

	public static String END = "END";
	public static byte ENDBYTE = 0;
	
	public TagEnd() {
		super("");
	}
	
	public TagEnd(String s) {
		super("");
	}
	
	public Object value()
	{
		return null;
	}

	@Override
	public void read(DataInput in) {
	}

	@Override
	public void write(DataOutput out) {
	}

	public Collection<String> toStringCollection() {
		return Arrays.asList("END");
	}

}
