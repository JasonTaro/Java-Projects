package Tower;
 
import Enemies.Enemy;
import MVC.Point;

/**
 * 
 * @author  YongqiJia & JasonFukumoto & QingzhouPan & GuojunWei
 * CSC 335, Fall 2019
 * Team Project
 * Tower1 concrete class implementation
 * 
 */
public class Tower1 extends Tower {

	/**
	 * constructor gives tower1 initial value
	 */
	public Tower1() {
		super();
		this.damage = 1;
		this.cost = 100;
		this.range = 1;
		this.exist = true;
		this.index = 1;
		this.name = "Machine Gun";
		this.description = "";
		this.direction = "Up(North) 2 Meters";
	}
	
	
	/**
	 * tower2 shoots up direction
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
		
		if (this.towerCOL == point.getCol() && this.towerROW - 2 == point.getRow()) {
			System.out.println("return true");
			return true;
		}
		return false;
	};
	
} 
