import java.io.Serializable;

/**
 * @author Jason Fukumoto
 * File: Connect4MoveMessage
 * The MoveMessage has all the information needed for the game transporting 
 * through the network
 */
public class Connect4MoveMessage implements Serializable{
	 
    public static int YELLOW = 1;
    public static int RED = 2;
    private static final long serialVersionUID = 1L;

    private int row;

    private int col;

    private int color;

    public Connect4MoveMessage(int row, int col, int color) {
		super();
		this.row = row;
		this.col = col;
		this.color = color;
	}

    /**
     * row getter
     * @return which row
     */
	public int getRow() {
    	 return row;
     }

	/**
     * column getter
     * @return which column
     */
     public int getColumn() {
    	 return col;
    }

     /**
      * color getter
      * @return which color
      */
     public int getColor() {
    	 return color;
     }

}
