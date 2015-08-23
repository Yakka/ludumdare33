package ludumdare33;

import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public abstract class Panel implements GameObject{
	protected PApplet processing;
	
	protected boolean started = false;
	protected boolean iAmHere = false;
	
	// Contour
	protected final int MARGIN = 2;
	public PVector topLeft;
	public PVector bottomRight;
	
	// Colors
	protected int[] backgroundColor; // Background color
	
	// Inner elements
	protected ArrayList<GameObject> gameObjects;
	
	public Panel(PApplet _processing, PVector _topLeft, PVector _bottomRight, int[] _backgroundColor) {
		processing = _processing;
		backgroundColor = _backgroundColor;
		topLeft = new PVector(_topLeft.x, _topLeft.y);
		bottomRight = new PVector(_bottomRight.x, _bottomRight.y);

		gameObjects = new ArrayList<GameObject>();
	}
	
	public boolean iAmHere() {
		return iAmHere;
	}
	
	@Override
	public void update() {
		iAmHere = true;
		for (Iterator<GameObject> i = gameObjects.iterator(); i.hasNext();)
			i.next().update();
	}
	@Override
	public void display() {
		// Panel's sides
		processing.fill(0);
		processing.beginShape();
		// Exterior part of shape, clockwise winding
		processing.vertex(topLeft.x, topLeft.y, 2);
		processing.vertex(bottomRight.x, topLeft.y, 2);
		processing.vertex(bottomRight.x, bottomRight.y, 2);
		processing.vertex(topLeft.x, bottomRight.y, 2);
		// Interior part of shape, counter-clockwise winding
		processing.beginContour();
		processing.vertex(topLeft.x + MARGIN, topLeft.y + MARGIN, 2);
		processing.vertex(bottomRight.x - MARGIN, topLeft.y + MARGIN, 2);
		processing.vertex(bottomRight.x - MARGIN, bottomRight.y - MARGIN, 2);
		processing.vertex(topLeft.x + MARGIN, bottomRight.y - MARGIN, 2);
		processing.endContour();
		processing.endShape(PConstants.CLOSE);
		
		for (Iterator<GameObject> i = gameObjects.iterator(); i.hasNext();)
			i.next().display();
		
	}
	@Override
	public void init() {
		for (Iterator<GameObject> i = gameObjects.iterator(); i.hasNext();)
			i.next().init();

		started = false;
		iAmHere = false;
	}

	public boolean isFinished() {
		return false;
	}
	
	public boolean hasStarted() {
		return started;
	}
	
	public void start() {
		started = true;
	}

	public abstract void mousePressed();
}
