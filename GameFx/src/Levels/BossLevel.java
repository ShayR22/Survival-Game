package Levels;

import java.util.ArrayList;

import BossBar.BossBar;
import Game_Objects.Game_BossBeamBall_Fx;
import Game_Objects.Game_Boss_Fx;
import Game_Objects.Game_Entity;
import InterfacesAndTech.IFinals;
import InterfacesAndTech.IPaneObeservable;
import InterfacesAndTech.I_Level;
import javafx.scene.Parent;
import javafx.scene.paint.Color;

public class BossLevel implements I_Level, IFinals {

	private Game_Boss_Fx boss;
	private BossBar bossBar;
	private boolean resetHappen;
	
	private ArrayList<Game_BossBeamBall_Fx> livingEntities;

	private IPaneObeservable myPane;

	public BossLevel(IPaneObeservable gp) {
		resetHappen = false;
		myPane = gp;

		livingEntities = new ArrayList<Game_BossBeamBall_Fx>();
		bossBar = new BossBar();
		boss = new Game_Boss_Fx(PANE_WIDTH / 2 - BOSS_RADIUS, BOSS_RADIUS, BOSS_INITIAL_DX, BOSS_INITIAL_DY,
				Color.GREEN, this.bossBar);
		liveNotify(boss);
		liveNotify(bossBar);
	}

	@Override
	public void startSpawn() {
		livingEntities = new ArrayList<Game_BossBeamBall_Fx>();
		if (resetHappen) {
			liveNotify(boss);
			liveNotify(bossBar);
			resetHappen = false;
		}
	}

	@Override
	public void pauseSpawn() {
		// not using
	}

	@Override
	public void advanceEveryone(double x, double y) {
		if (!boss.isDead()) {
			for (Game_BossBeamBall_Fx beam : boss.getArrayOfGame_BossBeamBall_Fx()) {
				if (!livingEntities.contains(beam) && !beam.isDead()) {
					livingEntities.add(beam);
					liveNotify(beam);
				}
			}
			boss.advanceMySelf();
		}

		for (Game_BossBeamBall_Fx et : livingEntities) {
			et.advanceMySelf(x, y);
		}

		deathCheck();
	}

	private void liveNotify(Parent entity) {
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

		if (boss.isDead()) {
			deathNotify(boss);
		}
	}

	private void deathNotify(Parent entity) {
		myPane.entityIsDead(entity);
	}

	// --------------------------- death check ends ------------------------------


	@Override
	public boolean checkForEnd() {
		if (!boss.isDead()) {
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
		ArrayList<Game_Entity> livingEntitiesCast = new ArrayList<Game_Entity>();

		for (Game_BossBeamBall_Fx beam : livingEntities) {
			livingEntitiesCast.add(beam);
		}
		livingEntitiesCast.add(boss);

		return livingEntitiesCast;
	}

	@Override
	public void clear() {
		deathNotify(boss);
		deathNotify(bossBar);
		for (Game_Entity et : livingEntities) {
			deathNotify(et);
		}
		livingEntities.clear();

		// TODO not sure will work
		boss.getArrayOfGame_BossBeamBall_Fx().clear();

		bossBar.setProgress(1);
		boss.setLife(BOSS_LIFE);
		boss.setX(PANE_WIDTH / 2 - BOSS_RADIUS);
		boss.setDx(BOSS_INITIAL_DX);

		resetHappen = true;

	}

	//TODO what to do with methods (they need to be empty as boss level has no perks)
	@Override
	public ArrayList<Game_Entity> getPerks() {
		return null;
	}

	@Override
	public void slowMotionPerkResume() {
		
	}

	@Override
	public void slowMotionPerkPause() {
		

	}
	
	@Override
	public void slowMotionPerkActivate() {

	}

	@Override
	public void boomPerkActivate() {
	
	}
	
}
