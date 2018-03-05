package Game_Objects;

import Game_Perks.Game_SB_Boom_Perk_Fx;
import Game_Perks.Game_SlowMotion_Perk_Fx;
import InterfacesAndTech.IFinals;
import javafx.scene.paint.Color;

public class EntityFactory implements IFinals {

	public EntityFactory() {
		// TODO not sure if constructor is needed
	}

	public Game_Ball_Fx createBall() {
		// side will random if ball will come from the left or right side
		int side = (int) (Math.random() * 2);

		int direction;
		double xPosition;
		if (side == 0) {
			xPosition = Game_Ball_Fx.getRadius();
			direction = 1;
		} else {
			xPosition = PANE_WIDTH - Game_Ball_Fx.getRadius();
			direction = -1;
		}

		double yPosition = Math.random() * 50;

		double dx = direction * Math.random() * BALL_X_SPEED_FACTOR;
		double dy = Math.random() * BALL_Y_SPEED_FACTOR;
		Color randColor = BALL_COLOR_ARRAY[(int) (Math.random() * BALL_COLOR_ARRAY_LENGTH)];

		return new Game_Ball_Fx(xPosition, yPosition, dx, dy, randColor);
	}

	public Game_Star_Fx createStar() {
		Game_Star_Fx star;
		double randDx = Math.random() * STAR_X_SPEED_FACTOR;
		double randDy = Math.random() * STAR_Y_SPEED_FACTOR + 1;
		double randYPos = Math.random() * STAR_Y_RANDOM_POS_COEFFICIENT;

		int sideGenerator = (int) (Math.random() * 2);
		if (sideGenerator == 0) {
			star = new Game_Star_Fx(0, randYPos, randDx, randDy, true);
		} else {
			star = new Game_Star_Fx(PANE_WIDTH - STAR_WIDTH, randYPos, randDx, randDy, false);
		}
		return star;
	}

	public Game_SlowMotion_Perk_Fx createSlowMotionPerk() {
		// will make sure that xPosition is within panel width
		double randXPos = Math.random() * (PANE_WIDTH - 2 * SLOW_MOTION_PERK_RADIUS) + SLOW_MOTION_PERK_RADIUS;
		return new Game_SlowMotion_Perk_Fx(randXPos);
	}

	public Game_SB_Boom_Perk_Fx createSB_BoomPerk() {
		// will make sure that xPosition is within panel width
		double randXPos = Math.random() * (PANE_WIDTH - 2 * SLOW_MOTION_PERK_RADIUS) + SLOW_MOTION_PERK_RADIUS;
		return new Game_SB_Boom_Perk_Fx(randXPos);
	}
}
