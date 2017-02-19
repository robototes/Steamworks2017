package org.usfirst.frc.team2412.robot;

import static org.usfirst.frc.team2412.robot.Constants.*;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveBaseController implements RobotController {

	private RobotDrive rd;
	private Joystick js;
	private CANTalon left;
	private CANTalon right;
	private int rotateOffset = 0;
	private Encoder encoderLeft, encoderRight;
	
	public static double ROTATION_SPEED = 1.0;
	public static double DRIVE_SPEED = 1.0;
	
	
	/**
	 * 
	 * @param j
	 *            - the joystick
	 * @param l1
	 *            - front left motor
	 * @param l2
	 *            - back left motor
	 * @param r1
	 *            - front right motor
	 * @param r2
	 *            - back right motor
	 */
	
	public DriveBaseController(Joystick j, int l1, int l2, int r1, int r2) {
		left = new CANTalon(l1);
		right = new CANTalon(r1);
		rd = new RobotDrive(left, new CANTalon(l2), right, new CANTalon(r2));
		
		// TIM and ALEX! This seems to have helped fix the intermittent connection issue.
		rd.setSafetyEnabled(false);
		js = j;
		
//		encoderLeft = new Encoder(ENCODER_ID_LEFT_IN, ENCODER_ID_LEFT_OUT);
//		encoderRight = new Encoder(ENCODER_ID_RIGHT_IN, ENCODER_ID_RIGHT_OUT);
//		
//		encoderLeft.reset();
//		encoderRight.reset();
	}
	
	@Override
	public void debug() {
		try {
			System.out.println("Encoder Data: Rate: " + (encoderLeft.getRate()) +"/" + encoderRight.getRate() + " Distance: " + encoderLeft.getDistance()+"/"+encoderRight.getDistance());
		} catch (Exception e) {
			
		}
	}

	/**
	 * Drives robot in coordination with controller.
	 */

	public void processTeleop() {
		Constants.DRIVE_SPEED = js.getRawButton(1) ? 0.6 : 0.8;
		Constants.DRIVE_ROTATE_SPEED = js.getRawButton(1) ? 0.456 : 0.7;
		
		double jsY = -js.getY()*Constants.DRIVE_SPEED;
		double jsX = -js.getX();

		double jsTwist = -js.getTwist(); // getRawAxis(3) is for the new
											// joystick, getTwist is for the
											// logitech joystick.
		if (js.getRawButton(5)) {
			// Drive like airplane
			rd.arcadeDrive(jsY, jsX*Constants.DRIVE_ROTATE_SPEED, true);					
		} else {
			// Drive with twist
//			rd.arcadeDrive(1.0d, 0d, true);

			rd.arcadeDrive(jsY, jsTwist*Constants.DRIVE_ROTATE_SPEED, true);
		}
	}

	public void processAutonomous() {
		//Drive forward, no matter what station we're at
		driveForTime(rd, 0.3d, 0d, Constants.DRIVE_FORWARD_START, Constants.DRIVE_FORWARD_DURATION);
		//Do some more driving, depending on what station we're at
		if(Constants.STARTING_STATION == 2) {
			//Drive backwards after a short delay
			driveForTime(rd, -0.3d, 0d, Constants.DRIVE_REVERSE_START, Constants.DRIVE_REVERSE_DURATION);
		} else {
			int direction = (Constants.STARTING_STATION == 1) ? -1 : 1;
			//Turn based on the station
			driveForTime(rd, 0d, 0.1d * direction, Constants.TURN_START, Constants.TURN_DURATION);
			
		}
	}

//	//Drive forward (assumes robot is lined up with peg)
//	private void driveForward() {
//		if(System.nanoTime() - Constants.startuptime > 13E8) return;
//		rd.arcadeDrive(0.3d, 0, false);
//	}
	//Drives for a specified amount of time
	private void driveForTime(RobotDrive rd, double move, double rotate, double startuptime, double duration) {
		double deltaTime = System.nanoTime() - startuptime; 
		if(deltaTime < 0 || deltaTime > duration) return;
		rd.arcadeDrive(move, rotate, false);
	}
	
	public void teleopInit() {

	}

	public void autonomousInit() {
	}
	
	
}
