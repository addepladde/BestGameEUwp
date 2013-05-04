package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class Monster extends Creature {
	
	public Monster(float x, float y, int width, int height, Image sprite, boolean[][] blocked, float horizontalSpeed) {
		super(x, y, width, height, sprite, blocked, horizontalSpeed);
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
		
	    /** Move towards the player **/
	    /** Move 32 pixels inside the player **/
	    if(playerPosition.x + player.getWidth() > pos.x + width) {
	    	trans.x += horizontalSpeed * delta;
	    	hDir = HorizontalDirection.RIGHT;
	    }
	    else if(playerPosition.x + player.width < pos.x + player.getWidth()) {
	    	trans.x -= horizontalSpeed * delta;
	    	hDir = HorizontalDirection.LEFT;
	    }
	    
	    pos.x += trans.x;
	    
	    
	    //BUGGED: if a monster falls on another monster they get stuck
	   /**
	     * Makes sure that the monsters don't stand in each other
	     */
	//    for(int i = 0; i < Game.entities.size(); i++) {
	    	/** If colliding **/
	    /*	if(this.collides(Game.entities.get(i)) && Game.entities.get(i) instanceof game.Monster && trans.x != 0) {
	    		pos.x -= trans.x;
	    	}*/
	    	/** If they are still colliding, it must be because of falling **/
	    /*	if(this.collides(Game.entities.get(i)) && Game.entities.get(i) instanceof game.Monster && !isOnGround()) { 
	    		pos.y = getYTile(vDir) * 32;
	    	}
	    }*/
	    
	    if(isInBlock() && trans.x != 0) {
			pos.x -= trans.x;			
		}

	}

}
