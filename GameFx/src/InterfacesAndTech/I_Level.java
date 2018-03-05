package InterfacesAndTech;

import java.util.ArrayList;

import Game_Objects.Game_Entity;

public interface I_Level {
	
	public void advanceEveryone(double x , double y);
	
	public void slowMotionPerkActivate();
	
	public void boomPerkActivate();
	
	public boolean checkForEnd();
	
	public ArrayList<Game_Entity> getLiving();
	
	public void clear();
	
	public ArrayList<Game_Entity> getPerks();
	
	public void startSpawn();
	
	public void pauseSpawn();
	
	public void slowMotionPerkResume();
	
	public void slowMotionPerkPause();
	
}
