package me.hawsoo.juniorproyekt;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Point;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import me.hawsoo.juniorproyekt.engine.gameobject.gamestate.BETAgamestate;
import me.hawsoo.juniorproyekt.engine.gameobject.gamestate.GameState;
import me.hawsoo.juniorproyekt.engine.input.VC_Controller;
import me.hawsoo.juniorproyekt.engine.input.VC_Keyboard;
import me.hawsoo.juniorproyekt.engine.input.VirtualController;
import me.hawsoo.juniorproyekt.res.Resources;
import me.hawsoo.juniorproyekt.util.drawing.Rect;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 * The main game loop.
 * @author Hawsoo
 *
 */
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
	
	public int viewWidth = 0, viewHeight = 0;
	
	private GameState room;
	
	// BETA the basic input
	public static final int PLAYER_ONE = 0;
	public static List<VirtualController> controllers = new ArrayList<VirtualController>();
	
	public static boolean motionblur = false;
	
	// BETA model scaling
	private static int scalefactor = 1/*00*/;
	
	// BETA 3D vector
	private static Vector3f betaVec = new Vector3f(0, 0, 0);
	private static int betaVecMoveSpeed = 5;
	
	/**
	 * Starts the whole game.
	 */
	public void launch()
	{
		// Create Window Icons
		try
		{
			windowicons = new ByteBuffer[]
					{
					Resources.importImageToByteBuffer(ImageIO.read(Game.class.getResource(Resources.dirRes + "icon_16x16.png"))),
					Resources.importImageToByteBuffer(ImageIO.read(Game.class.getResource(Resources.dirRes + "icon_32x32.png"))),
					Resources.importImageToByteBuffer(ImageIO.read(Game.class.getResource(Resources.dirRes + "icon_48x48.png"))),
					Resources.importImageToByteBuffer(ImageIO.read(Game.class.getResource(Resources.dirRes + "icon_64x64.png"))),
					Resources.importImageToByteBuffer(ImageIO.read(Game.class.getResource(Resources.dirRes + "icon_128x128.png"))),
					Resources.importImageToByteBuffer(ImageIO.read(Game.class.getResource(Resources.dirRes + "icon_256x256.png")))
					};
			
		} catch (IOException e) {}
		
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
		room = new BETAgamestate();
		
		// FIXME configure better input system
		// Keyboard input
		controllers.add(new VC_Keyboard(
				Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_UP, Keyboard.KEY_DOWN,
				Keyboard.KEY_Z, Keyboard.KEY_X, Keyboard.KEY_C));
		
		// Controller input
//		controllers.add(new VC_Controller(
//				/*Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, Keyboard.KEY_UP, Keyboard.KEY_DOWN,
//				Keyboard.KEY_C, Keyboard.KEY_X, Keyboard.KEY_Z*/));
		
		int rot = 0;
		
		// Game Loop
		while (running)
		{
			{
				///////////////
				// GET INPUT //
				///////////////
				{
					Mouse.poll();
					
					// BETA move vector
					if (Keyboard.isKeyDown(Keyboard.KEY_W)) betaVec = new Vector3f(betaVec.x, betaVec.y + betaVecMoveSpeed, betaVec.z);
					if (Keyboard.isKeyDown(Keyboard.KEY_A)) betaVec = new Vector3f(betaVec.x - betaVecMoveSpeed, betaVec.y, betaVec.z);
					if (Keyboard.isKeyDown(Keyboard.KEY_S)) betaVec = new Vector3f(betaVec.x, betaVec.y - betaVecMoveSpeed, betaVec.z);
					if (Keyboard.isKeyDown(Keyboard.KEY_D)) betaVec = new Vector3f(betaVec.x + betaVecMoveSpeed, betaVec.y, betaVec.z);
					
					if (Keyboard.isKeyDown(Keyboard.KEY_Q)) betaVec = new Vector3f(betaVec.x, betaVec.y, betaVec.z - betaVecMoveSpeed);
					if (Keyboard.isKeyDown(Keyboard.KEY_E)) betaVec = new Vector3f(betaVec.x, betaVec.y, betaVec.z + betaVecMoveSpeed);
					if (Keyboard.isKeyDown(Keyboard.KEY_F)) betaVec = new Vector3f(0, 0, 0);
					
//					rot = Mouse.getX(); BETA captures mouse movement
					
					// Get all input
					for (VirtualController controller : controllers)
					{
						controller.pumpInput();
					}

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
					// Update the camera
					Resources.mainCamera.updateCamera();
					
					// Update resources
					Resources.updateResources();
					
					// Update the room
					room.update();
					// ////////////////
					// update stuff //
//					gamestate.update();
//					Camera.update();
					// ////////////////
					
				}
				
				////////////
				// RENDER //
				////////////

				// Check if should render (based on the frame-skip value)
				//					if ((renderPassCount % (frameSkip + 1)) == 0)
				{
					// Clear Buffers
					glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
					glLoadIdentity();

					// Enter renderpass
					glPushMatrix();
					{
						// BETA lighting is moving independent from camera rotation
						glLight(GL_LIGHT0, GL_POSITION, Resources.asFloatBuffer(new float[] {viewWidth / 2 + betaVec.x, viewHeight / 2 + 55 + betaVec.y, 100 + betaVec.z, 1}));
						System.out.println(betaVec);
//						glLight(GL_LIGHT0, GL_POSITION, Resources.asFloatBuffer(new float[] {betaVec.x, betaVec.y, betaVec.z, 1}));
//						new Rect(8, 8, -4, -4).drawRect((int)betaVec.x, (int)betaVec.y, null, false);
//						new Rect(8, 8, -4, -4).drawRect(viewWidth / 2, viewHeight / 2, null, false);
						
						// Translate matrix to the camera
						glTranslatef(-Resources.mainCamera.getPosition().x, -Resources.mainCamera.getPosition().y, 0);

						// BETA scaling is unnecessary in real concepts
//						glScalef(2 * scalefactor, 2 * scalefactor, 2 * scalefactor);

						// BETA add motion blur
//						glAccum(GL_LOAD, 1.0f);
//						glAccum(GL_ACCUM, 1.0f);		//adding the current frame to the buffer
						{
							// Rotate matrix to the camera
							glRotatef(Resources.mainCamera.getAngle().x, 1, 0, 0);
							glRotatef(Resources.mainCamera.getAngle().y, 0, 1, 0);
							glRotatef(Resources.mainCamera.getAngle().z, 0, 0, 1);

							// Render the room
							room.render();
							//						glTranslatef(-0.5f * scalefactor, 0, 0);
							//						Resources.contra.render();		// BETA draws a contra bugle

							//						glTranslatef(1 * scalefactor, 0, 0);
							//						Resources.contra.render();
						}
						// BETA undo
//						glAccum(GL_RETURN, 1f);			//Drawing last frame, saved in buffer
//						glAccum(GL_MULT, 0.75f);		//make current frame in buffer dim
						
						// Do motion blur
						if (motionblur)
						{
							glAccum(GL_MULT, 0.5f);
							glAccum(GL_ACCUM, 0.5f);
							glAccum(GL_RETURN, 1.0f);
						}
					}
					// Exit renderpass
					glPopMatrix();
				}
			}
			
			// Check if close is requested
			if (Display.isCloseRequested()) running = false;
			
			// Reiterate OpenGL (if changed)
			if (displayWidth != Display.getWidth() || displayHeight != Display.getHeight()) setupOpenGLContext();
			
			// Update Display
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
		viewWidth = (int)(Display.getWidth() * heightRatio);
		viewHeight = (int)(Display.getHeight() * heightRatio);
		
		Resources.halfwayPointTransformation = new Point(viewWidth / 2, viewHeight / 2);
		
		glOrtho(0, viewWidth, 0, viewHeight, -500, 500);
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
