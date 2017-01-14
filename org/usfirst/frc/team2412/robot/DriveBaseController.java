package org.usfirst.frc.team2412.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveBaseController implements RobotController {

	public static final boolean DRIVE_FORWARD = true, DRIVE_SIDEWAYS = !DRIVE_FORWARD;
	private RobotDrive rd;
	private Joystick js;
	
	private double lastRotation = 0.0;
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
	@Override
	public void processTeleop() {
		double x = (js.getX()-0.5)*DRIVE_SPEED;
		double y = (js.getY()-0.5)*DRIVE_SPEED;
		double rotation = (js.getZ()-0.5)/ROTATION_SPEED;
		
		// process the direction

		rd.mecanumDrive_Cartesian(x, y, rotation-lastRotation, 0.0);
		
		
		lastRotation += rotation;
		
	}

	@Override
	public void processAutonomous() {
		switch (Constants.AutoStatus.getCurrent()) {
			default:{
				
			}break; // sorry, I put brackets here!
		}
	}
	
	/**
	 * Sets the speed and direction to drive at
	 * @param speed - how fast you want to get the gear
	 * @param rotation - how much to rotate at
	 * @param direction - Constant DRIVE_FORWARD or DRIVE_SIDEWAYS
	 */
	public void setDrive(double speed, double rotation, boolean direction) {
		
	}

}
