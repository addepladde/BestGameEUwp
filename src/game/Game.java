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
 * 	göra så man kan få slut på ammo?
 * 
 *  göra så vissa monster kan skjuta kanske? va vet jag
 *  vissa med mer hp? boss-aktiga? 
 * 
 * statebased game - fixa menu och end
 * 
 * rendera i andra klasser än game? hur
 * 
 * fixa så monster inte collidar
 * 
 * fixa animations. animationklassen har jag tagit från thenewboston tutorial 10 på youtube.
 * 
 * @author addewp
 *
 */
public class Game extends BasicGame {

	//Constants
	public static final int WIDTH = 800;
	public static final int HEIGHT = 640;

	private static final boolean fullscreen = false;
	private static final boolean showFPS = true;
	private static final String title = "BestGameEUwp";
	private static final int fpslimit = 120;

	//Variables
	public static final float GRAVITY = 0.01f;
	private Map map; 				//The file that contain the world we are
	private Player player; 			//The moving entity we will follow
	private Camera camera; 
	private int mapHeight, mapWidth; 
	private boolean[][] blocked; 	//Contains information whether the tile at x, y can be walked through
	private static GUI gui;			//The graphical user interface, containing experience bar etc.
	public static LinkedList<Entity> entities;	//List of all moving entities in this game

	public Game(String title) {
		super(title);
	}
	public void init(GameContainer gc) throws SlickException {
		map = new Map("res/banatillmig.tmx");
		mapWidth = map.getWidth() * map.getTileWidth(); // Map size = Tile Size * number of Tiles
		mapHeight = map.getHeight() * map.getTileHeight();
		blocked = map.getBlockedTiles();

		entities = new LinkedList<Entity>();

		entities.add(new Player(1*32, 2*32, 32, 32, new Image("res/emo.png"), blocked, 0.2f));
		entities.add(new Monster(20*32, 2*32, 32, 32, new Image("res/emo.png"), blocked, 0.1f));
		entities.add(new Monster(19*32, 5*32, 32, 32, new Image("res/emo.png"), blocked, 0.1f));
		entities.add(new Monster(11*32, 3*32, 32, 32, new Image("res/emo.png"), blocked, 0.1f));
		entities.add(new Monster(15*32, 8*32, 32, 32, new Image("res/emo.png"), blocked, 0.1f));
		entities.add(new Monster(20*32, 2*32, 32, 32, new Image("res/emo.png"), blocked, 0.1f));
		entities.add(new Monster(21*32, 2*32, 32, 32, new Image("res/emo.png"), blocked, 0.1f));
		entities.add(new Monster(22*32, 2*32, 32, 32, new Image("res/emo.png"), blocked, 0.1f));
		entities.add(new Monster(17*32, 2*32, 32, 32, new Image("res/emo.png"), blocked, 0.1f));

		player = (Player) entities.get(0);

		camera = new Camera(map, mapWidth, mapHeight);	
		gui = new GUI(player, map);
	}

	public void update(GameContainer gc, int delta) throws SlickException {
		
		for(int i = 0; i < entities.size() ; i++) {
			
		/** Kanske ska göra if entities.get(i) instanceof Player 
		 *  (Player entities.get(i)).update(player, delta); lr ngt så slipper vi onödiga funktionsanrop
		 *  i stil med ((Player) this).getAttacked();	
		 */
			entities.get(i).update(player, delta);
			entities.get(i).update(gc, mapWidth, mapHeight, delta);
			entities.get(i).update(delta);
			
			for(int j = 0; j < entities.size(); j++) {
				
				if(i >= entities.size() || j >= entities.size())
					break;
				
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