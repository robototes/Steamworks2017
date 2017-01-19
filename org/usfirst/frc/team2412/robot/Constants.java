package org.usfirst.frc.team2412.robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Locale;

import javax.jws.WebService;

import org.usfirst.frc.team2412.robot.sd.SmartDashboardUtils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@WebService
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
		   // 3			front-right  
		   // 5			moves the rotor clamp up and down
		5
	};
	public static double PICKUP_SPEED = 0.5, DROP_SPEED = 0.5, DRIVE_SPEED = 0.8, DRIVE_ROTATE_SPEED = 0.8;
	public static int BUTTON_ID_PICKUP_GEAR = -1, 
			BUTTON_ID_DROP_GEAR = -1,
			BUTTON_ID_ROTATE_CLAMP_UP = -1,
			BUTTON_ID_ROTATE_CLAMP_DOWN = -1,
			PICKUP_GEAR_MSTIME = 500,
			DROP_GEAR_MSTIME = 500,
			AUTO_MSDELAY = 500;
	public static VisionController rcVision;
	public static DriveBaseController rcDriveBase;
	public static GearController rcClampGear, rcRotateClamp;
	public static Joystick jsDriver, jsCoDriver;
	public static void init() {
		applyPrintStreams();
		ALLIANCE_COLOR = DriverStation.getInstance().getAlliance();
		STARTING_STATION = DriverStation.getInstance().getLocation();
		autoDelay = SmartDashboard.getNumber("Autonomous Initial Delay", 0.0)*1000;
		if (autoDelay > 15.0 || autoDelay < 0.0) autoDelay = 0.0; // this way, we don't go for too long in auto to skip parts of teleop period
		constantsInitialized = true;
		SmartDashboardUtils.init();
		jsDriver = new Joystick(0);
		jsCoDriver = new Joystick(1);
		
		rcDriveBase = new DriveBaseController(jsDriver, motors[0], motors[1], motors[2], motors[3]);
		rcClampGear = new GearController(motors[4], jsCoDriver, BUTTON_ID_PICKUP_GEAR, BUTTON_ID_DROP_GEAR);
		rcRotateClamp = new GearController(motors[5], jsCoDriver, BUTTON_ID_ROTATE_CLAMP_UP, BUTTON_ID_ROTATE_CLAMP_DOWN);
	}

	private static void applyPrintStreams() {
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
			
			final PrintStream oldSysErr = System.err;
			
			PrintStream p1 = new PrintStream(new File(System.getProperty("user.home") + File.separatorChar + "Desktop" + File.separatorChar + "Logs" + File.separatorChar + new SimpleDateFormat("MM.dd.HH.mm.ss").format(Date.from(Instant.now())+".log.err.txt"))) {
				
				@Override
				public void print(String s) {
					super.print(s);
					oldSysErr.print(s);
				}
				
				@Override
				public void print(int i) {
					super.print(i);
					oldSysErr.print(i);
				}
				
				@Override
				public void print(long l) {
					super.print(l);
					oldSysErr.print(l);
				}
				
				@Override
				public void print(double d) {
					super.print(d);
					oldSysErr.print(d);
				}
				
				@Override
				public void print(float f) {
					super.print(f);
					oldSysErr.print(f);
				}
				
				@Override
				public void print(char c) {
					super.print(c);
					oldSysErr.print(c);
				}
				
				@Override
				public void print(char[] c) {
					super.print(c);
					oldSysErr.print(c);
				}
				
				@Override
				public void print(Object o) {
					super.print(o);
					oldSysErr.print(o);
					
				}
				
				@Override
				public void print(boolean b) {
					super.print(b);
					oldSysErr.print(b);
					
				}
				
				@Override
				public void println(String s) {
					super.println(s);
					oldSysErr.println(s);
				}
				
				@Override
				public void println(int i) {
					super.println(i);
					oldSysErr.println(i);
				}
				
				@Override
				public void println(long l) {
					super.println(l);
					oldSysErr.println(l);
				}
				
				@Override
				public void println(double d) {
					super.println(d);
					oldSysErr.println(d);
				}
				
				@Override
				public void println(float f) {
					super.println(f);
					oldSysErr.println(f);
				}
				
				@Override
				public void println(char c) {
					super.println(c);
					oldSysErr.println(c);
				}
				
				@Override
				public void println(char[] c) {
					super.println(c);
					oldSysErr.println(c);
				}
				
				@Override
				public void println(Object o) {
					super.println(o);
					oldSysErr.println(o);
					
				}
				
				@Override
				public void println(boolean b) {
					super.println(b);
					oldSysErr.println(b);
					
				}
				
				@Override
				public PrintStream printf(String s, Object... args) {
					oldSysErr.printf(s, args);
					return super.printf(s, args);
					
				}
				
				@Override
				public PrintStream printf(Locale l, String s, Object... o) {
					oldSysErr.printf(l, s, o);
					return super.printf(l, s, o);
				}
				
				@Override
				public PrintStream append(char c) {
					oldSysErr.append(c);
					return super.append(c);
				}
				
				@Override
				public PrintStream append(CharSequence c) {
					oldSysErr.append(c);
					return super.append(c);
				}
				
				@Override
				public void println() {
					oldSysErr.println();
					super.println();
				}
				
				@Override
				public void flush() {
					oldSysErr.flush();
					super.flush();
				}
				
				@Override
				public boolean checkError() {
					return oldSysErr.checkError() || super.checkError();
				}
				
				@Override
				public void close() {
					oldSysErr.close();
					super.close();
				}
				
				@Override
				public String toString() {
					return oldSysErr.toString() + " " + super.toString();
				}
				
				@Override
				public PrintStream append(CharSequence c, int arg1, int arg2) {
					oldSysErr.append(c, arg1, arg2);
		
					return super.append(c, arg1, arg2);
				}
				
				
			};

			
			System.setOut(p);
			System.setErr(p1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
