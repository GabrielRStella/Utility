package com.ralitski.util.comm;

import com.ralitski.util.comm.packet.Packet;

public class PacketListener implements Runnable {
	
	protected ConnectionManager player;
	protected Connectable owner;
	
	public PacketListener(ConnectionManager player) {
		this.player = player;
		this.owner = player.getHandler();
	}
	
	public ConnectionManager getPlayer() {
		return player;
	}
	
	public Connectable getOwner() {
		return owner;
	}
	
	public boolean start() {
		Thread thread = new Thread(this);
		thread.start();
		return true;
	}
	
	@Override
	public void run() {
		while(owner.running() && !player.getConnection().isClosed()) {
			try {
				Packet p = player.receivePacket();
				if(p != null) {
					owner.handle(p, player);
				}
			} catch(Exception e) {
				//not do anything? idk
				errorAlert(e);
			}
		}
		end();
	}
	
	protected void errorAlert(Exception error) {}

	//you might want to override this.
	protected void end() {
		owner.stop();
	}
}
