package oajava.net.bytesender;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import oajava.net.packet.Packet;


public class ByteClient {
	
	Socket s;
	ByteClientManager m;
	ArrayList<byte[]> PROCESSQUEUE = new ArrayList<byte[]>();
	InputStream stream;
	
	public static int PORT = 42590;
	
	public ByteClient(String ip, int port) throws IOException {
		s = new Socket();
		s.connect(new InetSocketAddress(InetAddress.getByName(ip),port),1000);
		stream = s.getInputStream();
		new ClientThread().start();
	}
	
	public ByteClient(Socket socket) {
		s = socket;
		try {
			stream = s.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new ClientThread().start();
	}
	
	public void setManager(ByteClientManager manager) {
		this.m=manager;
		for (byte[] str : PROCESSQUEUE.toArray(new byte[][] {})) {
			m.UPONRECIEVE(s.getInetAddress().getHostAddress(), s.getPort(), str, Integer.MIN_VALUE);
			PROCESSQUEUE.remove(0);
		}
	}
	
	public void send(byte[] data) {
		if (!new String(data).contains("pingpong"))
		try {
			PrintWriter p = new PrintWriter(s.getOutputStream(), true);
			p.println(new String(data));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] recieve(boolean wait) {
		if (wait) {
			try {
				
				while (!s.isConnected()) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				
				byte[] data = new byte[8192];
				lastcount = stream.read(data);
				while (lastcount==-1) {
						try {Thread.sleep(1);
					} catch (Exception e) {}
				}
				return data;
			} catch (Exception e) {
				try {Thread.sleep(25);} catch (Exception ex) {}
				if (!e.getMessage().equals("Connection reset")) {e.printStackTrace();try {
					System.err.println("[Client " + InetAddress.getLocalHost().getHostAddress() + " <-> " + s.getInetAddress().getHostAddress()+"] Disconnecting.");while (s.isConnected()) {try {disconnect();}catch (Exception ex) {}}
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
					while (s.isConnected()) {
					try {
						disconnect();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
						
					}
					}
				}}
			}
		} else {
			try {
				byte[] data = new byte[8192];
				while (stream.read(data)==-1) {
						try {Thread.sleep(1);
					} catch (Exception e) {}
				}
				int count = stream.read(data);
				lastcount = count;
				System.out.println("Count: "+count);
				return data;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NoSuchElementException e) {
				return new byte[] {};
			}
		}
		return new byte[]{};
	}

	private int lastcount = 0;
	private class ClientThread extends Thread {
		
		ClientThread() {
		}
		
		public void run() {
			boolean IndexOut = false;
			while (!IndexOut) {
			try {
			byte[] st = recieve(true);
			if (st!=null) {
			if (m!=null) {
				m.UPONRECIEVE(s.getInetAddress().getHostAddress(), s.getPort(), st, lastcount);
			} else {
				PROCESSQUEUE.add(st);
			}
			}
			} catch (Exception e) {
				if (e instanceof java.lang.IndexOutOfBoundsException) IndexOut = true;
				e.printStackTrace();
			}
		}
		}
	}
	

	public void send(Packet p) {
		
		String s = "";String[] pd = p.getPacketData();
		for (String str : pd) s += str.replace(Packet.SEPERATOR, '?')+Packet.SEPERATOR;
		
		send((p.packetID() + " " + s).getBytes());
	}

	public boolean isConnected() {
		return s.isConnected();
	}
	
	public Socket getSocket() {
		return s;
	}
	
	public void disconnect() throws IOException {
		s.shutdownInput();
		s.shutdownOutput();
		while (!s.isClosed()) {
			try {
				s.close();
			} catch (Exception e) {
				
			}
		}
	}

}
