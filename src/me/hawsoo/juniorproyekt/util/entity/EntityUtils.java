package me.hawsoo.juniorproyekt.util.entity;

import java.awt.Rectangle;
import java.util.List;

import me.hawsoo.juniorproyekt.engine.gameobject.entity.Entity;
import me.hawsoo.juniorproyekt.engine.gameobject.entity.ground.Ground;

/**
 * This is a set of convenience methods
 * for any entity.
 * @author Administrator
 *
 */
public class EntityUtils
{
	/**
	 * Gets if a rectangle
	 * is colliding with some
	 * grounds indicated.
	 * @param bounds - the rectangle bounds
	 * @param grounds - a list of all grounds to check collision to
	 * @return if the bounds are colliding with the grounds
	 */
	public static boolean isColliding(Rectangle bounds, List<Ground> grounds)
	{
		// See if any ground is colliding w/ bounds
		for (Ground ground : grounds)
		{
			if (ground.isColliding(bounds))
			{
				// Is colliding; return true
				return true;
			}
		}
		
		// Didn't end up colliding
		return false;
	}
	
	/**
	 * Takes entity's properties and
	 * moves the entity for them.
	 * @param entity
	 */
	public static void move(Entity entity, List<Ground> grounds)
	{
		/*
		 * Move vspeed
		 */
		for (int repititions = 0; repititions < Math.ceil(Math.abs(entity.getVspeed())); repititions++)
		{
			int vspDir = (int)Math.signum(entity.getVspeed());
			
			// Check if there is a collision
			if (isColliding(entity.getBounds(0, vspDir), grounds))
			{
				// Try to climb out of it
				boolean climbedOut = false;
				for (int i = 1; i <= entity.getMaxClimbHeight(); i++)
				{
					// Check if can go left
					if (!isColliding(entity.getBounds(-i, vspDir), grounds))
					{
						// Move
						entity.setX(entity.getX() - i);
						entity.setY(entity.getY() + vspDir);
						climbedOut = true;
					}
					// Check if can go right
					else if (!isColliding(entity.getBounds(i, vspDir), grounds))
					{
						// Move
						entity.setX(entity.getX() + i);
						entity.setY(entity.getY() + vspDir);
						climbedOut = true;
					}
					
					// Break if climbed out
					if (climbedOut) break;
				}
				
				// If it hasn't climbed out, then it's a wall
				if (!climbedOut) entity.setVspeed(0);
			}
			// If there is no collision
			else
			{
				// Keep moving
				entity.setY(entity.getY() + vspDir);
			}
		}
		
		/*
		 * Move Hspeed
		 */
		for (int repititions = 0; repititions < Math.ceil(Math.abs(entity.getHspeed())); repititions++)
		{
			int hspDir = (int)Math.signum(entity.getHspeed());
			
			// Check if there is a collision
			if (isColliding(entity.getBounds(hspDir, 0), grounds))
			{
				// Try to climb out of it
				boolean climbedOut = false;
				for (int i = 1; i <= entity.getMaxClimbHeight(); i++)
				{
					// Check if can go up
					if (!isColliding(entity.getBounds(hspDir, i), grounds))
					{
						// Move
						entity.setX(entity.getX() + hspDir);
						entity.setY(entity.getY() + i);
						climbedOut = true;
					}
					// Check if can go down
					else if (!isColliding(entity.getBounds(hspDir, -i), grounds))
					{
						// Move
						entity.setX(entity.getX() + hspDir);
						entity.setY(entity.getY() - i);
						climbedOut = true;
					}
					
					// Break if climbed out
					if (climbedOut) break;
				}
				
				// If it hasn't climbed out, then it's a wall
				if (!climbedOut) entity.setHspeed(0);
			}
			// If there is no collision
			else
			{
				// Try to climb down
				boolean climbedDown = false;
				for (int i = entity.getMaxClimbHeight(); i >= 1; i--)
				{
					// Check if colliding at the first spot
					if (isColliding(entity.getBounds(hspDir, -i - 1), grounds)
							&& !isColliding(entity.getBounds(hspDir, -i), grounds))
					{
						// Move
						entity.setX(entity.getX() + hspDir);
						entity.setY(entity.getY() - i);
						climbedDown = true;
					}
				}
				
				// Keep moving
				if (!climbedDown) entity.setX(entity.getX() + hspDir);
			}
		}
	}
}
