package org.usfirst.frc.team2412.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveBaseController implements RobotController {

	private RobotDrive rd;
	private Joystick js;
	
	public static double ROTATION_SPEED = 1.0;
	public static double DRIVE_SPEED = 1.0;
	
	/**
	 * 
	 * @param j - the joystick
	 * @param l1 - front left motor
	 * @param l2 - back left motor
	 * @param r1 - front right motor
	 * @param r2 - back right motor
	 */
	public DriveBaseController(Joystick j, int l1, int l2, int r1, int r2) {
		rd = new RobotDrive(l1, l2, r1, r2);
		js = j;
	}
	
	/**
	 * Drives robot in coordination with controller.
	 * Also, gets current rotation of robot and changes the magnitude of the
	 * x and y vectors of the requested direction with that. It's something that
	 * may not work, but will try
	 * 
	 * Trigger down = 
	 */
	public void processTeleop() {
		double x = (js.getX()-0.5)*DRIVE_SPEED;
		double y = (js.getY()-0.5)*DRIVE_SPEED;
		double rotation = (js.getZ()-0.5)/ROTATION_SPEED;
		
		// process the direction

		rd.mecanumDrive_Cartesian(x, y, rotation, 0.0);
		
		
		
	}

	public void processAutonomous() {
		switch (Constants.AutoStatus.getCurrent()) {
			default:{
				
			}break; // sorry, I put brackets here!
		}
	}
	
	
}
