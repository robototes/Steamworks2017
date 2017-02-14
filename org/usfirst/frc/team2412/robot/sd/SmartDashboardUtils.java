package org.usfirst.frc.team2412.robot.sd;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.usfirst.frc.team2412.robot.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SmartDashboardUtils {

	public static String DRIVERSTATION = "DriverStation IP", ROBOTPOSITION = "Robot Station: ", ROBOTALLIANCE = "Robot is on the Red Alliance: ", TIMEREMAINING = "Time Remaining: ";
	
	public static void init() {
		Scheduler.getInstance().run();
		SmartDashboard.putString(TIMEREMAINING, "Initialization Seqence");
		getRobotPosition(false);
		TRU.start();
	}
	
	public static void firstTimeInit() {
		SmartDashboard.putString(ROBOTPOSITION, "center");
		SmartDashboard.putString(DRIVERSTATION, "default value");
	}
	
	/**
	 * 
	 * @param reset1 Reset Robot Position
	 * @param reset2 Reset DriverStation IP
	 */
	public static void firstTimeInit(boolean reset1, boolean reset2) {
		if (reset1) SmartDashboard.putString(ROBOTPOSITION, "center");
		if (reset2) SmartDashboard.putString(DRIVERSTATION, "default value");
	}
	
	/**
	 * @param startAt1 - if true, returns 1 - 3, otherwise, returns 0 - 2
	 * @return Which station the robot is at, specified by the user
	 * 
	 */
	public static int getRobotPosition(boolean startAt1) {
		String in = SmartDashboard.getString(ROBOTPOSITION, "The data was not returned").toLowerCase();
		int i = -1;
		
		if (in.contains("left") && i == -1) {
			i = 1;
		}
		
		if (in.contains("center") && i == -1) {
			i = 2;
		}
		
		if (in.contains("right") && i == -1) {
			i = 3;
		}
		
		if (i < 1 || i > 3) {
			//Read from getAlliance() instead.
			i = Constants.STARTING_STATION;
		}
		
		if ((i) != DriverStation.getInstance().getLocation()) {
			System.err.println("The specified driver station, " + (i) + " doesn't match up with " + DriverStation.getInstance().getLocation() + ". Returning specified station anyways.");
		}
		
		return i + (startAt1 ? 0 : -1);
	}
	
	/**
	 * A new method for simplicity.
	 * @return getRobotPosition(true) (numbers 1 - 3)
	 */
	public static int getRobotPosition() {
		return getRobotPosition(true);
	}
	
	
	public static String formatTime(double d) {
		return ((int) d/60)+":"+((((int) d % 60)+"").length()==1 ? ("0"+((int) d % 60)) : (((int) d % 60)));
	}
	
	/**
	 * TRU = time remaining updater
	 */
	public static Thread TRU = new Thread() {
		public void run() {
			while (true) {
				Scheduler.getInstance().run();
				
				SmartDashboard.putString(TIMEREMAINING, (int) (DriverStation.getInstance().getMatchTime()/60) + ":" + (new String(""+ ((int) DriverStation.getInstance().getMatchTime()%60)).length()==2 ? "" : "0") + (new String(""+ (DriverStation.getInstance().getMatchTime()%60))));

				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.err.println("Thread interrupted!");
				}
			}
		}
	};
	
	@Deprecated
	public static InetAddress getRobotIP() {
		try {
			return InetAddress.getByName(SmartDashboard.getString("", InetAddress.getLocalHost().getHostAddress()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	

	public static String getDriverStationIP() {
		return SmartDashboard.getString(DRIVERSTATION, "there was a problem retrieving the string apparently");
	}

}
