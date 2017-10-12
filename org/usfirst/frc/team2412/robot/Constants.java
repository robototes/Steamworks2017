package org.usfirst.frc.team2412.robot;

import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

import org.usfirst.frc.team2412.robot.autonomous.AutonomousStage;
import org.usfirst.frc.team2412.robot.autonomous.Command2;
import org.usfirst.frc.team2412.robot.autonomous.DriveForTimeCommand;
import org.usfirst.frc.team2412.robot.autonomous.EncoderCommand;
import org.usfirst.frc.team2412.robot.autonomous.GyroCommand;
import org.usfirst.frc.team2412.robot.autonomous.MotionProfileCommand;
import org.usfirst.frc.team2412.robot.autonomous.PlaceGearCommand;
import org.usfirst.frc.team2412.robot.autonomous.VisionCommand;
import org.usfirst.frc.team2412.robot.sd.SmartDashboardUtils;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
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
	public static final CANTalon[] talons = new CANTalon[motors.length];
	public static double PICKUP_SPEED = 0.5, DROP_SPEED = 0.5, DRIVE_SPEED = 0.8, DRIVE_ROTATE_SPEED = 0.7;
	public static int BUTTON_ID_OPEN_CLAMP = 2,
			BUTTON_ID_CLOSE_CLAMP = 1,
			BUTTON_ID_ROTATE_CLAMP_UP = 5,
			BUTTON_ID_ROTATE_CLAMP_DOWN = 4,
			BUTTON_ID_CLIMB_UP = 3,
			BUTTON_ID_CLIMB_DOWN = 8,
			BUTTON_ID_FAST_FORWARD = 6,
			BUTTON_ID_FAST_TURN = 4,
			BUTTON_ID_SLOW_FORWARD = 5,
			BUTTON_ID_SLOW_TURN = 3,
			BUTTON_ID_VISION_TRACKING = 1,
			SOLENOID_ID_UP_DOWN = 3, SOLENOID_ID_UP_DOWN_REVERSE = 6,
			SOLENOID_ID_OPEN_CLOSE = 1,
			SOLENOID_ID_OPEN_CLOSE_REVERSE = 4,
			SOLENOID_ID_OPEN_CLOSE_R = 2,
			SOLENOID_ID_OPEN_CLOSE_REVERSE_R = 5,
			MOTOR_ID_CLIMB = 11;
	
	/** Constants for driving straight forward (center)*/
	//Driving forward
	public static double DRIVE_FORWARD_START;
	public static double DRIVE_FORWARD_DURATION = 5E8;
	//Driving in reverse
	public static double DRIVE_REVERSE_START;
	public static double DRIVE_REVERSE_DURATION = 1E9;
	
	public static Joystick jsDriver, jsCoDriver;
	public static DoubleSolenoid upDownGripper, openCloseGripper, openCloseGripperR;
	public static NetworkTable visionTable = null;
	public static NetworkTable pydashboardTable = null;
	public static double AUTO_FINAL_DIST = 0.2d, AUTO_SECOND_STEP_DIST = 2;
	public static long startuptime;
	
	public static RobotDrive rd;
	
	public static boolean dropGear;
	
	public static boolean debug = false; // Can change, so note that
	
	/**Autonomous commands*/
	//Variables for selecting autonomous stages
	public static int currentStage = 0;

	//Step 2 Commands.
	public static MotionProfileCommand mpc;
	public static EncoderCommand ec;
	public static DriveForTimeCommand dftc;

	//Step 3 Commands.
	public static GyroCommand gc;
	public static VisionCommand vc;
	public static DriveForTimeCommand dftc2;

	//Step 4 Commands.
	public static VisionCommand vc2;
	public static EncoderCommand ec2;
	public static DriveForTimeCommand dftc3;
	
	public static PlaceGearCommand pgc;
	
	public static AutonomousStage as2;
	public static AutonomousStage as3;
	public static AutonomousStage as4;

	public static ArrayList<AutonomousStage> stages;
	 
	public static Command2 selectedCommand;
	
	public static String step1;
	
	public static void init() {
		for(int i = 0; i < talons.length; i++) {
			talons[i] = new CANTalon(motors[i]);
		}
		
		SmartDashboardUtils.firstTimeInit();
		Scheduler.getInstance().run();
		
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
		pydashboardTable = NetworkTable.getTable("PyDashboard");
		
		/** Autonomous commands */
		for(CANTalon talon : talons) {
			talon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		}
		
		rd = new RobotDrive(talons[0], talons[2], talons[1], talons[3]);
		rd.setSafetyEnabled(false);
		for(CANTalon talon : talons) {
			talon.enable();
		}
		//Setup Step2 Commands.
		CANTalon slaves[] = {talons[0], talons[2], talons[3]};

		mpc = new MotionProfileCommand(talons[1], slaves);
		ec = new EncoderCommand(talons[1], slaves, rd, 2.3, false);
		dftc = new DriveForTimeCommand(1, rd, 0.3d, 0.0d, 2.4E9);
		
		//Setup Step3 Commands.
		gc = new GyroCommand(new ADXRS450_Gyro(), talons[1], slaves, 0.2d, 60, pydashboardTable);
		vc = new VisionCommand(rd, visionTable);
		dftc2 = new DriveForTimeCommand(2, rd, 0.0d, 0.3d, 0.3E9);

		//Setup Step4 Commands.
		vc2 = new VisionCommand(rd, visionTable);
		ec2 = new EncoderCommand(talons[1], slaves, rd, 0.975, false);
		dftc3 = new DriveForTimeCommand(3, rd, 0.3d, 0.0d, 1.70E9);
		
		//End of autonomous commands
		pgc = new PlaceGearCommand(upDownGripper, openCloseGripper, openCloseGripperR);
		
		//Setup autonomous stages
		as2 = new AutonomousStage(pydashboardTable);
		as2.addDefaultCommand("Motion Profiling", mpc);
		as2.addCommand("Encoders", ec);
		as2.addCommand("Time-Based", dftc);
		as2.sendCommands("Step2");
		
		as3 = new AutonomousStage(pydashboardTable);
		as3.addDefaultCommand("Gyroscope", gc);
		as3.addCommand("Vision Processing", vc);
		as3.addCommand("Time-Based", dftc2);
		as3.sendCommands("Step3");

		as4 = new AutonomousStage(pydashboardTable);
		as4.addDefaultCommand("Vision Processing", vc2);
		as4.addCommand("Encoders", ec2);
		as4.addCommand("Time-Based", dftc3);
		as4.sendCommands("Step4");
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
