package org.usfirst.frc.team2412.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionController implements RobotController {
	private int x = 0;
	private NetworkTable table = null;
	public void processTeleop() {
		if(table == null) {
			//Initialize NetworkTable
			table = NetworkTable.getTable("datatable");
		}
		table.putNumber("X", x++);
	}

	public void processAutonomous() {
	}
	
}