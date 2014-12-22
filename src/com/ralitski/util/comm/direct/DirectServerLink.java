package com.ralitski.util.comm.direct;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.ralitski.util.comm.PlayerConnection;

public class DirectServerLink {
	
	private ByteArrayStream streamC2S;
	private ByteArrayStream streamS2C;
	private ByteArrayStreamInput serverInput;
	private ByteArrayStreamOutput serverOutput;
	private ByteArrayStreamInput clientInput;
	private ByteArrayStreamOutput clientOutput;
	private DirectLinkClient clientLink;
	private DirectLinkServer serverLink;
	private boolean closed;
	
	public DirectServerLink() {
		streamC2S = new ByteArrayStream();
		streamS2C = new ByteArrayStream();
		serverInput = new ByteArrayStreamInput(streamC2S);
		serverOutput = new ByteArrayStreamOutput(streamS2C);
		clientInput = new ByteArrayStreamInput(streamS2C);
		clientOutput = new ByteArrayStreamOutput(streamC2S);
		this.clientLink = new DirectLinkClient();
		clientLink.link = this;
		this.serverLink = new DirectLinkServer();
		serverLink.link = this;
	}
	
	public void close() {
		closed = true;
	}
	
	public PlayerConnection getClientConnection() {
		return clientLink;
	}
	
	public PlayerConnection getServerConnection() {
		return serverLink;
	}
	
	private class DirectLinkClient implements PlayerConnection {
		
		private DirectServerLink link;

		@Override
		public InputStream getInputStream() throws IOException {
			return link.clientInput;
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			return link.clientOutput;
		}

		@Override
		public boolean isClosed() {
			return link.closed;
		}

		@Override
		public void close() {
			link.close();
		}
		
	}
	
	private class DirectLinkServer implements PlayerConnection {
		
		private DirectServerLink link;

		@Override
		public InputStream getInputStream() throws IOException {
			return link.serverInput;
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			return link.serverOutput;
		}

		@Override
		public boolean isClosed() {
			return link.closed;
		}

		@Override
		public void close() {
			link.close();
		}
		
	}
}
