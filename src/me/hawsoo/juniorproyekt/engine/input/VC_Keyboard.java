package me.hawsoo.juniorproyekt.engine.input;

import org.lwjgl.input.Keyboard;

public class VC_Keyboard extends VirtualController
{
	// Keybindings
	private int keyLeft;
	private int keyRight;
	private int keyUp;
	private int keyDown;
	
	private int keyJump;
	private int keyAttack;
	private int keyItem;
	
	/**
	 * Inputs all the keybindings. Each argument is corresponding to the
	 * key in the class <code>VirtualController</code>.
	 * @param keyLeft
	 * @param keyRight
	 * @param keyUp
	 * @param keyDown
	 * @param keyJump
	 * @param keyAttack
	 * @param keyItem
	 * @see me.hawsoo.juniorproyekt.engine.input.VirtualController
	 */
	public VC_Keyboard(int keyLeft, int keyRight, int keyUp, int keyDown, int keyJump, int keyAttack, int keyItem)
	{
		setKeybindings(keyLeft, keyRight, keyUp, keyDown, keyJump, keyAttack, keyItem);
	}
	
	/**
	 * Inputs all the keybindings. Each argument is corresponding to the
	 * key in the class <code>VirtualController</code>.
	 * @param keyLeft
	 * @param keyRight
	 * @param keyUp
	 * @param keyDown
	 * @param keyJump
	 * @param keyAttack
	 * @param keyItem
	 * @see me.hawsoo.juniorproyekt.engine.input.VirtualController
	 */
	public void setKeybindings(int keyLeft, int keyRight, int keyUp, int keyDown, int keyJump, int keyAttack, int keyItem)
	{
		this.keyLeft 	= keyLeft;
		this.keyRight 	= keyRight;
		this.keyUp 		= keyUp;
		this.keyDown 	= keyDown;
		this.keyJump 	= keyJump;
		this.keyAttack 	= keyAttack;
		this.keyItem 	= keyItem;
	}

	@Override
	public void pumpInput()
	{
		// Update key inputs
		left 	= Keyboard.isKeyDown(keyLeft);
		right 	= Keyboard.isKeyDown(keyRight);
		up 		= Keyboard.isKeyDown(keyUp);
		down 	= Keyboard.isKeyDown(keyDown);

		jump 	= Keyboard.isKeyDown(keyJump);
		attack 	= Keyboard.isKeyDown(keyAttack);
		item 	= Keyboard.isKeyDown(keyItem);
	}
	
}
