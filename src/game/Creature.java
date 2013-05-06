package game;
 
import org.newdawn.slick.Image;

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
			verticalSpeed += Game.GRAVITY * delta;
		}			
		
		if(verticalSpeed >= 0)
			vDir = VerticalDirection.DOWN;
		else 
			vDir = VerticalDirection.UP;
			
	}
	
	public void jump(){	

		if(!isOnGround()){ 
			return;
        }

		vDir = VerticalDirection.UP;
		verticalSpeed = -10f;
		
	}
}