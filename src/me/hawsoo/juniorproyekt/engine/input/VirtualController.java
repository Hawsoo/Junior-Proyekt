package me.hawsoo.juniorproyekt.engine.input;

/**
 * This class handles all
 * input for the game.
 * @author Administrator
 *
 */
public abstract class VirtualController
{
	// Controls
	public boolean left 	= false;		// Move left; select
	public boolean right 	= false;		// Move right; select
	public boolean up 		= false;		// Horn ready/up; climb up; select
	public boolean down 	= false;		// Horn ready/rest; climb down; select; action (talk to people, etc.)
	
	public boolean jump 	= false;		// Jump; confirm; accept
	public boolean attack	= false;		// Attack; cancel; skip
	public boolean item		= false;		// Use item (electric gloves, rope, etc.); <hold> select different item
	
	/**
	 * This looks thru all inputs to
	 * then change the control input
	 * variables accordingly.
	 */
	public abstract void pumpInput();
}
