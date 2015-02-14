package me.hawsoo.juniorproyekt.engine.gameobject.gamestate;

import me.hawsoo.juniorproyekt.engine.gameobject.entity.Player;
import me.hawsoo.juniorproyekt.engine.gameobject.entity.ground.Gnd_Reg_Line;

/**
 * This gamestate will serve as a
 * beta world.
 * @author Administrator
 *
 */
public class BETAgamestate extends GameState
{
	public BETAgamestate()
	{
		addGrounds(new Gnd_Reg_Line(-64, -64, 600, 40));
		
		addEntities(new Player(0, 0, getGrounds()));
	}
	
}
