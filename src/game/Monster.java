package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Monster extends Creature {
	
	public Monster(float x, float y, int width, int height, Image sprite, float walkingSpeed, boolean[][] blocked) {
		super(x, y, width, height, sprite, blocked);
		this.walkingSpeed = walkingSpeed;
	}
	
	@Override
	public void update(Player player, int delta) {
		fall(delta);
		moveTowardsPlayer(player, delta);		
	}
	
	/**
	 * Will move the monster towards the player if it is closer than X pixels.
	 * @param player The player to which the monster is moving
	 * @param blocked The map's blocked tiles
	 */
	public void moveTowardsPlayer(Player player, int delta) {
		
		Vector2f trans = new Vector2f(0, 0);
		Vector2f playerPosition = player.getPos();
		
		
		/** Ignore if the player is too far away **/		
		if(Math.abs(pos.x - playerPosition.x) > 400)
			return;
		
			    
	    /** Easy way to check for collisions with rectangles **/
	    Rectangle playerRect = new Rectangle(playerPosition.x, playerPosition.y, 32, 32);
	    Rectangle monster = new Rectangle(pos.x, pos.y, 32, 32);	    
	    
	    
	    /** Move towards the player **/
	    /** Move 15 pixels inside the player **/
	    if(playerPosition.x + 15 > pos.x + width) {
	    	trans.x += walkingSpeed * delta;
	    	hDir = HorizontalDirection.RIGHT;
	    }
	    else if(playerPosition.x + player.width < pos.x + 15) {
	    	trans.x -= walkingSpeed * delta;
	    	hDir = HorizontalDirection.LEFT;
	    }
	    
	    pos.x += trans.x;
	    
	    if (isInBlock() && hDir == HorizontalDirection.RIGHT) {
	    	pos.x -= trans.x;
	    } 
	    else if (isInBlock() && hDir == HorizontalDirection.LEFT) {
	    	pos.x += trans.x;
	    }
	    
	    if(monster.intersects(playerRect)) {
	    	player.getAttacked();
	    }
	}

}
