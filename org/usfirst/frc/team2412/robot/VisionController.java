package org.usfirst.frc.team2412.robot;

public class VisionController implements RobotController {
	
	public static final String TABLENAME = "datatable";
	
	/* Raspberry Pi side, in python (sorry Alexander)
		#!/usr/bin/env python3
		#
		# This is a NetworkTables client (eg, the DriverStation/coprocessor side).
		# You need to tell it the IP address of the NetworkTables server (the
		# robot or simulator).
		#
		# This shows how to use a listener to listen for changes in NetworkTables
		# values. This will print out any changes detected on the SmartDashboard
		# table.
		#
		
		import sys
		import time
		from networktables import NetworkTables
		
		# To see messages from networktables, you must setup logging
		import logging
		logging.basicConfig(level=logging.DEBUG)
		
		#if len(sys.argv) != 2:
		#    print("Error: specify an IP to connect to!")
		#    exit(0)
		
		#ip = sys.argv[1]
		ip = "10.24.12.105"
		
		NetworkTables.initialize(server=ip)
		
		
		def valueChanged(table, key, value, isNew):
		    print("valueChanged: key: '%s'; value: %s; isNew: %s" % (key, value, isNew))
		    table.putNumber('Y', value)
		
		def connectionListener(connected, info):
		    print(info, '; Connected=%s' % connected)
		
		
		NetworkTables.addConnectionListener(connectionListener, immediateNotify=True)
		
		sd = NetworkTables.getTable("datatable")
		sd.addTableListener(valueChanged)
		
		while True:
		    time.sleep(1)
	 */
	public void processTeleop() {
		boolean targetsFound = Constants.visionTable.getBoolean("targetsFound", false);
		if(targetsFound) {
			double angle = Constants.visionTable.getNumber("angle", -1);
			double distance = Constants.visionTable.getNumber("distance", -1);
//			System.out.println("Angle: " + angle);
//			System.out.println("Distance: " + distance);
		} else {
//			System.out.println("No targets found!");
		}
	}

	public void processAutonomous() {
	}

	public void teleopInit() {
		
	}

	public void autonomousInit() {
		// TODO Auto-generated method stub
		
	}
	
	public double getAngle() {
		return Constants.visionTable == null ? Double.NaN : Constants.visionTable.getNumber("angle", Double.NaN);
	}
	
	public double getDist() {
		return Constants.visionTable == null ? Double.NaN: Constants.visionTable.getNumber("distance", Double.NaN);
	}
	
}
