package org.frc.team2412.app;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import org.frc.team2412.app.util.ArrayUtil;

public class Main implements Remote {


	private static Main mainServer, mainClient;
	
	public Main(String ip, int port) throws RemoteException {
		mainClient = this;
	}
	
	public Main(int port) throws RemoteException, AlreadyBoundException {
		
		mainServer = this;
	}
	
	{
		
	}

	
	public static void main(String[] args) throws NumberFormatException, AlreadyBoundException, NotBoundException, IOException {
		int off;
		if (args.length == 0) {
			String[] data = JOptionPane.showInputDialog("Ip of server (and port):\nExample: 10.24.12.17:339\n10.24.12.17 is the ip and 339 is the port\n\nType:").split(":", 2);
			Main.main(new String[] {"-client", data[0], data[1]});
		} else if ((off = ArrayUtil.find(args, "-server"))!=-1) {
			System.out.println("server found! off:"+off);
			new Main(Integer.valueOf(args[off+1]));
		} if ((off = ArrayUtil.find(args, "-client"))!=-1) {
			System.out.println("client found! off:"+off);
			new Main(args[off+1], Integer.valueOf(args[off+2]));
		} if (mainServer == null && mainClient == null) {
			JOptionPane.showMessageDialog(null, "Format:\njava -jar <jar> -client <ip> <port>\nOR java -jar <jar> -server <port>\nOR use both: java -jar <jar> -client 127.0.0.1 <port> -server <port>", "Invalid Arguments", JOptionPane.ERROR_MESSAGE);
		}
		
		
		Inet6Address idr =  (Inet6Address) InetAddress.getByName("2002:1811:39a8:0:0:0:0:0");
		System.out.println(idr.isReachable(10000000));
		
	}

	
}
