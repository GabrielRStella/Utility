package com.ralitski.util.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Streamable {
	void write(DataOutputStream out) throws IOException;
	void read(DataInputStream in) throws IOException;
}
