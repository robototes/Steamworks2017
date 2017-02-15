package org.usfirst.frc.team2412.robot.cam.net;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.net.DatagramPacket;

import org.usfirst.frc.team2412.robot.cam.Camera;

public class CameraSender {

	public static final int DIV;
	
	static {
		
		int max = 0;
		
		for (int i = 1; i < 65536; i++) {
			if ((Camera.SENT_HEIGHT * Camera.SENT_WIDTH * 3) % i == 0) {
				max = i;
			}
		}
		
		
		System.out.println("Max:"+max);
		DIV = max;
	}
	
	private CameraSender() {}
	
	public static void send(DgsConnection con, BufferedImage raw) {
		BufferedImage cooked = new BufferedImage(Camera.SENT_WIDTH, Camera.SENT_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		cooked.getGraphics().drawImage(raw, 0, 0, Camera.SENT_WIDTH, Camera.SENT_HEIGHT, null);
		
		byte[] data = ((DataBufferByte) cooked.getRaster().getDataBuffer()).getData();
		for (int i = 0 ; i < (data.length / DIV) ; i ++) {
			try {
				con.send(new DatagramPacket(data, i * DIV, DIV));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(0, 500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.print(((data.length / DIV) -1) * DIV + " : " + DIV + " : " + data.length);
		
	}

}
