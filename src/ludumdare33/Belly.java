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
	private PVector ANCHOR_POINT;
	private PVector LEFT_POINT;
	private PVector RIGHT_POINT;

	// Belly state
	private enum BellyState {
		Sleep, Grabbed, Released
	};

	private BellyState bellyState;
	private Timer releaseTimer;
	private PVector MID_ANCHOR;
	private final int SPEED_RELEASE_FACTOR = 5000; // Time factor: the higher, the
												// longer the belly moves
	private final float SPEED_BELLY_FACTOR = 0.001f; // The speed of the belly's
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

	public Belly(PApplet _processing) {
		releaseTimer = new Timer(_processing);
		dropOrigin = new PVector();
		factorAnchorPoint = new PVector();
		processing = _processing;
		init();
	}

	/*private float computeCurve() {
		return (float) (Math.sin((float) releaseTimer.getRemainingTime() / 100f));
	}

	private float computeAnchorPoint(float _factorAnchorPoint, float refAnchorPoint, float _toSin, int _remainingTime,
			float _dropOrigin) {
		//return (float) (_factorAnchorPoint * (refAnchorPoint
		//		+ Math.abs(_dropOrigin - refAnchorPoint) * SPEED_BELLY_FACTOR * _remainingTime * Math.sin(_toSin)));
		return (float) (_factorAnchorPoint * (refAnchorPoint
				+ Math.abs(_dropOrigin - refAnchorPoint) * SPEED_BELLY_FACTOR * _remainingTime * Math.sin(_toSin)));
	}*/

	@Override
	public void update() {
		switch (bellyState) {
		case Released:
			float remainingTimeRatio = releaseTimer.getRemainingRatio();
			/*** ######################################################################### ***/
			
			anchorPoint.y = ANCHOR_POINT.y + (float) (dropOrigin.y * (1f - remainingTimeRatio) * Math.sin(45 * (1f - remainingTimeRatio)));
			System.out.println(remainingTimeRatio);
			/*** ######################################################################### ***/
			//float toSin = computeCurve();
			//anchorPoint.y = computeAnchorPoint(factorAnchorPoint.y, ANCHOR_POINT.y, toSin, remainingTime, dropOrigin.y);
			// anchorPoint.x = computeAnchorPoint(factorAnchorPoint.x,
			// ANCHOR_POINT.x, toSin, remainingTime, dropOrigin.x);
			//TODO: debug this shit
			if (anchorPoint.y == ANCHOR_POINT.y && anchorPoint.x == ANCHOR_POINT.x)
				bellyState = BellyState.Sleep;
			break;
		case Grabbed:
			float distance = PApplet.dist(processing.mouseX, processing.mouseY, ANCHOR_POINT.x, ANCHOR_POINT.y);
			if (distance < MAX_DISTANCE) {
				anchorPoint.x = processing.mouseX;
				anchorPoint.y = processing.mouseY;
			} else {
				float cosinus = (processing.mouseX - ANCHOR_POINT.x) / distance;
				float sinus = (processing.mouseY - ANCHOR_POINT.y) / distance;
				anchorPoint.x = ANCHOR_POINT.x + MAX_DISTANCE * cosinus;
				anchorPoint.y = ANCHOR_POINT.y + MAX_DISTANCE * sinus;
			}
			if (anchorPoint.y > ANCHOR_POINT.y) {
				anchorPoint.y = ANCHOR_POINT.y;
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void display() {
		processing.fill(50);
		processing.noStroke();
		processing.beginShape();
		processing.vertex(LEFT_POINT.x, LEFT_POINT.y);
		processing.bezierVertex(leftPoint.x, leftPoint.y, MID_ANCHOR.x, MID_ANCHOR.y, anchorPoint.x, anchorPoint.y);
		processing.bezierVertex(anchorPoint.x, anchorPoint.y, processing.width - MID_ANCHOR.x, MID_ANCHOR.y,
				rightPoint.x, rightPoint.y);
		processing.vertex(processing.width, processing.height);
		processing.vertex(0, processing.height);
		processing.endShape();
	}

	@Override
	public void init() {
		LEFT_POINT = new PVector(0, (int) processing.height * 5f / 6f);
		RIGHT_POINT = new PVector(processing.width, (int) processing.height * 5f / 6f);
		ANCHOR_POINT = new PVector(RIGHT_POINT.x / 2, RIGHT_POINT.y);
		MID_ANCHOR = new PVector(processing.width / 4, RIGHT_POINT.y);

		leftPoint = new PVector(LEFT_POINT.x, LEFT_POINT.y);
		rightPoint = new PVector(RIGHT_POINT.x, RIGHT_POINT.y);
		anchorPoint = new PVector(ANCHOR_POINT.x, ANCHOR_POINT.y);

		bellyState = BellyState.Sleep;
	}

	public void grabBelly() {
		bellyState = BellyState.Grabbed;
	}

	public void releaseBelly() {
		// Change state and initiate timers
		bellyState = BellyState.Released;
		int timer = SPEED_RELEASE_FACTOR;
		releaseTimer.reset(timer);
		int remainingTime = releaseTimer.getRemainingTime();
		// Sets the points of the curve
		dropOrigin.x = anchorPoint.x;
		dropOrigin.y = anchorPoint.y;
		// float toSin = computeCurve();
		// anchorPoint.y = computeAnchorPoint(factorAnchorPoint.y,
		// ANCHOR_POINT.y, toSin, remainingTime, dropOrigin.y);
		// anchorPoint.x = computeAnchorPoint(factorAnchorPoint.x,
		// ANCHOR_POINT.x, toSin, remainingTime, dropOrigin.x);
		if (anchorPoint.y - ANCHOR_POINT.y < 0 && dropOrigin.y - ANCHOR_POINT.y > 0)
			factorAnchorPoint.y = -1;
		else
			factorAnchorPoint.y = 1;
		if (anchorPoint.x - ANCHOR_POINT.x < 0 && dropOrigin.x - ANCHOR_POINT.x > 0)
			factorAnchorPoint.x = -1;
		else
			factorAnchorPoint.x = 1;
	}

	public PVector getAnchorPoint() {
		return anchorPoint;
	}

}
