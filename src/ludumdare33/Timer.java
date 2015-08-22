package ludumdare33;

import processing.core.PApplet;
/**
 * This class is a stopwatch. Use isFinished() to know if your timer has reached its goal.
 * @author Yakka
 *
 */
public class Timer {
	private PApplet processing;
	
	private int goal = 0;
	private int currentTime = 0;
	private int delta = 0;
	private int lastTimeCheck = 0;
	
	public Timer(PApplet _processing) {
		processing = _processing;
	}
	
	public boolean isFinished() {
		update();
		return currentTime >= goal;
	}
	
	public void reset() {
		currentTime = 0;
		lastTimeCheck = processing.millis();
	}
	
	public void reset(int _goal) {
		reset();
		goal = _goal;
	}
	
	public int getDelta() {
		delta = processing.millis() - lastTimeCheck;
		lastTimeCheck = processing.millis();
		return delta;
	}
	
	public int getRemainingTime() {
		update();
		if (isFinished())
			return 0;
		else
			return goal - currentTime;
	}
	
	private void update() {
		currentTime += processing.millis() - lastTimeCheck;
		lastTimeCheck = processing.millis();
	}
	
	public float getRemainingRatio() {
		update();
		if(goal == 0)
			return -1;
		else if (currentTime > goal)
			return 1f;
		else
			return (float) currentTime / (float) goal;
	}
}
