package com.ralitski.util.comm;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * not sure if this is useful in any way, other than for parsing "example.com:80"
 * 
 * @author ralitski
 */
public class Host {
	
	public static Host getLocalHost(int port) throws UnknownHostException {
		String ip = InetAddress.getLocalHost().getHostAddress();
		return new Host(ip, port);
	}
	
	public static Host getHost(String host) {
		return getHost(host, -1);
	}
	
	public static Host getHost(String host, int defaultPort) {
		String[] split = host.split(SPLIT, 2);
		if(split.length == 2) {
			String ip = split[0];
			int port = Integer.parseInt(split[1]);
			return new Host(ip, port);
		} else {
			return new Host(host, defaultPort);
		}
	}
	
	private static final String SPLIT = ":";
	
	private String ip;
	private int port;
	
	public Host(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public String getIp() {
		return ip;
	}
	
	public int getPort() {
		return port;
	}
	
	public Socket connect() {
		try {
			return new Socket(ip, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PlayerConnector getPlayerConnector() {
		return new SocketPlayerConnector();
	}
	
	private class SocketPlayerConnector implements PlayerConnector {

		@Override
		public PlayerConnection connect() {
			return new SocketPlayerConnection(Host.this.connect());
		}
		
	}
}
