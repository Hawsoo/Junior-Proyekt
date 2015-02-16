package me.hawsoo.juniorproyekt.engine.gameobject.entity;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.Rectangle;

import me.hawsoo.juniorproyekt.Game;
import me.hawsoo.juniorproyekt.res.Resources;
import me.hawsoo.juniorproyekt.util.drawing.DrawUtils;
import me.hawsoo.juniorproyekt.util.drawing.Rect;

/**
 * This contains keyframe data.
 * @author Administrator
 *
 */
class Keyframe
{
	// Components
	public float xoff;
	public float yoff;
	public float zAngle;
	
	/**
	 * Creates a keyframe for animations.
	 * @param xoff - the primitive xoff
	 * @param yoff - primitive yoff
	 * @param zAngle - the absolute zAngle
	 */
	public Keyframe(float xoff, float yoff, float zAngle)
	{
		super();
		this.xoff = xoff;
		this.yoff = yoff;
		this.zAngle = zAngle;
	}
	
	/**
	 * Finds the current frame.
	 * @param from - the from state
	 * @param to - the target state
	 * @param stageWaited - the current frame
	 * @param stageSteps - the number of frames
	 * @return
	 */
	public static Keyframe process(Keyframe from, Keyframe to, int stageSteps)
	{
		// Calculate the total values
		float totalX = to.xoff - from.xoff;
		float totalY = to.yoff - from.yoff;
		float totalZangle = to.zAngle - from.zAngle;
		
		// Calculate delta values
		float deltaX = totalX / stageSteps;
		float deltaY = totalY / stageSteps;
		float deltaZangle = totalZangle / stageSteps;
		
		// Spit out
		return new Keyframe(deltaX, deltaY, deltaZangle);
	}
}

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
		stage2 = new Keyframe(/*bounds.width / 2*/0, bounds.height, 95);
		stage1 = new Keyframe(/*bounds.width + */(getBounds(0, 0).width/* / 2*/), bounds.height, 0);
//		stage0 = new Keyframe(/*bounds.width + */(getBounds(0, 0).width/* / 2*/), getBounds(0, 0).height / 2, 0);		// BETA parade rest
		stage0 = new Keyframe(/*-bounds.width / 2*/0, bounds.height / 3, -85);		// BETA alt. parade rest
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
			orientation = Math.max(MIN_ORIENTATION, Math.min(MAX_ORIENTATION, orientation));
		}
		
		// In-between keyframes stage
		if (aniStage == -1)
		{
			stageWaited++;
			
			// Get current keyframe
			Keyframe currentframe = Keyframe.process(from, to, STAGE_STEPS);
			fromDeltaX += currentframe.xoff;
			fromDeltaY += currentframe.yoff;
			fromDeltaZangle += currentframe.zAngle;
			
			// Add onto basic animation
			xoff = from.xoff + fromDeltaX;
			yoff = from.yoff + fromDeltaY;
			zAngle = from.zAngle + fromDeltaZangle;
			
			// If stage has been gone thru enough, then switch
			if (stageWaited >= STAGE_STEPS) aniStage = aniTargetStage;
		}
		
		// On shoulder
		if (aniStage == 2)
		{
			xoff = stage2.xoff;
			yoff = stage2.yoff;
			zAngle = stage2.zAngle;
			
			// Find next place to be
			if (orientation == 2)
			{
				aniTargetStage = aniStage;
			}
			else if (orientation < 2)
			{
				aniTargetStage = 1;
				aniStage = -1;
				stageWaited = 0;
				
				from = stage2;
				to = stage1;
				
				fromDeltaX = 0;
				fromDeltaY = 0;
				fromDeltaZangle = 0;
			}
		}
		// Attention
		else if (aniStage == 1)
		{
			xoff = stage1.xoff;
			yoff = stage1.yoff;
			zAngle = stage1.zAngle;
			
			// Find next place to be
			if (orientation > 1)
			{
				aniTargetStage = 2;
				aniStage = -1;
				stageWaited = 0;
				
				from = stage1;
				to = stage2;
				
				fromDeltaX = 0;
				fromDeltaY = 0;
				fromDeltaZangle = 0;
			}
			else if (orientation < 1)
			{
				aniTargetStage = 0;
				aniStage = -1;
				stageWaited = 0;
				
				from = stage1;
				to = stage0;
				
				fromDeltaX = 0;
				fromDeltaY = 0;
				fromDeltaZangle = 0;
				
				// BETA
//				targetStage = 1;
//				aniStage = 1;
			}
			else
			{
				aniTargetStage = aniStage;
			}
		}
		// Parade rest
		else if (aniStage == 0)
		{
			xoff = stage0.xoff;
			yoff = stage0.yoff;
			zAngle = stage0.zAngle;
			
			// Find next place to be
			if (orientation == 0)
			{
				aniTargetStage = aniStage;				
			}
			else if (orientation > 0)
			{
				aniTargetStage = 1;
				aniStage = -1;
				stageWaited = 0;
				
				from = stage0;
				to = stage1;
				
				fromDeltaX = 0;
				fromDeltaY = 0;
				fromDeltaZangle = 0;
			}
		}
		
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
