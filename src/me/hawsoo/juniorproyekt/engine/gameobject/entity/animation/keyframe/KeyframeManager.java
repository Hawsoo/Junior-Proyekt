package me.hawsoo.juniorproyekt.engine.gameobject.entity.animation.keyframe;

import java.util.ArrayList;

/**
 * This class manages keyframe
 * animations and timing and such.
 * @author Administrator
 *
 */
public class KeyframeManager
{
	// Components
	private ArrayList<Keyframe> keyframes;
	
	// Current frame components
	private int stageSteps = 1;							// This is per-stage, not total
	private int stageWaited = 0;						// How much time waited already
	private int actualKeyframeID = 0;					// This keeps track of progress
	private int targetKeyframeID = 0;					// This is the over-arching goal
	private Keyframe deltaFrameAccumulation;			// Total delta movements accumulated during the animation
	
	private boolean inbetweenAnimations = false;		// Indicates if ready for another animation or not
	private int requestedKeyframeID = 0;				// This keeps animations from skipping
	private boolean newKeyframeRequested = false;
	
	/**
	 * Sets up the manager.
	 */
	public KeyframeManager(ArrayList<Keyframe> keyframes)
	{
		this.keyframes = keyframes;
		deltaFrameAccumulation = new Keyframe(0, 0, 0);
	}
	
	/**
	 * Adds a keyframe.
	 * @param frame - the keyframe
	 * @return the id of the keyframe
	 */
	public int addKeyframe(Keyframe frame)
	{
		keyframes.add(frame);
		return keyframes.size() - 1;
	}
	
	/**
	 * Changes the keyframe target.
	 * @param id - the ID of the keyframe
	 */
	public void changeKeyframe(int id, int stageSteps)
	{
		if (!inbetweenAnimations)
		{
			targetKeyframeID = id;
		}
		else
		{
			newKeyframeRequested = true;
			requestedKeyframeID = id;
		}
		
		this.stageSteps = stageSteps;
	}
	
	/**
	 * Updates the animation (if any), and
	 * then returns the current keyframe.
	 * @return the current keyframe
	 */
	public Keyframe updateAnimation()
	{
		// Find target keyframe direction
		int next = 0;
		if (targetKeyframeID > actualKeyframeID)
		{
			next = 1;
		}
		else if (targetKeyframeID < actualKeyframeID)
		{
			next = -1;
		}
		
		// Calculate keyframe offsets...
		if (next != 0)
		{
			inbetweenAnimations = true;
			
			// Get next frame
			Keyframe fromKeyframe = keyframes.get(actualKeyframeID);
			Keyframe deltaFrame = Keyframe.findDeltaFrame(fromKeyframe, keyframes.get(actualKeyframeID + next), stageSteps);
			
			// Add onto basic animation
			deltaFrameAccumulation.xoff += deltaFrame.xoff;
			deltaFrameAccumulation.yoff += deltaFrame.yoff;
			deltaFrameAccumulation.zAngle += deltaFrame.zAngle;
			
			// If stage has been gone thru enough, then switch
			stageWaited++;
			if (stageWaited >= stageSteps)
			{
				// Reset
				deltaFrameAccumulation = new Keyframe(0, 0, 0);
				stageWaited = 0;
				
				// Get new keyframe if requested
				if (newKeyframeRequested)
				{
					newKeyframeRequested = false;
					targetKeyframeID = requestedKeyframeID;
				}
				
				// Switch ahead
				actualKeyframeID += next;
				return keyframes.get(actualKeyframeID);
			}
			
			// Return accumulated delta values with original keyframe offset
			return new Keyframe(deltaFrameAccumulation.xoff + fromKeyframe.xoff, deltaFrameAccumulation.yoff + fromKeyframe.yoff, deltaFrameAccumulation.zAngle + fromKeyframe.zAngle);
		}
		// Offsets are unnecessary...
		else
		{
			inbetweenAnimations = false;
			
			// Get the original keyframe
			return keyframes.get(actualKeyframeID);
		}
	}
}
