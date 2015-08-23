package ludumdare33;

import processing.core.PApplet;
import processing.core.PVector;

public class Narrative extends Panel {

	// Dialogue
	private String text = "Prout.";
	private int textIndex = 0;
	
	// Monster
	private int animationIndex;
	
	public Narrative(PApplet _processing, PVector _topLeft, PVector _bottomRight, int[] _backgroundColor, String _text) {
		super(_processing, _topLeft, _bottomRight, _backgroundColor);
		
		init();
	}

	@Override
	public void init() {
		super.init();
		textIndex = 0;
	}
	
	@Override
	public void display() {
		super.display();
		processing.textFont(Main.textFont);
		processing.fill(0);
		processing.text(text, topLeft.x, topLeft.y+50, 1);
	}
	
}
