package ludumdare33;

import processing.core.PApplet;

public class Level implements GameObject{
	private PApplet processing;
	private Timer deltaTimeTimer;
	private int deltaTime; //TODO: use the delta time as a statics in Main
	private Belly belly;
	
	public Level(PApplet _processing) {
		processing = _processing;
		deltaTimeTimer = new Timer(_processing);
		belly = new Belly(processing);
		init();
	}

	@Override
	public void update() {
		deltaTime = deltaTimeTimer.getDelta();
		belly.update();
	}

	@Override
	public void display() {
		belly.display();
	}

	@Override
	public void init() {
		belly.init();
	}
	
	public void mouseDragged() {
		belly.grabBelly();
	}
	
	public void mouseReleased(){
		belly.releaseBelly();
	}
}
