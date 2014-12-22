package com.ralitski.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface Streamable {
	void write(DataOutputStream out) throws IOException;
	void read(DataInputStream in) throws IOException;
}
