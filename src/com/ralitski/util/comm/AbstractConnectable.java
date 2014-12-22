package com.ralitski.util.comm;

import com.ralitski.util.comm.packet.PacketHandlerSimple;

public abstract class AbstractConnectable extends PacketHandlerSimple implements Connectable {
	
	private boolean running;
	
	public boolean start() {
		running = true;
		return true;
	}

	@Override
	public boolean running() {
		return running;
	}

	@Override
	public void stop() {
		running = false;
	}

}
