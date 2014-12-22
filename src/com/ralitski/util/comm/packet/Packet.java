package com.ralitski.util.comm.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.ralitski.util.comm.ConnectionManager;

/**
 *
 * @author ralitski
 */
public abstract class Packet {
	
	private static PacketList list;
	
	static {
		list = new PacketList();
	}
	
	public static PacketList getPacketList() {
		return list;
	}
	
	public static void registerPacket(int id, Class<? extends Packet> type) {
		list.addBytePacketType(id, type);
	}

    /**
     * reads a packet from the given input stream.
     * @param in the input stream to be read from
     * @return a new packet, read from the input stream
     * @throws IOException if an error occurs while reading (such as an EOFException)
     */
    public static Packet read(PacketList list, DataInputStream in) throws IOException {
    	return list.read(in);
    }

    /**
     * writes a packet to the output stream.
     * @param p the packet to be written
     * @param out the output stream for the packet to be written to
     * @throws PacketTypeNotFoundException if the passed packet is not of a registered type.
     * @throws IOException if an error occurs while writing the packet
     */
    public static void write(PacketList list, Packet p, DataOutputStream out) throws PacketTypeNotFoundException,  IOException {
        list.write(p, out);
    }

    //the channel which listeners must listen to to catch this packet
    private String channel = Channels.DEFAULT.toString();

    /*
     * empty constructor; must be included in all packet subclasses
     */
    public Packet() {}

    /*
     * abstract data loading.
     * note: readData() and writeData() must read/write
     * the SAME TYPES OF DATA (opposite methods; ie readInt() and writeInt(i))
     * in the SAME ORDER otherwise EVERYTHING WILL BREAK
     */

    /**
     * for the object to read its data from the socket.
     * @param in the data input stream constructed around the socket's input stream, for data to be read from
     * @throws IOException so you don't have to catch any errors
     */
    protected abstract void readData(DataInputStream in) throws IOException;

    /**
     * for the object to write its data to the socket.
     * @param out the data output stream constructed around the socket's output stream, for data to be written to
     * @throws IOException so you don't have to catch any errors
     */
    protected abstract void writeData(DataOutputStream out) throws IOException;

    /**
     * for the packet to do whatever it does
     * @param player the player this packet was received from
     */
    public abstract void handle(ConnectionManager player);

    /**
//     * gets the channel this packet will be put on for {@link com.gameon.libs.comm.event.PacketReceivedEvent PacketReceivedEvents}.
     * @return the channel this packet is currently on
     */
    public String getChannel() {
        return channel;
    }

    /**
     * sets the channel for this packet to put events on (if {@link #callEvent()} returns true)
     * @param c the new channel for this packet to call events on
     * @return this packet
     */
    public Packet setChannel(Channels c) {
        return setChannel(c.toString());
    }

    /**
     * sets the channel for this packet to put events on (if {@link #callEvent()} returns true)
     * @param c the new channel for this packet to call events on
     * @return this packet
     */
    public Packet setChannel(String c) {
        this.channel = c;
        return this;
    }
}
