import javafx.application.Application;
/**
 * 
 * @author GuojunWei & jasonfukumoto
 * CSC 335, Fall 2019
 * Connect 4
 * 
 * This project is to create a two-player connect4 game, it uses MVC implementation
 * and networks the two players with sockets
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