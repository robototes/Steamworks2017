package org.usfirst.frc.team2412.robot;

import static org.usfirst.frc.team2412.robot.Constants.debug;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * Improved IterativeRobot.
 * @author Team 2412 <https://robototes.com, github.com/robototes>
 */
public class Robot extends IterativeRobot {
	private boolean firstRun;
	private boolean startup;
	
	private RobotController rcs[] = new RobotController[4];
	
	public void robotInit() {
		Constants.init();
		DriverStation ds = DriverStation.getInstance();
		Alliance alliance = ds.getAlliance();
		int location = ds.getLocation(); 
		double voltage = ds.getBatteryVoltage();
		
		System.out.println("	==== STARTING INITIALIZATION PROCEDURES ====");
		System.out.println("		" + alliance.toString().toUpperCase() + " ALLIANCE, POSITION " + location);
		System.out.println("		RUNNING SYSTEM CHECKS");
		System.out.println("			VOLTAGE: " + voltage);
		if (ds.isFMSAttached()) {
			System.out.println("			FMS ATTACHED");
		}
		
		if (voltage < 12.0) {
			System.out.println();
			System.out.println("!!-- WARNING: LOW VOLTAGE --!!");
			System.out.println();
		}
		
		System.out.println();
		System.out.println("	=======================");
		System.out.println("	==== ROBOT IS READY ===");
		System.out.println("	=======================");
		
		
		//Initialize RobotControllers
		rcs[0] = new DriveBaseController(Constants.jsDriver, Constants.motors[0], Constants.motors[2], Constants.motors[1], Constants.motors[3]);
		rcs[1] = new GearController(Constants.upDownGripper, Constants.openCloseGripper, Constants.openCloseGripperR, Constants.jsCoDriver, Constants.BUTTON_ID_ROTATE_CLAMP_UP, Constants.BUTTON_ID_ROTATE_CLAMP_DOWN, Constants.BUTTON_ID_OPEN_CLAMP, Constants.BUTTON_ID_CLOSE_CLAMP);
		rcs[2] = new VisionController();
		rcs[3] = new ClimbController(Constants.MOTOR_ID_CLIMB, Constants.jsCoDriver, Constants.BUTTON_ID_CLIMB_UP, Constants.BUTTON_ID_CLIMB_DOWN);
		
		startup = true;
	}
	
	public void autonomousInit() {
		System.out.println("	==== STARTING AUTONOMOUS MODE ====");
		Constants.dropGear = false;
		Constants.startuptime = System.nanoTime();
		//Set Constants to values because they're not Constants
		Constants.DRIVE_FORWARD_START = Constants.startuptime;
		Constants.TURN_START = Constants.DRIVE_FORWARD_START + Constants.DRIVE_FORWARD_DURATION;
		try {
			for(RobotController rc : rcs) {
				if(rc != null)
					rc.autonomousInit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		firstRun = true;
	}
	
	public void autonomousPeriodic() {
		if (firstRun) {
			System.out.println("		No autonomous code.");
			firstRun = false;
		}
		try {
			for(RobotController rc : rcs) {
				if(rc != null)
					rc.processAutonomous();
				if (rc != null && debug) {
					rc.debug();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void teleopInit() {
		System.out.println("	==== STARTING TELE-OPERATED MODE ====");
		try {
			for(RobotController rc : rcs) {
				if(rc != null)
					rc.teleopInit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		firstRun = true;
	}
	
	public void teleopPeriodic() {
		if (firstRun) {
			System.out.println("		No tele-operated code.");
			firstRun = false;
		}
		try {
			for(RobotController rc : rcs) {
				if(rc != null)
					rc.processTeleop();
				if(rc != null && debug) {
					rc.debug();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void testInit() {
		System.out.println("	==== TEST MODE STARTING ====");
	}
	
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public void disabledInit() {
		if (!startup) {
			System.out.println("	==== ROBOT DISABLED ====");
		} else {
			startup = false;
		}
	}
	
	public void disabledPeriodic() {
	}
	
	
}
