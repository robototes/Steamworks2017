package org.usfirst.frc.team2412.robot;

import static org.usfirst.frc.team2412.robot.Constants.AUTO_MSDELAY;
import static org.usfirst.frc.team2412.robot.Constants.DROP_GEAR_MSTIME;
import static org.usfirst.frc.team2412.robot.Constants.DROP_SPEED;
import static org.usfirst.frc.team2412.robot.Constants.PICKUP_GEAR_MSTIME;
import static org.usfirst.frc.team2412.robot.Constants.PICKUP_SPEED;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class GearController implements RobotController {
	
	private Joystick stick;
	public Talon motor;
	private int pickupGearButton, dropGearButton;
	
	private final Script pickupScript = new Script() {
	
		@Override
		protected void execute() {
			try {
				motor.set(PICKUP_SPEED);
				Thread.sleep(PICKUP_GEAR_MSTIME);
				motor.set(0.0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	},
	dropScript = new Script() {

		@Override
		protected void execute() {
			try {
				motor.set(DROP_SPEED);
				Thread.sleep(DROP_GEAR_MSTIME);
				motor.set(0.0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	},
	DropInAutonomous = new Script() {
		
		@Override
		protected void execute() {
			try {
				dropScript.run();
				Thread.sleep(AUTO_MSDELAY);
				dropScript.run();
			} catch (Exception e) {}
		}
		
	};
	
	
	public GearController(int motor, Joystick stick, int pickup, int drop) {
		this.stick = stick;
		
		this.motor = new Talon(motor);
		pickupGearButton = pickup;
		dropGearButton = drop;
	}

	public void processTeleop() {
		DropInAutonomous.start();
	}

	public void processAutonomous() {
		if (stick.getRawButton(pickupGearButton)) {
			pickupScript.start();
		} else if (stick.getRawButton(dropGearButton)) {
			dropScript.start();
		}
	}

}
