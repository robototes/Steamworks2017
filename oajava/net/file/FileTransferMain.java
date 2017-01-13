package oajava.net.file;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileTransferMain {
	
    private static File myFile = new File("C:\\Users\\s-bowmana\\Desktop\\chattertxt.txt");
	
	public static void main(String[] args) {
		int s = JOptionPane.showOptionDialog(null, "Send or recieve?", "FTP", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, new String[] { "Send", "Recieve" }, "Send");
		// send = 0; recieve = 1;

		String str = "";
		if (s == 0)
			str = "send";
		if (s == 1)
			str = "recieve";

		if (str.equals("send")) {
			// Sending file
			JFileChooser jfc = new JFileChooser();
			jfc.setCurrentDirectory(new File(System.getProperty("user.home")+File.separatorChar+"\\Desktop"));
			jfc.setMultiSelectionEnabled(false);
			jfc.showOpenDialog(null);
			try {
				sendFile(42424, jfc.getSelectedFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (str.equals("recieve")) {
			
			try {
				recieveFile(JOptionPane.showInputDialog("IP:"), 42424, new File(System.getProperty("user.home")+File.separatorChar+"\\Desktop\\res.mp4"));
			} catch (HeadlessException | IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void sendFile(int port, File f) throws IOException {
		ServerSocket ss = new ServerSocket(port);
		Socket socket = ss.accept();
	
		FileGiver fg = new FileGiver(socket, f);
		fg.send();
		ss.close();
	}
	
	public static void recieveFile(String ip, int port, File dir) throws UnknownHostException, IOException {
			Socket socket = new Socket(InetAddress.getByName(ip), port);
			FileTaker fw = new FileTaker(socket, dir);
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				
			}
			fw.copy();
			socket.close();
	}

}
