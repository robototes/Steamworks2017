package org.usfirst.frc.team2412.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveBaseController implements RobotController {

	private RobotDrive rd;
	private Joystick js;
	private CANTalon left;
	private CANTalon right;
	
	//Variables for detecting whether targets weren't found three times in a row.
	private boolean targetsFoundLast;
	private boolean targetsFoundSecondLast;
	
	//When we were first close to the peg
	private double pegclosetime;
	private double delay; //The delay between pegclosetime and when we actually let go of the gear
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
		js = j;
	}
	
	@Override
	public void debug() {
		
	}

	/**
	 * Drives robot in coordination with controller.
	 */

	public void processTeleop() {
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
			rd.arcadeDrive(jsY, jsTwist*Constants.DRIVE_ROTATE_SPEED, true);
		}
	}

	
	public void processAutonomous() {
		driveForTime(rd, 0.3d, 0d, Constants.DRIVE_FORWARD_START, Constants.DRIVE_FORWARD_DURATION);
		//Check if we've finished turning blindly (above driveForTime() call)
		if(System.nanoTime() > Constants.DRIVE_FORWARD_START + Constants.DRIVE_FORWARD_DURATION + 1E9) {
			if(!Constants.dropGear) {
				//Turn if the robot isn't lined up with the peg
				boolean targetsFound = Constants.visionTable.getBoolean("targetsFound", false);
				if(targetsFound || targetsFoundLast || targetsFoundSecondLast) {
					double angle = Constants.visionTable.getNumber("angle", -1);
					double distance = Constants.visionTable.getNumber("distance", -1);
					System.out.println("Angle: " + angle);
					System.out.println("Distance: " + distance);
					if(Math.abs(angle) < 0.1) {
						//Robot is lined up, drive forward
						rd.arcadeDrive(0.2d, 0d, false);
					} else {
						//Line up robot
						double visionDirection = Math.signum(angle);
						rd.arcadeDrive(0d, 0.3*visionDirection, false);
						System.out.println("Turning");
					}
				} else { //Targets haven't been found for three times in a row.
					//System.out.println("No targets found!");
				}
				//Update targetsFoundLast and targetsFoundSeconLast
				targetsFoundSecondLast = targetsFoundLast;
				targetsFoundLast = targetsFound;
				if(Constants.visionTable.getBoolean("pegclose", false) && pegclosetime == Double.MAX_VALUE) {
					pegclosetime = System.nanoTime();
				}
				System.out.println(pegclosetime);
				Constants.dropGear = System.nanoTime() - pegclosetime > delay;
			}
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
		rd.setSafetyEnabled(false);
	}

	public void autonomousInit() {
		rd.setSafetyEnabled(false);
		targetsFoundLast = true;
		targetsFoundSecondLast = true;
		pegclosetime = Double.MAX_VALUE;
		delay = 1.5E9;
	}

}
