package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity {
	protected Vector2f pos; // Vector contains a value with components x &amp; y
	protected Image sprite;
	protected float walkingSpeed;
	protected float verticalSpeed;
	protected boolean[][] blocked;
	protected int width, height;
	protected HorizontalDirection hDir;
	protected VerticalDirection vDir;

	public Entity(float x, float y, int width, int height, Image sprite, boolean[][] blocked, float verticalSpeed) {		
		pos = new Vector2f(x,y);
	this.width = width;
	this.height = height;
	this.sprite = sprite;
	this.blocked = blocked;
	verticalSpeed = 1f;
	vDir = VerticalDirection.DOWN;
	}
	
	public boolean collides(Entity e) {
		Rectangle eRect = new Rectangle(e.getX(), e.getY(), 32, 32);
	    Rectangle thisRect = new Rectangle(this.getX(), this.getY(), 32, 32);
	    
	    return eRect.intersects(thisRect);
	}
	
	public void render() {
		sprite.draw(pos.x, pos.y);
	}
	
	public void checkCollisions(Entity e) {
		if(this == e)
			return;
		
		if(this.collides(e)) {
			System.out.println(e + " " + this);
			System.out.println("yolo");
		}
	}
	
	public float getX() {
		return pos.x;
	}
 
	public float getY() {
		return pos.y;
	}

	public void update(Player player, int delta) {
		// TODO Auto-generated method stub
		
	}

	public void update(GameContainer gc, int mapWidth, int mapHeight, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	public void update(int delta) {
		// TODO Auto-generated method stub
		
	}
}