package org.usfirst.frc.team2412.robot;

import edu.wpi.first.wpilibj.Joystick;

public class GearController implements RobotController {
	
	private Joystick stick;
	private final Script pickup = new Script() {

		@Override
		public void execute() {
			
		}
		
	},
	drop = new Script() {

		@Override
		public void execute() {
			
		}
		
	};
	
	
	public GearController(Joystick stick) {
		this.stick = stick;
	}

	@Override
	public void processTeleop() {
		
	}

	@Override
	public void processAutonomous() {
		
	}

}
