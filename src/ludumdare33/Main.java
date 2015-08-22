package ludumdare33;

import processing.core.PApplet;

public class Main extends PApplet {
	private static final long serialVersionUID = 1L;

	private Level hairsLevel;

	public void setup() {
		size(displayWidth, displayHeight, P3D);
		
		hairsLevel = new Level(this);
		
	}

	public void draw() {
		background(0, 255, 255);
		hairsLevel.update();
		hairsLevel.display();
	}

	
	public boolean sketchFullScreen() {
		return true;
	}
	
	public void mouseDragged() {
		hairsLevel.mouseDragged();
	}
	
	public void mouseReleased() {
		hairsLevel.mouseReleased();
	}
	
	
	public static void main(String args[]) {
		PApplet.main(new String[] {"ludumdare33.Main", "--full-screen --display=0"});
	}
}