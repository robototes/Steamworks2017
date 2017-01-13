package oajava.net.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class FileGiver {
	
	FileInputStream fis;
	OutputStream os;
	Socket s;
	
	public FileGiver(Socket s, File f) {
		this.s = s;
		try {
			fis = new FileInputStream(f);
			os = s.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void send() throws IOException {
		boolean bool = true;
		while (fis.available()>0 || bool) {
			byte[] b = new byte[8196];
			int a = fis.read(b, 0, b.length);
			boolean gooded = false;
			while (!gooded) {
				try {
					os.write(b, 0, a);
					gooded=true;
				} catch (Exception e) {
					try {
						os = s.getOutputStream();
					} catch (Exception ex) {}
				}
			}
			if (a==-1) bool = false;
		}
		s.close();
	}

}
