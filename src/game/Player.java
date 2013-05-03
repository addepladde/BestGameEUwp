package game;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Player extends Creature {
	
	private boolean jumping, ducking;
	private int lives;
	
	int[] experienceNeededList = {50, 100, 150, 200, 250, 300, 350, 400, 450, 500};
	int experienceNeeded;
	int level;
	int experienceGained;
	int maxLevel;
	int maxNumberOfLives;
	
	
	private boolean immune;
	private long timeForImmunity;
	
	
	private LinkedList<Bullet> listOfShots;
	
	
	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Player(float x, float y, int width, int height, Image sprite, boolean[][] blocked) {
		super(x, y, width, height, sprite, blocked);
		
		this.hDir = HorizontalDirection.RIGHT;
		
		listOfShots = new LinkedList<Bullet>();
		
		walkingSpeed = 0.2f;
		jumping = false;
		ducking = false;
		immune = false;
		lives = 4;
		level = 1;
		experienceGained = 20;
		maxLevel = 10;
		maxNumberOfLives = 5;
		
		experienceNeeded = experienceNeededList[level-1];
	}
	
	
	
	public void getAttacked() {
		
		if(!immune) {
			lives--;
			makeImmune();
		}
		
	}
	/** Makes the player immune to damage for X seconds **/
	public void makeImmune() {
		timeForImmunity = System.currentTimeMillis();		
		immune = true;		
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public int getMaxNumberOfLives() {
		return maxNumberOfLives;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}

	public int getExperienceGained() {
		return experienceGained;
	}
	public void setExperienceGained(int experienceGained) {
		this.experienceGained = experienceGained;
	}
	public int[] getExperienceNeededList() {
		return experienceNeededList;
	}
	public void setExperienceNeededList(int[] experienceNeededList) {
		this.experienceNeededList = experienceNeededList;
	}
	public void setExperienceNeeded(int experienceNeeded) {
		this.experienceNeeded = experienceNeeded;
	}
	public int getExperienceNeeded(){
		return experienceNeeded;
	}
	public boolean isJumping() {
		return jumping;
	}


	public boolean isDucking() {
		return ducking;
	}


	public float getVerticalSpeed() {
		return verticalSpeed;
	}

	public void setVerticalSpeed(float verticalSpeed) {
		this.verticalSpeed = verticalSpeed;
	}

	public float getWalkingSpeed() {
		return walkingSpeed;
	}

	public void setWalkingSpeed(float walkingSpeed) {
		this.walkingSpeed = walkingSpeed;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public void update(GameContainer gc, int mapWidth, int mapHeight, int delta) throws SlickException {
		/**
		 * How much the character moved
		 */
		Vector2f trans = new Vector2f(0, 0); 
		Input input = gc.getInput();
		
		fall(delta);
		
		if(isOnGround())
			jumping = false;
	    
		if (input.isKeyDown(Input.KEY_W)) {
				jump();
		}
 		if (input.isKeyDown(Input.KEY_S)) {	
				duck();			
		}
 		if (input.isKeyDown(Input.KEY_D)) {
			trans.x = moveRight(delta, walkingSpeed);
		} 
		if (input.isKeyDown(Input.KEY_A)) {
			trans.x = moveLeft(delta, walkingSpeed);			
		}		
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			shoot();			
		}
		
		/**
		 * Should not move faster diagonally
		 */
		if (trans.x != 0 && trans.y != 0) { 
			trans.set(trans.x / 1.5f, trans.y / 1.5f);
		}
 
		pos.x += trans.x; 
		
		/**
		 * No need to check direction due to the fact that we need to subtract it either way
		*/
		if(isInBlock() && trans.x != 0) {
			pos.x -= trans.x;			
		}
		
		
		/** 
		 * Funkar ej av ngn anledning. skotten renderas EJ.
		 */
		for(int i = 0; i < listOfShots.size(); i++) {
			listOfShots.get(i).update(delta);
			listOfShots.get(i).render();
		}
	}
	
	public void shoot() throws SlickException {
		// Where the bullet spawns
		float bulletStartPosition_X;
		float bulletStartPosition_Y = pos.y + width/2; //In the middle of the character's height
		
		if(hDir == HorizontalDirection.LEFT) 
			bulletStartPosition_X = pos.x - 1;
		else 
			bulletStartPosition_X = pos.x + width + 1;
		
		listOfShots.add(new Bullet(bulletStartPosition_X, bulletStartPosition_Y, 10, 10, new Image("res/heart.png"), blocked, hDir));
	}
	
	public boolean isImmune() {
		return immune;
	}

	public void setImmune(boolean immune) {
		this.immune = immune;
	}

	public long getTimeForImmunity() {
		return timeForImmunity;
	}

	public void setTimeForImmunity(long timeForImmunity) {
		this.timeForImmunity = timeForImmunity;
	}

	public void jump(){	

		if(!isOnGround()){ 
			return;
        }

		vDir = VerticalDirection.UP;
		verticalSpeed = -5f;
		
	}
	public LinkedList<Bullet> getBullets() throws SlickException{
		return listOfShots;
	}
	public void duck() {
		if(!isOnGround()){ 
			return;
        }
	}
	
}
