package Game_Perks;

import Game_Objects.Game_Entity;
import InterfacesAndTech.IFinals;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class Game_SlowMotion_Perk_Fx extends Game_Entity implements IFinals {

	private static double RADIUS = SLOW_MOTION_PERK_RADIUS;

	private Label slowText = new Label("Slow");
	private Circle slowCircle;
	private boolean taken = false;

	public Game_SlowMotion_Perk_Fx(double x) {
		super(x, 0, 0, 2.5, Color.BLACK);

		slowText.setTextFill(SLOW_MOTION_PERK_COLOR);
		slowText.setFont(Font.font(20));
		slowText.setTranslateX(this.x);
		slowText.setTranslateY(this.y);

		slowCircle = new Circle(RADIUS);
		slowCircle.setFill(Color.BLACK);
		slowCircle.setTranslateX(this.x);
		slowCircle.setTranslateY(this.y);
		slowCircle.setStroke(Color.WHITE);
		slowCircle.setStrokeWidth(3);

		this.getChildren().add(new StackPane(slowCircle, slowText));
	}

	@Override
	public void advanceMySelf() {
		super.advanceMySelf();

		slowCircle.setTranslateY(y);
		slowText.setTranslateY(this.y);
	}

	@Override
	public boolean isDead() {
		if (this.y > PANE_HEIGHT) {
			return true;
		}
		return taken;
	}

	public static double getRadius() {
		return RADIUS;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}
}
