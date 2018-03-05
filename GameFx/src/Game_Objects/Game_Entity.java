package Game_Objects;

import InterfacesAndTech.IFinals;
import InterfacesAndTech.I_Game_Entity;
import javafx.scene.Parent;
import javafx.scene.paint.Color;

public abstract class Game_Entity extends Parent implements IFinals, I_Game_Entity {

	protected static myRelativeSpeed gameSpeed = myRelativeSpeed.MEDIUM;
	protected double gameSpeedConst = gameSpeed.getSpeed();

	protected double x;
	protected double y;
	protected double dx;
	protected double dy;

	protected double originalSpeedX;
	protected double originalSpeedY;

	protected boolean isDead = false;

	protected Color color;

	public Game_Entity(double x, double y, double dx, double dy, Color c) {
		this.x = x;
		this.y = y;

		this.dx = dx;
		this.dy = dy;

		originalSpeedX = dx;
		originalSpeedY = dy;

		this.color = c;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public void setDx(double Dx) {
		this.dx = Dx;
	}

	@Override
	public double getDx() {
		return this.dx;
	}

	@Override
	public void setDy(double Dy) {
		this.dy = Dy;
	}

	@Override
	public double getDy() {
		return this.dy;
	}

	@Override
	public double getOriginalXSpeed() {
		return this.originalSpeedX;
	}

	@Override
	public double getOriginalYSpeed() {
		return this.originalSpeedY;
	}

	@Override
	public void advanceMySelf() {
		this.x += dx;
		this.y += dy;
	}

	public abstract boolean isDead();
	
}
