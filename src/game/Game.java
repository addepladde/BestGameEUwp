package game;

import java.util.LinkedList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
 

/**
 * TODO
 * 
 * tr�ffa med skott
 * 
 * kunna hoppa p� fiender s� att de d�r
 * 
 * kunna d�
 * 
 * statebased game
 *
 * d�p om creature till entity, l�t all �rva fr�n den, bulet ocks�
 * 
 * rendera i andra klasser �n game? hur
 * 
 * 
 * state based game!!! yolo brother swaginator
 * 
 * @author addewp
 *
 */
public class Game extends BasicGame {
 
	//Constants
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 320;
 
	private static final boolean fullscreen = false;
	private static final boolean showFPS = true;
	private static final String title = "BestGameEUwp";
	private static final int fpslimit = 60;
 
	//Variables
	public static final float GRAVITY = 1f;
	private Map map; 				//The file that contain the world we are
	private Player player; 			//The moving entity we will follow
	private Camera camera; 
	private int mapHeight, mapWidth; 
	private boolean[][] blocked; 	//Contains information whether the tile at x, y can be walked through
	Monster monster;	
	private static GUI gui;			//The graphical user interface, containing experience bar etc.
 
	
	public Game(String title) {
		super(title);
	}
	public void init(GameContainer gc) throws SlickException {
		map = new Map("res/banatillmig.tmx");
		mapWidth = map.getWidth() * map.getTileWidth(); // Map size = Tile Size * number of Tiles
		mapHeight = map.getHeight() * map.getTileHeight();
		blocked = map.getBlockedTiles();
		
		
		player = new Player(1*32, 2*32, 32, 32, new Image("res/emo.png"), blocked);
		monster = new Monster(20*32, 2*32, 32, 32, new Image("res/emo.png"), 0.1f, blocked);
		
		camera = new Camera(map, mapWidth, mapHeight);	
		gui = new GUI(player, map);
	}
 
	public void update(GameContainer gc, int delta) throws SlickException {
		monster.update(player, delta);
		player.update(gc, mapWidth, mapHeight, delta);
		
		
		//FULT- m�ste fixas
		if((System.currentTimeMillis() - player.getTimeForImmunity()) > 3000) {
			player.setImmune(false);
		}
	}
 
	public void render(GameContainer gc, Graphics g) throws SlickException {
		camera.centerAroundPlayer(g, player); 
		map.render(0, 0);
		player.render();

		gui.render(gc, g, player);
		
		LinkedList<Bullet> bl = player.getBullets();
		for(Bullet bullet : bl)
			bullet.render();
		monster.render();
		
	}
 
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Game(title));
		app.setDisplayMode(WIDTH, HEIGHT, fullscreen);
		app.setTargetFrameRate(fpslimit);
		app.setVSync(true);
		app.setShowFPS(showFPS);
		app.start();
	}
}