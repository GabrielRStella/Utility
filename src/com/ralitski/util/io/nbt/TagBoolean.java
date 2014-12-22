package com.ralitski.util.io.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 *
 * @author ralitski
 */
public class TagBoolean extends DataBase<Boolean> {
	
	public boolean value;
	
	public TagBoolean(String s) {
		super(s);
	}
	
	public TagBoolean(String s, boolean v) {
		super(s);
		this.value = v;
	}
	
	public Boolean value()
	{
		return this.value;
	}
	
	@Override
	public void read(DataInput in) throws IOException {
		this.value = in.readBoolean();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeBoolean(this.value);
	}

}
