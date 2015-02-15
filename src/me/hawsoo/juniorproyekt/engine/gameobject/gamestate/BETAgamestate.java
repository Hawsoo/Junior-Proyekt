package me.hawsoo.juniorproyekt.engine.gameobject.gamestate;

import me.hawsoo.juniorproyekt.engine.gameobject.entity.Player;
import me.hawsoo.juniorproyekt.engine.gameobject.entity.ground.Gnd_Reg_Line;
import me.hawsoo.juniorproyekt.res.Resources;

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
		
		Player p = new Player(0, 0, getGrounds());
		Resources.mainCamera.setFollowObj(p);
		addEntities(p);
	}
	
}
