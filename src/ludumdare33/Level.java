package ludumdare33;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;

public class Level implements GameObject {
	private PApplet processing;
	private Belly belly;
	private Hair hair;
	
	// Contour
	private final int MARGIN = 25;
	private final int THICKNESS = 10;

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
		processing.background(221, 234, 255);
		for (Iterator<GameObject> i = gameObjects.iterator(); i.hasNext();)
			i.next().display();
		
		processing.fill(255);
		processing.strokeWeight(5);
		processing.strokeJoin(processing.MITER);
		processing.stroke(0);
		processing.beginShape();
		// Exterior part of shape, clockwise winding
		processing.vertex(0, 0);
		processing.vertex(processing.width, 0);
		processing.vertex(processing.width, processing.height);
		processing.vertex(0, processing.height);
		// Interior part of shape, counter-clockwise winding
		processing.beginContour();
		processing.vertex(MARGIN + THICKNESS, MARGIN + THICKNESS);
		processing.vertex(processing.width - MARGIN - THICKNESS, MARGIN + THICKNESS);
		processing.vertex(processing.width - MARGIN - THICKNESS, processing.height - MARGIN - THICKNESS);
		processing.vertex(MARGIN + THICKNESS, processing.height - MARGIN - THICKNESS);
		processing.endContour();
		processing.endShape(processing.CLOSE);
	}

	@Override
	public void init() {
		hair.setCurrentFootX(belly.getAnchorPoint().x);
		hair.setCurrentFootY(belly.getAnchorPoint().y);
		for (Iterator<GameObject> i = gameObjects.iterator(); i.hasNext();)
			i.next().init();
	}
	
	public void mousePressed() {
		if(PApplet.dist(processing.mouseX, processing.mouseY, belly.ANCHOR_POINT.x, belly.ANCHOR_POINT.y) < 100)
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
