import javafx.application.Application;
/**
 * 
 * @author jasonfukumoto
 * Connect 4
 * 
 * This project is a two player connect 4 game. Uses Graphical User Interface with javafx.
 * Follows MVC architecture with networking.
 *
 */
public class Connect4 {

	public static final int ROW = 6;
	public static final int COL = 7;
	public static boolean MY_TURN = false;
	public static int MY_COLOR = 0;
	public static Connect4Connection connection;
	
	/**
	 * the main function directs program directly to the GUI view
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(Connect4View.class,args);	
	}
	
}
