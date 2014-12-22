package com.ralitski.util.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketPlayerConnection implements PlayerConnection {
	
	private Socket internalConnection;
	private InputStream in;
	private OutputStream out;
	
	public SocketPlayerConnection(Socket s) {
		this.internalConnection = s;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return in != null ? in : (in = internalConnection.getInputStream());
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return out != null ? out : (out = internalConnection.getOutputStream());
	}

	@Override
	public boolean isClosed() {
		return internalConnection.isClosed();
	}
	
	public void close() {
		try {
			internalConnection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
