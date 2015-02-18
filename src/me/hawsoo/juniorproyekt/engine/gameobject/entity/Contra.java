package me.hawsoo.juniorproyekt.engine.gameobject.entity;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;

import me.hawsoo.juniorproyekt.Game;
import me.hawsoo.juniorproyekt.engine.gameobject.entity.animation.keyframe.Keyframe;
import me.hawsoo.juniorproyekt.engine.gameobject.entity.animation.keyframe.KeyframeManager;
import me.hawsoo.juniorproyekt.res.Resources;
import me.hawsoo.juniorproyekt.util.drawing.DrawUtils;
import me.hawsoo.juniorproyekt.util.drawing.Rect;

/** BETA object
 * This represents a weapon that
 * is a hunk of metal dubbed 'contra'.
 * @author Administrator
 *
 */
public class Contra extends Entity
{
	private Player player;
	private Rect collisionMap = new Rect(Resources.tileSize, Resources.tileSize * 2, -(Resources.tileSize / 2), -Resources.tileSize);		// BETA for collision mapping
	private float xoff, yoff;
	
	// Components
	private Direction currentfacing;
	private float facingAngle;
	private int flipFacingWaited = 0;
	private boolean currentlyFlipping = false;
	
	private int orientation = 0;					// Orient. 0: parade rest; O1: attention; O2: on shoulder
	private static final int MAX_ORIENTATION = 2;
	private static final int MIN_ORIENTATION = 0;
	private boolean prevChangedOrientation = false;	// Tracks up/down registers
	
	private KeyframeManager manager;
	
	private int aniStage = 0;						// Stage 0: parade rest; S1: attention; S2: on shoulder; S-1: in-between
	private int aniTargetStage = 0;
	private Keyframe stage2, stage1, stage0;
	
	private static final int STAGE_STEPS = 15;		// Number of steps it takes to get to another stage
	private int stageWaited = 0;
	private Keyframe from, to;
	private float fromDeltaX, fromDeltaY, fromDeltaZangle;
	
	
	/**
	 * Creates a contra (dependency
	 * to the player).
	 * @param player
	 */
	public Contra(Player player)
	{
		super(0, 0, null);
		this.player = player;
		
		currentfacing = facing = this.player.facing;
		if (facing == Direction.LEFT)
		{
			// Init in left
			facingAngle = 180;
		}
		else if (facing == Direction.RIGHT)
		{
			// Init in right
			facingAngle = 0;
		}
		
		Rectangle bounds = player.getBounds(0, 0);
		stage2 = new Keyframe(/*bounds.width / 2*/0, bounds.height, 95 + 360);
		stage1 = new Keyframe(/*bounds.width + */(getBounds(0, 0).width/* / 2*/), bounds.height, 0);
//		stage0 = new Keyframe(/*bounds.width + */(getBounds(0, 0).width/* / 2*/), getBounds(0, 0).height / 2, 0);		// BETA parade rest
		stage0 = new Keyframe(/*-bounds.width / 2*/0, bounds.height / 3, -85);		// BETA alt. parade rest
		
		manager = new KeyframeManager(
				new ArrayList<Keyframe>(
						Arrays.asList(stage0, stage1, stage2)
						)
						);
	}

	@Override
	public Rectangle getBounds(int xoff, int yoff)
	{
		return collisionMap.getAWTrect((int)x + xoff, (int)y + yoff);
	}
	
	@Override
	public void update()
	{
		if (!currentlyFlipping) facing = player.facing;
		if (facing != currentfacing)
		{
			// Lock animation
			currentlyFlipping = true;
			
			// Move to new facing direction
			float flipRange = 180;
			if (currentfacing == Direction.LEFT)
			{
				// Check if is first time
				if (facingAngle == 180) flipFacingWaited = 0;
				
				// Flip partially
				float deltaFlip = flipRange / STAGE_STEPS;
				facingAngle -= deltaFlip;
			}
			else if (currentfacing == Direction.RIGHT)
			{
				// Check if is first time
				if (facingAngle == 0) flipFacingWaited = 0;
				
				// Flip partially
				float deltaFlip = flipRange / STAGE_STEPS;
				facingAngle -= deltaFlip;
			}
			
			// Check if done
			flipFacingWaited++;
			if (flipFacingWaited >= STAGE_STEPS)
			{
				// Break out
				currentfacing = facing;
				currentlyFlipping = false;
				
				// Reset angles
				if (facing == Direction.LEFT)
				{
					facingAngle = 180;
				}
				else if (facing == Direction.RIGHT)
				{
					facingAngle = 0;
				}
			}
		}
		
		// Change orientation if wanted
		int orientationChangeRequest = 0;
		if (Game.controllers.get(Game.PLAYER_ONE).up) orientationChangeRequest = 1;
		else if (Game.controllers.get(Game.PLAYER_ONE).down) orientationChangeRequest = -1;
		
		boolean orientationChangeRequested = orientationChangeRequest != 0;
		if (orientationChangeRequested && !prevChangedOrientation)
		{
			// Change and clamp
			orientation += orientationChangeRequest;
			
			int newOrient;
			if (orientation == (newOrient = Math.max(MIN_ORIENTATION, Math.min(MAX_ORIENTATION, orientation))))
			{
				// Means nothing has changed; it passed the clamping test
				manager.changeKeyframe(orientation, STAGE_STEPS);
			}
			else
			{
				// Something changed, pull orig. variable back
				orientation = newOrient;
			}
		}
		
		// Update animation
		Keyframe newKeyframe = manager.updateAnimation();
		xoff = newKeyframe.xoff;
		yoff = newKeyframe.yoff;
		zAngle = newKeyframe.zAngle;
		
		// Update lagging variables
		prevChangedOrientation = orientationChangeRequested;
	}
	
	@Override
	public void render()
	{
		// BETA: Render a rectangle (green when neut.,
		// red when attacking).
		DrawUtils.setColorRGB(0, 255, 0);
//		collisionMap.drawRect((int)Math.round(x + xoff), (int)Math.round(y + yoff), this, true);
		
//		yAngle = -90;
		// Set orientation
//		applyTransformations(player.x/* + (xoff * facing.getMult())*/, player.y + yoff);
		// Normalize drawing area
		glTranslatef(player.x, player.y, 0);
		glRotatef(facingAngle, 0, 1, 0);
		glTranslatef(xoff, yoff, 0);
		glRotatef(xAngle, 1, 0, 0);
		glRotatef(yAngle, 0, 1, 0);
		glRotatef(zAngle, 0, 0, 1);
		
		// Correct model
		float scale = 5;
		glTranslatef(Resources.tileSize / 2, -Resources.tileSize, -10);
		glRotatef(-90, 0, 1, 0);
		glScalef(scale, scale, scale);
		{
			// Render model
			Resources.contra.render();
		}
		// Take off transformations
//		glScalef(-scale, -scale, -scale);
//		glTranslatef(-Resources.tileSize / 2, Resources.tileSize, 10);
//		
//		// Take off orientation
//		glRotatef(-90, 0, -1, 0);
//		takeOffTransformations(player.x + (xoff * facing.getMult()), player.y + yoff);
	}
}
