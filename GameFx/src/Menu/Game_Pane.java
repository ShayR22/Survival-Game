package Menu;

import Game_Objects.Game_Ball_Fx;
import Game_Objects.Game_BeamBall_Fx;
import Game_Objects.Game_Entity;
import Game_Objects.Game_Player_Fx;
import Game_Objects.Game_Star_Fx;
import Game_Perks.Game_SB_Boom_Perk_Fx;
import Game_Perks.Game_SlowMotion_Perk_Fx;
import InterfacesAndTech.IFinals;
import InterfacesAndTech.IPaneObeservable;
import InterfacesAndTech.I_Level;
import InterfacesAndTech.Sounds;
import Levels.BasicLevel;
import Levels.BossLevel;
import Levels.StarLevel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import myDialog.MyDialog;

public class Game_Pane extends Pane implements IPaneObeservable ,IFinals {

	private Game_Player_Fx player;

	private I_Level currentLevel;

	// sound
	private Sounds deathSound = new Sounds("Death", false, 10.0f);

	// timers
	private Timeline movementTimer;

	private boolean allowedToJump = true;

	// end game announcer
	private String gameOverText = DEATH;
	private Text gameOver = new Text();
	private Font font = new Font("serif", 40);

	// level design numbers
	private int level = 9;

	private Image img = new Image("/Images/background.jpg", true);
	private ImageView image = new ImageView(img);

	private double score = 0; // will be calculate with time
	private double currentLevelScore = 0;

	private double jumpSize = JUMP_SIZE;
	private int playerSpeed = 0;

	public Game_Pane() {
		// TODO remove this nonsense , no yellow
		currentLevel = new BasicLevel(this.level, this);
		currentLevel.clear();

		this.getChildren().add(image);

		gameOver.setFont(font);
		gameOver.setFill(TEXT_COLOR);

		setPlayerMovementKeys();

		this.setPrefSize(PANE_WIDTH, PANE_HEIGHT);

		player = new Game_Player_Fx(8);
		this.getChildren().add(player);

		setMovementTimer();

	}

	private void setPlayerMovementKeys() {
		// keys pressed
		this.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case ENTER: {
				reset();
				break;
			}
			case P: {
				pauseGame();
				break;
			}
			case UP: {
				if (allowedToJump) {
					player.setJump(jumpSize);
					allowedToJump = false;
				}
				break;
			}
			case LEFT: {
				if (player.getX() + player.getDx() > 0) {
					this.playerSpeed = -9;
				} else {
					this.playerSpeed = 0;
				}
				break;
			}
			case RIGHT: {
				if (player.getX() + player.getDx() < PANE_WIDTH) {
					this.playerSpeed = 9;
				} else {
					this.playerSpeed = 0;
				}
				break;
			}
			case SPACE: {
				player.teleportMySelfDown();
				break;
			}
			default: {
				e.consume();
				break;
			}

			}

		});

		// keys released
		this.setOnKeyReleased(e -> {
			switch (e.getCode()) {
			case LEFT:
			case RIGHT: {
				this.playerSpeed = 0;
				break;
			}
			default: {
				e.consume();
				break;
			}

			}
		});
	}

	private void setMovementTimer() {

		movementTimer = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				currentLevel.advanceEveryone(player.getX(), player.getY());
				playerHelper();

				currentLevelScore++;
				levelended();

			}
		}));
		movementTimer.setCycleCount(Timeline.INDEFINITE);
	}

	private void playerHelper() {
		if (playerDeathCheck()) {
			death();
			return;
		}

		player.advanceMySelf();
		player.graduallySpeed(playerSpeed);
		playerJumpCheck();
		playerPerkEncounterCheck();

	}

	private void playerJumpCheck() {
		if (player.getJump() == -JUMP_SIZE) {
			allowedToJump = true;
		}

	}

	private void playerPerkEncounterCheck() {
		if (currentLevel.getPerks() != null) {
			for (Game_Entity perk : currentLevel.getPerks()) {
				if (player.collisionCheckWithPerk(perk)) {
					if (perk instanceof Game_SlowMotion_Perk_Fx) {
						currentLevel.slowMotionPerkActivate();
						((Game_SlowMotion_Perk_Fx) perk).setTaken(true);
					} else {
						currentLevel.boomPerkActivate();
						((Game_SB_Boom_Perk_Fx) perk).setTaken(true);
					}
				}
			}
		}
	}

	private boolean playerDeathCheck() {

		for (Game_Entity et : currentLevel.getLiving()) {
			if (et instanceof Game_Ball_Fx) {
				if (player.collisionCheckwithBall((Game_Ball_Fx) et)) {
					return true;
				}
			}

			if (et instanceof Game_BeamBall_Fx) {
				if (player.collisionCheckWithBeam((Game_BeamBall_Fx) et)) {
					return true;
				}
			}

			if (et instanceof Game_Star_Fx) {
				if (player.collisionCheckWithStar((Game_Star_Fx) et)) {
					return true;
				}
			}
		}

		return false;
	}

	public void levelended() {
		if (currentLevel.checkForEnd()) {
			score += currentLevelScore;
			currentLevelScore = 0;
			pauseGame();
			if (this.level != 10) {
				new MyDialog(this, LEVEL_COMPLETE + this.level + NEXT_LEVEL, false);
			} else {
				new MyDialog(this, GAME_FINISHED, true);
			}
		}
	}

	public void nextLevel() {
		currentLevel.clear();
		this.level++;
		if (this.level == 10) {
			currentLevel = new BossLevel(this);
		} else if (this.level % 4 == 0) {
			currentLevel = new StarLevel(this.level, this);
		} else {
			currentLevel = new BasicLevel(this.level, this);
		}

		playGame();

	}

	private void death() {
		deathSound.play();
		movementTimer.stop();
		currentLevel.pauseSpawn();

		gameOver.setText(gameOverText + score);

		double gameOverLength = gameOver.getLayoutBounds().getWidth();
		gameOver.setX(PANE_WIDTH / 2 - gameOverLength / 2);
		gameOver.setY(PANE_HEIGHT / 2);

		this.getChildren().add(gameOver);

	}

	public void playGame() {
		movementTimer.play();
		;
		currentLevel.startSpawn();
		currentLevel.slowMotionPerkResume();
	}

	public void pauseGame() {
		movementTimer.stop();
		currentLevel.pauseSpawn();
		currentLevel.slowMotionPerkPause();
	}

	public void reset() {
		currentLevel.clear();
		currentLevel.startSpawn();

		if (this.getChildren().contains(gameOver)) {
			this.getChildren().remove(gameOver);
		}
		playGame();
	}

	public void restartGame() {
		this.level = 1;
		currentLevel.clear();
		currentLevel = new BasicLevel(this.level, this);

		playGame();
	}

	public void gameFinished() {
		// TODO think
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				restartGame();
				pauseGame();
				score = 0;
				currentLevelScore = 0;
				player.setX(PANE_WIDTH / 2);
			}
		});
	}

	@Override
	public void entityIsAlive(Parent entity) {
		if (!this.getChildren().contains(entity)) {
			this.getChildren().add(entity);
		}
	}

	@Override
	public void entityIsDead(Parent entity) {
		if (this.getChildren().contains(entity)) {
			this.getChildren().remove(entity);
		}
	}
}
