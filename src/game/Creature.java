package game;
 
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class Creature {
 
	
	
	protected Vector2f pos; // Vector contains a value with components x &amp; y
	protected Image sprite;
	protected float walkingSpeed;
	protected float verticalSpeed;
	protected boolean[][] blocked;
	protected int width, height;
	protected HorizontalDirection hDir;
	protected VerticalDirection vDir;
	
 
	public Creature(float x, float y, int width, int height, Image sprite, boolean[][] blocked) {
		pos = new Vector2f(x,y);
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		this.blocked = blocked;
		verticalSpeed = 1f;
		hDir = HorizontalDirection.RIGHT;
		vDir = VerticalDirection.DOWN;
	}
	
	/** Simulate gravity **/
	public void fall(int delta) {
		pos.y += verticalSpeed;
		
		if(isInBlock()) {
			pos.y -= verticalSpeed;
			verticalSpeed = Game.GRAVITY;
		}	
		
		if(!isOnGround()) {
			verticalSpeed += Game.GRAVITY * delta/100;
		}			
		
		vDir = VerticalDirection.DOWN;
			
			
	}
	/**
	 * Moves the character left
	 * @param delta Time since last frame
	 * @return How much the character moved
	 */
	public float moveLeft(int delta, float walkingSpeed) {
		hDir = HorizontalDirection.LEFT;
		return -walkingSpeed * delta;
	}
	
	/**
	 * Moves the character right
	 * @param delta Time since last frame
	 * @return How much the character moved
	 */
	public float moveRight(int delta, float walkingSpeed) {
		hDir = HorizontalDirection.RIGHT;
		return walkingSpeed * delta;
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
	
	public boolean isOnGround() {
	
		return	blocked[(getXTile(HorizontalDirection.LEFT))][(getYTile(VerticalDirection.DOWN)+1)] 
				|| blocked[(getXTile(HorizontalDirection.RIGHT))][(getYTile(VerticalDirection.DOWN)+1)];
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

 
	public void render() {
		sprite.draw(pos.x, pos.y);
	}
 
	// Getters and Setters
	public Vector2f getPos() {
		return pos;
	}
 
	public float getX() {
		return pos.x;
	}
 
	public float getY() {
		return pos.y;
	}
 
	public void setPos(Vector2f pos) {
		this.pos = pos;
	}
 
	public Image getSprite() {
		return sprite;
	}
 
	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}
}