package org.usfirst.frc.team2412.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class DriveBaseController implements RobotController {

	private RobotDrive rd;
	private Joystick js;
	private CANTalon left;
	private CANTalon right;

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

	private int stage;
	private boolean done = false;
	private double initDist = Double.NaN;
	private NetworkTable table = NetworkTable.getTable(VisionController.TABLENAME);
	private double lastD, lastA = Double.NaN;
	
	public void processAutonomous() {
		if (done) {
			stage = 0;
		}
		if(Constants.STARTING_STATION == 2) {
			driveForward();
		}
		try {
			switch (stage) {
			case 0:
				rd.arcadeDrive(.5d, 0d, false);
				break;
			case 1:
				if(System.nanoTime() - Constants.startuptime > 2E8) {
					stage = 2;
				}
				if (Constants.STARTING_STATION == 1) {
					rd.arcadeDrive(0d, .3d, false);
				} else if (Constants.STARTING_STATION == 3) {
					rd.arcadeDrive(0d, .3d, false);
				} else {
					stage = 2; //Robot is in center position
				}
				break;
			case 2:
				try {
					if(table.getBoolean("targetsFound", false) == false) break; //No targets found
					if (table.getNumber("distance", Double.NaN) < Constants.AUTO_SECOND_STEP_DIST) {
						stage = 3;
						lastD = table.getNumber("distance", 2d);
						lastA = table.getNumber("angle", -1d);
						initDist = lastD;
						return;
					} else {
						rd.arcadeDrive((initDist = (Double.isNaN(initDist) ? table.getNumber("distance", Double.NaN) : initDist)) / table.getNumber("distance", Double.NaN) + 0.1d, .8d * table.getNumber("angle", Double.NaN), true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 3:
				if(table.getBoolean("targetsFound", false) == false) break; //No targets found
				if (table.getNumber("distance", lastD) == lastD || table.getNumber("angle", lastA) == lastA) {
					Timer.delay(0.15d);
					return;
				}
				if (table.getNumber("distance", 2) < Constants.AUTO_FINAL_DIST) {
					done = true;
					return;
				} else {
					rd.arcadeDrive((initDist = (Double.isNaN(initDist) ? table.getNumber("distance", Double.NaN) : initDist)) / (table.getNumber("distance", Double.NaN) * 2d) + 0.1d, .8d * table.getNumber("angle", Double.NaN), true);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//Drive forward (assumes robot is lined up with peg)
	private void driveForward() {
		if(System.nanoTime() - Constants.startuptime > 9.5E8) return;
		rd.arcadeDrive(0.3d, 0, false);
	}

	public void teleopInit() {

	}

	public void autonomousInit() {
		stage = 0;
	}

}
