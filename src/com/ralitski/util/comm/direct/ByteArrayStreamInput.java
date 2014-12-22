package com.ralitski.util.comm.direct;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayStreamInput extends InputStream {
	
	private ByteArrayStream stream;
	
	public ByteArrayStreamInput(ByteArrayStream stream) {
		this.stream = stream;
	}

	@Override
	public int read() throws IOException {
		return stream.read();
	}
	
    public synchronized int read(byte b[], int off, int len) {
    	return stream.read(b, off, len);
    }
    
    public long skip(long n) throws IOException {
    	return stream.skip(n);
    }
    
    public int available() throws IOException {
    	return stream.available();
    }

}
