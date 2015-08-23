package ludumdare33;

import processing.core.PApplet;
import processing.core.PVector;

public class Belly implements GameObject {
	private PApplet processing;
	// Current values
	private PVector anchorPoint;
	private PVector leftPoint;
	private PVector rightPoint;
	// Initial values
	public PVector ANCHOR_POINT;
	private PVector LEFT_POINT;
	private PVector RIGHT_POINT;
	private PVector BOTTOM_RIGHT;

	// Z buffer
	private float z = UNGRABBED_Z;
	public static final float GRABBED_Z = -3f;
	public static final float UNGRABBED_Z = 0f;
	
	// Cursor
	private Cursor cursor;
	
	// Belly state
	private enum BellyState {
		Sleep, Grabbed, Released
	};

	private BellyState bellyState;
	private Timer releaseTimer;
	private final int SPEED_RELEASE_FACTOR = 3000; // Time factor: the higher,
													// the
													// longer the belly moves
	private final float SPEED_BELLY_FACTOR = 0.4f; // The speed of the belly's
													// rebound
	/**
	 * Correct the direction of the belly after the first release
	 */
	private PVector factorAnchorPoint;

	/**
	 * The position where the belly is dropped
	 */
	private PVector dropOrigin;
	private final float MAX_DISTANCE = 300f; // The maximum distance when
												// grabbed

	private Hair hair;
	
	public Belly(PApplet _processing, Hair _hair, Cursor _cursor, PVector _leftBorder, PVector _anchorPoint, PVector _rightBorder, PVector _bottomRight) {
		releaseTimer = new Timer(_processing);
		dropOrigin = new PVector();
		factorAnchorPoint = new PVector();
		processing = _processing;
		hair = _hair;
		cursor = _cursor;
		
		LEFT_POINT = new PVector(_leftBorder.x, _leftBorder.y);
		RIGHT_POINT = new PVector(_rightBorder.x, _rightBorder.y);
		ANCHOR_POINT = new PVector(_anchorPoint.x, _anchorPoint.y);
		BOTTOM_RIGHT = new PVector(_bottomRight.x, _bottomRight.y);
		
		init();
	}

	@Override
	public void update() {
		switch (bellyState) {
		case Released:
			cursor.setBlocked(false);
			float remainingTimeRatio = releaseTimer.getRemainingRatio();

			anchorPoint.y = ANCHOR_POINT.y + (float) ((processing.height - dropOrigin.y) * (1f - remainingTimeRatio)
					* Math.sin(SPEED_BELLY_FACTOR * 90 * (1f - remainingTimeRatio)));
			
			anchorPoint.x = PApplet.lerp(anchorPoint.x, ANCHOR_POINT.x, remainingTimeRatio);
			
			// When the timer is finished, we reinitialize the position:
			if (remainingTimeRatio >= 1f) {
				anchorPoint.y = ANCHOR_POINT.y;
				anchorPoint.x = ANCHOR_POINT.x;
			}

			if (anchorPoint.y == ANCHOR_POINT.y && anchorPoint.x == ANCHOR_POINT.x)
				bellyState = BellyState.Sleep;
			break;
		case Grabbed:
			float distance = PApplet.dist(processing.mouseX, processing.mouseY, ANCHOR_POINT.x, ANCHOR_POINT.y);
			if (distance < MAX_DISTANCE) {
				anchorPoint.x = processing.mouseX;
				anchorPoint.y = processing.mouseY;
				
				cursor.setBlocked(false);
			} else { // too far for the belly
				float cosinus = (processing.mouseX - ANCHOR_POINT.x) / distance;
				float sinus = (processing.mouseY - ANCHOR_POINT.y) / distance;
				anchorPoint.x = ANCHOR_POINT.x + MAX_DISTANCE * cosinus;
				anchorPoint.y = ANCHOR_POINT.y + MAX_DISTANCE * sinus;
				
				cursor.setBlocked(true);
				cursor.setPosition(anchorPoint.x, anchorPoint.y);
			}
			if (anchorPoint.y > ANCHOR_POINT.y) {
				anchorPoint.y = ANCHOR_POINT.y;
			}
			break;
		case Sleep:
			z = UNGRABBED_Z;
			hair.resetZ();
			break;
		default:
			break;
		}
	}

	@Override
	public void display() {
		processing.fill(0, 127, 127);
		processing.noStroke();
		processing.beginShape();
		processing.vertex(leftPoint.x, leftPoint.y, z);
		processing.bezierVertex(leftPoint.x, leftPoint.y, z,
				leftPoint.x + (anchorPoint.x - leftPoint.x) / 2, leftPoint.y, z,
				anchorPoint.x, anchorPoint.y, z);
		processing.bezierVertex(anchorPoint.x, anchorPoint.y, z, 
				rightPoint.x - (anchorPoint.x - leftPoint.x) / 2, leftPoint.y, z,
				rightPoint.x, rightPoint.y, z);
		processing.vertex(BOTTOM_RIGHT.x, BOTTOM_RIGHT.y, z);
		processing.vertex(leftPoint.x, BOTTOM_RIGHT.y, z);
		processing.endShape();
	}

	@Override
	public void init() {
		// TODO: il est possible de créer des formes différentes pour chacun
		leftPoint = new PVector(LEFT_POINT.x, LEFT_POINT.y);
		rightPoint = new PVector(RIGHT_POINT.x, RIGHT_POINT.y);
		anchorPoint = new PVector(ANCHOR_POINT.x, ANCHOR_POINT.y);
		z = UNGRABBED_Z;
		bellyState = BellyState.Sleep;
	}

	public void grabBelly() {
		hair.grabHair();
		if(hair.isHurt() || hair.isPulledOffOrDead()) {
			releaseBelly();
		}
	}

	public void releaseBelly() {
		hair.releaseHair();
		if(bellyState == BellyState.Grabbed) {
			// Change state and initiate timers
			bellyState = BellyState.Released;
			int timer = SPEED_RELEASE_FACTOR;
			releaseTimer.reset(timer);
			// Sets the points of the curve
			dropOrigin.x = anchorPoint.x;
			dropOrigin.y = anchorPoint.y;
			// Compute the direction of the belly. TODO: is this really useful? Currently not used.
			if (anchorPoint.y - ANCHOR_POINT.y < 0 && dropOrigin.y - ANCHOR_POINT.y > 0)
				factorAnchorPoint.y = -1;
			else
				factorAnchorPoint.y = 1;
			if (anchorPoint.x - ANCHOR_POINT.x < 0 && dropOrigin.x - ANCHOR_POINT.x > 0)
				factorAnchorPoint.x = -1;
			else
				factorAnchorPoint.x = 1;
		}
	}
	
	public boolean isSleeping() {
		return bellyState == BellyState.Sleep;
	}
	
	public boolean isFinished() {
		return hair.isPulledOffOrDead();
	}
	
	public boolean isGrabbable() {
		return PApplet.dist(processing.mouseX, processing.mouseY, ANCHOR_POINT.x, ANCHOR_POINT.y) < 100;
	}

	public PVector getAnchorPoint() {
		return anchorPoint;
	}

	public void justGrabBelly() {
		if(isGrabbable()) {
			bellyState = BellyState.Grabbed;
			z = GRABBED_Z;
			hair.justGrabHair();
		}
	}

}
