package org.usfirst.frc.team2412.robot;

import static org.usfirst.frc.team2412.robot.Constants.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class GearController implements RobotController {
	
	private Joystick stick;
	public Talon t;
	private int pickupi, dropi;
	
	private final Script pickups = new Script() {

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
	drops = new Script() {

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
	autoDrops = new Script() {
		
		@Override
		protected void execute() {
			try {
				drops.run();
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
		autoDrops.start();
	}

	public void processAutonomous() {
		if (stick.getRawButton(pickupi)) {
			pickups.start();
		} else if (stick.getRawButton(dropi)) {
			drops.start();
		}
	}

}
