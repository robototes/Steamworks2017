package org.usfirst.frc.team2412.robot.cam.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.usfirst.frc.team2412.robot.cam.net.DgsConnection.UponRecieve;

public class TcpConnection {

	Socket sock = new Socket();
	public UponRecieve uponr;
	
	private Thread __update = new Thread() {
		public void run() {
			while (sock.isConnected()) {
				try {
					BufferedReader rd = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					String line = rd.readLine();
					//JOptionPane.showMessageDialog(null, "Line:" + line);
					if (uponr != null) uponr.uponRecieve(sock.getInetAddress(), sock.getPort(), line.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
	};
	
	public TcpConnection(int port) throws IOException {
		ServerSocket server = new ServerSocket(port);
		sock = server.accept();
		server.close();
		__update.start();
	}
	
	public TcpConnection(InetAddress idr, int port) throws IOException {
		sock = new Socket();
		sock.connect(new InetSocketAddress(idr, port));
		__update.start();
	}
	
	public void send(String line) throws IOException {
		sock.getOutputStream().write(line.getBytes());
	}

}
