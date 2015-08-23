package ludumdare33;

import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Main extends PApplet {
	private static final long serialVersionUID = 1L;
	
	public static Minim minim;

	
	private PVector[] topLefts;
	private PVector[] bottomRights;
	private Level[] levels;
	
	private final int NB_OF_LEVELS = 1;

	private Timer deltaTimeTimer;
	static public int deltaTime; // TODO: use the delta time as a statics in Main

	public void setup() {
		size(displayWidth, displayHeight, P3D);

		noCursor();
		
		minim = new Minim(this);
		levels = new Level[NB_OF_LEVELS];
		topLefts = new PVector[NB_OF_LEVELS];
		bottomRights = new PVector[NB_OF_LEVELS];
		
		topLefts[0] = new PVector(50, 50);
		bottomRights[0] = new PVector(width / 3, height / 3);
		
		for(int i = 0; i < levels.length; i++) {
			levels[i] = new Level(this, topLefts[i], bottomRights[i]);
		}
		
		deltaTimeTimer = new Timer(this);
	}

	public void draw() {
		deltaTime = deltaTimeTimer.getDelta();
		background(255);
		
		for(int i = 0; i < levels.length; i++) {
			levels[i].update();
			levels[i].display();
		}
		
		fill(255);
		beginShape();
		// Exterior part of shape, clockwise winding
		vertex(0, 0);
		vertex(width, 0);
		vertex(width, height);
		vertex(0, height);
		// Interior part of shape, counter-clockwise winding
		for(int i = 0; i < levels.length; i++) {
			beginContour();
			vertex(levels[i].topLeft.x, levels[i].topLeft.y);
			vertex(levels[i].bottomRight.x, levels[i].topLeft.y);
			vertex(levels[i].bottomRight.x, levels[i].bottomRight.y);
			vertex(levels[i].topLeft.x, levels[i].bottomRight.y);
			endContour();
		}
		endShape(PConstants.CLOSE);
	}

	
	public boolean sketchFullScreen() {
		return true;
	}
	
	public void mousePressed() {
		for(int i = 0; i < levels.length; i++) {
			levels[i].mousePressed();
		}
	}
	
	public void mouseDragged() {
		for(int i = 0; i < levels.length; i++) {
			levels[i].mouseDragged();
		}
	}
	
	public void mouseReleased() {
		for(int i = 0; i < levels.length; i++) {
			levels[i].mouseReleased();
		}
	}
	
	public void mouseMoved() {
		for(int i = 0; i < levels.length; i++) {
			levels[i].mouseMoved();
		}
	}
	
	
	public static void main(String args[]) {
		PApplet.main(new String[] {"ludumdare33.Main", "--full-screen --display=0"});
	}
}