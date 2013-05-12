package game;

import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Player extends Creature {
	
	/** Taken from Tibia's experience formula **/
	private static final int[] experienceNeededList = {100, 100, 200, 400, 700, 1100, 1600, 2200, 2900};
	/** Experience needed to reach the next level **/
	private int experienceNeeded;
	
	/** Experience gained in the current level **/
	private int experienceGained;
	
	private int level;
	private int maxLevel;
	
	private int lives;	
	private int maxNumberOfLives;
		
	private boolean immune;
	private long timeForImmunity;
	
	private Music levelUp = new Music("res/chest_fanfare.wav");
	private Music BGM = new Music("res/songofstorms.wav");
	
	private LinkedList<Bullet> listOfShots;
	
	private int stage;
	
	public int getStage() {
		return stage;
	}
	
	public void setStage(int stage) {
		this.stage = stage;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Player(float x, float y, int width, int height, Image sprite, boolean[][] blocked, float horizontalSpeed) throws SlickException {
		super(x, y, width, height, sprite, blocked, horizontalSpeed);
		
		this.hDir = HorizontalDirection.RIGHT;
		
		listOfShots = new LinkedList<Bullet>();
		
		stage = 0;

		immune = false;
		
		level = 1;
		lives = level;
		
		maxLevel = 5;
		maxNumberOfLives = level;
		
		experienceGained = 0;
		experienceNeeded = experienceNeededList[level-1];
	}
	
	
	public void getAttacked() {		
		if(!immune) {
			lives--;
			makeImmune();
		}
		
		if(lives <= 0) {
			Game.entities.remove(this);
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
	
	public int[] getExperienceNeededList() {
		return experienceNeededList;
	}
	public int getExperienceNeeded(){
		return experienceNeeded;
	}

	public float getVerticalSpeed() {
		return verticalSpeed;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	@Override
	public void update(GameContainer gc, int mapWidth, int mapHeight, int delta) throws SlickException {
		
		/**
		 * How much the character moved
		 */
		if(!levelUp.playing() && !BGM.playing()){
			BGM.play();
		}
		Vector2f trans = new Vector2f(0, 0); 
		Input input = gc.getInput();
		fall(delta);
		
		if(isOnGround()) {
		}
	    
		if (input.isKeyDown(Input.KEY_UP)) {
				jump();
		}
 		if (input.isKeyDown(Input.KEY_DOWN)) {	
				duck();			
		}
 		if (input.isKeyDown(Input.KEY_RIGHT)) {
			trans.x = moveRight(delta);
		} 
		if (input.isKeyDown(Input.KEY_LEFT)) {
			trans.x = moveLeft(delta);			
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
	}
	
	public void gainExperience() {
		if(level < maxLevel)
		experienceGained += 20;
		
		if(experienceGained >= experienceNeeded && level < maxLevel) {
			level++;
			experienceGained = experienceGained - experienceNeeded;
			
			lives++;
			maxNumberOfLives = level;
			levelUp.play(); 
		}
	}
	

	public void shoot() throws SlickException {
		/** Where the bullet spawns **/
		float bulletStartPosition_X;
		float bulletStartPosition_Y = pos.y + width/2; //In the middle of the character's height
		
		if(hDir == HorizontalDirection.LEFT) 
			bulletStartPosition_X = pos.x - 1;
		else 
			bulletStartPosition_X = pos.x + width + 1;
		
		Image sprite = new Image("res/testshot.png");
		Game.entities.add(new Bullet(bulletStartPosition_X, bulletStartPosition_Y, sprite.getWidth(), sprite.getHeight(), sprite, blocked, hDir, 0.5f, this));
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

	
	public LinkedList<Bullet> getBullets() throws SlickException{
		return listOfShots;
	}
	public void duck() {
		if(!isOnGround()){ 
			return;
        }
	}
	
}
