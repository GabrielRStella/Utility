package com.ralitski.util.comm.packet.event;

import com.ralitski.util.Cancellable;
import com.ralitski.util.comm.packet.Packet;
import com.ralitski.util.doc.Usage;

/**
 * @author ralitski
 */
@Usage(Usage.EXTERNAL)
public class PacketReceivedEvent implements Cancellable {

    //the packet that was received
    private Packet packet;
    //if the packet's handling was cancelled
    private boolean cancelled;

    public PacketReceivedEvent(Packet p) {
        this.packet = p;
    }

    public Packet getPacket() {
        return this.packet;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean bln) {
        this.cancelled = bln;
    }

}
