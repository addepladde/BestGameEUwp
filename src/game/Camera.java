package game;

import org.newdawn.slick.Graphics;

public class Camera {

	private int transX, transY;
	private int mapWidth, mapHeight;

	public Camera(Map map, int mapWidth, int mapHeight) {
		transX = 0;
		transY = 0;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
	}

	public void translate (Graphics g, Player player) {

		if(player.getX() - Game.WIDTH/2 < 0)     		
			transX = 0; 
		else if(player.getX() + Game.WIDTH/2 > mapWidth)
			transX = -mapWidth + Game.WIDTH;
		else
			transX = (int) -player.getX() + Game.WIDTH/2;

		if(player.getY()-Game.HEIGHT/2 < 0)     		
			transY = 0;     
		else if(player.getY()+Game.HEIGHT/2 > mapHeight)
			transY = -mapHeight+Game.HEIGHT;
		else
			transY = (int) -player.getY() + Game.HEIGHT/2;

		g.translate(transX, transY);

	}
}