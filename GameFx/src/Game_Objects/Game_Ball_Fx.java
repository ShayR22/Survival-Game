package Game_Objects;

import java.util.ArrayList;

import InterfacesAndTech.IFinals;
import InterfacesAndTech.Vector2D;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Game_Ball_Fx extends Game_Entity implements IFinals {

	private ArrayList<Game_BeamBall_Fx> myBeams = new ArrayList<Game_BeamBall_Fx>();

	private static int RADIUS = BALL_RADIUS;

	private int life = (int) (Math.random() * 5 + 3);

	private final int EYE_BALL_SOCKET_RADIUS = RADIUS - 4;
	private final int EYE_BALL_SIZE_RADIUS = EYE_BALL_SOCKET_RADIUS / 2;

	private Circle eyeOuter;
	private Circle eyeSocket;
	private Circle eye;

	private static myRelativeSpeed gameSpeed = myRelativeSpeed.MEDIUM;
	private double gameSpeedConst = gameSpeed.getSpeed();

	public Game_Ball_Fx() {
		this(0, 0, 2, 2, Color.LIGHTGREY);
	}

	public Game_Ball_Fx(double x, double y, double dx, double dy, Color c) {
		super(x, y, dx, dy, c);

		this.dx = dx * gameSpeedConst;
		this.dy = dy * gameSpeedConst;

		eyeOuter = new Circle(RADIUS);
		eyeOuter.setFill(this.color);

		eyeSocket = new Circle(EYE_BALL_SOCKET_RADIUS);
		eyeSocket.setFill(Color.WHITE);

		eye = new Circle(EYE_BALL_SIZE_RADIUS);
		eye.setFill(Color.BLACK);

		this.getChildren().add(new StackPane(eyeOuter, eyeSocket, eye));
	}

	public void advanceMySelf(Vector2D playerPosition) {
		if (x < Math.abs(dx)) { // if |dx| will set x into a negative don't allow it
			setDx(Math.abs(getDx()));
			this.originalSpeedX *= -1;
		}
		if (x > PANE_WIDTH - 2 * RADIUS) {
			setDx(-Math.abs(getDx()));
			this.originalSpeedX *= -1;
		}
		if (y < RADIUS) {
			setDy(Math.abs(getDy()));
		}
		if (y > PANE_HEIGHT - 2 * RADIUS && life > 0) {
			setDy(-Math.abs(getDy()) + 2);
			setLife(getLife() - 1);
		}

		x = x + dx;
		y = y + dy;

		physicsVectorHelper(playerPosition);
	}

	private void physicsVectorHelper(Vector2D playerPosition) {

		// create eyeposition vector
		Vector2D eyePosition = new Vector2D(this.x + 4, this.y + 4);

		// create relative vector position
		Vector2D relativePosition = playerPosition.sub(eyePosition);
		// normalize it
		relativePosition.normalize();

		Vector2D beamBallVector = relativePosition;

		// get distances between the eye socket and the eye itself
		double RelativeRadiusDistance = EYE_BALL_SOCKET_RADIUS - EYE_BALL_SIZE_RADIUS;

		relativePosition.multiply(RelativeRadiusDistance);
		relativePosition = relativePosition.add(eyePosition);

		setMyCircles(relativePosition);

		game_BeamBall_Fx_Generator(relativePosition, beamBallVector);

	}

	private void game_BeamBall_Fx_Generator(Vector2D relativePosition, Vector2D beamBallVector) {
		int rand = (int) (Math.random() * 100);
		if (0 == rand && (this.y < 500 || (this.y < 675 && this.y > 575))) {
			myBeams.add(new Game_BeamBall_Fx(relativePosition.getX(), relativePosition.getY(), beamBallVector.getX(),
						beamBallVector.getY(), BEAM_BALL_COLOR));
		}
	}

	private void setMyCircles(Vector2D relativePosition) {

		eyeOuter.setTranslateX(x);
		eyeOuter.setTranslateY(y);

		eyeSocket.setTranslateX(x);
		eyeSocket.setTranslateY(y);

		eye.setTranslateX(relativePosition.getX() - EYE_BALL_SIZE_RADIUS / 2);
		eye.setTranslateY(relativePosition.getY() - EYE_BALL_SIZE_RADIUS / 2);

	}

	@Override
	public boolean isDead() {
		if (this.life <= 0 && this.y - Game_Ball_Fx.RADIUS * 2 > PANE_HEIGHT) {
			return true;
		}
		return false;
	}

	// ------------------------ getters and setters ends
	// --------------------------------

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public ArrayList<Game_BeamBall_Fx> getArrayOfGame_BeamBall_Fx() {
		return myBeams;
	}

	public void setArrayOfBeamBall(ArrayList<Game_BeamBall_Fx> arrayOfBeamBall) {
		myBeams = arrayOfBeamBall;
	}

	public static void ChangeRadius(int num) {
		Game_Ball_Fx.RADIUS = num;
	}

	public static int getRadius() {
		return RADIUS;
	}

	public static void setStaticGameSpeed(myRelativeSpeed enumSpeed) {
		gameSpeed = enumSpeed;
	}

	// ------------------------ getters and setters ends
	// --------------------------------
}
