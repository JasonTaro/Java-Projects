package Tower;

import MVC.Point;

/**
 * 
 * @author  YongqiJia & JasonFukumoto & QingzhouPan & GuojunWei
 * CSC 335, Fall 2019
 * Team Project
 * Tower5 concrete class implementation
 * 
 */
public class Tower5 extends Tower {
 
	/**
	 * constructor gives tower5 initial value
	 */
	public Tower5() {
		super();
		this.damage = 5;
		this.cost = 500;
		this.range = 5;
		this.exist = true;
		this.index = 5;
		this.name = "Tesla Gun";
		this.description = "";
		this.direction = "Up/Down/Left/Right 2 Meters";
	}


	/**
	 * tower5 shoots up, down, left, right direction
	 * @param enemy point info
	 * @return true if in range, otherwise false
	 */
	public boolean inRange(Point point) {
		if (point.getCol()==null) {
			return false;
		}
		if (this.towerCOL == point.getCol() && this.towerROW == point.getRow() + 2) {
			return true;
		}else if(this.towerCOL == point.getCol() + 2 && this.towerROW == point.getRow()) {
			return true;
		}else if(this.towerCOL == point.getCol() - 2 && this.towerROW == point.getRow()) {
			return true;
		}else if(this.towerCOL == point.getCol() && this.towerROW == point.getRow() - 2) {
			return true;
		}
		return false;
	};
}
