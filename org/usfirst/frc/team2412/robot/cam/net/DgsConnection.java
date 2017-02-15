package org.usfirst.frc.team2412.robot.cam.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.usfirst.frc.team2412.robot.cam.Camera;

public class DgsConnection {

	private DatagramSocket sock;
	public static final int PORT = 5804, PORT_CONTROLLER = 5805;
	private InetAddress conn;
	public UponRecieve uponr;
	private int port = PORT;

	Thread __update = new Thread() {
		public void run() {
			while (sock == null || sock.isClosed())
				try {
					Thread.sleep(0, 50);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			while (!sock.isClosed()) {
				try {
					byte[] data = new byte[CameraSender.DIV];
					DatagramPacket pkt = new DatagramPacket(data, 0, data.length);
					pkt.setPort(PORT);
					sock.receive(pkt);
					new Thread("Upon-Receive") {
						public void run() {
							uponr.uponRecieve(pkt.getAddress(), PORT, data);
						}
					}.start();
				} catch (Exception e) {
				}
			}

			System.out.println("ended updater");
		}
	};

	{
		__update.start();
	}

	public DgsConnection(InetAddress conn) throws SocketException {
		sock = new DatagramSocket();
		this.conn = conn;
	}

	public DgsConnection(int port) throws SocketException {
		sock = new DatagramSocket(port);
		System.out.println("Send Size: " + sock.getSendBufferSize());
		this.conn = null;
	}

	public DgsConnection(InetAddress ip, int port) throws SocketException {
		this(ip);
		this.port = port;
	}

	public void setRecieveManager(UponRecieve ur) {
		uponr = ur;
	}

	public void send(byte[] data) throws IOException {
		DatagramPacket pkt = new DatagramPacket(data, 0, data.length);
		pkt.setAddress(conn);
		pkt.setPort(port);
		sock.send(pkt);
	}

	public void send(DatagramPacket pkt) throws IOException {
		pkt.setAddress(conn);
		pkt.setPort(port);
		sock.send(pkt);
	}

	public void close() {
		sock.close();
	}

	@FunctionalInterface
	public static interface UponRecieve {
		public void uponRecieve(InetAddress ip, int port, byte[] data);
	}

	public boolean isConnected() {
		return sock.isConnected();
	}

	public void setIp(InetAddress ip) {
		conn = ip;
	}

}
