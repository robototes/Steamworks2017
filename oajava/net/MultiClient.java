package oajava.net;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import oajava.net.packet.Packet;

public class MultiClient {
	
	ArrayList<Client> clients = new ArrayList<Client>();
	
	private ClientManager cm = null;

	public MultiClient(String ip, int port) throws IOException {
		clients.add(new Client(ip, port) {
			@Override
			public void disconnect() throws IOException {
				super.disconnect();
				clients.remove(this);
			}
		});
	}
	
	public MultiClient(Socket s) {
		clients.add(new Client(s) {
			@Override
			public void disconnect() throws IOException {
				super.disconnect();
				clients.remove(this);
			}
		});
	}
	
	public MultiClient() {
		
	}
	
	public static MultiClient applyManagerBeforeConnection(String ip, int port, ClientManager cm) throws IOException {
		MultiClient mc = new MultiClient();
		mc.cm = cm;
		mc.addClient(ip, port);
		return mc;
	}
	
	public static MultiClient applyManagerBeforeConnection(Socket s, ClientManager cm) throws IOException {
		MultiClient mc = new MultiClient();
		mc.cm = cm;
		mc.addClient(s);
		return mc;
	}
	
	public void addClient(String s, int port) throws IOException {
		clients.add(new Client(s, port) {
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
		clients.add(new Client(s) {
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
		for (Client c : clients) {
			c.send(data);
		}
	}
	
	public void send(Packet p) {
		for (Client c : clients) {
			c.send(p);
		}
	}
	
	public void send(byte[] data, String ip) {
		for (Client c : clients) {
			if (c.getSocket().getInetAddress().getHostAddress().equals(ip))
			c.send(data);
		}
	}
	
	public void send(Packet p, String ip) {
		for (Client c : clients) {
			if (c.getSocket().getInetAddress().getHostAddress().equals(ip))
			c.send(p);
		}
	}
	
	public void send(byte[] data, int port) {
		for (Client c : clients) {
			if (c.getSocket().getPort()==port)
			c.send(data);
		}
	}
	
	public void send(Packet p, int port) {
		for (Client c : clients) {
			if (c.getSocket().getPort()==port)
			c.send(p);
		}
	}
	
	public void setDefaultManager(ClientManager cm) {
		this.cm = cm;
	}
	
	public ClientManager getDefaultManager() {
		return cm;
	}

	public Client getClient(int i) {
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
