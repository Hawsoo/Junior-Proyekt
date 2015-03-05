package me.hawsoo.juniorproyekt.engine.gameobject.gamestate;

import me.hawsoo.juniorproyekt.engine.gameobject.entity.Player;
import me.hawsoo.juniorproyekt.engine.gameobject.entity.ground.Gnd_Reg_Line;
import me.hawsoo.juniorproyekt.res.Resources;

/**
 * This gamestate will serve as a
 * beta world.
 * @author Hawsoo
 *
 */
public class BETAgamestate extends GameState
{
	public BETAgamestate()
	{
		addGrounds(
				new Gnd_Reg_Line(-256, -64, 600, 40),
				new Gnd_Reg_Line(-256 + 600, -64 + 40, 0, 6000),
				new Gnd_Reg_Line(-256, -64, 40, 600));
		
		Player p = new Player(0, 0, getGrounds());
		Resources.mainCamera.setFollowObj(p);
		addEntities(p);
	}
	
}
