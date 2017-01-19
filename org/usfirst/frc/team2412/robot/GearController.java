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
	public Talon t;
	private int pickupi, dropi;
	
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
				drop.run();
				Thread.sleep(AUTO_MSDELAY);
				drop.run();
			} catch (Exception e) {}
		}
		
	};
	
	
	public GearController(int motor, Joystick stick, int pickup, int drop) {
		this.stick = stick;
		
		t = new Talon(motor);
		pickupi = pickup;
		dropi = drop;
	}

	public void processTeleop() {
		autoDrop.start();
	}

	public void processAutonomous() {
		if (stick.getRawButton(pickupi)) {
			pickup.start();
		} else if (stick.getRawButton(dropi)) {
			drop.start();
		}
	}

}
