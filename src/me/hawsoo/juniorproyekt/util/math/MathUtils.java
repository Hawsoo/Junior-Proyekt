package me.hawsoo.juniorproyekt.util.math;

import java.awt.Point;

/**
 * This is a set of convenience
 * methods for math operations.
 * @author Administrator
 *
 */
public class MathUtils
{
	/**
	 * Gets the angle betwixt
	 * two points in 2D space.
	 * @param from
	 * @param to
	 * @return the angle in degrees
	 */
	public static double getAngle(Point from, Point to)
	{
		return correctAngle(Math.toDegrees(Math.atan2(to.y - from.y, to.x - from.x)));
	}
	
	/**
	 * This flips an angle in degrees.
	 * @param angle - the angle in degrees
	 * @return the flipped angle
	 */
	public static double flipAngle(double angle)
	{
		return correctAngle(angle + 180);
	}
	
	/**
	 * Corrects an angle in degrees.
	 * @param angle - the angle in degrees
	 * @return the corrected angle
	 */
	public static double correctAngle(double angle)
	{
		while(angle < 0) angle += 360;
		while(angle >= 360) angle -= 360;
		return angle;
	}
	
	/**
	 * Gets the distance betwixt
	 * two points in 2D space.
	 * @param from
	 * @param to
	 * @return the distance betwixt
	 */
	public static double getDistance(Point from, Point to)
	{
		return Math.sqrt(Math.pow(from.x - to.x, 2) + Math.pow(from.y - to.y, 2));
	}
}
