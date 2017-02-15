package org.frc.team2412.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class RobototesServer {

	private ServerSocket server;
	private boolean enabled;
	private Thread updater;

	private ArrayList<Socket> socks = new ArrayList<Socket>();

	{
		enabled = true;
		new Thread() {
			public void run() {
				updater = this;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				super.setName("RobototesServer on port " + server.getLocalPort());

				while (enabled) {
					try {
						new Thread() {
							public void run() {
								try {
									accepted(server.accept());
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}.start();
					} catch (Exception e) {
					}
				}

			}
		}.start();
	}

	public RobototesServer() throws IOException {
		server = new ServerSocket(2420);
		updater.interrupt();
	}

	boolean update = false;

	private void accepted(Socket socket) throws IOException {

		replace: {
			for (Socket sock : socks) {
				socks.replaceAll(new UnaryOperator<Socket>() {

					@Override
					public Socket apply(Socket t) {
						if (t.getInetAddress().equals(socket.getInetAddress()) && socket.getPort() == t.getPort()) {
							update = true;
							return socket;
						}
						return t;
					}

				});
				if (!update)
					socks.add(socket);
			}
		}

		BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		while (socket.isConnected()) {
			String __in = socketReader.readLine();

			PacketFinder.findPacket(__in.split(Packet.SEPERATOR + "")[0]).processServer(__in.split(Packet.SEPERATOR + "", 2)[1].split(Packet.SEPERATOR + ""), socket);

		}
	}

	public void send(Packet p) {
		String s = Packet.toString(p);
		for (Socket sock : socks) {
			if (sock.isConnected() && !sock.isOutputShutdown() && !sock.isClosed()) {
				try {
					sock.getOutputStream().write(s.getBytes());
				} catch (Exception e) {

				}
			}
		}
	}

	public void disable() throws IOException {
		enabled = false;
		server.close();
	}

}
