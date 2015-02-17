package me.hawsoo.juniorproyekt.engine.gameobject.entity.animation.keyframe;

/**
 * This contains keyframe data.
 * @author Administrator
 *
 */
public class Keyframe
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
	 * Finds the delta values for the next step of the frame.
	 * @param from - the from state
	 * @param to - the target state
	 * @param stageWaited - the current frame
	 * @param totalSteps - the number of frames
	 * @return the next keyframe
	 */
	public static Keyframe findDeltaFrame(Keyframe from, Keyframe to, int totalSteps)
	{
		// Calculate the total values
		float totalX = to.xoff - from.xoff;
		float totalY = to.yoff - from.yoff;
		float totalZangle = to.zAngle - from.zAngle;
		
		// Calculate delta values
		float deltaX = totalX / totalSteps;
		float deltaY = totalY / totalSteps;
		float deltaZangle = totalZangle / totalSteps;
		
		// Spit out
		return new Keyframe(deltaX, deltaY, deltaZangle);
	}
}
