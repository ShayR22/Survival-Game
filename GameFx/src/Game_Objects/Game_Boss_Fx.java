package Game_Objects;

import java.util.ArrayList;

import BossBar.BossBar;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Game_Boss_Fx extends Game_Entity {

	private ArrayList<Game_BossBeamBall_Fx> myBeams = new ArrayList<Game_BossBeamBall_Fx>();
	private int life = BOSS_LIFE;

	private Rectangle bossRect = new Rectangle(BOSS_RADIUS, BOSS_RADIUS);
	private Rectangle bossRect2 = new Rectangle(BOSS_RADIUS, BOSS_RADIUS);

	private double rotateDiff = 45;

	private int beamCreateCounter;

	private Polygon bossPoly;

	private Double[] myPoints;
	private double rotate = 0;
	private double rotateRate = BOSS_ROTATE_SPEED;

	private BossBar myBar;

	public Game_Boss_Fx(double x, double y, double dx, double dy, Color c, BossBar myBar) {
		super(x, y, dx, dy, c);
		this.myBar = myBar;
		beamCreateCounter = 0;

		bossRect.setTranslateX(this.x);
		bossRect.setTranslateY(this.y);
		bossRect.setFill(this.color);

		bossRect2.setTranslateX(this.x);
		bossRect2.setTranslateY(this.y);
		bossRect2.setFill(this.color);
		bossRect2.setRotate(rotateDiff);

		Double[] pointsArray = { this.x + BOSS_RADIUS / 4, this.y + BOSS_RADIUS * 0.75, this.x + BOSS_RADIUS * 0.45,
				this.y + BOSS_RADIUS / 2, this.x + BOSS_RADIUS / 4, this.y + BOSS_RADIUS / 4,
				this.x + BOSS_RADIUS * 0.75, this.y + BOSS_RADIUS / 4, this.x + BOSS_RADIUS * 0.55,
				this.y + BOSS_RADIUS / 2, this.x + BOSS_RADIUS * 0.75, this.y + BOSS_RADIUS * 0.75 };

		this.myPoints = pointsArray;

		bossPoly = new Polygon();
		bossPoly.getPoints().addAll(this.myPoints);
		bossPoly.setFill(Color.WHITE);

		bossPoly.setTranslateX(this.x);
		bossPoly.setTranslateY(this.y);

		this.getChildren().add(new StackPane(bossRect, bossRect2, bossPoly));
	}

	public boolean isDead() {
		if (this.life <= 0) {
			return true;
		}
		return false;
	}

	@Override
	public void advanceMySelf() {
		if (x < Math.abs(dx)) { // if |dx| will set x into a negative don't allow it
			setDx(Math.abs(getDx() - BOSS_X_SPEED_INCREMENT));
			changeRotateSpeed();
			setLife(this.life - 1);
			this.myBar.setProgress(this.life * 1.0 / BOSS_LIFE);
		}
		// 1.1 cuz its not an actuall circle (will need to be changed if initiall pos
		// will be changed )
		if (x > PANE_WIDTH - 1.1 * BOSS_RADIUS) {
			setDx(-Math.abs(getDx() + BOSS_X_SPEED_INCREMENT));
			changeRotateSpeed();
			setLife(this.life - 1);
			this.myBar.setProgress(this.life * 1.0 / BOSS_LIFE);
			System.out.println(life);
		}
		if (y < BOSS_RADIUS) {
			setDy(Math.abs(getDy()));
		}
		if (y > PANE_HEIGHT - 2 * BOSS_RADIUS && life > 0) {
			setDy(-Math.abs(getDy()) + 2);
			setLife(this.life - 1);
		}

		x = x + dx;
		y = y + dy;

		bossRect.setTranslateX(x);
		bossRect.setTranslateY(y);

		bossRect2.setTranslateX(x);
		bossRect2.setTranslateY(y);

		bossPoly.setTranslateX(x);
		bossPoly.setTranslateY(y);

		rotateMySelf();
		randomBeamGenerator();

	}

	private void rotateMySelf() {
		rotate += rotateRate;
		bossRect.setRotate(rotate);
		bossRect2.setRotate(rotate + rotateDiff);
	}

	private void randomBeamGenerator() {
		int rand = (int) (Math.random() * (60 + 2 * this.life));
		if (0 == rand) {
			myBeams.add(
					new Game_BossBeamBall_Fx(x + BOSS_RADIUS / 2, y + BOSS_RADIUS, 0, 3, BOSS_BEAM_BALL_COLOR, true));
		}

		if (beamCreateCounter % (40 + 2 * this.life) == 0 + (2 * this.life / 5)) {
			myBeams.add(new Game_BossBeamBall_Fx(x, y + BOSS_RADIUS, -4, 3, BOSS_BEAM_BALL_COLOR, false));
		} else if (beamCreateCounter % (40 + 2 * this.life) == 8 + (2 * this.life / 5)) {
			myBeams.add(
					new Game_BossBeamBall_Fx(x + BOSS_RADIUS / 4, y + BOSS_RADIUS, -2, 3, BOSS_BEAM_BALL_COLOR, false));
		} else if (beamCreateCounter % (40 + 2 * this.life) == 16 + (2 * this.life / 5)) {
			myBeams.add(
					new Game_BossBeamBall_Fx(x + BOSS_RADIUS / 2, y + BOSS_RADIUS, 0, 3, BOSS_BEAM_BALL_COLOR, false));
		} else if (beamCreateCounter % (40 + 2 * this.life) == 24 + (2 * this.life / 5)) {
			myBeams.add(new Game_BossBeamBall_Fx(x + BOSS_RADIUS * 3 / 4, y + BOSS_RADIUS, 2, 3, BOSS_BEAM_BALL_COLOR,
					false));
		} else if (beamCreateCounter % (40 + 2 * this.life) == 32 + (2 * this.life / 5)) {
			myBeams.add(new Game_BossBeamBall_Fx(x + BOSS_RADIUS, y + BOSS_RADIUS, 4, 3, BOSS_BEAM_BALL_COLOR, false));
		}

		beamCreateCounter++;

	}

	private void changeRotateSpeed() {
		rotateRate *= -1;
	}

	public ArrayList<Game_BossBeamBall_Fx> getArrayOfGame_BossBeamBall_Fx() {
		return myBeams;
	}

	public void setLife(int life) {
		this.life = life;
	}
}