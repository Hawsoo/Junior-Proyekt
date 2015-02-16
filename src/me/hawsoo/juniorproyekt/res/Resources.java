package me.hawsoo.juniorproyekt.res;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Hashtable;

import me.hawsoo.juniorproyekt.util.Camera;
import me.hawsoo.juniorproyekt.util.model.Model;
import me.hawsoo.juniorproyekt.util.model.ObjModel;
import me.hawsoo.juniorproyekt.util.shader.DefaultShader;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Matrix4f;

/**
 * Huge list of all the required resources.
 * @author Administrator
 *
 */
public class Resources
{
	// Properties
	public static final boolean DEBUG = true;
	
	public static final DisplayMode STANDARD_DM = new DisplayMode(1280, 720);
	public static final DisplayMode FULLSCREEN_DM = Display.getDesktopDisplayMode();
	
	public static Point halfwayPointTransformation = new Point(0, 0);
	
	public static String dirRes = "/me/hawsoo/juniorproyekt/res/";
	
	public static int tileSize = 32;			// 32 by 32
	
	// Objects
	public static Camera mainCamera;
	public static Matrix4f modelViewMatrix;
	public static Matrix4f prevModelViewMatrix;
	
	public static DefaultShader defaultShader;
	
	public static Model contra;
	
	/**
	 * Creates all the resources.
	 */
	public static void init()
	{
		// Setup all resources
		mainCamera = new Camera();
		
		defaultShader = new DefaultShader();
		
		try
		{
			contra = ObjModel.loadModel(Resources.class.getResourceAsStream(dirRes + "model/contra.obj"));
			contra.shader = defaultShader;
		} catch (IOException e) {}
		
		// Prepare the main model view matrix
		modelViewMatrix = mainCamera.createViewMatrix();
		
		// Trigger initalizing update
		updateResources();
	}
	
	/**
	 * For certain variables, they
	 * must be updated every frame.
	 */
	public static void updateResources()
	{
		// Setup model view matrices
		prevModelViewMatrix = modelViewMatrix;
		modelViewMatrix = mainCamera.createViewMatrix();
	}
	
	/**
	 * Creates a buffer for you.
	 * @param values - buffer's values
	 * @return the created float buffer
	 */
	public static FloatBuffer asFloatBuffer(float[] values)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Creates a byte buffer texture from an image.
	 * @param image - the image
	 * @return the image as a bytebuffer
	 */
	public static ByteBuffer importImageToByteBuffer(BufferedImage image)
	{
		ByteBuffer imageBuffer;
		WritableRaster raster;
		BufferedImage texImage;
		
		// Create colormap of the bufferedimage
		ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] {8, 8, 8, 8}, true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);
		
		raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, image.getWidth(), image.getHeight(), 4, null);
		texImage = new BufferedImage(glAlphaColorModel, raster, true, new Hashtable<Object, Object>());
		
		// Copy the source image into the produced image
		Graphics g = texImage.getGraphics();
		g.setColor(new Color(0f, 0f, 0f, 0f));
		g.fillRect(0, 0, 256, 256);
		g.drawImage(image, 0, 0, null);
		
		// Build a byte buffer from the temporary image to be used by OpenGL to produce a texture
		byte[] data = ((DataBufferByte)texImage.getRaster().getDataBuffer()).getData();
		
		// Prepare byte buffer
		imageBuffer = ByteBuffer.allocateDirect(data.length);
		imageBuffer.order(ByteOrder.nativeOrder());
		imageBuffer.put(data, 0, data.length);
		imageBuffer.flip();
		
		return imageBuffer;
	}
}
