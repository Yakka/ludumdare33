package ludumdare33;

import ddf.minim.AudioPlayer;
import processing.core.PApplet;
import processing.core.PVector;

public class Hair implements GameObject {

	private PApplet processing;

	// Sounds
	private AudioPlayer aoutchAudio;
	private AudioPlayer gniiiAudio;
	
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

	private float z = Belly.UNGRABBED_Z;
	// Hair state
	private final float MAX_DIST = 150f;
	private int health;
	private enum HairState {
		Released, Grabbed, Hurt, PulledOff, Dead
	};
	private HairState hairState;
	private Timer hairDeathTimer;
	private final int HAIR_DEATH_TIME = 1000;

	public Hair(PApplet _processing) {
		aoutchAudio = Main.minim.loadFile("ludumdare33/aoutch1.wav");
		gniiiAudio = Main.minim.loadFile("ludumdare33/gniii.wav");
		
		processing = _processing;

		currentHead = new PVector();
		currentFoot = new PVector();
		currentAnchor = new PVector();

		resetAngleTimer = new Timer(processing);
		hairDeathTimer = new Timer(processing);
	}

	@Override
	public void update() {
		float ratio;
		switch (hairState) {
		case Released:
			gniiiAudio.pause();
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
			float distance = PApplet.dist(processing.mouseX, processing.mouseY, currentFoot.x, currentFoot.y);

			if (distance >= 1f) {
				float newcos = (processing.mouseX - currentFoot.x) / distance;
				float newsin = (processing.mouseY - currentFoot.y) / distance;
				cosinus = PApplet.lerp(newcos, cosinus, 0.1f);
				sinus = PApplet.lerp(newsin, sinus, 0.1f);
			} else {
				cosinus = PApplet.lerp(cosinus, 0, 0.1f);
				sinus = PApplet.lerp(sinus, -1, 0.1f);;
			}
			currentHead.x = currentFoot.x + SIZE * cosinus;
			currentHead.y = currentFoot.y + SIZE * sinus;
			if(distance > MAX_DIST) {
				System.out.println(distance);
				health --;
				if(health > 0) {
					hairState = HairState.Hurt;
					resetAngleTimer.reset(RESET_ANGLE_TIME);
				} else {
					hairState = HairState.PulledOff;
					aoutchAudio.play();
					hairDeathTimer.reset(HAIR_DEATH_TIME);
				}
			}
			break;
		case Hurt:
			gniiiAudio.pause();
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
			gniiiAudio.pause();
			currentHead.x = currentFoot.x + SIZE * cosinus;
			currentHead.y = currentFoot.y + SIZE * sinus;
			currentAnchor.x = currentFoot.x + (currentHead.x - currentFoot.x) * 6f/5f;
			currentAnchor.y = currentFoot.y + (currentFoot.y - currentHead.y) * 4f/5f;
			break;
		case Dead:
			break;
		}
	}

	@Override
	public void display() {
		if(hairState == HairState.PulledOff) {
			float alpha = 255f * (1f - hairDeathTimer.getRemainingRatio());
			processing.fill(0, alpha);
			if(alpha == 0)
				hairState = HairState.Dead;
		}
		else
			processing.fill(0);
		if(hairState != HairState.Dead) {
			processing.noStroke();
			processing.beginShape();
			// hair
			processing.vertex(currentFoot.x - THICKNESS, currentFoot.y, z - 1);
			processing.vertex(currentHead.x - THICKNESS, currentHead.y, z -1);
			processing.vertex(currentHead.x + THICKNESS, currentHead.y, z -1);
			processing.vertex(currentFoot.x + THICKNESS, currentFoot.y, z -1);
			// root
			processing.vertex(currentFoot.x + 2 * THICKNESS, currentFoot.y + THICKNESS, z -1);
			processing.vertex(currentFoot.x + THICKNESS, currentFoot.y + 3 * THICKNESS, z -1);
			processing.vertex(currentFoot.x, currentFoot.y + 3 * THICKNESS, z -1);
			processing.vertex(currentFoot.x - 2 * THICKNESS, currentFoot.y + THICKNESS, z -1);
			processing.endShape();
		}
			
	}
	
	public boolean isHurt() {
		if(hairState == HairState.Hurt)
			return true;
		else
			return false;
	}
	
	public boolean isPulledOffOrDead() {
		if(hairState == HairState.PulledOff || hairState == HairState.Dead)
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
		health = 1;
		z = Belly.UNGRABBED_Z;
	}

	public void setCurrentFootX(float _x) {
		if(isPulledOffOrDead())
			currentFoot.x = processing.mouseX;
		else
			currentFoot.x = _x;
	}

	public void setCurrentFootY(float _y) {
		if(isPulledOffOrDead())
			currentFoot.y = processing.mouseY;
		else
			currentFoot.y = _y;
	}

	public void grabHair() {
	}
	
	public void resetZ() {
		z = Belly.UNGRABBED_Z;
	}

	public void releaseHair() {
		if(!isPulledOffOrDead()) {
			hairState = HairState.Released;
			resetAngleTimer.reset(RESET_ANGLE_TIME);
		}
	}

	public void justGrabHair() {
		if(!isPulledOffOrDead()) {
			z = Belly.GRABBED_Z;
			hairState = HairState.Grabbed;
			gniiiAudio.loop();
		}
	}

}
