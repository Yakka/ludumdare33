package ludumdare33;

import processing.core.PApplet;
import processing.core.PVector;

public class Level extends Panel {
	
	private Belly belly;
	private Hair hair;

	public Level(PApplet _processing, PVector _topLeft, PVector _bottomRight, int _backgroundColor[]) {
		// Creates the panel
		super(_processing, _topLeft, _bottomRight, _backgroundColor);
		
		// Creates elements of the level
		hair = new Hair(processing);
		belly = new Belly(processing, hair, 
				new PVector(topLeft.x, bottomRight.y * 9f / 10f + processing.random(-bottomRight.y * 1f / 10f, bottomRight.y * 1f / 10f)),
				new PVector(topLeft.x + (bottomRight.x - topLeft.x) / 2, bottomRight.y * 9f / 10f + processing.random(-bottomRight.y * 1f / 10f, bottomRight.y * 1f / 10f)),
				new PVector(bottomRight.x, bottomRight.y * 9f / 10f + processing.random(-bottomRight.y * 1f / 10f, bottomRight.y * 1f / 10f)),
				bottomRight);
		gameObjects.add(hair);
		gameObjects.add(belly);
		init();
	}

	@Override
	public void update() {
		super.update();
		hair.setCurrentFootX(belly.getAnchorPoint().x);
		hair.setCurrentFootY(belly.getAnchorPoint().y);
		
		if(isFinished()) {
			backgroundColor[0] = 142;
			backgroundColor[1] = 255;
			backgroundColor[2] = 172;
		}
			
	}

	@Override
	public void display() {
		super.display();
		float z;
		if(!belly.isSleeping())
			z = Belly.GRABBED_Z;
		else
			z = Belly.UNGRABBED_Z;
		
		// Background
		processing.fill(221, 234, 255);
		processing.fill(backgroundColor[0], backgroundColor[1], backgroundColor[2]);
		processing.noStroke();
		processing.beginShape();
		processing.vertex(topLeft.x, topLeft.y, z - 2);
		processing.vertex(bottomRight.x, topLeft.y, z - 2);
		processing.vertex(bottomRight.x, bottomRight.y, z - 2);
		processing.vertex(topLeft.x, bottomRight.y, z - 2);
		processing.endShape();
	}

	@Override
	public void init() {
		super.init();
		hair.setCurrentFootX(belly.getAnchorPoint().x);
		hair.setCurrentFootY(belly.getAnchorPoint().y);
	}
	
	@Override
	public void mousePressed() {
		belly.justGrabBelly();
		if(belly.isGrabbable())
			Main.cursor.setGrabbingCursor();
		else
			Main.cursor.setGrabbingCursor();
		belly.grabBelly();
	}

	public void mouseDragged() {
		Main.cursor.setGrabbingCursor();
		belly.grabBelly();
	}

	public void mouseReleased() {
		belly.releaseBelly();
		Main.cursor.setBasicCursor();
	}
	
	public void mouseMoved() {
		if(belly.isGrabbable())
			Main.cursor.setGrabbableCursor();
		else
			Main.cursor.setBasicCursor();
	}
	
	@Override
	public boolean isFinished() {
		return belly.isFinished();
	}
}
