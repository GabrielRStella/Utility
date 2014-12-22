package com.ralitski.util.comm;

import com.ralitski.util.comm.packet.PacketHandler;

public interface Connectable extends PacketHandler {
	
	boolean running();
	boolean start();
	void stop();
}
