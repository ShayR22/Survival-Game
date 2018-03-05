package Game_Objects;

import InterfacesAndTech.Sounds;
import InterfacesAndTech.Vector2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Game_BeamBall_Fx extends Game_Entity {

	protected final int SPEED_CONST = 6;
	protected Sounds beamSound = new Sounds("Photon", false, 30.0f);

	protected boolean isDead = false;

	protected Vector2D relative;
	protected static int radius = 8;

	protected Circle me;

	protected static myRelativeSpeed gameSpeed = myRelativeSpeed.MEDIUM;
	protected double gameSpeedConst = gameSpeed.getSpeed();

	public Game_BeamBall_Fx(double x, double y, double dx, double dy, Color c) {
		super(x, y, dx, dy, c);

		if (dx != 0 || dy != 0) {
			Vector2D Relative = new Vector2D(dx, dy);
			Relative.normalize();
			this.relative = Relative;
		} else {
			// create random direction
			int rand = (int) (Math.random() * 2);
			int direction;
			if (rand == 0) {
				direction = 1;
			} else {
				direction = -1;
			}

			double randpow2 = Math.random() * 0.5;

			this.relative = new Vector2D(SPEED_CONST * gameSpeedConst * randpow2 * direction,
					SPEED_CONST * gameSpeedConst * randpow2);
		}

		this.dx = this.relative.getX() * SPEED_CONST * gameSpeedConst;
		this.dy = this.relative.getY() * SPEED_CONST * gameSpeedConst;

		this.originalSpeedX = this.relative.getX() * SPEED_CONST;
		this.originalSpeedY = this.relative.getY() * SPEED_CONST;

		me = new Circle(radius);
		me.setFill(this.color);

		this.getChildren().add(me);

		beamSound.play();
	}

	public void advanceMySelf() {
		super.advanceMySelf();

		me.setTranslateX(x);
		me.setTranslateY(y);
	}

	public Circle returnMySelf() {
		return me;
	}

	@Override
	public boolean isDead() {
		if (this.y - Game_BeamBall_Fx.radius * 2 > PANE_HEIGHT) {
			return true;
		}
		return isDead;
	}

	// ------------------------- setters and getters ----------------------------

	public Vector2D getRelative() {
		return this.relative;
	}

	public void setRelative(Vector2D Relative) {
		this.relative = Relative;
	}

	public static int getRadius() {
		return radius;
	}

	public static void setRadius(int radius) {
		Game_BeamBall_Fx.radius = radius;
	}

	public static void setStaticGameSpeed(myRelativeSpeed enumSpeed) {
		gameSpeed = enumSpeed;
	}

	public void setIsDead(boolean isDead) {
		this.isDead = true;
	}

	// ----------------------------- setters and getters
	// -------------------------------
}
