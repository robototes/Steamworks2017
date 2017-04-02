package org.usfirst.frc.team2412.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;

public class GearController implements RobotController {
	
	private Joystick stick;
	private DoubleSolenoid upDownGripper;
	private DoubleSolenoid openCloseGripperL, openCloseGripperR;
	private int raiseButton, lowerButton, openButton, closeButton;
	
	//Creates a GearController class.
	//upDownGripper - Solenoid that raises/lowers the gear intake.
	//openCloseGripper - Solenoid that makes the arms move in/out (left/right).
	//Joystick stick - The codriver board to read values from.
	//raiseButton/closeButton - buttons for raising/lowering the intake.
	//openButton/closeButton - buttons for opening/closing the gear intake's arms.
	public GearController(DoubleSolenoid upDownGripper, DoubleSolenoid openCloseGripper, DoubleSolenoid openCloseGripperR, Joystick stick, int raiseButton, int lowerButton, int openButton, int closeButton) {
		//Set member variables.
		this.upDownGripper = upDownGripper;
		this.openCloseGripperL = openCloseGripper;
		this.openCloseGripperR = openCloseGripperR;
		this.raiseButton = raiseButton;
		this.lowerButton = lowerButton;
		this.openButton = openButton;
		this.closeButton = closeButton;
		this.stick = stick;
	}
	
	public void processTeleop() {
		if (stick.getRawButton(raiseButton))
			upDownGripper.set(DoubleSolenoid.Value.kForward);
		else if(stick.getRawButton(lowerButton))
			upDownGripper.set(DoubleSolenoid.Value.kReverse);
		else
			upDownGripper.set(DoubleSolenoid.Value.kOff);
		if(stick.getRawButton(openButton))  {
			openCloseGripperL.set(DoubleSolenoid.Value.kForward);
			openCloseGripperR.set(DoubleSolenoid.Value.kForward);
		}
		else if(stick.getRawButton(closeButton)) {
			openCloseGripperL.set(DoubleSolenoid.Value.kReverse);
			openCloseGripperR.set(DoubleSolenoid.Value.kReverse);
		}
		else {
			openCloseGripperL.set(DoubleSolenoid.Value.kOff);
			openCloseGripperR.set(DoubleSolenoid.Value.kOff);
		}
	}

	public void processAutonomous() {
		double deltaTime = System.nanoTime() - Constants.startuptime;
		if(deltaTime > 5E9 && Constants.dropGear && upDownGripper.get() != DoubleSolenoid.Value.kReverse && openCloseGripperL.get() != DoubleSolenoid.Value.kForward && openCloseGripperR.get() != DoubleSolenoid.Value.kForward) {
			upDownGripper.set(DoubleSolenoid.Value.kReverse);
			openCloseGripperL.set(DoubleSolenoid.Value.kForward);
			openCloseGripperR.set(DoubleSolenoid.Value.kForward);
		}
	}
	
	public void teleopInit() {

	}

	public void autonomousInit() {
	}



}
