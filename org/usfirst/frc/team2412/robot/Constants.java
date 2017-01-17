package org.usfirst.frc.team2412.robot;

import edu.wpi.first.wpilibj.DriverStation;

public class Constants {
	
	public static enum AutoStatus {
		OFF("Teleop Period"), DRIVE_FORWARD("Driving Forward..."), PUT_GEAR_ON("Scoring 60 points...");
		
		private String desc;
		
		private AutoStatus(String desc) {
			this.desc = desc;
		}
		
		private int[] extraData;
		private static AutoStatus current = OFF;
		
		public int[] getData() {
			return extraData;
		}
		
		public void setData(int value, int index) {
			extraData[index] = value;
		}
		
		public void setData(int[] i) {
			extraData = i;
		}

		public static AutoStatus getCurrent() {
			return current;
		}
		
		public static void setCurrent(AutoStatus as) {
			current = as;
		}
		
		public String getSDDescriptor() {
			return desc;
		}
				
	}
	
	/**
	 * Color matters, since the field is asymetrical this year
	 */
	public static DriverStation.Alliance ALLIANCE_COLOR;
	public static int STARTING_STATION;
	public static boolean initialized = false, constantsInitialized = false;
	
	public static void init() {
		ALLIANCE_COLOR = DriverStation.getInstance().getAlliance();
		STARTING_STATION = DriverStation.getInstance().getLocation();
		constantsInitialized = true;
	}

}
