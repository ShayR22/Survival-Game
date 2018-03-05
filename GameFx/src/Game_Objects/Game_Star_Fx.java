package Game_Objects;

import InterfacesAndTech.IFinals;
import InterfacesAndTech.Sounds;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Game_Star_Fx extends Game_Entity implements IFinals {

	private Sounds starSound;

	private Polygon poly;
	private Circle circle;
	private Double[] myPoints;

	private double rotate = 0;
	private double rotateRate = 11.5;

	private static myRelativeSpeed gameSpeed = myRelativeSpeed.MEDIUM;
	private double gameSpeedConst = gameSpeed.getSpeed();

	private boolean leftmovementSpeed;

	public Game_Star_Fx(double posX, double posY, double dx, double dy, boolean leftmovement) {
		super(posX, posY, dx, dy, Color.BLACK);

		starSound = new Sounds("star", false, 30.0f);
		starSound.play();

		this.dx = dx * gameSpeedConst;

		this.dy = dy * gameSpeedConst;

		this.leftmovementSpeed = leftmovement;
		if (!this.leftmovementSpeed) {
			this.dx *= -1;
			changeRotateDirection();
		}

		this.setTranslateX(this.x);
		this.setTranslateY(this.y);

		this.circle = new Circle(3);
		this.circle.setFill(Color.BLACK);

		Double[] pointsArray = { this.x, this.y, // left sharp
				this.x + 20, this.y + 5, this.x + 25, this.y + 25, // top sharp
				this.x + 30, this.y + 5, this.x + 50, this.y, // right sharp
				this.x + 30, this.y - 5, this.x + 25, this.y - 25, // bot sharp
				this.x + 20, this.y - 5 };

		this.myPoints = pointsArray;

		poly = new Polygon();
		poly.getPoints().addAll(this.myPoints);

		poly.setFill(Color.WHITE);
		poly.setStroke(Color.BLACK);
		poly.setStrokeWidth(1);

		this.getChildren().add(new StackPane(poly, circle));

	}

	public boolean isDead() {
		if (this.y + STAR_HEIGHT / 2 >= PANE_HEIGHT) {
			return true;
		}
		return false;
	}

	public void advanceMySelf() {
		if (this.x + STAR_WIDTH > PANE_WIDTH || this.x < 0) {
			// swap direction
			this.leftmovementSpeed = !this.leftmovementSpeed;
			this.dx *= -1;
			changeRotateDirection();
		}

		this.x += dx;
		this.setTranslateX(this.x);

		this.y += this.dy;
		this.setTranslateY(this.y);

		rotate();

	}

	private void rotate() {
		rotate += rotateRate;
		poly.setRotate(rotate);
	}

	private void changeRotateDirection() {
		rotateRate *= -1;
	}

	public Polygon returnMySelf() {
		return poly;
	}

	// ---------------------- setters and getters ---------------------

	public double getOriginalXSpeed() {
		if (leftmovementSpeed) {
			return originalSpeedX;
		} else {
			return -1 * originalSpeedX;
		}
	}

	public double getOriginalStarterSpeedY() {
		return originalSpeedY;
	}

	public static void setStaticGameSpeed(myRelativeSpeed enumSpeed) {
		gameSpeed = enumSpeed;
	}

	// ---------------------- setters and getters ---------------------
}
