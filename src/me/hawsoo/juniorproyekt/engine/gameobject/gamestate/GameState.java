package me.hawsoo.juniorproyekt.engine.gameobject.gamestate;

import java.util.ArrayList;
import java.util.List;

import me.hawsoo.juniorproyekt.engine.gameobject.GameObject;
import me.hawsoo.juniorproyekt.engine.gameobject.entity.Entity;
import me.hawsoo.juniorproyekt.engine.gameobject.entity.ground.Ground;

/**
 * This class represents a 'room'
 * in the game.
 * @author Hawsoo
 *
 */
public abstract class GameState extends GameObject
{
	// Lists of gameobjects
	private List<Ground> grounds = new ArrayList<Ground>();
	private List<Entity> entities = new ArrayList<Entity>();
	
	/**
	 * Gets the collisions.
	 * @return the list of grounds
	 */
	public List<Ground> getGrounds()
	{
		return grounds;
	}
	
	/**
	 * Gets the entities.
	 * @return the list of entities
	 */
	public List<Entity> getEntities()
	{
		return entities;
	}

	/**
	 * Adds grounds
	 * @param grounds - item(s) of grounds
	 */
	public void addGrounds(Ground... grounds)
	{
		for (Ground ground : grounds)
		{
			this.grounds.add(ground);
		}
	}

	/**
	 * Adds entities
	 * @param entities - item(s) of entities
	 */
	public void addEntities(Entity... entities)
	{
		for (Entity entity : entities)
		{
			this.entities.add(entity);
		}
	}

	/**
	 * Update all entities within room.
	 */
	@Override
	public void update()
	{
		for (Entity entity : entities)
		{
			entity.update();
		}
	}
	
	/**
	 * Render all entities within room.
	 */
	@Override
	public void render()
	{
		for (Ground ground : grounds)
		{
			ground.render();
		}
		
		for (Entity entity : entities)
		{
			entity.render();
		}
	}
}
