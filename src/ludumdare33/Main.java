package ludumdare33;

import ddf.minim.Minim;
import processing.core.PApplet;

public class Main extends PApplet {
	private static final long serialVersionUID = 1L;
	
	public static Minim minim;

	private Level hairsLevel;

	private Timer deltaTimeTimer;
	static public int deltaTime; // TODO: use the delta time as a statics in Main

	public void setup() {
		size(displayWidth, displayHeight, P3D);
		
		minim = new Minim(this);
		
		hairsLevel = new Level(this);
		
		deltaTimeTimer = new Timer(this);
		
	}

	public void draw() {
		deltaTime = deltaTimeTimer.getDelta();
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