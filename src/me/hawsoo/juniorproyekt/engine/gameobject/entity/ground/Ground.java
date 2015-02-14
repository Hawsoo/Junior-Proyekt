package me.hawsoo.juniorproyekt.engine.gameobject.entity.ground;

import java.awt.Rectangle;

import me.hawsoo.juniorproyekt.engine.gameobject.entity.Entity;

/**
 * This is the representation
 * of ground to an entity.
 * @author Administrator
 *
 */
public abstract class Ground extends Entity
{
	/**
	 * Creates a ground.
	 * @param x
	 * @param y
	 */
	public Ground(int x, int y)
	{
		super(x, y, null);
	}

	@Override
	public void update() {}
	
	@Override
	public Rectangle getBounds(int xoff, int yoff) { return null; }
	
	/**
	 * Gets if <code>bounds</code> is colliding.
	 * @param bounds - a bounding box
	 * @return if it is colliding
	 */
	public abstract boolean isColliding(Rectangle bounds);
}
