package InterfacesAndTech;


public interface I_Game_Entity {
	
	public void setX(double x);
	public double getX();
	
	public void setY(double y);
	public double getY();
	
	public void setDx(double Dx);
	public double getDx();
	
	public void setDy(double Dy);
	public double getDy();
	
	public double getOriginalXSpeed();
	public double getOriginalYSpeed();
	
	public void advanceMySelf();
	
}
