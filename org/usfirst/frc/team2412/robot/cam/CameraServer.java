package org.usfirst.frc.team2412.robot.cam;

import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.SocketException;
import java.util.Random;

import javax.swing.JOptionPane;

import org.usfirst.frc.team2412.robot.cam.net.CameraSender;
import org.usfirst.frc.team2412.robot.cam.net.DgsConnection;
import org.usfirst.frc.team2412.robot.cam.net.TcpConnection;

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;

public class CameraServer {

	private static Robot robot;
	
	static {
		while (robot == null) {
			try {
				robot = new Robot();
			} catch (Exception e) {}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Camera cam = Camera.generateServerCamera();
		TcpConnection commander = new TcpConnection(DgsConnection.PORT_CONTROLLER);
		commander.uponr = (ip, port, data) -> {
			String __in = new String(data).trim();
			
			
			String messagecode = __in.split(" ", 2)[0];
			String messagedata = __in.split(" ", 2)[1];
			
			switch (messagecode) {
			case "keyPress":
				CameraServer.robot.keyPress(Integer.valueOf(messagedata));
				break;
			case "keyRelease":
				CameraServer.robot.keyRelease(Integer.valueOf(messagedata));
				break;
			case "mousePressed":
				CameraServer.robot.mousePress(Integer.valueOf(messagedata));
				break;
			case "mouseReleased":
				CameraServer.robot.mouseRelease(Integer.valueOf(messagedata));
				break;
			case "mouseMove":
				String[] pos = messagedata.split(",");
				
				int xpos = Integer.valueOf(pos[0]);
				int ypos = Integer.valueOf(pos[1]);
				
				DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
				xpos *= dm.getWidth();
				xpos /= Camera.WIDTH;
				
				ypos *= dm.getHeight();
				ypos /= Camera.HEIGHT;
				
				CameraServer.robot.mouseMove(xpos, ypos);
				
				break;
			}
			
		};
		RPiCamera picam = null;
		
		
		
		try {
			picam = new RPiCamera() {
				@Override
				public BufferedImage takeBufferedStill() {
					return robot.createScreenCapture(new Rectangle(0,0,Camera.SENT_WIDTH, Camera.SENT_HEIGHT));
				}
			};
		} catch (FailedToRunRaspistillException e) {
			e.printStackTrace();
			System.exit(1);
		}

		picam.enableBurst();
		picam.setWidth(Camera.WIDTH);
		picam.setHeight(Camera.HEIGHT);

		BufferedImage img = new BufferedImage(Camera.SENT_WIDTH, Camera.SENT_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

		int SLEEP = 1000 / Camera.FRAME_RATE;
		
		while (true) {
			long last = System.currentTimeMillis();
			try {
				img.getGraphics().drawImage(picam.takeBufferedStill(), 0, 0, Camera.SENT_WIDTH, Camera.SENT_HEIGHT, null);
				
				long prctime = System.currentTimeMillis();
				
				CameraSender.send(cam.connection, img);
				
				System.out.println(System.currentTimeMillis()-prctime);
				cam.connection.send("-end".getBytes());
				System.out.println("sent");
				long slp = 0;
				Thread.sleep((slp = SLEEP - (System.currentTimeMillis() - last)) < 0 ? 0 : slp);
				System.out.println("slp value: " + slp);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
