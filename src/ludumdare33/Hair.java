package ludumdare33;

import processing.core.PApplet;
import processing.core.PVector;

public class Hair implements GameObject {

	private PApplet processing;

	// Hair position
	private PVector currentHead;
	private PVector currentFoot;
	private final float SIZE = 50f;
	
	// Hair angle
	private Timer resetAngleTimer;
	private final int RESET_ANGLE_TIME = 1000;
	float cosinus = 0;
	float sinus = 0;
	
	// Hair state
	private enum HairState {Released, Grabbed, PulledOut};
	private HairState hairState;

	public Hair(PApplet _processing) {
		processing = _processing;

		currentHead = new PVector();
		currentFoot = new PVector();
		
		resetAngleTimer = new Timer(processing);
	}

	@Override
	public void update() {
		switch(hairState) {
		case Released:
			currentHead.x = currentFoot.x + SIZE * cosinus;
			currentHead.y = currentFoot.y + SIZE * sinus;
				float ratio = resetAngleTimer.getRemainingRatio();
				if(ratio >= 0.9f)
					ratio = 1;
				cosinus = PApplet.lerp(cosinus, 0, 1f - ratio);
				sinus = PApplet.lerp(sinus, -1, 1f - ratio);
			break;
		default:
			break;
		}
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		processing.fill(0);
		processing.stroke(0);
		processing.strokeWeight(10);
		processing.line(currentFoot.x, currentFoot.y, -1, currentHead.x, currentHead.y, -1);
	}

	@Override
	public void init() {
		currentHead.x = currentFoot.x;
		currentHead.y = currentFoot.y - SIZE;
		cosinus = 0;
		sinus = -1;
		hairState = HairState.Released;
	}

	public void setCurrentFootX(float _x) {
		currentFoot.x = _x;
	}

	public void setCurrentFootY(float _y) {
		currentFoot.y = _y + 10;
	}

	private void setCurrentHead() {
		float distance = PApplet.dist(processing.mouseX, processing.mouseY, currentFoot.x, currentFoot.y);
		
		if(distance != 0f) {
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
		hairState = HairState.Grabbed;
		setCurrentHead();
	}
	
	public void releaseHair() {
		hairState = HairState.Released;
		resetAngleTimer.reset(RESET_ANGLE_TIME);
	}

}
