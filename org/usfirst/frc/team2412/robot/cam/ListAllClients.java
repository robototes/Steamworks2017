package org.usfirst.frc.team2412.robot.cam;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ListAllClients {

	public ListAllClients() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws UnknownHostException {
		System.out.println(InetAddress.getByName("10.24.12.101").getHostName());
	}

}
