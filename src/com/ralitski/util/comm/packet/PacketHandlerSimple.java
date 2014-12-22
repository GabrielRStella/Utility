package com.ralitski.util.comm.packet;

import com.ralitski.util.comm.ConnectionManager;
import com.ralitski.util.comm.packet.event.PacketEventManager;
import com.ralitski.util.comm.packet.event.PacketReceivedEvent;

public class PacketHandlerSimple implements PacketHandler {
	
	private PacketEventManager events;
	
	public PacketHandlerSimple() {
		this(new PacketEventManager());
	}
	
	public PacketHandlerSimple(PacketEventManager events) {
		this.events = events;
	}
	
	public PacketEventManager getEventManager() {
		return events;
	}

	@Override
	public void handle(Packet p, ConnectionManager player) {
		PacketReceivedEvent event = events.callEvent(p);
		if(!event.isCancelled()) {
			p.handle(player);
		}
	}

	@Override
	public PacketList getPacketList() {
		return Packet.getPacketList();
	}

}
