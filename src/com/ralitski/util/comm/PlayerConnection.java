package com.ralitski.util.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface PlayerConnection {
	
	InputStream getInputStream() throws IOException;
	OutputStream getOutputStream() throws IOException;
	void close();
	boolean isClosed();
}
