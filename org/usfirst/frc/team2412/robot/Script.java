package org.usfirst.frc.team2412.robot;

public abstract class Script extends Thread {

	private boolean _running = false;
	
	@Override
	public final void start() {
		if (!_running) {
			_running = true;
			super.start();
		}
	}
	
	@Override
	public final void run() {
		_running = true;
		execute();
		_running = false;
	}
	
	protected abstract void execute();
	
}
