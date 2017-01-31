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
	 */
	
	public void processTeleop() {
		double jsY = js.getY();
		double jsX = -js.getX();
		
		double jsTwist = -js.getTwist(); //getRawAxis(3) is for the new joystick, getTwist is for the logitech joystick.
		if(js.getRawButton(5)) {
			//Drive with twist
			rd.arcadeDrive(jsY, jsTwist, true);
		} else {
			//Drive like airplane
			rd.arcadeDrive(jsY, jsX, true);
		}
	}

	public void processAutonomous() {
		
	}

	public void teleopInit() {

	}

	public void autonomousInit() {
		
	}
	
	
}
