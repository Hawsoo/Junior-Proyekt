package me.hawsoo.juniorproyekt.util;

import java.awt.Point;
import java.awt.geom.Line2D;

import me.hawsoo.juniorproyekt.engine.gameobject.entity.Entity;
import me.hawsoo.juniorproyekt.res.Resources;
import me.hawsoo.juniorproyekt.util.math.MathUtils;

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
	// Camera options
	public static final int DIV_FACTOR = 5;
	private Entity followObj;
	private Line2D cameraPath;
	
	// Camera properties
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
	 * Gets the angle.
	 * @return the angle of the camera
	 */
	public Vector3f getAngle()
	{
		return new Vector3f(xAngle, yAngle, zAngle);
	}
	
	/**
	 * Gets the following object.
	 * @return the following object.
	 */
	public Entity getFollowObj()
	{
		return followObj;
	}

	/**
	 * Gets the camera path.
	 * @return the camera path.
	 */
	public Line2D getCameraPath()
	{
		return cameraPath;
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
	 * Sets the follow object.
	 * @param followObj
	 */
	public void setFollowObj(Entity followObj)
	{
		this.followObj = followObj;
	}

	/**
	 * Sets the camera path.
	 * @param cameraPath
	 */
	public void setCameraPath(Line2D cameraPath)
	{
		this.cameraPath = cameraPath;
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
	
	/**
	 * Updates the camera.
	 */
	public void updateCamera()
	{
		// Move along path
		if (cameraPath != null && followObj != null)
		{
			
		}
		// Move to camera position
		else if (followObj != null)
		{
			int gotoX = (int)followObj.getX();
			int gotoY = (int)followObj.getY();
			
			// Get next position
			double distance = MathUtils.getDistance(new Point((int)position.x, (int)position.y), new Point(gotoX, gotoY)) / DIV_FACTOR;
			double angle = MathUtils.getAngle(new Point((int)position.x, (int)position.y), new Point(gotoX, gotoY));
			
			int nextX = (int)(distance * Math.cos(Math.toRadians(angle)));
			int nextY = (int)(distance * Math.sin(Math.toRadians(angle)));
			
			// Goto next point
			position.x += nextX;
			position.y += nextY;
		}
		// Default to 0, 0, 0
		else
		{
			position = new Vector2f(0, 0);
		}
	}
}
