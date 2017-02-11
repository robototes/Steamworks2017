package org.usfirst.frc.team2412.robot;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Locale;

import org.usfirst.frc.team2412.robot.sd.SmartDashboardUtils;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Constants {

	/**
	 * Color matters, since the field is asymetrical this year
	 */
	public static DriverStation.Alliance ALLIANCE_COLOR;
	public static int STARTING_STATION;
	public static boolean initialized = false, constantsInitialized = false;
	public static double autoDelay = -1.0; // delay before any autonomous
											// processing in milliseconds
	public static final int[] motors = new int[] {
			//    FRONT   //
			// -----------//
			9, /*        */5,
			// -----------//
			// -----------//
			// -----------//
			1, /*        */10,
			// -----------//
			//     BACK   //
			// [value] [descrption]
			// 1 back-left
			// 10 back-right
			// 9 front-left
			// 5 front-right
			};
	public static double PICKUP_SPEED = 0.5, DROP_SPEED = 0.5, DRIVE_SPEED = 0.8, DRIVE_ROTATE_SPEED = 0.8;
	public static int BUTTON_ID_OPEN_CLAMP = 1,
			BUTTON_ID_CLOSE_CLAMP = 2,
			BUTTON_ID_ROTATE_CLAMP_UP = 3,
			BUTTON_ID_ROTATE_CLAMP_DOWN = 4,
			SOLENOID_ID_UP_DOWN = 7, SOLENOID_ID_UP_DOWN_REVERSE = 1,
			SOLENOID_ID_OPEN_CLOSE = 2,
			SOLENOID_ID_OPEN_CLOSE_REVERSE = 6,
			SOLENOID_ID_OPEN_CLOSE_R = 3,
			SOLENOID_ID_OPEN_CLOSE_REVERSE_R = 4,
			PICKUP_GEAR_MSTIME = 500,
			DROP_GEAR_MSTIME = 500,
			AUTO_MSDELAY = 500;
	public static Joystick jsDriver, jsCoDriver;
	public static DoubleSolenoid upDownGripper, openCloseGripper, openCloseGripperR;
	public static NetworkTable visionTable = null;
	public static double AUTO_FINAL_DIST = 0.2d, AUTO_SECOND_STEP_DIST = 2;
	
	public static void init() {
		Scheduler.getInstance().run();
		Timer.delay(1d);
		
		try {
			applyPrintStreams(new Socket(SmartDashboardUtils.getDriverStationIP(), 5800));
		} catch (Exception e) {
			try {
				applyPrintStreams(new Socket(SmartDashboardUtils.getDriverStationIP(), 5800));
			} catch (Exception ex) {
				System.err.println(SmartDashboardUtils.getDriverStationIP());
				e.printStackTrace();
				ex.printStackTrace();
			}
		}
		
		ALLIANCE_COLOR = DriverStation.getInstance().getAlliance();
		STARTING_STATION = DriverStation.getInstance().getLocation();
		autoDelay = SmartDashboard.getNumber("Autonomous Initial Delay", 0.0) * 1000;
		if (autoDelay > 15000.0 || autoDelay < 0.0)
			autoDelay = 0.0; // this way, we don't go for too long in auto to
								// skip parts of teleop period
		constantsInitialized = true;
		//SmartDashboardUtils.firstTimeInit(true, false);
		SmartDashboardUtils.init();
		jsDriver = new Joystick(0);
		jsCoDriver = new Joystick(1);

		upDownGripper = new DoubleSolenoid(SOLENOID_ID_UP_DOWN, SOLENOID_ID_UP_DOWN_REVERSE);
		openCloseGripper = new DoubleSolenoid(SOLENOID_ID_OPEN_CLOSE, SOLENOID_ID_OPEN_CLOSE_REVERSE);
		openCloseGripperR = new DoubleSolenoid(SOLENOID_ID_OPEN_CLOSE_R, SOLENOID_ID_OPEN_CLOSE_REVERSE_R);
		
		visionTable = NetworkTable.getTable("datatable");
	}

	private static void applyPrintStreams(Socket s2) throws Exception {
		final PrintStream oldSysOut = System.out;

		final PrintStream ps_socket = new PrintStream(s2.getOutputStream());

		try {

			PrintStream p = new PrintStream(s2.getOutputStream()) {

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

			PrintStream p1 = new PrintStream(s2.getOutputStream()) {

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
			System.out.println("-end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
