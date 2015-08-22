package ludumdare33;

import processing.core.PApplet;
import processing.core.PVector;

public class Hair implements GameObject {

	private PApplet processing;
	
	// Hair position
	private PVector currentHead;
	private PVector currentFoot;
	private PVector SLEEP_FOOT;
	private final float SIZE = 50f;

	public Hair(PApplet _processing) {
		processing = _processing;
		
		currentHead = new PVector();
		currentFoot = new PVector();
	}

	@Override
	public void update() {
		setCurrentHead();
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		processing.fill(0);
		processing.stroke(0);
		processing.strokeWeight(10);
		processing.line(currentFoot.x, currentFoot.y, -1, currentHead.x, currentHead.y, -1);
	}

	@Override
	public void init() {
	}

	public void setCurrentFootX(float _x) {
		currentFoot.x = _x;
	}
	
	public void setCurrentFootY(float _y) {
		currentFoot.y = _y;
	}

	private void setCurrentHead() {
		currentHead.x = currentFoot.x;
		currentHead.y = currentFoot.y - SIZE;
	}
	
	
}
