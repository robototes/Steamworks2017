package oajava.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class Bootup {

	public static void main(String[] args) throws IOException {
		
		System.out.println(InetAddress.getLocalHost().getHostAddress());
		InetAddress in = InetAddress.getByName("10.52.106.92");
		System.out.println(in.getHostName());
		
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader inr = new BufferedReader(new InputStreamReader(
		                whatismyip.openStream()));

		String ip = inr.readLine(); //you get the IP as a String
		System.out.println(ip);
		
		Initialize.main(new String[] {"-server"});
	}

}
