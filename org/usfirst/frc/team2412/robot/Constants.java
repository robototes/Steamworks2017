package org.usfirst.frc.team2412.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Locale;

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
	public static double autoDelay = -1.0; // delay before any autonomous processing in milliseconds
	public static final int[] motors = new int[] {
			//-----------//
			0,/*        */1,
			//-----------//
			//-----------//
			//-----------//
			2,/*        */3,
			//-----------//
			 /* */4,/* */
			 /* */  /* */
		   //[Gear goes Here]
		   // [index]	[descrption]
		   // 4 		controls motor for picking up gear
		   // 0			back-right
		   // 1			back-left
		   // 2			front-right
		   // 3			fron-right  
		   // 5			null motor
		5
	};
	public static double PICKUP_SPEED = 0.5, DROP_SPEED = 0.5, DRIVE_SPEED = 0.8, DRIVE_ROTATE_SPEED = 0.8;
	public static int BUTTON_ID_PICKUP_GEAR = -1, 
			BUTTON_ID_DROP_GEAR = -1,
			PICKUP_GEAR_MSTIME = 500,
			DROP_GEAR_MSTIME = 500,
			AUTO_MSDELAY_BEFORE_GEAR_DROP = 5000;
	
	public static void init() {
		ALLIANCE_COLOR = DriverStation.getInstance().getAlliance();
		STARTING_STATION = DriverStation.getInstance().getLocation();
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
