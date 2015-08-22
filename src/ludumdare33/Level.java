package ludumdare33;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;

public class Level implements GameObject{
	private PApplet processing;
	private Timer deltaTimeTimer;
	private int deltaTime; //TODO: use the delta time as a statics in Main
	private Belly belly;
	private Hair hair;
	
	private ArrayList<GameObject> gameObjects;
	
	public Level(PApplet _processing) {
		processing = _processing;
		deltaTimeTimer = new Timer(_processing);
		// Create elements from the level
		belly = new Belly(processing);
		hair = new Hair(processing);
		gameObjects = new ArrayList<GameObject>();
		gameObjects.add(belly);
		gameObjects.add(hair);
		
		init();
	}

	@Override
	public void update() {
		deltaTime = deltaTimeTimer.getDelta();
		for(Iterator<GameObject> i = gameObjects.iterator(); i.hasNext();)
			i.next().update();
	}

	@Override
	public void display() {
		processing.background(0, 255, 255);
		for(Iterator<GameObject> i = gameObjects.iterator(); i.hasNext();)
			  i.next().display();
	}

	@Override
	public void init() {
		for(Iterator<GameObject> i = gameObjects.iterator(); i.hasNext();)
			  i.next().init();
	}
	
	public void mouseDragged() {
		belly.grabBelly();
	}
	
	public void mouseReleased(){
		belly.releaseBelly();
	}
}
