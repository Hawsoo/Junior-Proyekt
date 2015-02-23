import me.hawsoo.juniorproyekt.Game;

/**
 * This class starts the game.
 * @author Administrator
 *
 */
public class JuniorProyekt
{
	public static void main(String[] argv)
	{
		// Parse and print args
		System.out.println("Arguments:");
		for (int i = 0; i < argv.length; i++)
		{
			System.out.println(argv[i]);
		}
		
		// Start the game
		System.out.println("\n=========== " + Game.name + " ===========\n");
		new Game().launch();
	}
}
