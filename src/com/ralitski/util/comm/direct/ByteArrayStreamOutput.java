package com.ralitski.util.comm.direct;

import java.io.IOException;
import java.io.OutputStream;

public class ByteArrayStreamOutput extends OutputStream {
	
	private ByteArrayStream stream;
	
	public ByteArrayStreamOutput(ByteArrayStream stream) {
		this.stream = stream;
	}

	@Override
	public void write(int b) throws IOException {
		stream.write(b);
	}
	
    public synchronized void write(byte b[], int off, int len) {
    	stream.write(b, off, len);
    }
    
    public void flush() throws IOException {
    	stream.flush();
    }

}
