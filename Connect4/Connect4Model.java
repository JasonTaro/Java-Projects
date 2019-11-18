import java.util.Observable;

/**
 * 
 * @author Jason Fukumoto
 * Description: Model class have the 2d integer array, which represents different color
 *  0 for white, 1 for yellow, 2 for red
 */
public class Connect4Model extends Observable{

	private int board [][];
	
	public Connect4Model() {
		board = new int [Connect4.ROW][Connect4.COL]; 
	}
	 
	/**
	 * for board testing, print board state in console
	 */
	public void printBoard() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	} 
	
	/**
	 * getter to get color from the board
	 * @param row
	 * @param col
	 * @return a integer representing a color
	 */
	public int getColor(int row,int col) {
		return board[row][col];
	}

	/**
	 * whenever the view has the user input information, it updates model through 
	 * controller
	 * @param message has all the information of the user move, the circle coordinate
	 * and color, and it notifies the view
	 */
	public void handleMessage(Connect4MoveMessage message) {
		board[message.getRow()][message.getColumn()] = message.getColor();
		setChanged();
		notifyObservers(message);
	}
}
