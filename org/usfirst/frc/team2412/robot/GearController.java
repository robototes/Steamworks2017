package org.usfirst.frc.team2412.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

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

	private NetworkTable data;
	
	public void processAutonomous() {
		if ((data = (data == null ? NetworkTable.getTable(VisionController.TABLENAME) : data)).getNumber("distance", Double.NaN) <= Constants.AUTO_FINAL_DIST) {
			hookOnPeg();
		}
	}
	
	public void teleopInit() {

	}

	public void autonomousInit() {
		
	}

	public void hookOnPeg() {
		stick.setOutput(openButton, true);
		processTeleop();
		stick.setOutput(openButton, false);
	}


}
