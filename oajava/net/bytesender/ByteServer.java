package oajava.net.bytesender;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;


public class ByteServer {

	public ServerSocket s;
	public MultiClient SOCKET;
	private ByteClientManager cm = null;
	
	public ByteServer() {}
	
	public ByteServer(ByteClientManager cm) {
		this.cm = cm;
	}
	
	public void open(int port) {
		s = null;
		try {
			s = new ServerSocket(port);
			JOptionPane.showMessageDialog(null, "Opening server on port " + port + ".\nmake sure that port is portforwarded (for global uses)!\nPress ok to continue.", "Opened Server", JOptionPane.PLAIN_MESSAGE);
			Socket socket = s.accept();
			if (cm==null) {
				SOCKET = new MultiClient(socket);
			} else {
				SOCKET = MultiClient.applyManagerBeforeConnection(socket, cm);
			}
			
			new Thread() {
				public void run() {
					while (true) {
						Socket socket;
						try {
							socket = s.accept();
							SOCKET.addClient(socket);
							
						} catch (IOException e) {
							e.printStackTrace();
							
							try {Thread.sleep(100);} catch (Exception ex) {}
						}
					}
				}
			}.start();
			
			//SOCKET.s.close();
		} catch (IOException e) {
			JOptionPane.showConfirmDialog(null, "An error opening the server occured. Reason: " + e.getMessage(), "Server Error", JOptionPane.OK_OPTION);
		}
	}
	
	
}
