package org.usfirst.frc.team2412.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * Improved IterativeRobot.
 * @author Team 2412 <https://robototes.com, github.com/robototes>
 */
public class Robot extends IterativeRobot {
	private boolean firstRun;
	private boolean startup;
	
	private RobotController rcs[] = new RobotController[3];
	
	public void robotInit() {
		Constants.init();
		DriverStation ds = DriverStation.getInstance();
		Alliance alliance = ds.getAlliance();
		int location = ds.getLocation(); 
		double voltage = ds.getBatteryVoltage();
		
		System.out.println("	==== STARTING INITIALIZATION PROCEDURES ====");
			Timer.delay(0.15);
		System.out.println("		" + alliance.toString().toUpperCase() + " ALLIANCE, POSITION " + location);
			Timer.delay(0.2);
		System.out.println("		RUNNING SYSTEM CHECKS");
			Timer.delay(0.1);
		System.out.println("			VOLTAGE: " + voltage);
			Timer.delay(0.05);
		if (ds.isFMSAttached()) {
			System.out.println("			FMS ATTACHED");
			Timer.delay(0.2);
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
		
		
		startup = true;
	}
	
	public void autonomousInit() {
		System.out.println("	==== STARTING AUTONOMOUS MODE ====");
		Constants.startuptime = System.nanoTime();
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Timer.delay(0.05);
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
		Timer.delay(0.005);
	}
	
	public void disabledInit() {
		if (!startup) {
			System.out.println("	==== ROBOT DISABLED ====");
		} else {
			startup = false;
		}
	}
	
	public void disabledPeriodic() {
		Timer.delay(0.1);
	}
	
	
}
