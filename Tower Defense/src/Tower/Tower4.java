package Tower;

import MVC.Point;

/**
 * Tower4 concrete class implementation
 */
public class Tower4 extends Tower {

	/**
	 * constructor gives tower4 initial value
	 */
	public Tower4() {
		super();
		this.damage = 4;
		this.cost = 400; 
		this.range = 4;
		this.exist = true;
		this.index = 4;
		this.name = "Rocket Launcher";
		this.description = "";
		this.direction = "Right(East) 2 Meters";
	}


	/**
	 * tower4 shoots right direction
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
		
		if (this.towerCOL + 2 == point.getCol() && this.towerROW == point.getRow()) {
			System.out.println("return true");
			return true;
		}
		return false;
	};
}
