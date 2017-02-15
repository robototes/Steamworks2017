package org.usfirst.frc.team2412.robot.cam;

import java.awt.Canvas;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import org.usfirst.frc.team2412.robot.cam.net.DgsConnection;
import org.usfirst.frc.team2412.robot.cam.net.DgsConnection.UponRecieve;
import org.usfirst.frc.team2412.robot.cam.net.TcpConnection;

public class CameraClient {
	
	private static InetAddress idr;
	private static DgsConnection connection = null;

	public static void main(String[] args) throws UnknownHostException, IOException {
		Camera cam = Camera.generateUserCamera();
		final UponRecieve camUponRec = cam.connection.uponr;
		cam.connection.uponr = (ip, port, data) -> {
			camUponRec.uponRecieve(ip, port, data);
			if (idr == null) {
				idr = ip;
				try {
					connection = new DgsConnection(ip, port);
				} catch (SocketException e1) {
					e1.printStackTrace();
				}
			}
		};
		TcpConnection commander = new TcpConnection(InetAddress.getByName("localhost"), DgsConnection.PORT_CONTROLLER);
			
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, Camera.WIDTH, Camera.HEIGHT+50);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Canvas can = new Canvas();
		can.setBounds(0, 0, Camera.WIDTH, Camera.HEIGHT);
		
		can.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				send(commander, ("mousePressed " +e.getButton()));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				send(commander, ("mouseReleased " +e.getButton()));
			}
			
		});
		can.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent arg0) {
				send(commander, ("mouseMove " + arg0.getX() + "," + arg0.getY()));
			}
		});
		can.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				send(commander, ("keyPress " + arg0.getKeyCode()));
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				send(commander, ("keyRelease " + arg0.getKeyCode()));
			}
			
		});
		
		frame.getContentPane().add(can);
		
		frame.setVisible(true);
		
		
		while (frame.isDisplayable()) {
			can.getGraphics().drawImage(cam.getImage(), 0, 0, null);
		}
		
	}
	
	private static void send(TcpConnection con, String data) {
		try {
			con.send(data + "\n");
		} catch (Exception e) {}
	}
	
}
