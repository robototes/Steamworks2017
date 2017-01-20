package org.usfirst.frc.team2412.robot;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Locale;
import java.util.Scanner;

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
	public static Joystick jsDriver, jsCoDriver;
	public static void init() {
		try {
			applyPrintStreams();
		} catch (Exception e) {
			System.err.println(SmartDashboardUtils.getDriverStationIP());
			e.printStackTrace();
		}
		//applyPrintStreams();
		ALLIANCE_COLOR = DriverStation.getInstance().getAlliance();
		STARTING_STATION = DriverStation.getInstance().getLocation();
		autoDelay = SmartDashboard.getNumber("Autonomous Initial Delay", 0.0)*1000;
		if (autoDelay > 15.0 || autoDelay < 0.0) autoDelay = 0.0; // this way, we don't go for too long in auto to skip parts of teleop period
		constantsInitialized = true;
		SmartDashboardUtils.init();
		jsDriver = new Joystick(0);
		jsCoDriver = new Joystick(1);
		
		
		
	}

	private static void applyPrintStreams() throws Exception {
		final PrintStream oldSysOut = System.out;
		@SuppressWarnings("resource")
		Socket s = new Socket(InetAddress.getByName(SmartDashboardUtils.getDriverStationIP()), 5800);
		
		final PrintStream ps_socket = new PrintStream(s.getOutputStream());
		
		
		try {
			
			PrintStream p = new PrintStream(s.getOutputStream()) {
				
				@Override
				public void print(String s) {
					ps_socket.print(s);
					oldSysOut.print(s);
				}
				
				@Override
				public void print(int i) {
					ps_socket.print(i);
					oldSysOut.print(i);
				}
				
				@Override
				public void print(long l) {
					ps_socket.print(l);
					oldSysOut.print(l);
				}
				
				@Override
				public void print(double d) {
					ps_socket.print(d);
					oldSysOut.print(d);
				}
				
				@Override
				public void print(float f) {
					ps_socket.print(f);
					oldSysOut.print(f);
				}
				
				@Override
				public void print(char c) {
					ps_socket.print(c);
					oldSysOut.print(c);
				}
				
				@Override
				public void print(char[] c) {
					ps_socket.print(c);
					oldSysOut.print(c);
				}
				
				@Override
				public void print(Object o) {
					ps_socket.print(o);
					oldSysOut.print(o);
					
				}
				
				@Override
				public void print(boolean b) {
					ps_socket.print(b);
					oldSysOut.print(b);
					
				}
				
				@Override
				public void println(String s) {
					ps_socket.println(s);
					oldSysOut.println(s);
				}
				
				@Override
				public void println(int i) {
					ps_socket.println(i);
					oldSysOut.println(i);
				}
				
				@Override
				public void println(long l) {
					ps_socket.println(l);
					oldSysOut.println(l);
				}
				
				@Override
				public void println(double d) {
					ps_socket.println(d);
					oldSysOut.println(d);
				}
				
				@Override
				public void println(float f) {
					ps_socket.println(f);
					oldSysOut.println(f);
				}
				
				@Override
				public void println(char c) {
					ps_socket.println(c);
					oldSysOut.println(c);
				}
				
				@Override
				public void println(char[] c) {
					ps_socket.println(c);
					oldSysOut.println(c);
				}
				
				@Override
				public void println(Object o) {
					ps_socket.println(o);
					oldSysOut.println(o);
					
				}
				
				@Override
				public void println(boolean b) {
					ps_socket.println(b);
					oldSysOut.println(b);
					
				}
				
				@Override
				public PrintStream printf(String s, Object... args) {
					oldSysOut.printf(s, args);
					return ps_socket.printf(s, args);
					
				}
				
				@Override
				public PrintStream printf(Locale l, String s, Object... o) {
					oldSysOut.printf(l, s, o);
					return ps_socket.printf(l, s, o);
				}
				
				@Override
				public PrintStream append(char c) {
					oldSysOut.append(c);
					return ps_socket.append(c);
				}
				
				@Override
				public PrintStream append(CharSequence c) {
					oldSysOut.append(c);
					return ps_socket.append(c);
				}
				
				@Override
				public void println() {
					oldSysOut.println();
					ps_socket.println();
				}
				
				@Override
				public void flush() {
					oldSysOut.flush();
					ps_socket.flush();
				}
				
				@Override
				public boolean checkError() {
					return oldSysOut.checkError() || ps_socket.checkError();
				}
				
				@Override
				public void close() {
					oldSysOut.close();
					ps_socket.close();
				}
				
				@Override
				public String toString() {
					return oldSysOut.toString() + " " + ps_socket.toString();
				}
				
				@Override
				public PrintStream append(CharSequence c, int arg1, int arg2) {
					oldSysOut.append(c, arg1, arg2);
		
					return ps_socket.append(c, arg1, arg2);
				}
				
				
			};
			
			final PrintStream oldSysErr = System.err;
			
			
			PrintStream p1 = new PrintStream(s.getOutputStream()) {
				
				@Override
				public void print(String s) {
					ps_socket.print(s);
					oldSysErr.print(s);
				}
				
				@Override
				public void print(int i) {
					ps_socket.print(i);
					oldSysErr.print(i);
				}
				
				@Override
				public void print(long l) {
					ps_socket.print(l);
					oldSysErr.print(l);
				}
				
				@Override
				public void print(double d) {
					ps_socket.print(d);
					oldSysErr.print(d);
				}
				
				@Override
				public void print(float f) {
					ps_socket.print(f);
					oldSysErr.print(f);
				}
				
				@Override
				public void print(char c) {
					ps_socket.print(c);
					oldSysErr.print(c);
				}
				
				@Override
				public void print(char[] c) {
					ps_socket.print(c);
					oldSysErr.print(c);
				}
				
				@Override
				public void print(Object o) {
					ps_socket.print(o);
					oldSysErr.print(o);
					
				}
				
				@Override
				public void print(boolean b) {
					ps_socket.print(b);
					oldSysErr.print(b);
					
				}
				
				@Override
				public void println(String s) {
					ps_socket.println(s);
					oldSysErr.println(s);
				}
				
				@Override
				public void println(int i) {
					ps_socket.println(i);
					oldSysErr.println(i);
				}
				
				@Override
				public void println(long l) {
					ps_socket.println(l);
					oldSysErr.println(l);
				}
				
				@Override
				public void println(double d) {
					ps_socket.println(d);
					oldSysErr.println(d);
				}
				
				@Override
				public void println(float f) {
					ps_socket.println(f);
					oldSysErr.println(f);
				}
				
				@Override
				public void println(char c) {
					ps_socket.println(c);
					oldSysErr.println(c);
				}
				
				@Override
				public void println(char[] c) {
					ps_socket.println(c);
					oldSysErr.println(c);
				}
				
				@Override
				public void println(Object o) {
					ps_socket.println(o);
					oldSysErr.println(o);
					
				}
				
				@Override
				public void println(boolean b) {
					ps_socket.println(b);
					oldSysErr.println(b);
					
				}
				
				@Override
				public PrintStream printf(String s, Object... args) {
					oldSysErr.printf(s, args);
					return ps_socket.printf(s, args);
					
				}
				
				@Override
				public PrintStream printf(Locale l, String s, Object... o) {
					oldSysErr.printf(l, s, o);
					return ps_socket.printf(l, s, o);
				}
				
				@Override
				public PrintStream append(char c) {
					oldSysErr.append(c);
					return ps_socket.append(c);
				}
				
				@Override
				public PrintStream append(CharSequence c) {
					oldSysErr.append(c);
					return ps_socket.append(c);
				}
				
				@Override
				public void println() {
					oldSysErr.println();
					ps_socket.println();
				}
				
				@Override
				public void flush() {
					oldSysErr.flush();
					ps_socket.flush();
				}
				
				@Override
				public boolean checkError() {
					return oldSysErr.checkError() || ps_socket.checkError();
				}
				
				@Override
				public void close() {
					oldSysErr.close();
					ps_socket.close();
				}
				
				@Override
				public String toString() {
					return oldSysErr.toString() + " " + ps_socket.toString();
				}
				
				@Override
				public PrintStream append(CharSequence c, int arg1, int arg2) {
					oldSysErr.append(c, arg1, arg2);
		
					return ps_socket.append(c, arg1, arg2);
				}
				
				
			};

			
			System.setOut(p);
			System.setErr(p1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
