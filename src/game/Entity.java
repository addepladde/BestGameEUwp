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
			return (int) (pos.x + width - 1) / 32;
		}
		
		return (int) (pos.x + 1) / 32;
		
	}
	
	/** Returns the y-coordinate of the tile which the creature is standing on **/
	/** If the character is heading up and is between two blocks, return the lower block **/
	public int getYTile(VerticalDirection vDir) {
		if(vDir == VerticalDirection.UP) { 
			return (int) (pos.y + height - 1) / 32;
		}
		
		return (int) (pos.y + 1) / 32;
	}
	
	
	/**
	 * Moves the character left
	 * @param delta Time since last frame
	 * @return How much the character moved
	 */
	public float moveLeft(int delta) {
		hDir = HorizontalDirection.LEFT;
		return -horizontalSpeed * delta;
	}
	
	/**
	 * Moves the character right
	 * @param delta Time since last frame
	 * @return How much the character moved
	 */
	public float moveRight(int delta) {
		hDir = HorizontalDirection.RIGHT;
		return horizontalSpeed * delta;
	}
	
	public boolean collides(Entity e) {
		if(this == e)
			return false;
		
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
		
		/**
		 * If a bullet hit a wall
		 */
		if(this.isInBlock() && this instanceof game.Bullet) {
			Game.entities.remove(this);
			return;
		}
			
		/** 
		 * If a bullet hit a monster
		 */
		if(this.collides(e) && this instanceof game.Bullet	&& e instanceof game.Monster) {
			Game.entities.remove(e);
			Game.entities.remove(this);
			return;
		}
			
		
		/**
		 * If a player jumped on a monster
		 */

		if(this.collides(e) && vDir == VerticalDirection.DOWN && e.isOnGround() &&
				this instanceof game.Player && e instanceof game.Monster
					&& (this.getX() + this.getWidth() > e.getX() && this.getX() < e.getX() 
						 || this.getX() < e.getX() + e.getWidth() && this.getX() + this.getWidth() > e.getX() + e.getWidth())
								 && Math.abs(this.getY()+height - e.getY()) < 20  && this.getY() < e.getY()) {
			Game.entities.remove(e);
			return;
		}
		
		if(this.collides(e) && this instanceof game.Player && e instanceof game.Monster){
			((Player) this).getAttacked();
		}	
	}
	
	public boolean isOnGround() {
		
		return	blocked[(getXTile(HorizontalDirection.LEFT))][(getYTile(VerticalDirection.DOWN)+1)] 
				|| blocked[(getXTile(HorizontalDirection.RIGHT))][(getYTile(VerticalDirection.DOWN)+1)];
	}
	public Vector2f getPos() {
		return pos;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
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