package com.ralitski.util.io.nbt;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;

public class TagShort extends DataBase<Short> {
	
	public short value;
	
	public TagShort(String s) {
		super(s);
	}
	
	public TagShort(String s, Short v) {
		super(s);
		this.value = v;
	}
	
	public Short value()
	{
		return this.value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		this.value = in.readShort();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeShort(this.value);
	}

}
