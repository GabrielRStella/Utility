package com.ralitski.util.io.nbt;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;

public class TagDouble extends DataBase<Double> {
	
	public double value;
	
	public TagDouble(String s) {
		super(s);
	}
	
	public TagDouble(String s, double v) {
		super(s);
		this.value = v;
	}
	
	public Double value()
	{
		return this.value;
	}

	@Override
	public void read(DataInput in) throws IOException {
		this.value = in.readDouble();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeDouble(this.value);
	}

}
