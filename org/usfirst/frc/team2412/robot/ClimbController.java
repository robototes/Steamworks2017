package org.usfirst.frc.team2412.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;

public class ClimbController implements RobotController {
	
	private TalonSRX climbTalon;
	private Joystick stick;
	private int fwdButton, bakButton;
	
	//Creates a ClimbControl class.
	//climbID - ID of the motor controller that controls the climber's motor
	//Joystick stick - The codriver board to read values from.
	//fwdButton/bakButton - buttons for spinning forward or backwards.
	public ClimbController(int climbID, Joystick stick, int fwdButton, int bakButton) {
		this.climbTalon = new TalonSRX(climbID);
		this.stick = stick;
		this.fwdButton = fwdButton;
		this.bakButton = bakButton;
	}
	@Override
	public void teleopInit() {
	}

	@Override
	public void processTeleop() {
		if(stick.getRawButton(fwdButton))
			climbTalon.set(-1.0);
//		else if(stick.getRawButton(bakButton))
//			climbTalon.set(1.0);
		else
			climbTalon.set(0.0);
	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void processAutonomous() {
	}

}
