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
	
	public Timer(PApplet _processing) {
		processing = _processing;
		goal = processing.millis();
		currentTime = processing.millis();
	}
	
	public boolean isFinished() {
		currentTime = processing.millis();
		return currentTime >= goal;
	}
	
	public void reset() {
		currentTime = processing.millis();
	}
	
	public void reset(int _goal) {
		reset();
		goal = _goal + processing.millis();
	}
	
	public int getDelta() {
		delta = processing.millis() - currentTime;
		currentTime = processing.millis();
		return delta;
	}
	
	public int getRemainingTime() {
		currentTime = processing.millis();
		if (isFinished())
			return 0;
		else
			return goal - currentTime;
	}
	
	public float getRemainingRatio() {
		currentTime = processing.millis();
		if(goal != 0)
			return (float) currentTime / (float) goal;
		else return -1;
	}
}
