package ludumdare33;

import processing.core.PApplet;

public class Hair implements GameObject {

	private PApplet processing;
	
	public Hair(PApplet _processing) {
		processing = _processing;
	}
	
	@Override
	public void update() {
		
		
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		processing.fill(0);
		processing.stroke(0);
		processing.strokeWeight(10);
		processing.bezier((float) processing.width / 2f, (float) processing.height * 2f/3f, (float) processing.width / 2f, (float) processing.height * 2f/3f - 25f, (float) processing.width / 2f + 10f, (float) processing.height * 2f/3f - 35f, (float) processing.width / 2f + 25f, (float) processing.height * 2f/3f - 50f);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
