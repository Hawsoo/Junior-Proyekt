package me.hawsoo.juniorproyekt.engine.gameobject.entity;

import java.awt.Rectangle;

import static org.lwjgl.opengl.GL11.*;
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
	public static Keyframe process(Keyframe from, Keyframe to, int stageWaited, int stageSteps)
	{
		// Calculate the total values
		float totalX = to.xoff - from.xoff;
		float totalY = to.yoff - from.yoff;
		float totalZangle = to.zAngle - from.zAngle;
		
		// Calculate delta values
		float deltaX = totalX / stageSteps/* * stageWaited*/;
		float deltaY = totalY / stageSteps/* * stageWaited*/;
		float deltaZangle = totalZangle / stageSteps/* * stageWaited*/;
		
		// Spit out
		return new Keyframe(deltaX, deltaY, deltaZangle);
	}
}

/**
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
		
		Rectangle bounds = player.getBounds(0, 0);
		stage2 = new Keyframe(bounds.width / 2, bounds.height, 95);
		stage1 = new Keyframe(bounds.width + (getBounds(0, 0).width / 2), bounds.height, 0);
//		stage0 = new Keyframe(bounds.width + (getBounds(0, 0).width / 2), getBounds(0, 0).height / 2, 0);		// BETA parade rest
		stage0 = new Keyframe(bounds.width / 2, getBounds(0, 0).height / 3, -90);		// BETA alt. parade rest
	}

	@Override
	public Rectangle getBounds(int xoff, int yoff)
	{
		return collisionMap.getAWTrect((int)x + xoff, (int)y + yoff);
	}
	
	@Override
	public void update()
	{
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
			Keyframe currentframe = Keyframe.process(from, to, stageWaited, STAGE_STEPS);
			fromDeltaX += currentframe.xoff;
			fromDeltaY += currentframe.yoff;
			fromDeltaZangle += currentframe.zAngle;
			
			// Add onto basic animation
			Rectangle bounds = player.getBounds(0, 0);
			xoff = bounds.x + from.xoff + fromDeltaX;
			yoff = bounds.y + from.yoff + fromDeltaY;
			zAngle = from.zAngle + fromDeltaZangle;
			
			// If stage has been gone thru enough, then switch
			if (stageWaited >= STAGE_STEPS) aniStage = aniTargetStage;
		}
		
		// On shoulder
		if (aniStage == 2)
		{
			Rectangle bounds = player.getBounds(0, 0);
			xoff = bounds.x + stage2.xoff;
			yoff = bounds.y + stage2.yoff;
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
			Rectangle bounds = player.getBounds(0, 0);
			xoff = bounds.x + stage1.xoff;
			yoff = bounds.y + stage1.yoff;
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
			Rectangle bounds = player.getBounds(0, 0);
			xoff = bounds.x + stage0.xoff;
			yoff = bounds.y + stage0.yoff;
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
		applyTransformations(x + xoff, y + yoff);
		glRotatef(-90, 0, 1, 0);
		
		// Correct model
		float scale = 5;
		glTranslatef(Resources.tileSize / 2, -Resources.tileSize, -10);
		glScalef(scale, scale, scale);
		{
			// Render model
			Resources.contra.render();
		}
		// Take off transformations
		glScalef(-scale, -scale, -scale);
		glTranslatef(-Resources.tileSize / 2, Resources.tileSize, 10);
		
		// Take off orientation
		glRotatef(-90, 0, -1, 0);
		takeOffTransformations(x + xoff, y + yoff);
	}
}
