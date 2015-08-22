package ludumdare33;

import processing.core.PApplet;
import processing.core.PVector;

public class Hair implements GameObject {

	private PApplet processing;

	// Hair position
	private PVector currentHead;
	private PVector currentFoot;
	private PVector currentAnchor;
	private final float SIZE = 50f;
	private final float THICKNESS = 3;

	// Hair angle
	private Timer resetAngleTimer;
	private final int RESET_ANGLE_TIME = 1000;
	float cosinus = 0;
	float sinus = 0;

	// Hair state
	private final float MAX_DIST = 50f;
	private int health;
	private enum HairState {
		Released, Grabbed, Hurt, PulledOff
	};

	private HairState hairState;

	public Hair(PApplet _processing) {
		processing = _processing;

		currentHead = new PVector();
		currentFoot = new PVector();
		currentAnchor = new PVector();

		resetAngleTimer = new Timer(processing);
	}

	@Override
	public void update() {
		float ratio;
		switch (hairState) {
		case Released:
			currentHead.x = currentFoot.x + SIZE * cosinus;
			currentHead.y = currentFoot.y + SIZE * sinus;
			currentAnchor.x = currentFoot.x + (currentHead.x - currentFoot.x) * 6f/5f;
			currentAnchor.y = currentFoot.y + (currentFoot.y - currentHead.y) * 4f/5f;
			ratio = resetAngleTimer.getRemainingRatio();
			if (ratio >= 0.9f)
				ratio = 1;
			cosinus = PApplet.lerp(cosinus, 0, 1f - ratio);
			sinus = PApplet.lerp(sinus, -1, 1f - ratio);
			break;
		case Grabbed:
			setCurrentHead();
			if(PApplet.dist(currentHead.x, currentHead.y, processing.mouseX, processing.mouseY) > MAX_DIST) {
				health --;
				if(health > 0) {
					hairState = HairState.Hurt;
					resetAngleTimer.reset(RESET_ANGLE_TIME);
				} else
					hairState = HairState.PulledOff;
			}
			break;
		case Hurt:
			currentHead.x = currentFoot.x + SIZE * cosinus;
			currentHead.y = currentFoot.y + SIZE * sinus;
			currentAnchor.x = currentFoot.x + (currentHead.x - currentFoot.x) * 6f/5f;
			currentAnchor.y = currentFoot.y + (currentFoot.y - currentHead.y) * 4f/5f;
			ratio = resetAngleTimer.getRemainingRatio();
			if (ratio >= 0.9f)
				ratio = 1;
			cosinus = PApplet.lerp(cosinus, 0, 1f - ratio);
			sinus = PApplet.lerp(sinus, -1, 1f - ratio);
			break;
		case PulledOff:
			currentHead.x = currentFoot.x + SIZE * cosinus;
			currentHead.y = currentFoot.y + SIZE * sinus;
			currentAnchor.x = currentFoot.x + (currentHead.x - currentFoot.x) * 6f/5f;
			currentAnchor.y = currentFoot.y + (currentFoot.y - currentHead.y) * 4f/5f;
			break;
		}
	}

	@Override
	public void display() {
		if(hairState != HairState.PulledOff) {
			processing.fill(0);
			processing.noStroke();
			processing.beginShape();
			processing.vertex(currentFoot.x, currentFoot.y, -1);
			processing.bezierVertex(currentFoot.x, currentFoot.y, -1, 
					currentAnchor.x - THICKNESS, currentAnchor.y, -1, 
					currentHead.x - THICKNESS, currentHead.y, -1);
			processing.bezierVertex(currentHead.x + THICKNESS, currentHead.y, -1, 
					currentAnchor.x + THICKNESS, currentAnchor.y, -1, 
					currentFoot.x, currentFoot.y, -1);
			processing.endShape();
		}
	}
	
	public boolean isHurt() {
		if(hairState == HairState.Hurt)
			return true;
		else
			return false;
	}

	@Override
	public void init() {
		currentHead.x = currentFoot.x;
		currentHead.y = currentFoot.y - SIZE;
		cosinus = 0;
		sinus = -1;
		hairState = HairState.Released;
		health = 3;
	}

	public void setCurrentFootX(float _x) {
		if(hairState == HairState.PulledOff)
			currentFoot.x = processing.mouseX;
		else
			currentFoot.x = _x;
	}

	public void setCurrentFootY(float _y) {
		if(hairState == HairState.PulledOff)
			currentFoot.y = processing.mouseY;
		else
			currentFoot.y = _y;
	}

	private void setCurrentHead() {
		float distance = PApplet.dist(processing.mouseX, processing.mouseY, currentFoot.x, currentFoot.y);

		if (distance != 0f) {
			cosinus = (processing.mouseX - currentFoot.x) / distance;
			sinus = (processing.mouseY - currentFoot.y) / distance;
		} else {
			cosinus = 0;
			sinus = -1;
		}
		currentHead.x = currentFoot.x + SIZE * cosinus;
		currentHead.y = currentFoot.y + SIZE * sinus;
	}

	public void grabHair() {
	}

	public void releaseHair() {
		if(hairState != HairState.PulledOff) {
			hairState = HairState.Released;
			resetAngleTimer.reset(RESET_ANGLE_TIME);
		}
	}

	public void justGrabHair() {
		hairState = HairState.Grabbed;
	}

}
