package org.usfirst.frc.team2412.robot.autonomous;

import edu.wpi.first.wpilibj.RobotDrive;

public class DriveForTimeCommand extends Command2 {

	private RobotDrive rd;
	private double move;
	private double turn;
	private double duration;
	
	private long startuptime;
	public DriveForTimeCommand(int _commandStage, RobotDrive _rd, double _move, double _turn, double _duration) {
		this.rd = _rd;
		this.move = _move;
		this.turn = _turn;
		this.duration = _duration;
	}
	
	/**
	 * Called when the command first starts.
	 */
	public void initialize() {
		this.startuptime = System.nanoTime();
	}
	
	/**
	 * Determines if the command is finished.
	 */
	public boolean isFinished() {
		long deltaTime = System.nanoTime() - startuptime; 
		return deltaTime < 0 || deltaTime > duration; 
	}
	
	/**
	 * Called periodically when the command is running.
	 */
	public void execute() {
		rd.arcadeDrive(move, turn, false);
	}
	
	/**
	 * Called when the command ends.
	 */
	public void end() {
		rd.arcadeDrive(0.0d, 0.0d, false); //Stop driving
	}
}
