package org.usfirst.frc.team2412.robot;

public class Constants {
	
	public static enum AutoStatus {
		OFF, DRIVE_FORWARD, PUT_GEAR_ON;
		
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
				
	}

}
