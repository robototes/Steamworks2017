package oajava.net.bytesender;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import oajava.net.Client;
import oajava.net.packet.Packet;

public class MultiClient {
	
	ArrayList<ByteClient> clients = new ArrayList<ByteClient>();
	
	private ByteClientManager cm = null;

	public MultiClient(String ip, int port) throws IOException {
		clients.add(new ByteClient(ip, port) {
			@Override
			public void disconnect() throws IOException {
				super.disconnect();
				clients.remove(this);
			}
		});
	}
	
	public MultiClient(Socket s) {
		clients.add(new ByteClient(s) {
			@Override
			public void disconnect() throws IOException {
				super.disconnect();
				clients.remove(this);
			}
		});
	}
	
	public MultiClient() {
		
	}
	
	public static MultiClient applyManagerBeforeConnection(String ip, int port, ByteClientManager cm) throws IOException {
		MultiClient mc = new MultiClient();
		mc.cm = cm;
		mc.addClient(ip, port);
		return mc;
	}
	
	public static MultiClient applyManagerBeforeConnection(Socket s, ByteClientManager cm) throws IOException {
		MultiClient mc = new MultiClient();
		mc.cm = cm;
		mc.addClient(s);
		return mc;
	}
	
	public void addClient(String s, int port) throws IOException {
		clients.add(new ByteClient(s, port) {
			@Override
			public void disconnect() throws IOException {
				super.disconnect();
				clients.remove(this);
			}
		});
		
		if (cm!=null) {
			clients.get(clients.size()-1).setManager(cm);
		}
	}
	
	public void addClient(Socket s) throws IOException {
		clients.add(new ByteClient(s) {
			@Override
			public void disconnect() throws IOException {
				super.disconnect();
				clients.remove(this);
			}
		});
		
		if (cm!=null) {
			clients.get(clients.size()-1).setManager(cm);
		}
	}
	
	public void send(byte[] data) {
		for (ByteClient c : clients.toArray(new ByteClient[] {})) {
			c.send(data);
		}
	}
	
	public void send(Packet p) {
		for (ByteClient c : clients.toArray(new ByteClient[] {})) {
			c.send(p);
		}
	}
	
	public void send(byte[] data, String ip) {
		for (ByteClient c : clients.toArray(new ByteClient[] {})) {
			if (c.getSocket().getInetAddress().getHostAddress().equals(ip))
			c.send(data);
		}
	}
	
	public void send(Packet p, String ip) {
		for (ByteClient c : clients.toArray(new ByteClient[] {})) {
			if (c.getSocket().getInetAddress().getHostAddress().equals(ip))
			c.send(p);
		}
	}
	
	public void send(byte[] data, int port) {
		for (ByteClient c : clients.toArray(new ByteClient[] {})) {
			if (c.getSocket().getPort()==port)
			c.send(data);
		}
	}
	
	public void send(Packet p, int port) {
		for (ByteClient c : clients.toArray(new ByteClient[] {})) {
			if (c.getSocket().getPort()==port)
			c.send(p);
		}
	}
	
	public void setDefaultManager(ByteClientManager cm) {
		this.cm = cm;
	}
	
	public ByteClientManager getDefaultManager() {
		return cm;
	}

	public ByteClient getClient(int i) {
		return clients.get(i);
	}
	
	public void remove(String ip) {
		for (Client c : clients.toArray(new Client[] {})) {
			if (c.getSocket().getInetAddress().getHostAddress().equals(ip)) {
				clients.remove(c);
			}
		}
	}

}
