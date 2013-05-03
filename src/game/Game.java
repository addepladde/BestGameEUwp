package game;

import java.util.LinkedList;
import java.util.Stack;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


/**
 * TODO
 * 
 * träffa med skott
 * 
 * kunna hoppa på fiender så att de dör
 * 
 * kunna dö
 * 
 * statebased game
 * 
 * rendera i andra klasser än game? hur
 * 
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
	public static LinkedList<Entity> entities;

	public Game(String title) {
		super(title);
	}
	public void init(GameContainer gc) throws SlickException {
		map = new Map("res/banatillmig.tmx");
		mapWidth = map.getWidth() * map.getTileWidth(); // Map size = Tile Size * number of Tiles
		mapHeight = map.getHeight() * map.getTileHeight();
		blocked = map.getBlockedTiles();

		entities = new LinkedList<Entity>();

		entities.add(new Player(1*32, 2*32, 32, 32, new Image("res/emo.png"), blocked));
		entities.add(new Monster(20*32, 2*32, 32, 32, new Image("res/emo.png"), 0.1f, blocked));

		player = (Player) entities.get(0);

		camera = new Camera(map, mapWidth, mapHeight);	
		gui = new GUI(player, map);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		//monster.update(player, delta);
		//player.update(gc, mapWidth, mapHeight, delta);

		for(int i = 0; i < entities.size(); i++) {
			for(int j = 0; j < entities.size(); j++) {
				entities.get(i).update(player, delta);
				entities.get(i).update(gc, mapWidth, mapHeight, delta);
				entities.get(i).update(delta);
				entities.get(i).checkCollisions(entities.get(j));
			}
		}

		//FULT- måste fixas
		if((System.currentTimeMillis() - player.getTimeForImmunity()) > 3000) {
			player.setImmune(false);
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		camera.centerAroundPlayer(g, player); 
		map.render(0, 0);
		//player.render();

		gui.render(gc, g, player);

		LinkedList<Bullet> bl = player.getBullets();
		for(Bullet bullet : bl)
			bullet.render();

		for(Entity e : entities) {
			e.render();
		}

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