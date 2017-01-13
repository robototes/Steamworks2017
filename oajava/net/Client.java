package oajava.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import oajava.net.packet.Packet;


public class Client {
	
	Socket s;
	ClientManager m;
	ArrayList<String> PROCESSQUEUE = new ArrayList<String>();
	
	public static int PORT = 42590;
	
	public Client(String ip, int port) throws IOException {
		s = new Socket();
		s.connect(new InetSocketAddress(InetAddress.getByName(ip),port),1000);
		new ClientThread().start();
	}
	
	public Client(Socket socket) {
		s = socket;
		new ClientThread().start();
	}
	
	public void setManager(ClientManager manager) {
		this.m=manager;
		for (String str : PROCESSQUEUE.toArray(new String[] {})) {
			m.UPONRECIEVE(s.getInetAddress().getHostAddress(), s.getPort(), str);
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
	
	public String recieve(boolean wait) {
		if (wait) {
			try {
				
				while (!s.isConnected()) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
				return rd.readLine();
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
				Scanner s = new Scanner(this.s.getInputStream());
				return s.nextLine();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NoSuchElementException e) {
				return "nopacket a";
			}
		}
		return "nopacket a";
	}

	
	private class ClientThread extends Thread {
		
		
		
		
		ClientThread() {
		}
		
		public void run() {
			boolean IndexOut = false;
			while (!IndexOut) {
			try {
			String st = recieve(true);
			if (st!=null) {
			if (m!=null) {
				m.UPONRECIEVE(s.getInetAddress().getHostAddress(), s.getPort(), st);
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
		s.close();
	}

}
