package org.usfirst.frc.team2412.robot;

public interface RobotController {
	
	/**
	 * Processing only to be called in autonomousPeriodic()
	 * Like stronghold's process()
	 */
	public void processTeleop();
	
	
	/**
	 * Processing only to be called in teleopPeriodic()
	 * Like stronghold's process()
	 */
	public void processAutonomous();
	

}
