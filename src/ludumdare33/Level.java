package ludumdare33;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;

public class Level implements GameObject {
	private PApplet processing;
	private Belly belly;
	private Hair hair;

	private ArrayList<GameObject> gameObjects;

	public Level(PApplet _processing) {
		processing = _processing;
		// Create elements from the level
		belly = new Belly(processing);
		hair = new Hair(processing);
		gameObjects = new ArrayList<GameObject>();
		gameObjects.add(hair);
		gameObjects.add(belly);

		init();
	}

	@Override
	public void update() {
		hair.setCurrentFootX(belly.getAnchorPoint().x);
		hair.setCurrentFootY(belly.getAnchorPoint().y);
		for (Iterator<GameObject> i = gameObjects.iterator(); i.hasNext();)
			i.next().update();
	}

	@Override
	public void display() {
		processing.background(0, 255, 255);
		
		
		for (Iterator<GameObject> i = gameObjects.iterator(); i.hasNext();)
			i.next().display();
	}

	@Override
	public void init() {
		hair.setCurrentFootX(belly.getAnchorPoint().x);
		hair.setCurrentFootY(belly.getAnchorPoint().y);
		for (Iterator<GameObject> i = gameObjects.iterator(); i.hasNext();)
			i.next().init();
	}
	
	public void mousePressed() {
		belly.justGrabBelly();
		hair.justGrabHair();
	}

	public void mouseDragged() {
		if(!hair.isHurt()) {
			belly.grabBelly();
			hair.grabHair();
		} else {
			belly.releaseBelly();
			hair.grabHair();
		}
	}

	public void mouseReleased() {
		belly.releaseBelly();
		hair.releaseHair();
	}
}
