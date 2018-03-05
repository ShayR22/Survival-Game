package Levels;

import java.util.ArrayList;

import Game_Objects.EntityFactory;
import Game_Objects.Game_Ball_Fx;
import Game_Objects.Game_BeamBall_Fx;
import Game_Objects.Game_Entity;
import Game_Objects.Game_Star_Fx;
import Game_Perks.Game_SB_Boom_Perk_Fx;
import Game_Perks.Game_SlowMotion_Perk_Fx;
import InterfacesAndTech.IFinals;
import InterfacesAndTech.IPaneObeservable;
import InterfacesAndTech.I_Level;
import InterfacesAndTech.Vector2D;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class BasicLevel implements I_Level, IFinals {

	/*
	 * should be able to generate enemys know who is dead and alive ( all entities
	 * got isDead methods) advance all enemies perk usage
	 * 
	 * shouldnt know player ,player interaction by panel
	 * 
	 */

	// balls have a unique array as they have diffrent mechanics of moving and
	// dieing
	private ArrayList<Game_Entity> livingEntitiesWithoutBalls;
	private ArrayList<Game_Ball_Fx> livingBalls;

	private int levelNumber;
	private Timeline spawnTimer;

	private Timeline slowMotionPerkTimer;
	private int slowMotionPerkCounter = 0;
	private static myRelativeSpeed gameSpeed = myRelativeSpeed.MEDIUM;

	private boolean slowMotionPerkAllowToActivate = false;

	private final double gravity = OBJECT_GRAVITY;

	private EntityFactory entityFactory;

	private IPaneObeservable myPane;

	public BasicLevel(int levelNumber, IPaneObeservable gp) {
		this.levelNumber = levelNumber;
		this.myPane = gp;

		livingEntitiesWithoutBalls = new ArrayList<Game_Entity>();
		livingBalls = new ArrayList<Game_Ball_Fx>();

		entityFactory = new EntityFactory();

		setSlowMotionTimer();
		setSpawnTimer();
	}

	private void setSlowMotionTimer() {
		slowMotionPerkTimer = new Timeline(
				new KeyFrame(Duration.millis(1000), new javafx.event.EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						if (slowMotionPerkCounter++ > SLOW_MOITION_PERK_DURATION) {
							enemiesSpeedChange(gameSpeed);
							slowMotionPerkTimer.stop();
							slowMotionPerkCounter = 0;
							slowMotionPerkAllowToActivate = false;
						}
					}
				}));

		slowMotionPerkTimer.setCycleCount(Timeline.INDEFINITE);
	}

	private void setSpawnTimer() {
		spawnTimer = new Timeline(new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addEnemies();
			}
		}));

		spawnTimer.setCycleCount(this.levelNumber + 1);

	}

	@Override
	public void startSpawn() {
		spawnTimer.play();
	}

	public void pauseSpawn() {
		spawnTimer.pause();
	}

	// --------------------------- CreateEntities Start --------------------

	private void addEnemies() {
		addGameBall();
		addStar();
		addSlowMotionPerk();
		addBoomPerk();
	}

	private void addGameBall() {
		Game_Ball_Fx et = entityFactory.createBall();
		livingBalls.add(et);
		livingNotifier(et);

	}

	// can only add stars if level is bigger or equal to 3
	private void addStar() {
		int rand = (int) ((Math.random() * 2 + Math.random() * 2));
		if (this.levelNumber >= 3 && rand == 0) {
			Game_Star_Fx et = entityFactory.createStar();

			livingEntitiesWithoutBalls.add(et);
			livingNotifier(et);
		}
	}

	private void addSlowMotionPerk() {
		int rand = (int) (Math.random() * 2);
		if (rand == 0) {
			Game_SlowMotion_Perk_Fx et = entityFactory.createSlowMotionPerk();

			livingEntitiesWithoutBalls.add(et);
			livingNotifier(et);
		}
	}

	private void addBoomPerk() {
		int rand = (int) (Math.random() * 3);
		if (rand == 0) {
			Game_SB_Boom_Perk_Fx et = entityFactory.createSB_BoomPerk();

			livingEntitiesWithoutBalls.add(et);
			livingNotifier(et);
		}
	}

	// -------------------------- CreateEntities End --------------------

	// -------------------------- Advance Start
	// ---------------------------------------

	@Override
	public void advanceEveryone(double x, double y) {
		advanceBallsAndThierBeamsHelper(x, y);

		for (Game_Entity et : livingEntitiesWithoutBalls) {
			et.advanceMySelf();
		}

		// after advancing entites check if someone died
		deathCheck();
	}

	private void advanceBallsAndThierBeamsHelper(double x, double y) {
		Vector2D playerPosition = new Vector2D(x, y);
		for (Game_Ball_Fx ball : livingBalls) {
			// each advance will check for beam and add them
			for (Game_BeamBall_Fx beam : ball.getArrayOfGame_BeamBall_Fx()) {
				if (!livingEntitiesWithoutBalls.contains(beam) && !beam.isDead()) {
					livingEntitiesWithoutBalls.add(beam);
					// a beam can only be borned during ball advancement
					livingNotifier(beam);
				}
			}
			ball.advanceMySelf(playerPosition);
			ball.setDy(ball.getDy() + gravity);
		}
	}
	// -------------------------- Advance Ends
	// ---------------------------------------

	// --------------------------- Dead Check and alive notifier Start
	// --------------------------
	private void deathCheck() {
		for (Game_Ball_Fx ball : livingBalls) {
			if (ball.isDead()) {
				deathNotify(ball);
			}
		}

		for (Game_Entity et : livingEntitiesWithoutBalls) {
			if (et.isDead()) {
				deathNotify(et);
			}
		}
	}

	private void deathNotify(Game_Entity entity) {
		myPane.entityIsDead(entity);
	}

	private void livingNotifier(Game_Entity entity) {
		myPane.entityIsAlive(entity);
	}
	// --------------------------- Death and alive notifier
	// --------------------------

	// ---------------------------- Perk Handle Start
	// ----------------------------------------

	@Override
	public void boomPerkActivate() {
		ArrayList<Game_Entity> boomPerkDeaths = new ArrayList<Game_Entity>();
		// add all dead beam and stars to an arraylist
		for (Game_Entity et : livingEntitiesWithoutBalls) {
			if (et instanceof Game_BeamBall_Fx || et instanceof Game_Star_Fx) {
				boomPerkDeaths.add(et);
			}
		}

		for (Game_Entity et : boomPerkDeaths) {
			if (livingEntitiesWithoutBalls.contains(et)) {
				if (et instanceof Game_BeamBall_Fx) {
					((Game_BeamBall_Fx) et).setIsDead(true);
				}
				this.livingEntitiesWithoutBalls.remove(et);
			}
			// tell pane death accured
			deathNotify(et);
		}
	}

	public void slowMotionPerkActivate() {
		// make slow time activate timer and change all speed of created and existing
		// enemies
		slowMotionPerkAllowToActivate = true;
		slowMotionPerkCounter = 0;
		this.enemiesSpeedChange(myRelativeSpeed.ULTRA_SLOW);
		slowMotionPerkTimer.play();
	}

	public void slowMotionPerkPause() {
		if (slowMotionPerkAllowToActivate) {
			slowMotionPerkTimer.stop();
		}
	}

	public void slowMotionPerkResume() {
		if (slowMotionPerkAllowToActivate) {
			slowMotionPerkTimer.play();
		}
	}

	private void enemiesSpeedChange(myRelativeSpeed gameSpeed) {

		if (slowMotionPerkAllowToActivate) {

			Game_Ball_Fx.setStaticGameSpeed(gameSpeed);
			Game_BeamBall_Fx.setStaticGameSpeed(gameSpeed);
			Game_Star_Fx.setStaticGameSpeed(gameSpeed);

			double speedMultiplyer = gameSpeed.getSpeed();

			for (Game_Ball_Fx ball : livingBalls) {
				ball.setDx(ball.getOriginalXSpeed() * speedMultiplyer);
				// ball.setDy(ball.getOriginalSpeedY() * speedMultiplyer);
			}

			for (Game_Entity et : livingEntitiesWithoutBalls) {
				boolean isAPerkBoom = !(et instanceof Game_SB_Boom_Perk_Fx);
				boolean isAPerkSlow = !(et instanceof Game_SlowMotion_Perk_Fx);
				// System.out.println("boom " + isAPerkBoom + " --- " + "slow " + isAPerkSlow);

				if (isAPerkBoom && isAPerkSlow) {
					et.setDx(et.getOriginalXSpeed() * speedMultiplyer);
					et.setDy(et.getOriginalYSpeed() * speedMultiplyer);
				}
			}
		}
	}

	public void resetSlowMotion() {
		slowMotionPerkAllowToActivate = true;
		enemiesSpeedChange(gameSpeed);
		slowMotionPerkTimer.stop();
		slowMotionPerkCounter = 0;
		slowMotionPerkAllowToActivate = false;
	}

	@Override
	public ArrayList<Game_Entity> getPerks() {
		ArrayList<Game_Entity> myPerks = new ArrayList<Game_Entity>();
		for (Game_Entity et : this.livingEntitiesWithoutBalls) {
			if (et instanceof Game_SlowMotion_Perk_Fx || et instanceof Game_SB_Boom_Perk_Fx) {
				myPerks.add(et);
			}
		}

		return myPerks;

	}

	// ---------------------------- Perk Handle Endd
	// ----------------------------------------
	@Override
	public ArrayList<Game_Entity> getLiving() {
		ArrayList<Game_Entity> allLiving = new ArrayList<Game_Entity>();
		allLiving.addAll(this.livingBalls);
		allLiving.addAll(this.livingEntitiesWithoutBalls);

		return allLiving;
	}

	@Override
	public void clear() {

		for (Game_Entity et : getLiving()) {
			deathNotify(et);
		}
		livingEntitiesWithoutBalls.clear();
		
		livingBalls.clear();
		spawnTimer.playFromStart();
		spawnTimer.stop();
	}

	@Override
	public boolean checkForEnd() {
		if (this.livingBalls.isEmpty()) {
			return false;
		} else {
			for (Game_Entity et : getLiving()) {
				if (!et.isDead()) {
					return false;
				}
			}
			return true;
		}
	}

	
	public static void setStaticGameSpeed(myRelativeSpeed enumSpeed) {
		gameSpeed = enumSpeed;
	}
}