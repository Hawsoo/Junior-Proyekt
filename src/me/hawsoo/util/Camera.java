package me.hawsoo.util;

import me.hawsoo.res.Resources;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * This class is a camera that is used to
 * create a view matrix.
 * @author Administrator
 *
 */
public class Camera
{	
	private Vector2f position = new Vector2f(0, 0);
	private float xAngle = 0;				// The 'pitch'
	private float yAngle = 0;				// The 'roll'
	private float zAngle = 0;				// The 'angle'

	/**
	 * Gets the position (position is shown
	 * as the point being the middle, so if
	 * 0, 0, 0 were the point, then 0, 0, 0
	 * would be the exact middle).
	 * @return the position of the camera
	 */
	public Vector2f getPosition()
	{
		return new Vector2f(
				position.x - Resources.halfwayPointTransformation.x,
				position.y - Resources.halfwayPointTransformation.y);
	}

	/**
	 * Sets the position.
	 * @param position - the position of the camera
	 */
	public void setPosition(Vector2f position)
	{
		this.position = position;
	}
	
	/**
	 * Gets the angle.
	 * @return the angle of the camera
	 */
	public Vector3f getAngle()
	{
		return new Vector3f(xAngle, yAngle, zAngle);
	}

	/**
	 * Sets the angle.
	 * @param angle - the angle of the camera.
	 */
	public void setAngle(Vector3f angles)
	{
		xAngle = angles.x;
		yAngle = angles.y;
		zAngle = angles.z;
	}

	/**
	 * Creates a view matrix.
	 * @param camera - the camera object
	 * @return a view matrix
	 */
	public Matrix4f createViewMatrix()
	{
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		
		// Position the matrix
		viewMatrix.rotate((float)Math.toRadians(xAngle), new Vector3f(1, 0, 0));
		viewMatrix.rotate((float)Math.toRadians(yAngle), new Vector3f(0, 1, 0));
		viewMatrix.rotate((float)Math.toRadians(zAngle), new Vector3f(0, 0, 1));
		viewMatrix.translate(new Vector2f(-position.x, -position.y));
		
		// Return the created matrix
		return viewMatrix;
	}
}
