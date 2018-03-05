package Levels;

import java.util.ArrayList;

import Game_Objects.EntityFactory;
import Game_Objects.Game_BeamBall_Fx;
import Game_Objects.Game_Entity;
import Game_Objects.Game_Star_Fx;
import Game_Perks.Game_SB_Boom_Perk_Fx;
import Game_Perks.Game_SlowMotion_Perk_Fx;
import InterfacesAndTech.IFinals;
import InterfacesAndTech.IPaneObeservable;
import InterfacesAndTech.I_Level;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class StarLevel implements I_Level, IFinals {

	private ArrayList<Game_Entity> livingEntities;

	private int levelNumber;
	private Timeline spawnTimer;

	private Timeline slowMotionPerkTimer;
	private int slowMotionPerkCounter = 0;
	private static myRelativeSpeed gameSpeed = myRelativeSpeed.MEDIUM;

	private boolean slowMotionPerkAllowToActivate = false;

	private EntityFactory entityFactory;

	private IPaneObeservable myPane;

	public StarLevel(int levelNumber, IPaneObeservable gp) {

		this.levelNumber = levelNumber;

		this.myPane = gp;
		entityFactory = new EntityFactory();
		livingEntities = new ArrayList<Game_Entity>();

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
		spawnTimer = new Timeline(new KeyFrame(Duration.millis(666), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				addEnemies();
			}
		}));

		spawnTimer.setCycleCount((this.levelNumber + 1) * 3);

	}

	@Override
	public void startSpawn() {
		spawnTimer.play();
	}

	@Override
	public void pauseSpawn() {
		spawnTimer.pause();
	}

	private void addEnemies() {
		addSlowMotionPerk();
		addStar();
	}

	private void addStar() {
		Game_Star_Fx star = entityFactory.createStar();
		livingEntities.add(star);
		liveNotify(star);
	}

	private void addSlowMotionPerk() {
		int rand = (int) (Math.random() * 10);
		if (rand == 0) {
			Game_SlowMotion_Perk_Fx et = entityFactory.createSlowMotionPerk();

			livingEntities.add(et);
			liveNotify(et);
		}
	}

	@Override
	public void advanceEveryone(double x, double y) {
		for (Game_Entity et : livingEntities) {
			et.advanceMySelf();
		}

		deathCheck();

	}

	private void liveNotify(Game_Entity entity) {
		myPane.entityIsAlive(entity);
	}

	// --------------------------- death checks and notify starts
	// ---------------------------

	private void deathCheck() {
		ArrayList<Game_Entity> deathEntities = new ArrayList<Game_Entity>();
		for (Game_Entity et : this.livingEntities) {
			if (et.isDead()) {
				deathEntities.add(et);
			}
		}

		for (Game_Entity et : deathEntities) {
			if (livingEntities.contains(et)) {
				livingEntities.remove(et);
			}
			deathNotify(et);
		}
	}

	private void deathNotify(Game_Entity entity) {
		myPane.entityIsDead(entity);
	}

	// --------------------------- death check ends ------------------------------

	@Override
	public void slowMotionPerkActivate() {
		// make slow time activate timer and change all speed of created and existing
		// enemies
		slowMotionPerkAllowToActivate = true;
		slowMotionPerkCounter = 0;
		setStaticGameSpeed(myRelativeSpeed.ULTRA_SLOW);
		this.enemiesSpeedChange(gameSpeed);
		slowMotionPerkTimer.play();
	}

	@Override
	public void boomPerkActivate() {
		ArrayList<Game_Entity> boomPerkDeaths = new ArrayList<Game_Entity>();
		// add all dead beam and stars to an arraylist
		for (Game_Entity et : livingEntities) {
			if (et instanceof Game_BeamBall_Fx || et instanceof Game_Star_Fx) {
				boomPerkDeaths.add(et);
			}
		}

		for (Game_Entity et : boomPerkDeaths) {
			if (livingEntities.contains(et)) {
				if (et instanceof Game_BeamBall_Fx) {
					((Game_BeamBall_Fx) et).setIsDead(true);
				}
				this.livingEntities.remove(et);
			}
			// tell pane death accured
			deathNotify(et);
		}

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
			Game_Star_Fx.setStaticGameSpeed(gameSpeed);

			double speedMultiplyer = gameSpeed.getSpeed();

			for (Game_Entity et : livingEntities) {
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

	@Override
	public boolean checkForEnd() {
		if (livingEntities.isEmpty() && spawnTimer.getCurrentTime().lessThan(Duration.millis(665))) {
			return false;
		}
		for (Game_Entity et : livingEntities) {
			if (!et.isDead()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ArrayList<Game_Entity> getLiving() {
		return this.livingEntities;
	}

	@Override
	public void clear() {
		for (Game_Entity et : livingEntities) {
			deathNotify(et);
		}
		
		livingEntities.clear();
	}

	@Override
	public ArrayList<Game_Entity> getPerks() {
		ArrayList<Game_Entity> myPerks = new ArrayList<Game_Entity>();

		for (Game_Entity et : livingEntities) {
			if (et instanceof Game_SlowMotion_Perk_Fx || et instanceof Game_SB_Boom_Perk_Fx) {
				myPerks.add(et);
			}
		}

		return myPerks;
	}
	
	public static void setStaticGameSpeed(myRelativeSpeed enumSpeed) {
		gameSpeed = enumSpeed;
	}

}
