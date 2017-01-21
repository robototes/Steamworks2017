/**
 * 
 */
package org.usfirst.frc.team2412.robot.client.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Connects to robot and executes on the Client (DS) side
 *
 */
public class RobotConnection {

	/**
	 * Run this method on the DS
	 * roborio-2412-frc.local
	 * 
	 * or 
	 * 10.24.12.2
	 * 
	 * http://wpilib.screenstepslive.com/s/4485/m/24193/l/319135?data-resolve-url=true&data-manual-id=24193
	 * 
	 * [type]	[port]		[For]
	 * UDP/TCP	1180-1190	camera data
	 * TCP		1735 		smart dashboard
	 * UDP		1130		Dashboard-to-ROBOT
	 * UDP		1140		ROBOT-to-Dashboard
	 * HTTP		80			Camera connection
	 * HTTP		443			Camera connection
	 * UDP/TCP 	554			Realtime Streaming h.264
	 * UDP/TCP	5800-5810	Team Use
	 */
	public static void main_1(String[] args) {
		try {
			DatagramSocket s = new DatagramSocket();
			s.connect(InetAddress.getByName(args[0]), 5800);
			byte[] data = new byte[8092];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			while (true) {
				try {
					s.receive(packet);
					String sData = new String(data).trim();
					System.out.println(sData);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		 ServerSocket s = new ServerSocket(5800);
		 Socket socket = s.accept();
		 
		 System.out.println(socket.getInetAddress().getHostAddress());
	}

}
