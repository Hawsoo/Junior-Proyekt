package me.hawsoo.juniorproyekt.engine.gameobject.entity;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.Rectangle;
import java.util.List;

import me.hawsoo.juniorproyekt.engine.gameobject.GameObject;
import me.hawsoo.juniorproyekt.engine.gameobject.entity.ground.Ground;

/**
 * This class represents an
 * actual entity within the game.
 * @author Administrator
 *
 */
public abstract class Entity extends GameObject
{
	// Components
	float x, y, hspeed, vspeed;
	float movespeed, friction;
	int maxHspeed, maxClimbHeight;
	List<Ground> grounds;
	
	protected float xAngle = 0;				// The 'pitch'
	protected float yAngle = 0;				// The 'roll'
	protected float zAngle = 0;				// The 'angle'
	
	/**
//	 * Sets up an entity within a <code>GameState</code>
	 * @param x - the starting x position
	 * @param y - the starting y position
	 * @param grounds - the collisions in the room
	 */
	public Entity(int x, int y, List<Ground> grounds)
	{
		this.x = x;
		this.y = y;
		this.grounds = grounds;
	}
	
	/**
	 * Gets the bounds of an entity.
	 * @param xoff - an x offset
	 * @param yoff - a y offset
	 * @return the bounds
	 */
	public abstract Rectangle getBounds(int xoff, int yoff);
	
	/**
	 * Applies transformations for 
	 * the entity.
	 */
	public void applyTransformations(float x, float y)
	{
		glTranslatef(x, y, 0);
		glRotatef(getxAngle(), 1, 0, 0);
		glRotatef(getyAngle(), 0, 1, 0);
		glRotatef(getzAngle(), 0, 0, 1);
	}
	
	/**
	 * Takes off transformations for 
	 * the entity.
	 */
	public void takeOffTransformations(float x, float y)
	{
		glRotatef(getxAngle(), -1, 0, 0);
		glRotatef(getyAngle(), 0, -1, 0);
		glRotatef(getzAngle(), 0, 0, -1);
		glTranslatef(-x, -y, 0);
	}
	
	// Getters
	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public float getHspeed()
	{
		return hspeed;
	}

	public float getVspeed()
	{
		return vspeed;
	}

	public float getFriction()
	{
		return friction;
	}
	
	public int getMaxClimbHeight()
	{
		return maxClimbHeight;
	}
	
	public float getxAngle()
	{
		return xAngle;
	}

	public float getyAngle()
	{
		return yAngle;
	}

	public float getzAngle()
	{
		return zAngle;
	}

	// Setters
	public void setX(float x)
	{
		this.x = x;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public void setHspeed(float hspeed)
	{
		this.hspeed = hspeed;
	}

	public void setVspeed(float vspeed)
	{
		this.vspeed = vspeed;
	}

	public void setFriction(float friction)
	{
		this.friction = friction;
	}
	
	public void setMaxClimbHeight(int maxClimbHeight)
	{
		this.maxClimbHeight = maxClimbHeight;
	}
	
	public void setxAngle(float xAngle)
	{
		this.xAngle = xAngle;
	}

	public void setyAngle(float yAngle)
	{
		this.yAngle = yAngle;
	}

	public void setzAngle(float zAngle)
	{
		this.zAngle = zAngle;
	}
}
