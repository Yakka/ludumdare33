package ludumdare33;

import processing.core.PApplet;

public class Main extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void setup() {
		size(displayWidth, displayHeight, P3D);
		
	}

	public void draw() {
		background(0, 255, 255);
	}

	
	public boolean sketchFullScreen() {
		return true;
	}
	
	
	public static void main(String args[]) {
		PApplet.main(new String[] {"ludumdare33.Main", "--full-screen --display=0"});
	}
}