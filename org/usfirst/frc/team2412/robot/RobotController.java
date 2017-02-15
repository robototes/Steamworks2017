package org.usfirst.frc.team2412.robot;

public interface RobotController {
	
	/**
	 * Processing only to be called in teleopInit()
	 */
	public void teleopInit();
	
	/**
	 * Processing only to be called in teleopPeriodic()
	 * Like stronghold's process() function
	 */
	public void processTeleop();
	
	/**
	 * Processing only to be called in autonomousInit()
	 */
	public void autonomousInit();
	
	/**
	 * Processing only to be called in autonomousPeriodic()
	 * Like stronghold's process() function
	 */
	public void processAutonomous();
	
	
	/**
	 * Does things in debug mode
	 * Only called if Constants.debug = true
	 */
	public default void debug() {}

}
