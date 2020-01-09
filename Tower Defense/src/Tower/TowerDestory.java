package Tower;
 
import MVC.Point;

/**
 * TowerDestroy is the tower that is sold 
 */
public class TowerDestory extends Tower {
	private int x;
	private int y;
	private int cost;
	
	/**
	 * constructor takes in the tower position
	 * @param x tower x position
	 * @param y tower y position 
	 */
	public TowerDestory(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * set destroy tower UID
	 */
	public void setId(String id) {
		super.setId(id);
	}
	
	/**
	 * get sell tower x coordinate
	 * @return sell tower x coordinate
	 */
	public int getSellX() {
		return x;
	}

	/**
	 * get sell tower y coordinate
	 * @return sell tower y coordinate
	 */
	public int getSellY() {
		return y;
	}

	/**
	 * set tower sold price
	 */
	public void setCost(int credit) {
		this.cost = credit;
	}

	/**
	 * get tower sold money
	 * @return tower sold money
	 */
	public int getCost() {
		return this.cost;
	}



	/**
	 * this method is not used
	 * @param
	 * @return
	 */
	public boolean inRange(Point point) {
		if (this.towerCOL == point.getCol() && this.towerROW == point.getRow() + 1) {
			return true;
		}
		return false;
	};

}
