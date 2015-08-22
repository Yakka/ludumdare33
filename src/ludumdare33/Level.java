package ludumdare33;

import processing.core.PApplet;
import processing.core.PVector;

public class Level implements GameObject{
	private PApplet processing;
	private Timer deltaTimeTimer;
	private int deltaTime;
	
	// ### BELLY LINE ###
	// Current values
	private PVector anchorPoint;
	private PVector leftPoint;
	private PVector rightPoint;
	// Initial values
	private PVector ANCHOR_POINT;
	private PVector LEFT_POINT;
	private PVector RIGHT_POINT;
	// Belly state
	private enum BellyState {Sleep, Grabbed, Released};
	private BellyState bellyState = BellyState.Sleep;
	private Timer releaseTimer;
	private final int SPEED_RELEASE_FACTOR = 2;
	
	
	public Level(PApplet _processing) {
		processing = _processing;
		deltaTimeTimer = new Timer(_processing);
		releaseTimer = new Timer(_processing);
		init();
	}

	@Override
	public void update() {
		deltaTime = deltaTimeTimer.getDelta();
		
		if(bellyState == BellyState.Released) {
			anchorPoint.y = (float) (ANCHOR_POINT.y + releaseTimer.getRemainingTime() * Math.sin((float)releaseTimer.getRemainingTime() / 100f));
			if(anchorPoint.y == ANCHOR_POINT.y)
				bellyState = BellyState.Sleep;
		}
	}

	@Override
	public void display() {
		processing.fill(50);
		processing.noStroke();
		processing.beginShape();
		processing.vertex(0, (int)processing.height*2f/3f);
		processing.bezierVertex(leftPoint.x, leftPoint.y, anchorPoint.x, anchorPoint.y, rightPoint.x, rightPoint.y);
		processing.vertex(processing.width, processing.height);
		processing.vertex(0, processing.height);
		processing.endShape();

	}

	@Override
	public void init() {
		LEFT_POINT = new PVector(0, (int)processing.height*2f/3f);
		RIGHT_POINT = new PVector(processing.width, (int)processing.height*2f/3f);
		ANCHOR_POINT = new PVector(RIGHT_POINT.x / 2, RIGHT_POINT.y);
		
		leftPoint = new PVector(LEFT_POINT.x, LEFT_POINT.y);
		rightPoint = new PVector(RIGHT_POINT.x, RIGHT_POINT.y);
		anchorPoint = new PVector(ANCHOR_POINT.x, ANCHOR_POINT.y);
		
		bellyState = BellyState.Sleep;
	}
	
	public void mouseDragged() {
		bellyState = BellyState.Grabbed;
		anchorPoint.x = processing.mouseX;
		anchorPoint.y = processing.mouseY;
	}
	
	public void mouseReleased(){
		bellyState = BellyState.Released;
		int timer = SPEED_RELEASE_FACTOR * (int) Math.abs(anchorPoint.y - ANCHOR_POINT.y);
		releaseTimer.reset(timer);
	}

}
