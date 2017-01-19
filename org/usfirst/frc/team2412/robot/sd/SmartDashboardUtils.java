package org.usfirst.frc.team2412.robot.sd;

import org.usfirst.frc.team2412.robot.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardUtils {
	
	public static void init() {
		if (!Constants.constantsInitialized) Constants.init();
		SmartDashboard.putString("Alliance", ""+Constants.ALLIANCE_COLOR.name());
		SmartDashboard.putNumber("Station", Constants.STARTING_STATION);
		SmartDashboard.putString("Autonomous Status:", Constants.AutoStatus.getCurrent().getSDDescriptor());
		SmartDashboard.putString("Time Remaining:", formatTime(DriverStation.getInstance().getMatchTime()));
		SmartDashboard.putData("Grab Gear From Ground", createButton(SmartDashboardScript.GrabGearFromGround));
		SmartDashboard.putData("Release Gear on Hook", createButton(SmartDashboardScript.ReleaseToHook));
		TRU.start();
	}
	
	public static Button createButton(final SmartDashboardScript sds) {
		return new Button() {
			
			boolean pressed = false;
			
			@Override
			public void whenPressed(Command c) {
				pressed = true;
				sds.whenPressed();
			}
				
			@Override
			public void whenReleased(Command c) {
				pressed = false;
				sds.whenReleased();
			}

			@Override
			public boolean get() {
				return pressed;
			}
			
		};
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
				SmartDashboard.putString("Autonomous Status: ", Constants.AutoStatus.getCurrent().getSDDescriptor());
				SmartDashboard.putString("Time Remaining:", formatTime(DriverStation.getInstance().getMatchTime()));
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.err.println("Thread interrupted!");
				}
			}
		}
	};

}
