/**
 * 
 */
package org.usfirst.frc.team2412.robot.client.net;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
import java.util.ArrayList;
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
		new Thread() {
			public void run() {
				
			}
		}.start();
		
		
		ServerSocket s = new ServerSocket(5800);
		while (true) {
			try {
				Socket so = s.accept();
				new Thread() {
					public void run() {
						try {
							connected(so);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}
				}.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	private static String read(Scanner s, Socket sock) throws IOException {
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

	private static void connected(Socket socket) throws FileNotFoundException {
		System.out.println(socket.getInetAddress().getHostAddress());
		PrintStream log = new PrintStream(System.getProperty("user.home") + "/Desktop/Logs/"
				+ new SimpleDateFormat("MM.dd.hh.mm.ss").format(Date.from(Instant.now())) + ".txt");
		log.println("log created");

		boolean end = false;
		boolean endWhile = false;
		Scanner s = null;
		try {
			s = new Scanner(socket.getInputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (socket.isConnected() && !endWhile) {
			try {
				String s1 = "";
				try {
					s1 = read(s, socket);
					if (s1.equals("-end")) {
						if (end) {
							endWhile = true;
							socket.getOutputStream().write("reconnect".getBytes());	
							socket.close();
							socket = null;
						} else {
							end = true;
						}
					} else {
						System.out.println(s1);
						log.println(s1);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {

			}
		}
		log.close();
	}

}
