package org.usfirst.frc.team2412.robot.sd;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.usfirst.frc.team2412.robot.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SmartDashboardUtils {

	public static String IPROBOT = "IP of Robot",
						IPDRIVERSTATION = "IP of Driver Station";
	
	public static SmartDashboardNoTag STATION_NUMBER = new SmartDashboardNoTag(),
			ALLIANCE_STATION = new SmartDashboardNoTag(),
			TIME_REMAINING = new SmartDashboardNoTag(),
			BATTERY = new SmartDashboardNoTag();
	public static String DRIVERSTATION = "DriverStation IP", ROBOTPOSITION = "Robot Station Number: ", ROBOTALLIANCE = "Robot is on the Red Alliance: ";
	
	public static void init() {
		ALLIANCE_STATION.putString(DriverStation.getInstance().getAlliance().name());
		STATION_NUMBER.putString((Constants.STARTING_STATION == 1? "1st" : Constants.STARTING_STATION == 2 ? "2nd" : Constants.STARTING_STATION==3?"3rd" : "Unknown") + " Station");
		TIME_REMAINING.putString(((DriverStation.getInstance().getMatchTime()-5)/DriverStation.getInstance().getMatchTime()/12) + " gears left with "+formatTime(DriverStation.getInstance().getMatchTime())+" remaining.");
		BATTERY.putString("<unknown>% of battery remaining.");
		getRobotPosition(false);
		getSpecifiedAlliance();
		TRU.start();
	}
	/**
	 * @param startAt1 - if true, returns 1 - 3, otherwise, returns 0 - 2
	 * @return Which station the robot is at, specified by the user
	 * 
	 */
	public static int getRobotPosition(boolean startAt1) {
		int i = (int) SmartDashboard.getNumber(ROBOTPOSITION, 1.0)-(startAt1 ? 0 : 1);
		if ((i + (startAt1 ? 0 : 1)) != DriverStation.getInstance().getLocation()) {
			System.err.println("The specified driver station, " + (i + (startAt1 ? 0 : 1)) + " doesn't match up with " + DriverStation.getInstance().getLocation() + ". Returning specified station anyways.");
		}
		
		
		return i;
	}
	
	/**
	 * A new method for simplicity.
	 * @return getRobotPosition(true)
	 */
	public static int getRobotPosition() {
		return getRobotPosition(true);
	}
	
	public static DriverStation.Alliance getSpecifiedAlliance() {
		return SmartDashboard.getBoolean(ROBOTALLIANCE, false) ? DriverStation.Alliance.Blue : DriverStation.Alliance.Red;
	}
	
	public static String formatTime(double d) {
		return ((int) d/60)+":"+((((int) d % 60)+"").length()==1 ? ("0"+((int) d % 60)) : (((int) d % 60)));
	}
	
	/**
	 * TRU = time remaining updater
	 */
	public static Thread TRU = new Thread() {
		public void run() {
			while (DriverStation.getInstance().getMatchTime()>0) {
				TIME_REMAINING.putString(((DriverStation.getInstance().getMatchTime()-5)/DriverStation.getInstance().getMatchTime()/12) + " gears left with "+formatTime(DriverStation.getInstance().getMatchTime())+" remaining.");
				BATTERY.putString(DriverStation.getInstance().getBatteryVoltage()*8.333333333 + "% of battery remaining.");
				Scheduler.getInstance().run();
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
			return InetAddress.getByName(SmartDashboard.getString(IPROBOT, InetAddress.getLocalHost().getHostAddress()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public static String getDriverStationIP() {
		return SmartDashboard.getString(DRIVERSTATION, "there was a problem retrieving the string apparently");
	}

}
