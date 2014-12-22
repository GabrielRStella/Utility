package com.ralitski.util.io.nbt;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;

public class TagByte extends DataBase<Byte> {
	
	public byte value;
	
	public TagByte(String s) {
		super(s);
	}
	
	public TagByte(String s, byte v) {
		super(s);
		this.value = v;
	}
	
	public Byte value()
	{
		return this.value;
	}
	
	@Override
	public void read(DataInput in) throws IOException {
		this.value = in.readByte();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeByte(this.value);
	}

}
