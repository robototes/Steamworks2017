/**
 * 
 */
package org.usfirst.frc.team2412.robot.client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Connects to robot and executes on the Client (DS) side
 *
 */
public class RobotConnection {

	/**
	 * Run this method on the DS roborio-2412-frc.local
	 * 
	 * or 10.24.12.2
	 * 
	 * http://wpilib.screenstepslive.com/s/4485/m/24193/l/319135?data-resolve-
	 * url=true&data-manual-id=24193
	 * 
	 * [type] [port] [For] UDP/TCP 1180-1190 camera data TCP 1735 smart
	 * dashboard UDP 1130 Dashboard-to-ROBOT UDP 1140 ROBOT-to-Dashboard HTTP 80
	 * Camera connection HTTP 443 Camera connection UDP/TCP 554 Realtime
	 * Streaming h.264 UDP/TCP 5800-5810 Team Use
	 */

	public static void main(String[] args) throws IOException {
		InetAddress oldAddress = null;
		while (true) {
			s = null;
			if (oldAddress == null
					|| !InetAddress.getLocalHost().getHostAddress().equals(oldAddress.getHostAddress())) {
				if (oldAddress == null)
					oldAddress = InetAddress.getLocalHost();
				if (!oldAddress.getHostAddress().equals("127.0.0.1"))
					JOptionPane.showMessageDialog(null, "New IP Address, update the SmartDashboard variable!\n"
							+ (oldAddress = InetAddress.getLocalHost()).getHostAddress());
			}
			if (!oldAddress.getHostAddress().equals("127.0.0.1")) {
				ServerSocket s = new ServerSocket(5800);
				Socket socket = s.accept();

				System.out.println(socket.getInetAddress().getHostAddress());
				PrintStream log = new PrintStream(System.getProperty("user.home") + "/Desktop/Logs/"
						+ new SimpleDateFormat("MM.dd.hh.mm.ss").format(Date.from(Instant.now())) + ".txt");
				log.println("log created");

				while (socket.isConnected()) {
					try {
						String s1 = "";
						System.out.println((s1 = read(socket)));
						log.println(s1);
					} catch (Exception e) {

					}
				}
				log.close();
				try {
					s.close();
				} catch (Exception e) {

				}
			} else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static Scanner s = null;

	private static String read(Socket sock) throws IOException {
		if (s == null)
			s = new Scanner(sock.getInputStream());
		while (!s.hasNext()) {
			try {
				Thread.sleep(0, 25);
			} catch (Exception e) {

			}
		}
		return s.nextLine();
	}

}
