package game;
 
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class Creature extends Entity {
	public Creature(float x, float y, int width, int height, Image sprite, boolean[][] blocked, float horizontalSpeed) {
		super(x, y, width, height, sprite, blocked, horizontalSpeed);
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
		
		if(verticalSpeed > 0)
			vDir = VerticalDirection.DOWN;
		else 
			vDir = VerticalDirection.UP;
			
			
	}
	/**
	 * Moves the character left
	 * @param delta Time since last frame
	 * @return How much the character moved
	 */
	public float moveLeft(int delta, float horizontalSpeed) {
		hDir = HorizontalDirection.LEFT;
		return -horizontalSpeed * delta;
	}
	
	/**
	 * Moves the character right
	 * @param delta Time since last frame
	 * @return How much the character moved
	 */
	public float moveRight(int delta, float horizontalSpeed) {
		hDir = HorizontalDirection.RIGHT;
		return horizontalSpeed * delta;
	}

	public boolean isOnGround() {
	
		return	blocked[(getXTile(HorizontalDirection.LEFT))][(getYTile(VerticalDirection.DOWN)+1)] 
				|| blocked[(getXTile(HorizontalDirection.RIGHT))][(getYTile(VerticalDirection.DOWN)+1)];
	}
   
	// Getters and Setters
	public Vector2f getPos() {
		return pos;
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