package com.ralitski.util.io.nbt;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TagString extends DataBase<String> {
	
	public String value;
	
	public TagString(String s) {
		super(s);
	}
	
	public TagString(String s, String v) {
		super(s);
		this.value = v;
	}
	
	public String value()
	{
		return this.value;
	}

	public Collection<String> toStringCollection(int depth) {
		return Arrays.asList(construct(depth) + this.name + ": " + value);
	}

	@Override
	public void read(DataInput in) throws IOException {
		this.value = in.readUTF();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(this.value);
	}

}
