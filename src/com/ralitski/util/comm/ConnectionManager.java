package com.ralitski.util.comm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.ralitski.util.comm.packet.Packet;
import com.ralitski.util.comm.packet.PacketList;

public class ConnectionManager {
	
	private UUID id;
	private Connectable holder;
	
	private PlayerConnection connection;
	private DataOutputStream out;
	private DataInputStream in;
	
	private Object customData;
	
	public ConnectionManager(PlayerConnection connection, Connectable handler) {
		this(connection, handler, UUID.randomUUID());
	}
	
	public ConnectionManager(PlayerConnection connection, Connectable handler, UUID id) {
		this.connection = connection;
		this.holder = handler;
		this.id = id;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setCustomData(Object o) {
		customData = o;
	}
	
	public Object getCustomData() {
		return customData;
	}
	
	//shortcut method for ease
	@SuppressWarnings("unchecked")
	public <T> T getCustomDataCast() {
		return (T)customData;
	}
	
	public Connectable getHandler() {
		return holder;
	}
	
	public PlayerConnection getConnection() {
		return connection;
	}
	
	private DataOutputStream getOutput() {
		if(out == null) {
			try {
				out = new DataOutputStream(connection.getOutputStream());
			} catch (IOException e) {
			}
		}
		return out;
	}
	
	private DataInputStream getInput() {
		if(in == null) {
			try {
				in = new DataInputStream(connection.getInputStream());
			} catch (IOException e) {
			}
		}
		return in;
	}
	
	public boolean sendUTF(String s) {
		DataOutputStream out = getOutput();
		try {
			out.writeUTF(s);
			out.flush();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean sendPacket(Packet p) {
		PacketList list = holder.getPacketList();
		DataOutputStream out = getOutput();
		try {
			list.write(p, out);
			out.flush();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public String getUTF() {
		DataInputStream in = getInput();
		try {
			if(in.available() > 0) {
				return in.readUTF();
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}
	
	public Packet receivePacket() {
		PacketList list = holder.getPacketList();
		DataInputStream in = getInput();
		try {
			if(in.available() > 0) {
				return list.read(in);
			} else {
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}
	
	public boolean equals(Object o) {
		return o == this ? true : (o instanceof ConnectionManager && ((ConnectionManager)o).id.equals(id));
	}
	
	public String toString() {
		return "CONNECTION("+ id + ")";
	}
}
