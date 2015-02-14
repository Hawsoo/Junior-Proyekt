package me.hawsoo.juniorproyekt.engine.gameobject;

/**
 * This class represents the most basic
 * object the game can take in.
 * @author Administrator
 *
 */
public abstract class GameObject
{
	/**
	 * This updates the object.
	 */
	public abstract void update();
	
	/**
	 * This renders the object.
	 */
	public abstract void render();
}
