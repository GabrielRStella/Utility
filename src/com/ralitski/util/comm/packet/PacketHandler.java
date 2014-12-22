package com.ralitski.util.comm.packet;

import com.ralitski.util.comm.ConnectionManager;

public interface PacketHandler {
	void handle(Packet p, ConnectionManager player);
	PacketList getPacketList();
}
