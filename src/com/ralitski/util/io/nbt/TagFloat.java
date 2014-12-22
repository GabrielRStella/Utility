package com.ralitski.util.io.nbt;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;

public class TagFloat extends DataBase<Float> {
	
	public float value;
	
	public TagFloat(String s) {
		super(s);
	}
	
	public TagFloat(String s, float v) {
		super(s);
		this.value = v;
	}
	
	public Float value()
	{
		return this.value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		this.value = in.readFloat();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeFloat(this.value);
	}

}
