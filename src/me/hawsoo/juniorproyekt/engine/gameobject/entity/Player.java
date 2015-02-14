package me.hawsoo.juniorproyekt.engine.gameobject.entity;

import java.awt.Rectangle;
import java.util.List;

import me.hawsoo.juniorproyekt.Game;
import me.hawsoo.juniorproyekt.engine.gameobject.entity.ground.Ground;
import me.hawsoo.juniorproyekt.res.Resources;
import me.hawsoo.juniorproyekt.util.drawing.DrawUtils;
import me.hawsoo.juniorproyekt.util.drawing.Rect;
import me.hawsoo.juniorproyekt.util.entity.EntityUtils;

/**
 * This is the player of the game.
 * @author Administrator
 *
 */
public class Player extends Entity
{
	// Components
	private Rect collisionMap = new Rect(Resources.tileSize, Resources.tileSize * 2);		// BETA for collision mapping
	private double gravityForce = 1;
	private boolean prevJump = false;
	
	/**
	 * Sets up a player
	 * @param x
	 * @param y
	 * @param grounds
	 */
	public Player(int x, int y, List<Ground> grounds)
	{
		super(x, y, grounds);
		
		movespeed = 0.35;
		friction = 0.25;
		maxHspeed = 5;
		maxClimbHeight = 2;
	}
	
	@Override
	public void update()
	{
		// Input Movement
		boolean moved = false;
		if (Game.controllers.get(Game.PLAYER_ONE).left && !Game.controllers.get(Game.PLAYER_ONE).right)
		{
			hspeed -= movespeed;
			moved = true;
		}
		else if (Game.controllers.get(Game.PLAYER_ONE).right)
		{
			hspeed += movespeed;
			moved = true;
		}
		
		// Clamp movement
		hspeed = Math.max(-maxHspeed, Math.min(maxHspeed, hspeed));
		
		// If on the ground...
		if (!EntityUtils.isColliding(getBounds(0, -1), grounds))
		{
			// Impose gravity; LATER make a 'gravitypoint' thing for magnet fields
			vspeed -= gravityForce;
		}
		// If in the air...
		else
		{
			// Jump
			if (Game.controllers.get(Game.PLAYER_ONE).jump && !prevJump)
			{
				System.out.println("Oui, j'ai fait caca.");
				vspeed = 15;
			}
			
			// If hasn't moved...
			if (!moved)
			{
				// Apply friction
				if (hspeed < 0)
				{
					hspeed += friction;
					if (hspeed > 0) hspeed = 0;
				}
				else if (hspeed > 0)
				{
					hspeed -= friction;
					if (hspeed < 0) hspeed = 0;
				}
			}
		}
		
		// Move and collide
		EntityUtils.move(this, grounds);
		
		// Update lagging variables
		prevJump = Game.controllers.get(Game.PLAYER_ONE).jump;
	}

	@Override
	public void render()
	{
		// BETA render a black rectangle
		DrawUtils.setColorRGB(0, 0, 0);
		collisionMap.drawRect((int)Math.round(x - (Resources.tileSize / 2)), (int)Math.round(y), true);
	}

	@Override
	public Rectangle getBounds(int xoff, int yoff)
	{
		// Return with origin at bottom-middle
		return collisionMap.getAWTrect((int)x - (collisionMap.width / 2) + xoff, (int)y + yoff);
	}
	
}