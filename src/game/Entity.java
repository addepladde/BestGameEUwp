package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity {
	protected Vector2f pos; // Vector contains a value with components x &amp; y
	protected Image sprite;
	protected float walkingSpeed;
	protected float verticalSpeed;
	protected boolean[][] blocked;
	protected int width, height;
	protected HorizontalDirection hDir;
	protected VerticalDirection vDir;

	public Entity(float x, float y, int width, int height, Image sprite, boolean[][] blocked) {		pos = new Vector2f(x,y);
	this.width = width;
	this.height = height;
	this.sprite = sprite;
	this.blocked = blocked;
	verticalSpeed = 1f;
	vDir = VerticalDirection.DOWN;
	}
}