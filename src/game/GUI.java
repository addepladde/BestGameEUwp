package game;

import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class GUI {
	
	Rectangle experienceBar, experienceGainedBar;
	LinkedList<Image> livesArray;
	Image heart, emptyHeart;
	Player player;
	int mapWidth, mapHeight;
	
	public GUI(Player player, Map map) throws SlickException{
		heart = new Image("res/heart.png");
		emptyHeart = new Image("res/emptyheart.png");
		livesArray = new LinkedList<Image>();
		
		this.player = player;
		fillLivesBar();
		
		mapWidth = map.getWidth() * map.getTileWidth(); // Map size = Tile Size * number of Tiles
		mapHeight = map.getHeight() * map.getTileHeight();
		
		
	}
	
	public void render(GameContainer gc, Graphics g, Player player){
		fillExperienceBar(gc, g, player);
		drawRamUsage(gc, g);
		fillLivesBar();
		drawLives(gc, g);
		drawStage(gc, g, player);
	}	
	
	public void drawStage(GameContainer gc, Graphics g, Player player) {
		
		int transX = getTranslationX();
		int transY = getTranslationY();
		
		g.setColor(Color.white);
		g.drawString("Stage: " + player.getStage(), -transX + 10, -transY + 25);

	}
	
	public void fillExperienceBar(GameContainer gc, Graphics g, Player player){
		
		int experienceBarWidth = 250;
		
		/** To calculate the position of the experience bar and health points **/			
		int transX = getTranslationX();
		int transY = getTranslationY();
		
		
		experienceBar = new Rectangle(gc.getWidth()/2 - transX, 30 - transY, experienceBarWidth, 20);
		float gainedExpPercent = (experienceBar.getWidth()/player.getExperienceNeeded() * player.getExperienceGained());
		experienceGainedBar = new Rectangle(experienceBar.getX(), experienceBar.getY(), gainedExpPercent, experienceBar.getHeight());
		
		
		/** Draw the experience bars **/
		g.setColor(Color.green);
		g.fill(experienceGainedBar);
		g.setColor(Color.black);
		g.draw(experienceBar);
		g.setColor(Color.red);
		
		
		/** Draw experience information **/
		if(player.getLevel() == player.getMaxLevel())
			g.drawString("You are at maximum level", experienceBar.getX()+20, experienceBar.getY());
		else
			g.drawString("Level " + (player.getLevel()), experienceBar.getX() + 100, experienceBar.getY());
		
	}
	
	
	public int getTranslationX()  {		
		if(player.getX() - Game.WIDTH/2 < 0)     	
			return 0;     
		else if(player.getX() + Game.WIDTH/2 > mapWidth)
			return -mapWidth + Game.WIDTH;
		else
			return (int) -player.getX()+Game.WIDTH/2;
	}
	
	public int getTranslationY() {
		if(player.getY()-Game.HEIGHT/2 < 0)     		
			return 0;  
		else if(player.getY()+Game.HEIGHT/2 > mapHeight)
			return -mapHeight+Game.HEIGHT;
		else
			return (int) -player.getY() + Game.HEIGHT/2;
	}
	
	private void drawRamUsage(GameContainer gc, Graphics g){
		g.setColor(Color.black);		
		g.drawString("MEM total(used):   " + (Runtime.getRuntime().totalMemory()/1000000) + "(" + ((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1000000) + ") MB", Game.WIDTH, Game.HEIGHT - 20);
	}
	
	
	/** Fill the list of lives left **/
	private void fillLivesBar(){
		for(int i = 0; i < player.getLives(); i++) {
			livesArray.add(heart);
		}
		for(int i = livesArray.size(); i<player.getMaxNumberOfLives(); i++) {
			livesArray.add(emptyHeart);
		}
	}
	
	/** Draw the hearts that indicate number of lives left **/
	private void drawLives(GameContainer gc, Graphics g){
		float x = (float) (experienceBar.getX() + experienceBar.getWidth() + 10), y = experienceBar.getY();
		for(Image image : livesArray){
			image.draw(x, y, 0.3f);
			x+=25;
		}
		
		/** Emptying the list **/
		livesArray = new LinkedList<Image>();
	}
}