package me.hawsoo.juniorproyekt;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLightModel;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.Point;
import java.nio.ByteBuffer;

import me.hawsoo.juniorproyekt.res.Resources;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class Game
{
	// Components
	public static String name = "Junior Proyekt - vPreAlpha";
	private static ByteBuffer[] windowicons;
	public static boolean isFullscreen = false;
	public static boolean isVSync = true;
	
	public static float fps = 60;
	private boolean running = true;
	private int displayWidth = 0, displayHeight = 0;
	
	// BETA model scaling
	private static int scalefactor = 100;
	
	/**
	 * Starts the whole game.
	 */
	public void launch()
	{
		// Create Window Icons
//		try
//		{
//			windowicons =
//					new ByteBuffer[]
//							{
//								ResourceLoad.importImageToByteBuffer(ImageIO.read(Game.class.getResource(Game.resourceDirectoryPath + "icon_16x16.png"))),
//								ResourceLoad.importImageToByteBuffer(ImageIO.read(Game.class.getResource(Game.resourceDirectoryPath + "icon_32x32.png"))),
//								ResourceLoad.importImageToByteBuffer(ImageIO.read(Game.class.getResource(Game.resourceDirectoryPath + "icon_48x48.png"))),
//								ResourceLoad.importImageToByteBuffer(ImageIO.read(Game.class.getResource(Game.resourceDirectoryPath + "icon_64x64.png"))),
//								ResourceLoad.importImageToByteBuffer(ImageIO.read(Game.class.getResource(Game.resourceDirectoryPath + "icon_128x128.png"))),
//								ResourceLoad.importImageToByteBuffer(ImageIO.read(Game.class.getResource(Game.resourceDirectoryPath + "icon_256x256.png")))
//							};
//			
//		} catch (IOException e) {}
		
		// Setup Display and Inputs
		setupOpenGLDisplay(name, windowicons, isFullscreen, isVSync);
		
		try
		{
			Display.create();
			Keyboard.create();
			Controllers.create();
		} catch (LWJGLException e) {}
		
		// Setup Controllers, i.e. Gamepads
//		boolean controllerFound = false;
//		net.java.games.input.Controller c = null;
//		
//		for (int i = 0; i < Controllers.getControllerCount(); i++)
//		{
//			
//			c = ControllerEnvironment.getDefaultEnvironment().getControllers()[i];
//			if (c.getType().toString().equalsIgnoreCase("Gamepad"))
//			{
//				controllerFound = true;
//				break;
//			}
//			
//		}
//		
//		if (controllerFound)
//		{
//			
//			for (int i = 0; i < Controllers.getControllerCount(); i++)
//			{
//				
//				controller = Controllers.getController(i);
//				if (controller.getName().equalsIgnoreCase(c.getName()))
//				{
//					controllerAvailable = true;
//					break;
//				}
//				
//			}
//			
//		}
		
		// Initialize OpenGL
		setupOpenGLContext();
		
		// Setup Game Loop
		Resources.init();
		
		int rot = 45;
		
		// Game Loop
		while (running)
		{
			{
				///////////////
				// GET INPUT //
				///////////////
				{
					Mouse.poll();
					
					rot = Mouse.getX();

					// Setup camera
					Resources.mainCamera.setAngle(new Vector3f(0, rot, 0));
					
					// Refresh input of controller(s) //
//					if (controllerAvailable) controller.poll();
					
					// If F11 is pressed //
//					if (Keyboard.isKeyDown(Keyboard.KEY_F11))
//					{
//						
//						// Change isFullscreen and resize display //
//						isFullscreen = !isFullscreen;
//						try
//						{
//							
//							if (isFullscreen)
//							{
//								Display.setDisplayMode(fullscreenDM);
//								currentDisplayWidth = fullscreenDM.getWidth();
//								currentDisplayHeight = fullscreenDM.getHeight();
//								Display.setFullscreen(true);
//							}
//							else
//							{
//								Display.setDisplayMode(windowDM);
//								currentDisplayWidth = windowDM.getWidth();
//								currentDisplayHeight = windowDM.getHeight();
//								Display.setFullscreen(false);
//							}
//							
//						} catch (LWJGLException e) {}
//						
//						// Reiterate size of Display //
//						setupOpenGL();
//						
//					}
					
					// //////////////////
					// getInput stuff //
//					gamestate.getInput();
					// //////////////////
					
				}
				
				////////////
				// UPDATE //
				////////////
				{
					Resources.update();
					// ////////////////
					// update stuff //
//					gamestate.update();
//					Camera.update();
					// ////////////////
					
				}
				
				////////////
				// RENDER //
				////////////
				{
					
					// Check if should render (based on the frame-skip value)
//					if ((renderPassCount % (frameSkip + 1)) == 0)
					{
						// Clear Buffers
						glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
						glLoadIdentity();
						
						// Enter renderpass
						glPushMatrix();
						{
							// Offset camera
//							glTranslatef(-Camera.x + (Display.getWidth() / 2), -Camera.y + (Display.getHeight() / 2), 0f);
//							gamestate.render();
							
//							glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
							
							
//							glMaterialf(GL_FRONT, GL_SHININESS, 10.0f);
							
							
							// Translate matrix to the camera
							glTranslatef(-Resources.mainCamera.getPosition().x, -Resources.mainCamera.getPosition().y, 0);
							
							// BETA scaling is unnecessary in real concepts
							glScalef(2 * scalefactor, 2 * scalefactor, 2 * scalefactor);
							
							// BETA lighting is moving independent from camera rotation
							glLight(GL_LIGHT0, GL_POSITION, Resources.asFloatBuffer(new float[] {0, 0, 25, 1}));
							
							// Rotate matrix to the camera
							glRotatef(Resources.mainCamera.getAngle().x, 1, 0, 0);
							glRotatef(Resources.mainCamera.getAngle().y, 0, 1, 0);
							glRotatef(Resources.mainCamera.getAngle().z, 0, 0, 1);
							
							
							
//							glTranslatef(-0.5f * scalefactor, 0, 0);
							Resources.contra.render();
							
//							glTranslatef(1 * scalefactor, 0, 0);
//							Resources.contra.render();
							
						}
						// Exit renderpass
						glPopMatrix();
					}
					
//					renderPassCount++;
				}
			}
			
			// Check if close is requested
			if (Display.isCloseRequested()) running = false;
			
			// Reiterate OpenGL (if changed)
			if (displayWidth != Display.getWidth() || displayHeight != Display.getHeight()) setupOpenGLContext();
//			currentDisplayWidth = Display.getWidth();
//			currentDisplayHeight = Display.getHeight();
			
			// Update Display
//			if ((renderPassCount % (frameSkip + 1)) == 0)
			
			Display.update();
			Display.sync((int) fps);
		}
		
		// Dispose and Cleanup
		Display.destroy();
		Keyboard.destroy();
		Controllers.destroy();
	}
	
	/**
	 * Creates the OpenGL display to sit in.
	 * @param title - title of window
	 * @param windowIcons - icons of window
	 * @param isFullscreen - true if the screen wants to be full
	 * @param isVSync - true if vsync is wanted to be enabled
	 */
	public void setupOpenGLDisplay(String title, ByteBuffer[] windowIcons, boolean isFullscreen, boolean isVSync)
	{
		
		// Set size of window based on isFullscreen
		try
		{
			if (isFullscreen)
			{
				Display.setDisplayMode(Resources.FULLSCREEN_DM);
				displayWidth = Resources.FULLSCREEN_DM.getWidth();
				displayHeight = Resources.FULLSCREEN_DM.getHeight();
				Display.setFullscreen(true);
			}
			else
			{
				Display.setDisplayMode(Resources.STANDARD_DM);
				displayWidth = Resources.STANDARD_DM.getWidth();
				displayHeight = Resources.STANDARD_DM.getHeight();
				Display.setFullscreen(false);
			}
		} catch (LWJGLException e) {}
		
		// Set other window settings
		Display.setTitle(title);
		Display.setIcon(windowIcons);
		Display.setResizable(true);
		Display.setVSyncEnabled(isVSync);
		Display.setInitialBackground(0f, 0.25f, 0.5f);
	}
	
	/**
	 * Creates the context for OpenGL to work in.
	 */
	public void setupOpenGLContext()
	{
		// Setup OpenGL for 2D pixel matrices
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
//		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -100, 100);
		
		float heightRatio = Resources.STANDARD_DM.getHeight() / (float)Display.getHeight();
		int standardWidth = (int)(Display.getWidth() * heightRatio);
		int standardHeight = (int)(Display.getHeight() * heightRatio);
		
		Resources.halfwayPointTransformation = new Point(standardWidth / 2, standardHeight / 2);
		
		glOrtho(0, standardWidth, 0, standardHeight, -500, 500);
		glMatrixMode(GL_MODELVIEW);
		
		// Add shading properties
		glShadeModel(GL_SMOOTH);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_LIGHTING);
		
		glEnable(GL_LIGHT0);
		glLightModel(GL_LIGHT_MODEL_AMBIENT, Resources.asFloatBuffer(new float[] {0.025f, 0.025f, 0.025f, 1}));
		glLight(GL_LIGHT0, GL_DIFFUSE, Resources.asFloatBuffer(new float[] {1.5f * scalefactor, 1.5f * scalefactor, 1.5f * scalefactor, 1}));
//		glLight(GL_LIGHT0, GL_SPECULAR, Resources.asFloatBuffer(new float[] {1.5f * scalefactor, 1.5f * scalefactor, 1.5f * scalefactor, 1}));
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glEnable(GL_COLOR_MATERIAL);
		
		
		// Update flags
		displayWidth = Display.getWidth();
		displayHeight = Display.getHeight();
	}
}