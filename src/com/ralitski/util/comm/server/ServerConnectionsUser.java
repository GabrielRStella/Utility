package com.ralitski.util.comm.server;

import com.ralitski.util.comm.Connectable;
import com.ralitski.util.comm.ConnectionManager;
import com.ralitski.util.comm.PacketListener;

public interface ServerConnectionsUser extends Connectable {
	
	/**
	 * the port this server is running on
	 * @return the port number of this server
	 */
	int getPort();
	
	/**
	 * The ServerConnectionsUser can return null here if the connection is to be closed.
	 * @param m the ConnectionManager (PlayerConnection wrapper for use with packets)
	 * @return the server's PacketListener implementation, or null to trash the connection
	 */
	PacketListener getPacketListener(ConnectionManager m);
}
