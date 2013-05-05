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
	}	
	
	public void fillExperienceBar(GameContainer gc, Graphics g, Player player){
		
		int experienceBarWidth = 250;
		int transX;	
		
		/** To calculate the position of the experience bar and health points **/			
		if(player.getX()-Game.WIDTH/2 < 0)     	
			transX = 0;     
		else if(player.getX() + Game.WIDTH/2 > mapWidth)
			transX = -mapWidth + Game.WIDTH;
		else
			transX = (int) -player.getX()+Game.WIDTH/2;
		
		experienceBar = new Rectangle(gc.getWidth()/2-transX, 30, experienceBarWidth, 20);
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
			g.drawString("Experience to level " + (player.getLevel() + 1), experienceBar.getX()+25, experienceBar.getY());
		
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
		float x = (float) (experienceBar.getX() + experienceBar.getWidth() + 10), y = 28;
		for(Image image : livesArray){
			image.draw(x, y, 0.3f);
			x+=25;
		}
		
		/** Emptying the list **/
		livesArray = new LinkedList<Image>();
	}
}