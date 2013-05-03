package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public class Bullet extends Entity {
	public Bullet(float x, float y, int width, int height, Image sprite, boolean[][] blocked, HorizontalDirection hDir) {
		super(x, y, width, height, sprite, blocked);
		this.hDir = hDir;  
	}
	
	public void render() {
		sprite.draw(pos.x, pos.y, 0.1f);
	}
	
	public void update(int delta) {
		
		Vector2f trans = new Vector2f(0, 0);
		
		trans.x = verticalSpeed * delta;
		
		if(hDir == HorizontalDirection.RIGHT)
			pos.x += trans.x;
		else
			pos.x -= trans.x;
	}

	public HorizontalDirection getDir() {
		return hDir;
	}

	public void setDir(HorizontalDirection dir) {
		this.hDir = dir;
	}

	public float getX() {
		return pos.x;
	}

	public void setX(int x) {
		this.pos.x = x;
	}

	public float getY() {
		return pos.y;
	}

	public void setY(int y) {
		this.pos.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Image getSprite() {
		return sprite;
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}
}
