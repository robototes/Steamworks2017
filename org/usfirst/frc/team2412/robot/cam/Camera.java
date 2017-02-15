package org.usfirst.frc.team2412.robot.cam;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

import org.usfirst.frc.team2412.robot.cam.net.CameraSender;
import org.usfirst.frc.team2412.robot.cam.net.DgsConnection;

public class Camera {

	public static final int FRAME_RATE = 15;
	public static final String DESKTOP_NAME = /** "DESKTOP-ECKNV19" **/
			"10.52.106.240";
	public DgsConnection connection;

	static {
		double scale = 1.;

		int width = 640;
		int height = 480;

		WIDTH = 640;
		HEIGHT = 480;

		SENT_WIDTH = (int) (scale * width);
		SENT_HEIGHT = (int) (scale * height);
		
		
	}

	public static final int WIDTH, HEIGHT;
	public static final int SENT_WIDTH, SENT_HEIGHT;

	private int xoff;
	private BufferedImage img;

	private Camera(boolean cli) {
		if (!cli) {
			// Server. Wait for pi
			try {
				connection = new DgsConnection(InetAddress.getByName(DESKTOP_NAME));
			} catch (Exception e) {
			} finally {
			}
		} else {
			// Client

			img = new BufferedImage(SENT_WIDTH, SENT_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

			byte[] imgdata = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
			DataBufferByte buff = ((DataBufferByte) img.getRaster().getDataBuffer());
			
			try {
				connection = new DgsConnection(DgsConnection.PORT);

				connection.setRecieveManager((ip, port, data) -> {
					connection.setIp(ip);
					if (xoff != -1) xoff++; else xoff +=2;
					int xoff = (this.xoff - 1) * CameraSender.DIV;
					if (data[0] == '-' && data[1] == 'e' && data[2] == 'n' && data[3] == 'd') {
						this.xoff = 0;
						return;
					} else {
						for (int i = 0; i < data.length; i++) {
							try {
								buff.setElem(xoff+i, data[i]);
							} catch (Exception e) {
								this.xoff = 0;
								xoff = 0;
							}
						}
					}
				});

			} catch (SocketException e) {
				e.printStackTrace();
			}

		}
	}

	public BufferedImage getImage() {
		BufferedImage ret = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_3BYTE_BGR);

		while (xoff != 0) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		xoff = -1;
		ret.getGraphics().drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		return ret;
	}

	// On laptop
	public static Camera generateUserCamera() {
		return new Camera(true);
	}

	// on raspberry pi
	public static Camera generateServerCamera() {
		return new Camera(false);
	}

}
