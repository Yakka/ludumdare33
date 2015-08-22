package ludumdare33;

import processing.core.PApplet;

public class Level implements GameObject{
	private PApplet processing;
	private Timer deltaTimeTimer;
	private int deltaTime;
	
	public Level(PApplet _processing) {
		processing = _processing;
		deltaTimeTimer = new Timer(_processing);
		init();
	}

	@Override
	public void update() {
		deltaTime = deltaTimeTimer.getDelta();
	}

	@Override
	public void display() {
		processing.fill(50);
		processing.noStroke();
		processing.beginShape();
		processing.vertex(0, (int)processing.height*2f/3f);
		processing.bezierVertex(0, (int)processing.height*2f/3f, processing.mouseX, processing.mouseY, processing.width, (int)processing.height*2f/3f);
		processing.vertex(processing.width, processing.height);
		processing.vertex(0, processing.height);
		processing.endShape();

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
