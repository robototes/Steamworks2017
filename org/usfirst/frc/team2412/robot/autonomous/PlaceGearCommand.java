package org.usfirst.frc.team2412.robot.autonomous;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class PlaceGearCommand extends Command2 {

	private DoubleSolenoid upDownGripper;
	private DoubleSolenoid openCloseGripperL;
	private DoubleSolenoid openCloseGripperR;

	private NetworkTable pydashboardTable;
	
	public PlaceGearCommand(DoubleSolenoid _upDownGripper, DoubleSolenoid _openCloseGripperL, DoubleSolenoid _openCloseGripperR, NetworkTable _pydashboardTable) {
		this.upDownGripper = _upDownGripper;
		this.openCloseGripperL = _openCloseGripperL;
		this.openCloseGripperR = _openCloseGripperR;

		this.pydashboardTable = _pydashboardTable;
	}
	
	
	/**
	 * Determines if the command is finished.
	 */
	public boolean isFinished() {
		return upDownGripper.get() == DoubleSolenoid.Value.kReverse && openCloseGripperL.get() == DoubleSolenoid.Value.kForward && openCloseGripperR.get() == DoubleSolenoid.Value.kForward;
	}
	
	/**
	 * Called periodically when the command is running.
	 */
	public void execute() {
		if(pydashboardTable.getString("Step1", "Drive forward").equals("Drive forward")) return;
		upDownGripper.set(DoubleSolenoid.Value.kReverse);
		openCloseGripperL.set(DoubleSolenoid.Value.kForward);
		openCloseGripperR.set(DoubleSolenoid.Value.kForward);
		System.out.println("Placing gear...");
	}
}