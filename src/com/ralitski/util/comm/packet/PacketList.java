package com.ralitski.util.comm.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacketList {

    private Map<Byte, Class<? extends Packet>> bytePackets;
    private Map<String, Class<? extends Packet>> stringPackets;
    
    public PacketList() {
        bytePackets = new HashMap<Byte, Class<? extends Packet>>();
        stringPackets = new HashMap<String, Class<? extends Packet>>();
    }

    public byte getPacketByteId(Class<? extends Packet> type) {
        for(Byte b : bytePackets.keySet()) {
            if(bytePackets.get(b) == type) {
                return b;
            }
        }
        return -1;
    }

    public String getPacketStringId(Class<? extends Packet> type) {
        for(String s : stringPackets.keySet()) {
            if(stringPackets.get(s) == type) {
                return s;
            }
        }
        return null;
    }

    /*
     * packet registration
     */


    /**
     * see {@link #addBytePacketType(byte id, Class<? extends Packet> type)}
     * @param id the byte id under which the packet will be registered
     * @param type the packet type that will be registered under the given byte id
     * @return if the packet type was successfully registered
     */
    public boolean addBytePacketType(int id, Class<? extends Packet> type) {
        return addBytePacketType((byte)id, type);
    }

    /**
     * registers a packet type with a given byte id
     * @param id the byte id under which the packet will be registered
     * @param type the packet type that will be registered under the given byte id
     * @return if the packet type was successfully registered
     */
    public boolean addBytePacketType(byte id, Class<? extends Packet> type) {
        if(id < 0) {
            //System.out.println("[ServerComm] unable to add packet type " + type.getName() + " with id " + id + ", id can not be negative");
            return false;
        }
        if(bytePackets.get(id) != null) {
            //System.out.println("[ServerComm] unable to add packet type " + type.getName() + " with id " + id);
            return false;
        }
        //System.out.println("[ServerComm] added packet type " + type.getName() + " with id " + id);
        bytePackets.put(id, type);
        return true;
    }

    /**
     * registers a packet type with a given string id
     * @param id the string id under which the packet will be registered
     * @param type the packet type that will be registered under the given string id
     * @return if the packet type was successfully registered
     */
    public boolean addStringPacketType(String id, Class<? extends Packet> type) {
        if(id == null) {
            //System.out.println("[ServerComm] unable to add packet type " + type.getName() + " with id " + id + ", id can not be null");
            return false;
        }
        if(stringPackets.get(id) != null) {
            //System.out.println("[ServerComm] unable to add packet type " + type.getName() + " with id " + id);
            return false;
        }
        //System.out.println("[ServerComm] added packet type " + type.getName() + " with id " + id);
        stringPackets.put(id, type);
        return true;
    }

    /*
     * packet type getting
     */

    /**
     * gets the packet type registered under a given id
     * @param id the id to be looked for
     * @return the Class<? extends Packet> stored in the packet byte id map
     */
    public Class<? extends Packet> getPacketType(byte id) {
        return bytePackets.get(id);
    }

    /**
     * gets the packet type registered under a given id
     * @param id the id to be looked for
     * @return the Class<? extends Packet> stored in the packet string id map
     */
    public Class<? extends Packet> getPacketType(String id) {
        return stringPackets.get(id);
    }

    /*
     * actual packet getting (empty)
     */

    /**
     * constructs a new, empty packet with a given byte id
     * @param id the id of the packet type to be constructed
     * @return the new packet
     */
    public Packet newPacket(byte id) {
        try {
            Class<? extends Packet> c = getPacketType(id);
            return c == null ? null : c.newInstance();
        } catch (Throwable ex) {
            Logger.getLogger(Packet.class.getName()).log(Level.SEVERE, "New Packet (id: " + id + ") could not be instantiated", ex);
        }
        return null;
    }

    /**
     * constructs a new, empty packet with a given string id
     * @param id the id of the packet type to be constructed
     * @return the new packet
     */
    public Packet newPacket(String id) {
        try {
            Class<? extends Packet> c = getPacketType(id);
            return c == null ? null : c.newInstance();
        } catch (Throwable ex) {
            Logger.getLogger(Packet.class.getName()).log(Level.SEVERE, "New Packet (id: " + id + ") could not be instantiated", ex);
        }
        return null;
    }

    /*
     * saving/loading packets...
     * in.readBoolean():
     * true - byte id
     * false - string id
     */

    /**
     * reads a packet from the given input stream.
     * @param in the input stream to be read from
     * @return a new packet, read from the input stream
     * @throws IOException if an error occurs while reading (such as an EOFException)
     */
    public Packet read(DataInputStream in) throws IOException {
        Packet p;
        if(in.readBoolean()) {
            byte id = in.readByte();
            p = newPacket(id);
        } else {
            String id = in.readUTF();
            p = newPacket(id);
        }
        if(p != null) {
            p.setChannel(in.readUTF());
            p.readData(in);
        }
        return p;
    }

    /**
     * writes a packet to the output stream.
     * @param p the packet to be written
     * @param out the output stream for the packet to be written to
     * @throws PacketTypeNotFoundException if the passed packet is not of a registered type.
     * @throws IOException if an error occurs while writing the packet
     */
    public void write(Packet p, DataOutputStream out) throws IOException, PacketTypeNotFoundException {
        byte byteId = getPacketByteId(p.getClass());
        String stringId = null;
        if(byteId == -1) {
            stringId = getPacketStringId(p.getClass());
            if(stringId == null) {
                //the packet type is not registered; no data will be sent
                throw new PacketTypeNotFoundException(p.getClass());
            }
        }
        if(byteId >= 0) {
            out.writeBoolean(true);
            out.writeByte(byteId);
        } else {
            out.writeBoolean(false);
            out.writeUTF(stringId);
        }
        out.writeUTF(p.getChannel());
        p.writeData(out);
    }

}
