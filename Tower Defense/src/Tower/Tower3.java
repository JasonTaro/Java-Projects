package Tower;

import MVC.Point;

/**
 * Tower3 concrete class implementation
 */
public class Tower3 extends Tower {
 
	/**
	 * constructor gives tower3 initial value
	 */
	public Tower3() {
		super();
		this.damage = 3;
		this.cost = 300;
		this.range = 3;
		this.exist = true;
		this.index = 3;
		this.name = "Cannon";
		this.description = "";
		this.direction = "Left(West) 2 Meters";
	}


	/**
	 * tower4 shoots left direction
	 * @param enemy point info
	 * @return true if in range, otherwise false
	 */
	public boolean inRange(Point point) {
		/*System.out.println("this.towerCOL"+this.towerCOL);
		System.out.println("point.getCol()"+point.getCol());
		System.out.println("&this.towerROW"+this.towerROW);
		System.out.println("point.getRow()+1"+(point.getRow()+1));*/
		if (point.getCol()==null) {
			return false;
		}
		
		if (this.towerCOL - 2 == point.getCol() && this.towerROW == point.getRow()) {
			System.out.println("return true");
			return true;
		}
		return false;
	};
}
