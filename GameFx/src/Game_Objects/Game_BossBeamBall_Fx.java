package Game_Objects;

import InterfacesAndTech.IFinals;
import InterfacesAndTech.Vector2D;
import javafx.scene.paint.Color;

public class Game_BossBeamBall_Fx extends Game_BeamBall_Fx implements IFinals {

	private boolean homeingBeam;

	public Game_BossBeamBall_Fx(double x, double y, double dx, double dy, Color c, boolean homingBeam) {
		super(x, y, dx, dy, c);
		this.homeingBeam = homingBeam;

		if (this.homeingBeam) {
			me.setFill(BOSS_HOMEING_BEAM_COLOR);
		}
	}

	public void advanceMySelf(double x, double y) {
		if (homeingBeam) {
			Vector2D relativePos = new Vector2D(x - this.x, Math.abs(y - this.y));
			relativePos.normalize();
			setDx(relativePos.getX() * BOSS_HOMEING_BEAM_SPEED * gameSpeed.getSpeed());
			setDy(relativePos.getY() * BOSS_HOMEING_BEAM_SPEED * gameSpeed.getSpeed() +  BOSS_HOMEING_BEAM_Y_THROW);

		}

		if (this.x < Math.abs(dx)) { // if |dx| will set x into a negative don't allow it
			setDx(Math.abs(getDx()));
		}
		if (this.x > PANE_WIDTH - 2 * radius) {
			setDx(-Math.abs(getDx()));
		}

		super.advanceMySelf();

		this.me.setTranslateX(this.x);
		this.me.setTranslateY(this.y);
	}
}
