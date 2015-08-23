package ludumdare33;

import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PVector;

public class Main extends PApplet {
	private static final long serialVersionUID = 1L;
	
	public static Minim minim;

	private Level hairsLevel;

	private Timer deltaTimeTimer;
	static public int deltaTime; // TODO: use the delta time as a statics in Main

	public void setup() {
		size(displayWidth, displayHeight, P3D);

		noCursor();
		
		minim = new Minim(this);
		
		hairsLevel = new Level(this, new PVector(50, 50), new PVector(width / 3, height / 3));
		
		deltaTimeTimer = new Timer(this);
		
	}

	public void draw() {
		deltaTime = deltaTimeTimer.getDelta();
		background(255);
		hairsLevel.update();
		hairsLevel.display();
	}

	
	public boolean sketchFullScreen() {
		return true;
	}
	
	public void mousePressed() {
		hairsLevel.mousePressed();
	}
	
	public void mouseDragged() {
		hairsLevel.mouseDragged();
	}
	
	public void mouseReleased() {
		hairsLevel.mouseReleased();
	}
	
	public void mouseMoved() {
		hairsLevel.mouseMoved();
	}
	
	
	public static void main(String args[]) {
		PApplet.main(new String[] {"ludumdare33.Main", "--full-screen --display=0"});
	}
}