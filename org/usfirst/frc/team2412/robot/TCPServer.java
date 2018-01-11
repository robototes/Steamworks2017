package org.usfirst.frc.team2412.robot;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer implements RobotController {
	private ServerSocket welcomeSocket;
	private Socket connectionSocket;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	
	private static boolean stringIsValid(String data) {
		return data != null && !data.equals("");
	}
	@Override
	public void teleopInit() {
		
	}
	@Override
	public void processTeleop() {
		
	}
	@Override
	public void autonomousInit() {
		try {
			welcomeSocket = new ServerSocket(2412);
			connectionSocket = welcomeSocket.accept();
			inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			outToClient = new DataOutputStream(connectionSocket.getOutputStream());
		} catch(IOException e) {
			System.err.println("Error connecting to Jetson!");
			e.printStackTrace();
		}
	}
	@Override
	public void processAutonomous() {
		String clientSentence;
		String capitalizedSentence;
		try {
			do {
				clientSentence = inFromClient.readLine();
				if(!stringIsValid(clientSentence)) break;
				System.out.println("Received: " + clientSentence);
				capitalizedSentence = clientSentence.toUpperCase() + '\n';
				outToClient.writeBytes(capitalizedSentence);
			} while(stringIsValid(clientSentence));
			System.out.println("No data received! Quitting...");
			welcomeSocket.close();
		} catch(IOException e) {
			System.err.println("Error connecting to Jetson!");
			e.printStackTrace();
		}
	}
}
