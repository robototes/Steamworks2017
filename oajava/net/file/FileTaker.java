package oajava.net.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FileTaker {
	
	FileOutputStream fos;
	InputStream is;
	
	Socket s;
	
	public FileTaker(Socket s, File f) {
		
		this.s = s;
		try {
			is = s.getInputStream();
			fos = new FileOutputStream(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void copy() throws IOException {
		OutputStream os = s.getOutputStream();
		while (s.isConnected()||is.available()>0) {
			os.write(new byte[] {});
			byte[] b = new byte[8196];
			int av = is.read(b, 0, b.length);
			if (av<=b.length)
			fos.write(b, 0, av);
		}
	}

}
