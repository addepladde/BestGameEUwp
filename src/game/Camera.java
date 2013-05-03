package game;
 
import org.newdawn.slick.Graphics;
 
public class Camera {
 
	private int transX, transY;
	private int mapWidth, mapHeight;
	
	public Camera(Map map, int mapWidth, int mapHeight) {
		transX = 0;
		transY = 0;
		this.mapWidth = mapWidth;
		this.setMapHeight(mapHeight);
	}
	
	/** Center the camera around the player **/
	public void centerAroundPlayer(Graphics g, Player player) {
		/** If close to the left side edge of the map **/
		if(player.getX() - Game.WIDTH/2 < 0)     	
			transX = 0;     
		
		/** If close to the right side edge of the map **/
		else if(player.getX() + Game.WIDTH/2 > mapWidth)
			transX = -mapWidth + Game.WIDTH;
		
		/** If not close to any of the edges **/
		else
			transX = (int) -player.getX() + Game.WIDTH/2;
 
    	g.translate(transX, transY); 
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}
}