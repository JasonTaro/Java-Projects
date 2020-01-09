package Networking;

import Tower.*;
import Enemies.*;

/**
 * The model class has all the info required to update the view   
 */

public class TowerDefenseMessge {
	
	public static final int TYPE_CHANGE_MAP = 10;
	public static final int TYPE_LOAD = 11;
	private int row;
	private int col;
	private int color; // 0 grass. 1 road. 2 tower. 3 enemy.
	private Tower tower;
	private Enemy enemy;
	
	/**
	 * constructor for x y and behavior instruction
	 * @param row grid pane y position 
	 * @param col grid pane x position
	 * @param color behavior instruction
	 */
	public TowerDefenseMessge(int row, int col, int color) {
		this.row = row;
		this.col = col;
		this.color = color;
	}
	
	/**
	 * constructor for tower enemy and behavior instruction
	 * @param tower tower object info
	 * @param enemy enemy object info
	 * @param color behavior instruction
	 */
	public TowerDefenseMessge(Tower tower, Enemy enemy, int color) {
		this.tower = tower;
		this.enemy = enemy;
		this.color = color;
	}

	/**
	 * getter to get y position 
	 * @return y position
	 */
	public int getRow() {
		return row;
	}

	/**
	 * getter to get x position 
	 * @return x position
	 */
	public int getCol() {
		return col;
	}

	/**
	 * getter to get behavior instruction
	 * @return y behavior instruction
	 */
	public int getColor() {
		return color;
	}

	/**
	 * getter to get tower object 
	 * @return tower object
	 */
	public Tower getTower() {
		return tower;
	}

	/**
	 * getter to get enemy object 
	 * @return enemy object 
	 */
	public Enemy getEnemy() {
		return enemy;
	}
}
