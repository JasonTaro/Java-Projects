package MVC;

import java.io.Serializable;

/**
 * 
 * @author  YongqiJia & JasonFukumoto & QingzhouPan & GuojunWei
 * CSC 335, Fall 2019
 * Team Project
 * The point class has x, y coordinates, getters, setters and equal methods
 */
public class Point implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer col;
	private Integer row;
	
	/**
	 * constructor takes in col as x and row as y coordinate
	 * @param col x coordinate
	 * @param row y coordinate
	 */
	public Point(Integer col, Integer row) {
		super();
		this.col = col;
		this.row = row;
	}
	
	public Point() {
		super();
	}
	
	/**
	 * equal method to compare two points
	 * @param other
	 * @return true if equal, false otherwise
	 */
	public boolean equalPoint(Point other) {
		if(col==null||row==null) {
			return false;
		}else {
			return col.equals(other.getCol())&&row.equals(other.getRow());
		}
	}
	
	/**
	 * reset the point to the new position
	 * @param other
	 */
	public void move(Point other) {
		this.setCol(other.getCol());
		this.setRow(other.getRow());
	}
	
	/**
	 * get x position in pixel
	 * @return x position in pixel
	 */
	public double getX(){
		return this.col*TowerDefenseView.HEIGHT+TowerDefenseView.HEIGHT/2;
	}
	
	/**
	 * get y position in pixel
	 * @return y position in pixel
	 */
	public double getY(){
		return this.row*TowerDefenseView.HEIGHT+TowerDefenseView.HEIGHT/2;
	}
	
	/**
	 * get the x position of the point
	 * @return x position of the point
	 */
	public Integer getCol() {
		return col;
	}
	
	/**
	 * set x position of the point
	 * @param col as x
	 */
	public void setCol(Integer col) {
		this.col = col;
	}
	
	/**
	 * get the y position of the point
	 * @return y position of the point
	 */
	public Integer getRow() {
		return row;
	}
	
	/**
	 * set y position of the point
	 * @param row as y
	 */
	public void setRow(Integer row) {
		this.row = row;
	}
	
	
	
}
