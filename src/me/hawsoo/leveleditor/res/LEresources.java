package me.hawsoo.leveleditor.res;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * This is just a registry of the multiple
 * resources used in the level editor.
 * @author Administrator
 *
 */
public class LEresources
{
	public static BufferedImage imgBanner = getImage(LEresources.class.getResourceAsStream("/me/hawsoo/leveleditor/res/banner.png"));
	
	/**
	 * Creates bufferedimages automatically
	 * @param in - the file input stream
	 * @return a bufferedimage
	 */
	public static BufferedImage getImage(InputStream in)
	{
		BufferedImage img = null;
		try
		{
			img = ImageIO.read(in);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return img;
	}
}
