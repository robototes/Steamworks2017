package org.usfirst.frc.team2412.robot.autonomous;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.RobotDrive;

public class EncoderCommand extends Command2 {
	
	private WPI_TalonSRX talon;
	private WPI_TalonSRX slaves[];
	private RobotDrive rd;
	private boolean reverseSensor;
	private final double encodertocmconv = 0.0239534386;
	private final double targetPositionRotations;
	StringBuilder _sb = new StringBuilder();
	int _loops = 0;
	
	private double error = 0;
	public EncoderCommand(WPI_TalonSRX talon, WPI_TalonSRX[] _slaves, RobotDrive _rd, double _targetPositionRotations, boolean _reverseSensor) {
		this.talon = talon;
		//Copy WPI_TalonSRX array.
		slaves = new WPI_TalonSRX[_slaves.length];
		for(int i = 0; i < _slaves.length; i++) {
			slaves[i] = _slaves[i];
		}
		this.targetPositionRotations = _targetPositionRotations;
		this.reverseSensor = _reverseSensor;
		this.rd = _rd;
	}
	
	/**
	 * Called when the command first starts.
	 */
	public void start() {

		//Make sure all of the Talons are in PercentVbus mode.
		for(WPI_TalonSRX talon : slaves) {
			talon.set(ControlMode.PercentOutput, 0);
		}
		talon.set(ControlMode.PercentOutput, 0);
		
//		int absolutePosition = talon.getPulseWidthPosition() & 0xFFF; /* mask out the bottom12 bits, we don't care about the wrap arounds */
		/* use the low level API to set the quad encoder signal */
//        talon.setPosition(absolutePosition);
		talon.setSelectedSensorPosition(0, 0, 0);
        
        /* choose the sensor and sensor direction */
        talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        talon.reverseSensor(this.reverseSensor);
        talon.configEncoderCodesPerRev(2048); // if using FeedbackDevice.QuadEncoder

        /* set the peak and nominal outputs, 12V means full */
        talon.configNominalOutputVoltage(+0f, -0f);
        talon.configPeakOutputVoltage(+12f, -12f);   
	}
	
	
	/**
	 * Determines if the command is finished.
	 */
	public boolean isFinished() {
		return error < 0.1;
	}
	
	/**
	 * Called periodically when the command is running.
	 */
	public void execute() {
		error = targetPositionRotations - talon.getPosition();
		rd.drive(error/targetPositionRotations, 0.0);
		/*
		System.out.println("Position: " + talon.getPosition());
		System.out.println("Target: " + targetPositionRotations);
		System.out.println("Output: " + error/targetPositionRotations);
		*/
	}
	
	/**
	 * Called when the command ends.
	 */
	public void end() {
		rd.drive(0.0, 0.0);
	}
	
	//Gets the encoder's position value in cm.
	public double getPositionCm() {
		return talon.getPosition() * encodertocmconv;
	}
	
}
