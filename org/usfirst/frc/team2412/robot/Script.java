package org.usfirst.frc.team2412.robot;

public abstract class Script extends Thread {

	private boolean _running = false;
	private boolean _killed = false;
	private boolean _disabled = false;
	
	@Override
	public final void start() {
		if (!_running) {
			_running = true;
			try {
				super.start();
			} catch (Exception e) {
				
			}
		}
	}
	
	@Override
	public final void run() {
		_running = true;
		execute();
		_running = false;
	}
	
	protected abstract void execute();

	// Sounds like python (; <-- smiley face!
	public void kill() {
		if (_killed) return;
		try {
			super.yield();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		try {
			// 9.22337204e18 minutes of sleeping
			super.sleep(Long.MAX_VALUE);
			_killed = true;
		} catch (Exception e) {
			System.err.println("It's ok. The robot won't fail because of this.");
			e.printStackTrace();
			_killed = false;
		}
	}
	
	public void disable() {
		kill();
		_running = true;
		_disabled = true;
	}
	
	public void enable() {
		_running = false;
		_disabled = true;
	}
	
	public boolean isRunning() {
		return _running & !_disabled;
	}
	
	public boolean isDisabled() {
		return _disabled;
	}
	
}
