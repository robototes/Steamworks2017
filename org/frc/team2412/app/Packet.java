package org.frc.team2412.app;

import java.net.Socket;

public interface Packet {
	
	public final char SEPERATOR = '';
	public final char[] OTHERSEPERATORS = new char[] {'','','','','','','','','','','','','','','','','','','','','','','','','',' '};
	
	public String packetID();
	public String[] packetData();
	public void process(String[] data, Socket sock);
	public void processServer(String[] data, Socket sock);
	
	public static String toString(Packet p) {
		String ret = p.packetID() + SEPERATOR;
		for (String s : p.packetData()) {
			ret += s + SEPERATOR;
		}
		ret.substring(0, ret.length()-2);
		System.out.println("Packet: returning " +ret);
		return ret;
	}

}
