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
 * @author Hawsoo
 *
 */
public class Player extends Entity
{
	// Components
	private Rect collisionMap = new Rect(Resources.tileSize, Resources.tileSize * 2, -(Resources.tileSize / 2), 0);		// BETA for collision mapping
	private double gravityForce = 1;
	private boolean prevJump = false;
	
	// Weapons
	private Contra weapon0 = new Contra(this);
	
	/**
	 * Sets up a player
	 * @param x
	 * @param y
	 * @param grounds
	 */
	public Player(int x, int y, List<Ground> grounds)
	{
		super(x, y, grounds);
		
		movespeed = 0.65f;
		friction = 0.5f;
		maxHspeed = 10;
		maxClimbHeight = 2;
		facing = Direction.RIGHT;
	}
	
	@Override
	public void update()
	{
		// Input Movement
		boolean moved = false;
		if (Game.controllers.get(Game.PLAYER_ONE).left && !Game.controllers.get(Game.PLAYER_ONE).right)
		{
			hspeed -= movespeed;
			facing = Direction.LEFT;
			moved = true;
		}
		else if (Game.controllers.get(Game.PLAYER_ONE).right && !Game.controllers.get(Game.PLAYER_ONE).left)
		{
			hspeed += movespeed;
			facing = Direction.RIGHT;
			moved = true;
		}
		
		// Clamp movement
		hspeed = Math.max(-maxHspeed, Math.min(maxHspeed, hspeed));
		
		// If in the air...
		if (!EntityUtils.isColliding(getBounds(0, -1), grounds))
		{
			// Impose gravity; LATER make a 'gravitypoint' thing for magnet fields
			vspeed -= gravityForce;
			
			// Jump lower if jump button is released
			if (vspeed > 0 && !Game.controllers.get(Game.PLAYER_ONE).jump)
			{
				vspeed -= gravityForce;
			}
		}
		// If on the ground...
		else
		{
			// Jump
			if (Game.controllers.get(Game.PLAYER_ONE).jump && !prevJump)
			{
				vspeed = 20;
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
		
		// Update children
		weapon0.update();
		
		// Update lagging variables
		prevJump = Game.controllers.get(Game.PLAYER_ONE).jump;
	}

	@Override
	public void render()
	{
		// BETA render a white rectangle
		DrawUtils.setColorRGB(255, 255, 255);
		collisionMap.drawRect((int)Math.round(x), (int)Math.round(y), this, true);
		
		// Render children
		weapon0.render();
	}

	@Override
	public Rectangle getBounds(int xoff, int yoff)
	{
		return collisionMap.getAWTrect((int)x + xoff, (int)y + yoff);
	}
	
}
