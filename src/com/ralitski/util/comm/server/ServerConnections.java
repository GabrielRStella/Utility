package com.ralitski.util.comm.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.ralitski.util.comm.ConnectionManager;
import com.ralitski.util.comm.PacketListener;
import com.ralitski.util.comm.PlayerConnection;
import com.ralitski.util.comm.SocketPlayerConnection;
import com.ralitski.util.comm.packet.Packet;

/**
 * a connection listener implementation that allows for direct (socketless)
 * connection within the same runtime environment.
 * 
 * the server may want to call the stop() method on this class when it stops by itself to ensure the ServerSocket closes.
 * 
 * @author ralitski
 */
public class ServerConnections implements Runnable {
	
	private int timeout;
	private ServerConnectionsUser owner;
	private ServerSocket sock;
	private Thread thread;
	
	public ServerConnections(ServerConnectionsUser server) {
		this(server, 0);
	}
	
	public ServerConnections(ServerConnectionsUser server, int timeout) {
		this.owner = server;
		this.timeout = timeout;
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout > 0 ? timeout : 0; //no negatives, yo
	}
	
	public int getTimeout() {
		return timeout;
	}
	
	public Thread getThread() {
		return thread;
	}
	
	public boolean start() {
		//open the server socket
		if(thread == null && owner.running()) {
			int port = owner.getPort();
			try {
				sock = new ServerSocket(port);
				if(timeout > 0) {
					sock.setSoTimeout(timeout);
				}
			} catch (Exception e) {
				return false;
			}
			thread = new Thread(this);
			thread.start();
			return true;
		}
		return false;
	}
	
	public void stop() {
		thread = null;
		if(owner.running()) {
			owner.stop();
		} else {
			try {
				sock.close();
			} catch (IOException e) {
			}
		}
	}
	
	public void run() {
		while(owner.running() && !sock.isClosed()) {
			try {
				Socket s = sock.accept();
				handleConnection(new SocketPlayerConnection(s));
			} catch (IOException e) {
				//the server was probably stopped
				//if that's the case, the loop will exit automatically
			}
		}
		stop();
	}
	
	public ConnectionManager handleConnection(PlayerConnection connection) {
		ConnectionManager player = new ConnectionManager(connection, owner);
		PacketListener listener = owner.getPacketListener(player);
		if(listener == null) {
			player.getConnection().close();
			return null;
		} else {
			Thread t = new Thread(listener);
			t.start();
			return player;
		}
	}
}
