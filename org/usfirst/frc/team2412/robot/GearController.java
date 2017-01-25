package org.usfirst.frc.team2412.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;

public class GearController implements RobotController {
	
	private Joystick stick;
	private Solenoid upDownGripper;
	private Solenoid openCloseGripper;
	private int raiseButton, lowerButton, openButton, closeButton;
	
	//Creates a GearController class.
	//upDownGripper - Solenoid that raises/lowers the gear intake.
	//openCloseGripper - Solenoid that makes the arms move in/out (left/right).
	//Joystick stick - The codriver board to read values from.
	//raiseButton/closeButton - buttons for raising/lowering the intake.
	//openButton/closeButton - buttons for opening/closing the gear intake's arms.
	public GearController(Solenoid upDownGripper, Solenoid openCloseGripper, Joystick stick, int raiseButton, int lowerButton, int openButton, int closeButton) {
		//Set member variables.
		this.upDownGripper = upDownGripper;
		this.openCloseGripper = openCloseGripper;
		this.raiseButton = raiseButton;
		this.lowerButton = lowerButton;
		this.openButton = openButton;
		this.closeButton = closeButton;
	}
	
	public void processTeleop() {
		if (stick.getRawButton(raiseButton))
			upDownGripper.set(true);
		else if(stick.getRawButton(lowerButton))
			upDownGripper.set(false);
		else if(stick.getRawButton(openButton)) 
			openCloseGripper.set(true);
		else if(stick.getRawButton(closeButton))
			openCloseGripper.set(false);
	}

	public void processAutonomous() {
		
	}

}
