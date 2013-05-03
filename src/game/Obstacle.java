package game;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Obstacle {
	
	private Rectangle obstacle;
	
	private boolean collidable;
	private Vector2f pos;
	
	public Obstacle(int x, int y, int width, int height, boolean collidable) {
		pos = new Vector2f(x, y);
		obstacle = new Rectangle(x, y, width, height);
		this.collidable = collidable;  
	}
	
	public boolean intersects(Shape s) {
		return obstacle.intersects(s);
	}

	public Rectangle getObstacle() {
		return obstacle;
	}

	public void setObstacle(Rectangle obstacle) {
		this.obstacle = obstacle;
	}

	public boolean isCollidable() {
		return collidable;
	}

	public void setCollidable(boolean collidable) {
		this.collidable = collidable;
	}

	public Vector2f getPos() {
		return pos;
	}

	public void setPos(Vector2f pos) {
		this.pos = pos;
	}
}
