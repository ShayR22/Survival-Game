package InterfacesAndTech;

import javafx.scene.paint.Color;

public interface IFinals {
	public final int PANE_WIDTH = 1500;
	// if problem go 1000
	public final int PANE_HEIGHT = 900;
	
	public final int FRAME_WIDTH = 1500;
	public final int FRAME_HEIGHT = 950;
	
	public final int DESCRIPTION_WIDTH = 500;
	public final int DESCRIPTION_HEIGHT = 350;
	
	public final int OPTION_FRAME_WIDTH = 500;
	public final int OPTION_FRAME_HEIGHT = 600;
	public final String ULTRA_LOW = "Ultra Low";
	public final String LOW = "Low";
	public final String MEDIUM = "Medium";
	public final String HIGH = "High";
	public final String ULTRA_HIGH = "Ultra High";
	
	public final int BALL_RADIUS = 25;
	public final int BALL_X_SPEED_FACTOR = 10;
	public final int BALL_Y_SPEED_FACTOR = 3;
	public final Color[] BALL_COLOR_ARRAY = { Color.RED, Color.BLUE, Color.YELLOW };
	public final int BALL_COLOR_ARRAY_LENGTH = BALL_COLOR_ARRAY.length;
	
	public final int BOSS_RADIUS = 100;
	public final int BOSS_LIFE = 20;
	public final int BOSS_ROTATE_SPEED = 4;
	public final double BOSS_HOMEING_BEAM_Y_THROW = 1.5;
	public final double BOSS_HOMEING_BEAM_SPEED = 7.5;
	public final double BOSS_X_SPEED_INCREMENT = 1;
	public final Color BOSS_HOMEING_BEAM_COLOR = Color.DARKVIOLET;
	
	public final double BOSS_INITIAL_DX = 5;
	public final double BOSS_INITIAL_DY = 0;
	
	public final int BOSS_BAR_X_SPACE = 50;
	public final int BOSS_BAR_Y_SPACE = 20;
	public final int BOSS_BAR_Y_HEIGHT = 40;
	
	
	public final int MY_DIALOG_WIDTH = 400;
	public final int MY_DIALOG_HEIGHT = 400;
	
	public final int STAR_HEIGHT = 50;
	public final int STAR_WIDTH = 50;
	public final int PLAYER_STAR_SHAPE_HEIGHT_OFFSET= 10;
	public final double STAR_X_SPEED_FACTOR = 13;
	public final double STAR_Y_SPEED_FACTOR = 3;
	public final int STAR_Y_RANDOM_POS_COEFFICIENT =  150;
									 
	
	
	public final int SLOW_MOTION_PERK_RADIUS = 25;
	public final int SLOW_MOITION_PERK_DURATION = 2;
	
	public final int SB_BOOM_PERK_RADIUS = 25;
	
	public final int JUMP_SIZE = -8;
	
	public final double PLAYER_GRAVITY =  0.25;
	public final double OBJECT_GRAVITY = 0.3;
	
	
	
	public final Color BEAM_BALL_COLOR = Color.RED; 
	public final Color BOSS_BEAM_BALL_COLOR = Color.CHOCOLATE;
	public final Color PLAYER_COLOR = Color.WHITE;
	public final Color TEXT_COLOR = Color.WHITE;
	public final Color SLOW_MOTION_PERK_COLOR = Color.LIGHTSEAGREEN;
	public final Color SB_BOOM_PERK_COLOR = Color.RED;
	
	public enum myRelativeSpeed{
		ULTRA_SLOW(0.2),
		SLOW(0.4),
		MEDIUM(1.0),
		HIGH(2.5),
		ULTRA_HIGH(10.0);
		
		private double mySpeed;
		private myRelativeSpeed(double speed) {
			this.mySpeed = speed;
		}
		
		public double getSpeed() {
			return this.mySpeed;
		}
		
	}
	
	public final String LEVEL_COMPLETE = "You successfully completed level: ";
	public final String NEXT_LEVEL = " Lets go to the next Level";
	public final String GAME_FINISHED = "congratulations u finished the game now FUCK OFF";
	public final String DEATH = "Noob u just got pwned, better luck next time \nbtw ur score is: ";
			
}
