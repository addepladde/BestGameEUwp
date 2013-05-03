package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity {
	protected Vector2f pos; // Vector contains a value with components x &amp; y
	protected Image sprite;
	protected float horizontalSpeed;
	protected float verticalSpeed;
	protected boolean[][] blocked;
	protected int width, height;
	protected HorizontalDirection hDir;
	protected VerticalDirection vDir;

	public Entity(float x, float y, int width, int height, Image sprite, boolean[][] blocked, float horizontalSpeed) {		
	pos = new Vector2f(x,y);
	this.width = width;
	this.height = height;
	this.sprite = sprite;
	this.blocked = blocked;
	this.horizontalSpeed = horizontalSpeed;
	vDir = VerticalDirection.DOWN;
	}
	/**
	 * Checks if any part of the creature is in a block
	 */
	public boolean isInBlock() {
		
		int xBlock = getXTile(HorizontalDirection.RIGHT);
		int yBlock = getYTile(VerticalDirection.DOWN);
		
		int xBlock2 = getXTile(HorizontalDirection.LEFT);
		int yBlock2 = getYTile(VerticalDirection.UP);
		
		return (blocked[xBlock][yBlock] 
				|| blocked[xBlock2][yBlock]
						|| blocked[xBlock][yBlock2]
								|| blocked[xBlock2][yBlock2]);
	}

	/** Returns the x-coordinate of the tile which the creature is standing on **/
	/** If the character is facing LEFT and is standing between two blocks, returns the right block **/
	public int getXTile(HorizontalDirection hDir) {
				
		if(hDir == HorizontalDirection.LEFT) { 
			return (int) (pos.x + width) / 32;
		}
		
		return (int) (pos.x) / 32;
		
	}
	
	/** Returns the y-coordinate of the tile which the creature is standing on **/
	/** If the character is heading up and is between two blocks, return the lower block **/
	public int getYTile(VerticalDirection vDir) {
		if(vDir == VerticalDirection.UP) { 
			return (int) (pos.y + height - 1) / 32;
		}
		
		return (int) (pos.y + 1) / 32;
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
		
		if(this.isInBlock() && this.getClass().toString().equals("class game.Bullet")) {
			Game.entities.remove(this);
		}
			
		
		if(this.collides(e) && this.getClass().toString().equals("class game.Bullet") 
				&& e.getClass().toString().equals("class game.Monster")) {
			Game.entities.remove(e);
			Game.entities.remove(this);
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