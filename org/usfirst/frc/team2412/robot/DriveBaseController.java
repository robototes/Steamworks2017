package org.usfirst.frc.team2412.robot;

import static org.usfirst.frc.team2412.robot.Constants.AUTO_FINAL_DIST;

import org.usfirst.frc.team2412.robot.sd.SmartDashboardUtils;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class DriveBaseController implements RobotController {

	private RobotDrive rd;
	private Joystick js;
	private Encoder encoder;

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
		rd = new RobotDrive(l1, l2, r1, r2);
		js = j;
	}

	/**
	 * Drives robot in coordination with controller.
	 */

	public void processTeleop() {
		double jsY = js.getY();
		double jsX = -js.getX();

		double jsTwist = -js.getTwist(); // getRawAxis(3) is for the new
											// joystick, getTwist is for the
											// logitech joystick.
		if (js.getRawButton(5)) {
			// Drive with twist
			rd.arcadeDrive(jsY, jsTwist, true);
		} else {
			// Drive like airplane
			rd.arcadeDrive(jsY, jsX, true);
		}
	}

	private int last;
	private int stage = 0;
	private boolean done = false;
	private double initDist = Double.NaN;
	private NetworkTable table = NetworkTable.getTable(VisionController.TABLENAME);

	public void processAutonomous() {
		if (done)
			return;
		if (encoder == null)
			encoder = new Encoder(6, 7); // channel a and channel b

		switch (stage) {
		case 0:
			rd.arcadeDrive(.8d, 0d, false);
			if (encoder
					.getDistance() >= 15d /** or other constant we determine **/
			) {
				stage = 1;
			}
			break;
		case 1:
			if (SmartDashboardUtils.getRobotPosition(false) == 0) {
				rd.arcadeDrive(0d, .5d, false);
				Timer.delay(.5d);
				rd.arcadeDrive(0d, 0d, false);
				stage = 2;
			} else if (SmartDashboardUtils.getRobotPosition(false) == 2) {
				rd.arcadeDrive(0d, .5d, false);
				Timer.delay(.5d);
				rd.arcadeDrive(0d, 0d, false);
				stage = 2;
			}
			break;
		case 2:
			try {
				if (table.getNumber("distance", Double.NaN) < AUTO_FINAL_DIST) {
					done = true;
					return;
				} else {
					rd.arcadeDrive(
							(initDist = (Double.isNaN(initDist) ? table.getNumber("distance", Double.NaN) : initDist))
									/ table.getNumber("distance", Double.NaN) + 0.1d,
							.8d * table.getNumber("angle", Double.NaN), true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		Timer.delay(.15d);

	}

	public void teleopInit() {

	}

	public void autonomousInit() {

	}

}
