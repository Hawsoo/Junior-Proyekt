package me.hawsoo.juniorproyekt.engine.gameobject.entity.ground;

import java.awt.Rectangle;

import me.hawsoo.juniorproyekt.util.drawing.DrawUtils;
import me.hawsoo.juniorproyekt.util.drawing.Line;

public class Gnd_Reg_Line extends Ground
{
	// Components
	Line line;
	
	public Gnd_Reg_Line(int x, int y, int width, int height)
	{
		super(x, y);
		line = new Line(width, height);
	}

	@Override
	public boolean isColliding(Rectangle bounds)
	{
		return line.getLine2D((int)getX(), (int)getY()).intersects(bounds);
	}
	
	@Override
	public void render()
	{
		// BETA render a yellow line
		DrawUtils.setColorRGB(242, 255, 0);
		line.drawLine((int)getX(), (int)getY());
	}
	
}
