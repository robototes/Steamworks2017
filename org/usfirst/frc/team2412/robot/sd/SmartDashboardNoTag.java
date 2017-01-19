package org.usfirst.frc.team2412.robot.sd;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardNoTag {
	
	public static final char TAG = '';
	public final String tag;
	public static final double DEFAULT_DOUBLE = Double.longBitsToDouble(Long.MAX_VALUE);
	
	private static int count = 1;
	
	public SmartDashboardNoTag() {
		String s = "";
		for (int i = 0; i < count; i ++) {
			s += TAG;
		}
		count++;
		tag = s;
	}
	
	public void putString(String s) {
		SmartDashboard.putString(tag, s);
	}
	
	public void putNumber(double d) {
		SmartDashboard.putNumber(tag, d);
	}
	
	public void putData(Sendable s) {
		SmartDashboard.putData(tag, s);
	}
	
	public void putBoolean(boolean b) {
		SmartDashboard.putBoolean(tag, b);
	}
	
	public String getString() {
		return SmartDashboard.getString(tag, "");
	}
	
	@Override
	public String toString() {
		return tag+":"+getString();
	}
	
	public double getNumber() {
		return SmartDashboard.getNumber(tag, DEFAULT_DOUBLE);
	}
	
	public boolean getBoolean() {
		return SmartDashboard.getBoolean(tag, false);
	}
	
	public Sendable getData() {
		return SmartDashboard.getData(tag);
	}

}
