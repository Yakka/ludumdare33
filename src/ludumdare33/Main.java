package ludumdare33;

import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;

public class Main extends PApplet {
	private static final long serialVersionUID = 1L;
	
	public static Minim minim;

	
	private PVector[] topLefts;
	private PVector[] bottomRights;
	private Level[] levels;
	private Panel[] panels;
	private Narrative[] narratives;
	
	private final int NB_OF_LEVELS = 4;
	private final int NB_OF_NARRATIVES = 1;
	
	// Font
	public static PFont textFont;
	
	// Monster
	public static PImage[] monster;
	
	//Layout
	private final int MARGIN = 10;
	private final int UNIT = 50;

	private Timer deltaTimeTimer;
	static public int deltaTime; // TODO: use the delta time as a statics in Main

	public void setup() {
		size(displayWidth, displayHeight, P3D);
		ortho();
		noCursor();
		
		textFont = loadFont("ludumdare33/font_text.vlw");
		monster = new PImage[1];
		
		monster[0] = loadImage("ludumdare33/monster.png");
		
		minim = new Minim(this);
		levels = new Level[NB_OF_LEVELS];
		narratives = new Narrative[NB_OF_NARRATIVES];
		panels = new Panel[NB_OF_LEVELS+NB_OF_NARRATIVES];
		topLefts = new PVector[NB_OF_LEVELS+NB_OF_NARRATIVES];
		bottomRights = new PVector[NB_OF_LEVELS+NB_OF_NARRATIVES];
		
		topLefts[0] = new PVector(MARGIN, MARGIN);
		bottomRights[0] = new PVector(width * 10 / UNIT, height * 16 / UNIT);
		
		topLefts[1] = new PVector(width * 11 / UNIT, MARGIN);
		bottomRights[1] = new PVector(width * 21 / UNIT, height * 33 / UNIT);
		
		topLefts[2] = new PVector(MARGIN, height * 17 / UNIT);
		bottomRights[2] = new PVector(width * 10 / UNIT, height * 33 / UNIT);
		
		topLefts[3] = new PVector(MARGIN, height * 34 / UNIT);
		bottomRights[3] = new PVector(width * 21 / UNIT, height * 49 / UNIT);
		
		topLefts[4] = new PVector(width * 22 / UNIT, MARGIN);
		bottomRights[4] = new PVector(width * 32 / UNIT, height * 16 / UNIT);
		
		for(int i = 0; i < levels.length; i++) {
			levels[i] = new Level(this, topLefts[i], bottomRights[i], new int[] {221, 234, 255});
			panels[i] = levels[i];
		}
		
		for(int i = 0; i < narratives.length; i++) {
			narratives[i] = new Narrative(this, topLefts[NB_OF_LEVELS+i], bottomRights[NB_OF_LEVELS+i], new int[] {221, 234, 255}, "I wish I were a kiwi...");
			panels[i+NB_OF_LEVELS] = narratives[i];
		}
		
		deltaTimeTimer = new Timer(this);
	}

	public void draw() {
		deltaTime = deltaTimeTimer.getDelta();
		background(255);
		
		for(int i = 0; i < panels.length; i++) {
			panels[i].update();
			panels[i].display();
		}
		
		fill(255);
		beginShape();
		// Exterior part of shape, clockwise winding
		vertex(0, 0);
		vertex(width, 0);
		vertex(width, height);
		vertex(0, height);
		// Interior part of shape, counter-clockwise winding
		for(int i = 0; i < panels.length; i++) {
			beginContour();
			vertex(panels[i].topLeft.x, panels[i].topLeft.y, 5);
			vertex(panels[i].bottomRight.x, panels[i].topLeft.y, 5);
			vertex(panels[i].bottomRight.x, panels[i].bottomRight.y, 5);
			vertex(panels[i].topLeft.x, panels[i].bottomRight.y, 5);
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