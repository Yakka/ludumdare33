package ludumdare33;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Narrative extends Panel {

	// Dialogue
	private String text = "";
	private int textIndex = 0;
	private final int TEXT_MARGIN = 20 + MARGIN;
	
	// Monster
	private int animationIndex;
	
	public Narrative(PApplet _processing, PVector _topLeft, PVector _bottomRight, int[] _backgroundColor, String _text) {
		super(_processing, _topLeft, _bottomRight, _backgroundColor);
		text = _text;
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
		processing.rectMode(PConstants.CORNERS);
		processing.text(text, topLeft.x + TEXT_MARGIN, topLeft.y + TEXT_MARGIN, bottomRight.x - TEXT_MARGIN, bottomRight.y - TEXT_MARGIN);
		processing.rectMode(PConstants.CORNER);
	}
	
}
