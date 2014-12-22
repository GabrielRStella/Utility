package com.ralitski.util.comm.packet;

/**
 * packet channels are used to notify specific handlers on a packet's arrival.
 *
 * @author ralitski
 */
public enum Channels {

    //default channel
    DEFAULT,
    //channel that will notify all packet reception handlers of this packet
    ALL,
    //packet requests
    REQUEST;
}
