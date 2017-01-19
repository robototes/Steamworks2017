package org.usfirst.frc.team2412.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Locale;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Constants {
	
	public static enum AutoStatus {
		
	}
	
	/**
	 * Color matters, since the field is asymetrical this year
	 */
	public static DriverStation.Alliance ALLIANCE_COLOR;
	public static int STARTING_STATION;
	public static boolean initialized = false, constantsInitialized = false;
	public static double autoDelay = -1.0; // delay before any autonomous processing in milliseconds
	
	
	public static void init() {
		ALLIANCE_COLOR = DriverStation.getInstance().getAlliance();
		STARTING_STATION = DriverStation.getInstance().getLocation();
		autoDelay = SmartDashboard.getNumber("Autonomous Initial Delay", 0.0)*1000;
		if (autoDelay > 15.0 || autoDelay < 0.0) autoDelay = 0.0; // this way, we don't go for too long in auto to skip parts of teleop period
		constantsInitialized = true;
		
		final PrintStream oldSysOut = System.out;
		
		try {
			PrintStream p = new PrintStream(new File(System.getProperty("user.home") + File.separatorChar + "Desktop" + File.separatorChar + "Logs" + File.separatorChar + new SimpleDateFormat("MM.dd.HH.mm.ss").format(Date.from(Instant.now())+".log.txt"))) {
				
				@Override
				public void print(String s) {
					super.print(s);
					oldSysOut.print(s);
				}
				
				@Override
				public void print(int i) {
					super.print(i);
					oldSysOut.print(i);
				}
				
				@Override
				public void print(long l) {
					super.print(l);
					oldSysOut.print(l);
				}
				
				@Override
				public void print(double d) {
					super.print(d);
					oldSysOut.print(d);
				}
				
				@Override
				public void print(float f) {
					super.print(f);
					oldSysOut.print(f);
				}
				
				@Override
				public void print(char c) {
					super.print(c);
					oldSysOut.print(c);
				}
				
				@Override
				public void print(char[] c) {
					super.print(c);
					oldSysOut.print(c);
				}
				
				@Override
				public void print(Object o) {
					super.print(o);
					oldSysOut.print(o);
					
				}
				
				@Override
				public void print(boolean b) {
					super.print(b);
					oldSysOut.print(b);
					
				}
				
				@Override
				public void println(String s) {
					super.println(s);
					oldSysOut.println(s);
				}
				
				@Override
				public void println(int i) {
					super.println(i);
					oldSysOut.println(i);
				}
				
				@Override
				public void println(long l) {
					super.println(l);
					oldSysOut.println(l);
				}
				
				@Override
				public void println(double d) {
					super.println(d);
					oldSysOut.println(d);
				}
				
				@Override
				public void println(float f) {
					super.println(f);
					oldSysOut.println(f);
				}
				
				@Override
				public void println(char c) {
					super.println(c);
					oldSysOut.println(c);
				}
				
				@Override
				public void println(char[] c) {
					super.println(c);
					oldSysOut.println(c);
				}
				
				@Override
				public void println(Object o) {
					super.println(o);
					oldSysOut.println(o);
					
				}
				
				@Override
				public void println(boolean b) {
					super.println(b);
					oldSysOut.println(b);
					
				}
				
				@Override
				public PrintStream printf(String s, Object... args) {
					oldSysOut.printf(s, args);
					return super.printf(s, args);
					
				}
				
				@Override
				public PrintStream printf(Locale l, String s, Object... o) {
					oldSysOut.printf(l, s, o);
					return super.printf(l, s, o);
				}
				
				@Override
				public PrintStream append(char c) {
					oldSysOut.append(c);
					return super.append(c);
				}
				
				@Override
				public PrintStream append(CharSequence c) {
					oldSysOut.append(c);
					return super.append(c);
				}
				
				@Override
				public void println() {
					oldSysOut.println();
					super.println();
				}
				
				@Override
				public void flush() {
					oldSysOut.flush();
					super.flush();
				}
				
				@Override
				public boolean checkError() {
					return oldSysOut.checkError() || super.checkError();
				}
				
				@Override
				public void close() {
					oldSysOut.close();
					super.close();
				}
				
				@Override
				public String toString() {
					return oldSysOut.toString() + " " + super.toString();
				}
				
				@Override
				public PrintStream append(CharSequence c, int arg1, int arg2) {
					oldSysOut.append(c, arg1, arg2);
		
					return super.append(c, arg1, arg2);
				}
				
				
			};
			System.setOut(p);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
