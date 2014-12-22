package com.ralitski.util.io.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class TagByteArray extends DataBase<byte[]> {

	public byte[] value;
	
	public TagByteArray(String s)
	{
		super(s);
	}
	
	@Override
	public byte[] value() {
		return this.value;
	}

	public Collection<String> toStringCollection(int depth) {
		StringBuilder ret = new StringBuilder("[");
		int count = 0;
		for(byte b : value) {
			ret.append(construct(depth) + (count++ == 0 ? "" : ", ")).append(""+b);
		}
		ret.append("]");
		return Arrays.asList(construct(depth) + this.name + ": " + ret.toString());
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(this.value.length);
		for(int c = 0; c < this.value.length; c++) out.writeByte(this.value[c]);
	}

	@Override
	public void read(DataInput in) throws IOException {
		int n = in.readInt();
		byte[] b = new byte[n];
		for(int c = 0; c < n; c++) b[c] = in.readByte();
		this.value = b;
	}

}
