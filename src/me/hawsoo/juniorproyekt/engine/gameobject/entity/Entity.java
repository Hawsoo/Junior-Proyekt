package me.hawsoo.juniorproyekt.engine.gameobject.entity;

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
	double x, y, hspeed, vspeed;
	double movespeed, friction;
	int maxHspeed, maxClimbHeight;
	List<Ground> grounds;
	
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
	
	// Getters
	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getHspeed()
	{
		return hspeed;
	}

	public double getVspeed()
	{
		return vspeed;
	}

	public double getFriction()
	{
		return friction;
	}
	
	public int getMaxClimbHeight()
	{
		return maxClimbHeight;
	}

	// Setters
	public void setX(double x)
	{
		this.x = x;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public void setHspeed(double hspeed)
	{
		this.hspeed = hspeed;
	}

	public void setVspeed(double vspeed)
	{
		this.vspeed = vspeed;
	}

	public void setFriction(double friction)
	{
		this.friction = friction;
	}
	
	public void setMaxClimbHeight(int maxClimbHeight)
	{
		this.maxClimbHeight = maxClimbHeight;
	}
}
