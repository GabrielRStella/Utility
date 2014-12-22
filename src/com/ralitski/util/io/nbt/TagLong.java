package com.ralitski.util.io.nbt;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;

public class TagLong extends DataBase<Long> {
	
	public long value;
	
	public TagLong(String s) {
		super(s);
	}
	
	public TagLong(String s, long v) {
		super(s);
		this.value = v;
	}
	
	public Long value()
	{
		return this.value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		this.value = in.readLong();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(this.value);
	}

}
