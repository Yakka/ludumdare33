package ludumdare33;

import ddf.minim.AudioPlayer;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Narrative extends Panel {

	// Sounds
	private AudioPlayer blblAudio;
	
	// Dialogue
	private String text = "";
	private String currentText = "";
	private final int TEXT_MARGIN = 20 + MARGIN;
	
	private Timer textTimer;
	private final int MILLIS_PER_CHAR = 100; //100
	
	// Monster
	private int animationIndex;
	private PVector position;
	private final int SIZE = 128;
	private int lastChar = 0;
	
	public Narrative(PApplet _processing, PVector _topLeft, PVector _bottomRight, int[] _backgroundColor, String _text, int _animationIndex) {
		super(_processing, _topLeft, _bottomRight, _backgroundColor);
		blblAudio = Main.minim.loadFile("ludumdare33/gniii.wav");
		
		text = _text;
		animationIndex = _animationIndex;
		textTimer = new Timer(processing);
		position = new PVector((_bottomRight.x - _topLeft.x) / 2 + _topLeft.x - SIZE / 2, _bottomRight.y - SIZE);
		init();
	}

	@Override
	public void init() {
		super.init();
		currentText = "";
		lastChar = 0;
	}
	
	@Override
	public void update() {
		super.update();
		if(started) {
			updateCurrentText();
			if(lastChar != currentText.length()) {
				position.y += 2 * processing.random(-1f, 1f);
				lastChar ++;
			}
			else
				blblAudio.pause();
		}
	}
	
	@Override
	public void display() {
		super.display();
		// Monster
		processing.noStroke();
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
		
		if(animationIndex == 1 && isFinished()) {
			processing.textFont(Main.textFont);
			processing.fill(0);
			processing.rectMode(PConstants.CORNERS);
			processing.text("The end", (bottomRight.x - topLeft.x) * 4f/5f + topLeft.x, bottomRight.y - (bottomRight.y - topLeft.y) * 1f/5f, bottomRight.x - TEXT_MARGIN, bottomRight.y - TEXT_MARGIN);
			processing.rectMode(PConstants.CORNER);
		}
		
		// Background
		processing.fill(221, 234, 255);
		processing.noStroke();
		processing.beginShape();
		processing.vertex(topLeft.x, topLeft.y, - 2);
		processing.vertex(bottomRight.x, topLeft.y, - 2);
		processing.vertex(bottomRight.x, bottomRight.y, - 2);
		processing.vertex(topLeft.x, bottomRight.y, - 2);
		processing.endShape();
	}
	
	@Override
	public void mousePressed() {
		if(PApplet.dist(processing.mouseX, processing.mouseY, position.x + SIZE /2, position.y + SIZE /2) < SIZE && !started)
			start();
	}
	
	public void start() {
		super.start();
		blblAudio.loop();
		System.out.println(blblAudio.isLooping());
		textTimer.reset(MILLIS_PER_CHAR * text.length());
	}
	
	private void updateCurrentText() {
		int next = 0;
		next = (int) (textTimer.getRemainingRatio() * text.length());
		currentText = text.substring(0, next);
	}
	
	@Override
	public boolean isFinished() {
		return currentText == text && started;
	}
	
}
