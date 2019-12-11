package Tower;
 
import MVC.Point;

/**
 * 
 * @author  YongqiJia & JasonFukumoto & QingzhouPan & GuojunWei
 * CSC 335, Fall 2019
 * Team Project
 * Tower6 concrete class implementation
 * 
 */
public class Tower6 extends Tower {

	/**
	 * constructor gives tower6 initial value
	 */
	public Tower6() {
		super();
		this.damage = 0;
		this.cost = 600;
		this.range = 0;
		this.exist = true;
		this.index = 6;
		this.name = "Money Generator";
		//this.description = "Increase Balance by $30/second";
		this.direction = "Increase Balance by $30/sec";
	}


	/**
	 * this method is not used for money generating tower
	 * @param
	 * @return
	 */
	public boolean inRange(Point point) {
		return false;
	};
}
