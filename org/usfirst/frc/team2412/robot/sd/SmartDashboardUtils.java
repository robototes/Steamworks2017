package org.usfirst.frc.team2412.robot.sd;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.usfirst.frc.team2412.robot.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class SmartDashboardUtils {

	public static String IPROBOT = "IP of Robot",
						IPDRIVERSTATION = "IP of Driver Station";
	
	public static SmartDashboardNoTag STATION_NUMBER = new SmartDashboardNoTag(),
			ALLIANCE_STATION = new SmartDashboardNoTag(),
			TIME_REMAINING = new SmartDashboardNoTag(),
			BATTERY = new SmartDashboardNoTag();
	public static String DRIVERSTATION = "DriverStation IP";
	
	public static void init() {
		ALLIANCE_STATION.putString(DriverStation.getInstance().getAlliance().name());
		STATION_NUMBER.putString((Constants.STARTING_STATION == 1? "1st" : Constants.STARTING_STATION == 2 ? "2nd" : Constants.STARTING_STATION==3?"3rd" : "Unknown") + " Station");
		TIME_REMAINING.putString(((DriverStation.getInstance().getMatchTime()-5)/DriverStation.getInstance().getMatchTime()/12) + " gears left with "+formatTime(DriverStation.getInstance().getMatchTime())+" remaining.");
		BATTERY.putString("<unknown>% of battery remaining.");
		SmartDashboard.getString(DRIVERSTATION, "<dynamic>");
		TRU.start();
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
