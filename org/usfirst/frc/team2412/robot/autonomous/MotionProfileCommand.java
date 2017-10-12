package org.usfirst.frc.team2412.robot.autonomous;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

public class MotionProfileCommand extends Command2 {

	private CANTalon master;
	private CANTalon[] slaves;
	private MotionProfiler profiler;
	
	/**
	 * 
	 * @param _master The CANTalon to be driven using motion profiling.
	 * @param _slaves The CANTalons that will be slaves to _master.
	 */
	public MotionProfileCommand(CANTalon _master, CANTalon[] _slaves) {
		this.master = _master;
		//Copy CANTalon array.
		slaves = new CANTalon[_slaves.length];
		for(int i = 0; i < _slaves.length; i++) {
			slaves[i] = _slaves[i];
		}
		
		profiler = new MotionProfiler(master);
	}
	
	
	/**
	 * Called when the Command starts.
	 */
	public void start() {
		super.start();
		
		setupMotionProfiling();
	}
	
	/**
	 * Determines if the command is finished.
	 */
	public boolean isFinished() {
		return profiler.getSetValue() == CANTalon.SetValueMotionProfile.Hold;
	}
	
	/**
	 * Called periodically when the command is running.
	 */
	public void execute() {
		profiler.control();
		//Make all talons follow master
		for(CANTalon talon : slaves) {
			talon.changeControlMode(CANTalon.TalonControlMode.Follower);
			talon.set(master.getDeviceID());
			if(talon.getDeviceID() != 10) {
				talon.reverseOutput(true);
			}
		}
		master.changeControlMode(TalonControlMode.MotionProfile); //Make Talon go into motion profiling mode.
		CANTalon.SetValueMotionProfile setOutput = profiler.getSetValue();
		master.set(setOutput.value);
	}
	
	private void setupMotionProfiling() {
		master.reverseOutput(true); //Reverse the motor output.
		master.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		
		master.setPosition(0); //Zero out the encoder in the beginning
		master.configEncoderCodesPerRev(2048);
		master.setF(0.2600);
		master.setP(0);
		master.setI(0);
		master.setD(0);
		
		profiler.startMotionProfile();
	}
	
	/**
	 * Called when the command ends.
	 */
	public void end() {
		master.reverseOutput(false);
		master.changeControlMode(TalonControlMode.PercentVbus);
		master.set(0);
		profiler.reset();
		//Don't make all talons follow master
		for(CANTalon talon : slaves) {
			talon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		}
	}
}
