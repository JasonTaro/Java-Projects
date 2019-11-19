
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author Jason Fukumoto
 * File: Connect4Controller.java
 * Controller holds the state of the game and interacts between model and view.
 *
 */
public class Connect4Controller {
	Connect4Model model;
	Connect4View view;
 
	/**
	 * 
	 * @param connect4View
	 */
	public Connect4Controller(Connect4View connect4View) {
		this.view = connect4View;
		model = new Connect4Model();
		if (connect4View!=null) {
			model.addObserver(connect4View);
		}
	}
	
	/**
	 * the humanTurn put the human move to the available position(white spot), then the 
	 * information is stored in the MoveMessage and sent to the other user, at the same 
	 * time, let the model handle the message
	 * @param col is the x coordinate of the user move
	 */
	public void humanTurn(int col) {
		for (int i = Connect4.ROW - 1; i >= 0; i--) {
			if (0 == model.getColor(i, col)) { //if the position is not taken (white color)
				Connect4MoveMessage message = new Connect4MoveMessage(i, col, Connect4.MY_COLOR);
				model.handleMessage(message);
				break;
			}
		}
	}
	
	/**
	 * the computer turn is to randomly generate a possible column(x coordinate)
	 * to put a circle, as long as the column is not full 
	 */
	public void computerTurn() {
		List<Integer> colList =new ArrayList<>();
		for (int col = 0; col < Connect4.COL; col++) {
			int color = model.getColor(0, col);
			if (color==0) {
				colList.add(col);
			}
		}
		Collections.shuffle(colList);
		humanTurn(colList.get(0));
	}

	/**
	 * allow current user to move, and let the model to handle the message
	 * @param message
	 */
	public void handleMessage(Connect4MoveMessage message) {
		model.handleMessage(message);
	}
	

	/**
	 * this method is to get the click column position
	 * @param sceneX
	 * @return the click at which column, from 0 - 6 inclusive
	 */
	public int getCol(double sceneX) {
		int clickCol = -1;
		int interval = 4;
		for (int j = 0; j < Connect4.COL; j++) {
			interval = interval + view.GAP + view.RAD * 2;
			if (j == Connect4.COL - 1) {
				interval += 4;
			}
			if (sceneX < interval) {
				clickCol = j;
				break;
			}
		}
		return clickCol;
	}
	
	/**
	 * Checks whole board to see if there is a tie
	 * @return boolean to determine tie
	 */
	public boolean isTie() {
		for(int i = 0; i < Connect4.ROW; i++) {
			for(int j = 0; j < Connect4.COL; j++) {
				if(model.getColor(i, j) == 0) {
					return false;
				}
			}
		}
		return true;
	}
}

