package me.hawsoo.juniorproyekt.engine.gameobject.entity;

/**
 * This is an enum representing
 * the direction an entity is facing
 * (LEFT or RIGHT).
 * @author Administrator
 *
 */
public enum Direction
{
	// Registers
	LEFT(-1), RIGHT(1);
	
	// Components
	private final int multiplier;
	
	/**
	 * Initalizes a direction.
	 * @param multiplier - the <code>int</code> representation
	 */
	Direction(int multiplier)
	{
		this.multiplier = multiplier;
	}
	
	/**
	 * Gets the multiplier.
	 * @returns the <code>int</code> representation of the enum
	 */
	public int getMult()
	{
		return multiplier;
	}
}
