package org.usfirst.frc.team2412.robot;

import static org.usfirst.frc.team2412.robot.Constants.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

public class GearController implements RobotController {
	
	private Joystick stick;
	public RobotDrive rd;
	
	
	private final Script pickup = new Script() {

		@Override
		protected void execute() {
			try {
				rd.arcadeDrive(PICKUP_SPEED, 0.0);
				Thread.sleep(PICKUP_GEAR_MSTIME);
				rd.arcadeDrive(0.0, 0.0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	},
	drop = new Script() {

		@Override
		protected void execute() {
			try {
				rd.arcadeDrive(DROP_SPEED, 0.0);
				Thread.sleep(DROP_GEAR_MSTIME);
				rd.arcadeDrive(0.0, 0.0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	};
	
	
	public GearController(Joystick stick) {
		this.stick = stick;
		
		rd = new RobotDrive(motors[4], motors[5]);
	}

	@Override
	public void processTeleop() {
		
	}

	@Override
	public void processAutonomous() {
		if (stick.getRawButton(BUTTON_ID_PICKUP_GEAR)) {
			pickup.start();
		} else if (stick.getRawButton(BUTTON_ID_DROP_GEAR)) {
			drop.start();
		}
	}

}
