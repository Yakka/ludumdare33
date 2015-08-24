package ludumdare33;

import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;

public class Main extends PApplet {
	private static final long serialVersionUID = 1L;
	
	public static Cursor cursor;
	
	public static Minim minim;

	private boolean splashscreen = true;
	
	private PVector[] topLefts;
	private PVector[] bottomRights;
	private Level[] levels;
	private Panel[] panels;
	private Narrative[] narratives;
	private String[] texts = {
			"All my life, I was a hairy monster.",
			"If only I could be something fresher. Let's say a kiwi.",
			"Oh, this could be the solution? Pulling off each one of my hairs...",
			"Yes, I feel colder. I mean, I feel fresher. Closer to the kiwi! Just a few more...",
			"It kind of hurted, but finally I am a kiwi. Life is better when you can be the fruit of your choice."
			};
	
	private final int NB_OF_LEVELS = 6;
	private final int NB_OF_NARRATIVES = 5;
	
	private PImage img;
	
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

		cursor = new Cursor(this);
		
		textFont = loadFont("ludumdare33/font_text.vlw");
		monster = new PImage[2];
		img = new PImage();
		img = loadImage("ludumdare33/splashscreen.png");
		
		monster[0] = loadImage("ludumdare33/monster.png");
		monster[1] = loadImage("ludumdare33/monster2.png");
		
		minim = new Minim(this);
		levels = new Level[NB_OF_LEVELS];
		narratives = new Narrative[NB_OF_NARRATIVES];
		panels = new Panel[NB_OF_LEVELS+NB_OF_NARRATIVES];
		topLefts = new PVector[NB_OF_LEVELS+NB_OF_NARRATIVES];
		bottomRights = new PVector[NB_OF_LEVELS+NB_OF_NARRATIVES];
		
		// Narratives
		
		topLefts[0] = new PVector(MARGIN, MARGIN);
		bottomRights[0] = new PVector(width * 10 / UNIT, height * 16 / UNIT);
		
		topLefts[1] = new PVector(MARGIN, height * 17 / UNIT);
		bottomRights[1] = new PVector(width * 10 / UNIT, height * 32 / UNIT);
		
		topLefts[2] = new PVector(MARGIN, height * 33 / UNIT);
		bottomRights[2] = new PVector(width * 10 / UNIT, height - MARGIN);
		
		topLefts[3] = new PVector(width * 23 / UNIT, height * 17 / UNIT);
		bottomRights[3] = new PVector(width * 33 / UNIT, height * 32 / UNIT);
		
		topLefts[4] = new PVector(width * 34 / UNIT, height * 33 / UNIT);
		bottomRights[4] = new PVector(width - MARGIN, height - MARGIN);
		
		
		// Levels
		
		topLefts[5] = new PVector(width * 11 / UNIT, MARGIN);
		bottomRights[5] = new PVector(width * 22 / UNIT, height * 32 / UNIT);
		
		topLefts[6] = new PVector(width * 11 / UNIT, height * 33 / UNIT);
		bottomRights[6] = new PVector(width * 22 / UNIT, height - MARGIN);
		
		topLefts[7] = new PVector(width * 23 / UNIT, MARGIN);
		bottomRights[7] = new PVector(width * 33 / UNIT, height * 16 / UNIT);
		
		topLefts[8] = new PVector(width * 34 / UNIT, MARGIN);
		bottomRights[8] = new PVector(width - MARGIN, height * 16 / UNIT);
		
		topLefts[9] = new PVector(width * 34 / UNIT, height * 17 / UNIT);
		bottomRights[9] = new PVector(width - MARGIN, height * 32 / UNIT);
		
		topLefts[10] = new PVector(width * 23 / UNIT, height * 33 / UNIT);
		bottomRights[10] = new PVector(width * 33 / UNIT, height - MARGIN);
		
		for(int i = 0; i < levels.length; i++) {
			levels[i] = new Level(this, topLefts[i+NB_OF_NARRATIVES], bottomRights[i+NB_OF_NARRATIVES], new int[] {221, 234, 255});
		}
		
		for(int i = 0; i < narratives.length; i++) {
			narratives[i] = new Narrative(this, topLefts[i], bottomRights[i], new int[] {221, 234, 255}, texts[i], i == narratives.length - 1? 1 : 0);
		}
		
		// FUCKING STORY TELLING
		panels[0] = narratives[0];
		panels[1] = narratives[1];
		panels[2] = levels[0];
		panels[3] = narratives[2];
		panels[4] = levels[1];
		panels[5] = levels[2];
		panels[6] = levels[3];
		panels[7] = narratives[3];
		panels[8] = levels[4];
		panels[9] = levels[5];
		panels[10] = narratives[4];
		
		deltaTimeTimer = new Timer(this);
	}

	public void draw() {
		deltaTime = deltaTimeTimer.getDelta();
		background(255);
		
		
		if(splashscreen) {
			beginShape();
			textureMode(PConstants.NORMAL);
			texture(img);
			vertex(0, 0, 0, 0);
			vertex(width, 0, 1, 0);
			vertex(width, height, 1, 1);
			vertex(0, height, 0, 1);
			endShape();
			textureMode(PConstants.IMAGE);
			
		}
		else {
			cursor.update();
			cursor.display();
			for(int i = 0; i < panels.length; i++) {
				panels[i].update();
				panels[i].display();
				if(!panels[i].isFinished())
					break;
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
				if(!panels[i].isFinished())
					break;
			}
			endShape(PConstants.CLOSE);
		}
	}

	
	public boolean sketchFullScreen() {
		return true;
	}
	
	public void mousePressed() {
		splashscreen = false;
		for(int i = 0; i < levels.length; i++) {
			if(levels[i].iAmHere())
				levels[i].mousePressed();
		}
		for(int i = 0; i < narratives.length; i++) {
			if(narratives[i].iAmHere())
				narratives[i].mousePressed();
		}
	}
	
	public void mouseDragged() {
		for(int i = 0; i < levels.length; i++) {
			if(levels[i].iAmHere())
				levels[i].mouseDragged();
		}
	}
	
	public void mouseReleased() {
		for(int i = 0; i < levels.length; i++) {
			if(levels[i].iAmHere())
				levels[i].mouseReleased();

		}
	}
	
	public void mouseMoved() {
		for(int i = 0; i < levels.length; i++) {
			if(levels[i].iAmHere())
				levels[i].mouseMoved();
		}
	}
	
	
	public static void main(String args[]) {
		PApplet.main(new String[] {"ludumdare33.Main", "--full-screen --display=0"});
	}
}