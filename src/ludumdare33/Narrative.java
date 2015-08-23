package ludumdare33;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Narrative extends Panel {

	// Dialogue
	private String text = "";
	private String currentText = "";
	private final int TEXT_MARGIN = 20 + MARGIN;
	
	private Timer textTimer;
	private final int MILLIS_PER_CHAR = 100;
	
	// Monster
	private int animationIndex = 0;
	private PVector position;
	private final int SIZE = 128;
	private int lastChar = 0;
	
	public Narrative(PApplet _processing, PVector _topLeft, PVector _bottomRight, int[] _backgroundColor, String _text) {
		super(_processing, _topLeft, _bottomRight, _backgroundColor);
		text = _text;
		textTimer = new Timer(processing);
		position = new PVector((_bottomRight.x - _topLeft.x) / 2 + _topLeft.x - SIZE / 2, _bottomRight.y - SIZE);
		init();
	}

	@Override
	public void init() {
		super.init();
		currentText = "";
		textTimer.reset(MILLIS_PER_CHAR * text.length());
		lastChar = 0;
	}
	
	@Override
	public void update() {
		updateCurrentText();
		if(lastChar != currentText.length()) {
			position.y += 2 * processing.random(-1f, 1f);
			lastChar ++;
		}
	}
	
	@Override
	public void display() {
		super.display();
		// Monster
		processing.beginShape();
		processing.textureMode(PConstants.NORMAL);
		processing.texture(Main.monster[animationIndex]);
		processing.vertex(position.x, position.y, 0, 0);
		processing.vertex(position.x + SIZE, position.y, 1, 0);
		processing.vertex(position.x + SIZE, position.y + SIZE, 1, 1);
		processing.vertex(position.x, position.y + SIZE, 0, 1);
		processing.endShape();
		processing.textureMode(PConstants.IMAGE);
		
		// Text
		processing.textFont(Main.textFont);
		processing.fill(0);
		processing.rectMode(PConstants.CORNERS);
		processing.text(currentText, topLeft.x + TEXT_MARGIN, topLeft.y + TEXT_MARGIN, bottomRight.x - TEXT_MARGIN, bottomRight.y - TEXT_MARGIN);
		processing.rectMode(PConstants.CORNER);
	}
	
	private void updateCurrentText() {
		int next = 0;
		next = (int) (textTimer.getRemainingRatio() * text.length());
		currentText = text.substring(0, next);
	}
	
	public boolean isFinished() {
		return currentText == text;
	}
	
}
