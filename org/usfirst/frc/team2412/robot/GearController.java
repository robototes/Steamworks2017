package org.usfirst.frc.team2412.robot;

import static org.usfirst.frc.team2412.robot.Constants.AUTO_MSDELAY;
import static org.usfirst.frc.team2412.robot.Constants.BUTTON_ID_DROP_GEAR;
import static org.usfirst.frc.team2412.robot.Constants.BUTTON_ID_PICKUP_GEAR;
import static org.usfirst.frc.team2412.robot.Constants.DROP_GEAR_MSTIME;
import static org.usfirst.frc.team2412.robot.Constants.DROP_SPEED;
import static org.usfirst.frc.team2412.robot.Constants.PICKUP_GEAR_MSTIME;
import static org.usfirst.frc.team2412.robot.Constants.PICKUP_SPEED;
import static org.usfirst.frc.team2412.robot.Constants.motors;
import edu.wpi.first.wpilibj.Joystick;
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
				Thread.sleep(AUTO_MSDELAY);
				drop.run();
			} catch (Exception e) {}
		}
		
	};
	
	
	public GearController(Joystick stick) {
		this.stick = stick;
		
		t = new Talon(motors[4]);
	}

	public void processTeleop() {
		autoDrop.start();
	}

	public void processAutonomous() {
		if (stick.getRawButton(BUTTON_ID_PICKUP_GEAR)) {
			pickup.start();
		} else if (stick.getRawButton(BUTTON_ID_DROP_GEAR)) {
			drop.start();
		}
	}

}
