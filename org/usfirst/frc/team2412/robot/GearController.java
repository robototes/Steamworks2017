package org.usfirst.frc.team2412.robot;

import static org.usfirst.frc.team2412.robot.Constants.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

public class GearController implements RobotController {
	
	private Joystick stick;
	public Talon t;
	
	
	private final Script pickup = new Script() {

		@Override
		protected void execute() {
			try {
				t.set(PICKUP_SPEED);
				Thread.sleep(PICKUP_GEAR_MSTIME);
				t.set(0.0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	},
	drop = new Script() {

		@Override
		protected void execute() {
			try {
				t.set(DROP_SPEED);
				Thread.sleep(DROP_GEAR_MSTIME);
				t.set(0.0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	},
	autoDrop = new Script() {
		
		@Override
		protected void execute() {
			try {
				Thread.sleep(AUTO_MSDELAY_BEFORE_GEAR_DROP);
				drop.run();
			} catch (Exception e) {}
		}
		
	};
	
	
	public GearController(Joystick stick) {
		this.stick = stick;
		
		t = new Talon(motors[4]);
	}

	@Override
	public void processTeleop() {
		autoDrop.start();
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
