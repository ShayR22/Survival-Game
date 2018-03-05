package Game_Objects;

import Game_Perks.Game_SlowMotion_Perk_Fx;
import InterfacesAndTech.IFinals;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Game_Player_Fx extends Game_Entity implements IFinals {
	private static int radius = 20;

	private double dx = 0;
	private double jump;

	private Circle player;

	public Game_Player_Fx(double jump) {
		super(PANE_WIDTH / 2, PANE_HEIGHT - 2 * radius + 5, 0, 0, PLAYER_COLOR);
		this.jump = jump;

		this.player = new Circle(radius);
		this.player.setFill(this.color);
		this.player.setStroke(Color.LIGHTSKYBLUE);
		this.player.setStrokeWidth(3);

		this.getChildren().add(new StackPane(player));

		player.setTranslateX(x);
		player.setTranslateY(y);

	}

	public Circle returnMySelf() {
		return player;
	}

	public void advanceMySelf() {
		if (dx > 1) {
			if (x + radius * 2 + dx < PANE_WIDTH) {
				this.x += dx;
				player.setTranslateX(x);
			} else if (dx != 0) {
				this.dx = -21;
			}

		} else if (dx < -1) {
			if (x + dx > 0) {
				this.x += dx;
				player.setTranslateX(x);
			} else if (dx != 0) {
				this.dx = 21;
			}
		}

		if (jump < -JUMP_SIZE) {

			y += jump;
			jump += PLAYER_GRAVITY;

			// edge case to correct last irritation
			if (jump == -JUMP_SIZE) {
				y += jump;
			}
			player.setTranslateY(y);
		}
	}

	public void teleportMySelfDown() {
		this.y = PANE_HEIGHT - 2 * radius + 5;
		this.jump = -JUMP_SIZE;

		player.setTranslateY(y);
	}

	public void graduallySpeed(int speed) {

		if (speed > 0 && this.dx <= speed) {
			this.dx += 3;
		}
		if (speed < 0 && this.dx >= speed) {
			this.dx -= 3;
		}
		if (speed == 0) {
			if (this.dx > 0) {
				this.dx -= 3;
			} else if (this.dx < 0) {
				this.dx += 3;
			}
		}
	}

	// ------------------------- getters and setters ---------------------------

	public static int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		Game_Player_Fx.radius = radius;
	}

	public double getJump() {
		return jump;
	}

	public void setJump(double jump) {
		this.jump = jump;
	}

	// ------------------------- getters and setters ----------------------------

	public boolean collisionCheckwithBall(Game_Ball_Fx ball) {
		double ballX = ball.getX();
		double ballY = ball.getY();

		double deltaX = this.x - ballX;
		double deltaY = this.y - ballY;

		return pythagorasHelper(deltaX, deltaY, Game_Ball_Fx.getRadius());

	}

	public boolean collisionCheckWithBeam(Game_BeamBall_Fx b) {

		double bX = b.getX();
		double bY = b.getY();

		double deltaX = this.x - bX + Game_Player_Fx.radius;
		double deltaY = this.y - bY + Game_Player_Fx.radius;

		return pythagorasHelper(deltaX, deltaY, Game_BeamBall_Fx.getRadius());
	}

	public boolean collisionCheckWithStar(Game_Entity star) {

		double cX = star.getX();
		double cY = star.getY();

		double deltaX, deltaY;

		deltaX = this.x - cX;
		deltaY = this.y - cY - PLAYER_STAR_SHAPE_HEIGHT_OFFSET;

		return pythagorasHelper(deltaX, deltaY, STAR_WIDTH / 2);
	}

	public boolean collisionCheckWithPerk(Game_Entity perk) {

		double perkX = perk.getX();
		double perkY = perk.getY();

		double deltaX = this.x - perkX;
		double deltaY = this.y - perkY;

		return pythagorasHelper(deltaX, deltaY, Game_SlowMotion_Perk_Fx.getRadius());
	}

	private boolean pythagorasHelper(double deltaX, double deltaY, double otherRadius) {
		double pythagoras = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		double radiusSums = Game_Player_Fx.radius + otherRadius;

		if (pythagoras < radiusSums) {
			return true;
		}
		return false;

	}

	@Override
	public boolean isDead() {
		System.out.println("should be executed threw collision checkers");
		return false;
	}
}
