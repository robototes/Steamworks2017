package org.frc.team2412.app;

import java.util.HashMap;

public class PacketFinder {

	private static HashMap<String, Packet> packets;
	
	static {
		
		if (packets == null) {
			packets = new HashMap<String, Packet>();
			
			
			
			/**
			 * Add all the packets here
			 */
		}
		
	}
	
	
	public static Packet findPacket(String id) {
		return packets.get(id);
	}
	
	public static void bind(Packet p) {
		packets.put(p.packetID(), p);
	}
	
	public static void unbind(Packet p) {
		packets.remove(p.packetID(), p);
	}
	
	private PacketFinder() {}

}
