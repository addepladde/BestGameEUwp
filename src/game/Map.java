package game;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Map {
	
	TiledMap map;
	private boolean[][] blocked;
	
	public Map(String fileName) throws SlickException {
		map = new TiledMap(fileName);
		
		blocked = new boolean[map.getWidth()][map.getHeight()];
		
		defineBlockedTiles();
	}
	
	/**
	 * Read the .tmx-file and define which tiles are collidable
	 */
	public void defineBlockedTiles() {
		for(int x = 0; x < map.getWidth(); x++) {
			for(int y = 0; y < map.getHeight(); y++) {
				int tileID = map.getTileId(x, y, 0);
                String value = map.getTileProperty(tileID, "Collidable", "false");
                if ("true".equals(value))
                {
                    blocked[x][y] = true;
                }			
			}
		}
	}
	
	public void render(int x, int y) {
		map.render(x, y);
	}
	
	public boolean[][] getBlockedTiles() {		
		return blocked;
	}
	
	public int getTileWidth() {
		return map.getTileWidth();
	}
	
	public int getTileHeight() {
		return map.getTileHeight();
	}

	public int getWidth() {
		return map.getWidth();
	}

	public int getHeight() {
		return map.getHeight();
	}

}
