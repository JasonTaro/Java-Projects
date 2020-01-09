package Tower;
 
import Enemies.Enemy;
import MVC.Point;

/**
 * Tower2 concrete class implementation
 */
public class Tower2 extends Tower {

	/**
	 * constructor gives tower2 initial value
	 */
	public Tower2() {
		super();
		this.damage = 2;
		this.cost = 200;
		this.range = 2;
		this.exist = true;
		this.index = 2;
		this.name = "Sniper";
		this.description = "";
		this.direction = "Down(South) 2 Meters";
	}



	/**
	 * tower2 shoots down direction
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
		
		if (this.towerCOL == point.getCol() && this.towerROW + 2 == point.getRow()) {
			System.out.println("return true");
			return true;
		}
		return false;
	};
}
