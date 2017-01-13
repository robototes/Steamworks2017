package oajava.chat;

import java.io.File;
import java.io.StringWriter;
import java.net.InetAddress;

import javax.swing.JOptionPane;

import org.json.JSONArray;

import oajava.net.Client;
import oajava.net.ClientManager;
import oajava.net.Server;

public class Initialize implements ClientManager {
	
	public static Server s;
	public static Client c;
	public FChatMain frame;
	public FChatTalk input;
	private static Initialize main;
	public static String username = "<dynamic>";
	public static int namecolor = 0;
	public static final File DATA = new File(System.getProperty("user.home")+File.separatorChar+"OAJava/Chat/JSONArray.json".replace('/', File.separatorChar));
	private static JSONArray master_array;
	public static void main(String[] args) {
		if (args.length==0) {args = new String[] {"-servera"};
			
		}
		if (args[0].equals("-server")) {
			s = new Server();
			s.open(Client.PORT);
			s.SOCKET.setDefaultManager(new Initialize(false));
			s.SOCKET.getClient(0).setManager(new Initialize(false));
			master_array = new JSONArray();
			
			new Thread() {
				public void run() {
					while (true) {
					this.setName("Server Ping-Pong Packet Sender");
					if (s.SOCKET!=null)
					s.SOCKET.send("pingpong".getBytes());
					try {Thread.sleep(1000);} catch (Exception e) {}
					}
				}
			}.start();
		} else {
			try {
				new Initialize();
			} catch (Exception e) {}
		}
	}
	
	public static Initialize getInitialize() {
		return main;
	}
	
	private Initialize(boolean b) {
		
	}

	private Initialize() throws Exception {
		Initialize.main = this;
		
		UserNameInput i = new UserNameInput();
		i.waitFor();
		i.close();
		int usercolor = i.getSelectedColor();
		String username = i.getSelectedName();
		String ip = i.getSelectedIP();
		System.out.println("name: " + username + " ip: " + ip + " col: " + usercolor);
		namecolor = usercolor;
		Initialize.username = username;
		try {
			InetAddress.getByName(ip);
			Initialize.c = new Client(ip, Client.PORT);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unable to Connect to " + ip + ".\nPlease restart the application.", "Unable to Connect", JOptionPane.ERROR_MESSAGE);System.exit(1);
		}
		
		Initialize.c.setManager(this);
		try {
			frame = new FChatMain();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void UPONRECIEVE(String ip, int port, String message) {
		String messagedata = "";
		try {messagedata = message.split(" ", 2)[1];} catch (Exception e) {
			messagedata = "";
		}
		String messagecode = message.split(" ", 2)[0];
				
		if (c!=null) {
		if (messagecode.equals("msgarray")) {
			master_array = new JSONArray(messagedata);
			System.out.println("message array recieved ("+messagedata+")");
			
			frame.reset(master_array);
		}
		if (messagecode.equals("pingpong")) {
			c.send("pingpong".getBytes());
		}
		} else {
			if (messagecode.equals("pingpong")) {
				
			}
			
			if (messagecode.equals("newmsg")) {
				System.out.println("new message");
				master_array.put(messagedata);
				java.io.StringWriter str = new StringWriter();
				master_array.write(str);
				Initialize.s.SOCKET.send(("msgarray " + str.getBuffer().toString()).getBytes());
			}
			
			if (messagecode.equals("disconnect")) {
				s.SOCKET.remove(ip);
			}
			
			
		}
		
		
		
	}
	
	public static String getHTMLString() {
		return Integer.toHexString(namecolor & 0xffffff);
	}
	
}
