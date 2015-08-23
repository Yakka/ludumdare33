package ludumdare33;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Cursor implements GameObject {
	
	private PApplet processing;
	// Cursors
	private PImage[] cursors;
	private PVector position;
	private int index = 0;
	private final int SIZE = 16;
	private boolean blocked = false; 
	
	public Cursor(PApplet _processing) {
		processing = _processing;
		
		position = new PVector(0,0);
		
		cursors = new PImage[4];
		for(int i = 0; i < cursors.length; i++) {
			cursors[i] = processing.loadImage("ludumdare33/cursor"+(i+1)+".png");
		}
	}
	
	public void setBasicCursor() {
		index = 0;
	}
	
	public void setGrabbableCursor() {
		index = 1;
	}

	public void setGrabbingCursor() {
		index = 2;
	}
	
	@Override
	public void update() {
		if(!blocked) {
			position.x = processing.mouseX;
			position.y = processing.mouseY;
		}
			
	}

	public void setBlocked(boolean _blocked) {
		blocked = _blocked;
	}
	
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}
	
	@Override
	public void display() {
		processing.beginShape();
		processing.texture(cursors[index]);
		processing.vertex(position.x - SIZE / 2, position.y - SIZE / 2, 10, 0, 0);
		processing.vertex(position.x + SIZE / 2, position.y - SIZE / 2, 10, SIZE, 0);
		processing.vertex(position.x + SIZE / 2, position.y + SIZE / 2, 10, SIZE, SIZE);
		processing.vertex(position.x - SIZE / 2, position.y + SIZE / 2, 10, 0, SIZE);
		processing.endShape();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
