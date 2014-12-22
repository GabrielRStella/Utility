package com.ralitski.util.io.nbt;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;

public class TagInteger extends DataBase<Integer> {
	
	public int value;
	
	public TagInteger(String s) {
		super(s);
	}
	
	public TagInteger(String s, int v) {
		super(s);
		this.value = v;
	}
	
	public Integer value()
	{
		return this.value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		this.value = in.readInt();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(this.value);
	}

}
