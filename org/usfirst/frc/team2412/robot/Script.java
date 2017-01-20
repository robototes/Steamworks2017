package org.usfirst.frc.team2412.robot;

public abstract class Script extends Thread {

	private boolean _running = false;
	private boolean _killed = false;
	
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

	public void kill() {
		if (_killed) return;
		try {
			// 9.22337204e18 minutes of sleeping
			super.sleep(Long.MAX_VALUE);
			_killed = true;
		} catch (Exception e) {
			_killed = false;
		}
	}
	
}
