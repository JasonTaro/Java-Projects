package Tower;
 
import java.io.Serializable;

import MVC.Point;

/**
 * 
 * @author  YongqiJia & JasonFukumoto & QingzhouPan & GuojunWei
 * CSC 335, Fall 2019
 * Team Project
 * The abstract tower class provides all the info for the the MVC mode
 * 
 */

public abstract class Tower implements Serializable {

	private static final long serialVersionUID = 7990071241630315383L;
	
	protected int damage;
	protected int cost;
//	protected int soldPrice = (int) 0.75 * cost;
	protected int range;
	protected boolean exist;
	protected int index;
	protected String name;
	protected int towerROW;
	protected int towerCOL;
	private String id;
	protected String description;
	protected String direction;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public abstract boolean inRange(Point point);
	
	/**
	 * get sold price which is 75% of original price
	 * @return the sold price 
	 */
	public int getSoldPrice() {
		return (int) (0.75 * cost);
	}
	
	/**
	 * get tower y position
	 * @return tower y position
	 */
	public int getTowerROW() {
		return towerROW;
	}

	/**
	 * set tower y position
	 * @return tower y position
	 */
	public void setTowerROW(int towerROW) {
		this.towerROW = towerROW;
	}

	/**
	 * get tower x position
	 * @return tower x position
	 */
	public int getTowerCOL() {
		return towerCOL;
	}

	/**
	 * set tower x position
	 * @return tower x position
	 */
	public void setTowerCOL(int towerCOL) {
		this.towerCOL = towerCOL;
	}

	/**
	 * set tower damage
	 * @return tower damage
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	/**
	 * set tower cost
	 * @return tower cost
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	/**
	 * set tower range
	 * @param tower range
	 */
	public void setRange(int range) {
		this.range = range;
	}
	
	/**
	 * set tower existence
	 * @param exist boolean
	 */
	public void setExist(boolean exist) {
		this.exist = exist;
	}
	
	/**
	 * set tower name
	 * @param tower name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * get tower damage
	 * @return tower damage
	 */
	public int getDamage() {
		return this.damage;
	}
	
	/**
	 * get tower cost
	 * @return tower cost
	 */
	public int getCost() {
		return this.cost;
	}
	
	/**
	 * get tower range
	 * @return tower range
	 */
	public int getRange() {
		return this.range;
	}
	
	/**
	 * get tower existence
	 * @return tower existence
	 */
	public boolean isExist() {
		return this.exist;
	}
	
	/**
	 * get tower number
	 * @return tower number
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * get tower name
	 * @return tower name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * get tower UID
	 * @return tower UID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * set tower UID
	 * @param tower UID
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
} 
