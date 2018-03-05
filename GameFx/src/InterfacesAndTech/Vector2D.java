package InterfacesAndTech;

public class Vector2D {

	private double x;
	private double y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Vector2D add(Vector2D v) {
		return new Vector2D(x + v.x, y + v.y);
	}

	public Vector2D sub(Vector2D v) {
		return new Vector2D(x - v.x, y - v.y);
	}

	public void multiply(double s) {
		x *= s;
		y *= s;
	}

	public Vector2D multiply(Vector2D v) {
		return new Vector2D(x * v.x, y * v.y);
	}

	public double size() {
		return Math.sqrt(x * x + y * y);
	}

	public void normalize() {
		double size = size();
		multiply(1 / size);
	}
}
