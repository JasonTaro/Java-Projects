package MVC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.UUID;

import Enemies.Enemy;
import Networking.TowerDefenseMessge;
import Tower.Tower;

/**
 * 
 * @author  YongqiJia & JasonFukumoto & QingzhouPan & GuojunWei
 * CSC 335, Fall 2019
 * Team Project
 * The model class has all the data for the game, intBoard, objBoard, the clicked
 * tower information, enemyWave list, etc,.
 *
 */

public class TowerDefenseModel extends Observable implements Serializable {

	private int imagePos = 225;//it's 15*15 gridpane, so totally 225 pieces of images 
	
	private static int LEVEL = 1;
	private int level = LEVEL;

	private int ROW; // this is temporary
	private int COL; // this is temporary
	private List<Point> path = new ArrayList<>();
	private List<List<Enemy>> wave = new ArrayList<>();
	private ArrayList<Tower> towerList = new ArrayList<>();

	private Map<Enemy, Point> map = new HashMap<>();

	private TowerDefenseCell objBoard[][];
	private int intBoard[][]; // [0-Grass] [1-Road] [2-Tower] [3-Enemy]

	private Tower LAST_CLICKED_TOWER;
	private int balance;

	private ArrayList<Integer> pathCoord = new ArrayList<Integer>();

	public boolean changingMap = false;

	public List<Enemy> currentEnemyList;
	
	/**
	 * get the level
	 * @return the level
	 */
	public int getLevel() {
		return LEVEL;
	}
	
	/**
	 * when the stage is over, increment level by 1
	 */
	public void addLevel() {
		LEVEL += 1;
		//System.out.println("add one !!!!!!!!!!!!!!!!!");
	}

	/**
	 * get the list of turn point x, y coordinate
	 * @return list of turn point x, y coordinate
	 */
	public ArrayList<Integer> getPathCoord() {
		return pathCoord;
	}

	/**
	 * it gives which image in number
	 * @return image number
	 */
	public int imagePos() {
		return imagePos;
	}

	/**
	 * image number decrement for background image paste
	 */
	public void update_imagePos() {
		imagePos -= 1;

	}

	/**
	 * the model constructor initialize objBoard and intBoard class and balance
	 */
	public TowerDefenseModel() {
		objBoard = new TowerDefenseCell[ROW][COL];
		intBoard = new int[ROW][COL];
		this.balance = 1000;

	}

	/**
	 * turn point is added to the list
	 * @param a is the x y coordinate read from the txt file
	 */
	public void setPathCoord(int a) {
		this.pathCoord.add(a);
	}

	/**
	 * getter for tower list
	 * @return an arraylist of towers
	 */
	public ArrayList<Tower> getTowerList() {
		return this.towerList;
	}

	/**
	 * Places tower and notifies the observer if the cost is in the budget.
	 * 
	 * @param row
	 *            coordinate of row.
	 * @param col
	 *            coordinate of col.
	 */
	public void placeTower(int row, int col) {
		TowerDefenseMessge message = null;
		if ((this.balance - LAST_CLICKED_TOWER.getCost()) >= 0) { // checks negative balance
			LAST_CLICKED_TOWER.setTowerROW(row);
			LAST_CLICKED_TOWER.setTowerCOL(col);
			// give a unique id
			LAST_CLICKED_TOWER.setId(UUID.randomUUID().toString());
			towerList.add(LAST_CLICKED_TOWER);
			objBoard[row][col].add(LAST_CLICKED_TOWER);
			intBoard[row][col] = 2;
			this.balance -= LAST_CLICKED_TOWER.getCost();
			message = new TowerDefenseMessge(row, col, LAST_CLICKED_TOWER.getIndex());
		}else {
			message = new TowerDefenseMessge(0, 0, 404);
		}
		setChanged();
		notifyObservers(message);
	}

	/**
	 * show different types of tower in the view
	 */
	public void loadTower() {
		for (Tower tower : towerList) {
			TowerDefenseMessge message = new TowerDefenseMessge(tower.getTowerROW(), tower.getTowerCOL(),
					tower.getIndex());
			setChanged();
			notifyObservers(message);
		}
	}

	/**
	 * sell the tower and notify the observer
	 * 
	 * @param row
	 *            coordinate of row.
	 * @param col
	 *            coordinate of col.
	 */
	public void sellTower(int row, int col) {
		this.balance += LAST_CLICKED_TOWER.getCost();
		for (Tower tower : towerList) {
			if (LAST_CLICKED_TOWER.getId().equals(tower.getId())) {
				towerList.remove(LAST_CLICKED_TOWER);
			}
			break;
		}
		LAST_CLICKED_TOWER = null;
		intBoard[row][col] = 0;  // here!!!!!!!!!!!!!!!!!!!!
		TowerDefenseMessge message = new TowerDefenseMessge(row, col, 0);
		setChanged();
		notifyObservers(message);
	}

	/**
	 * for tower 6 only, it increase the money 30 dollars per second
	 */
	public void fps() {
		for (Tower tower : towerList) {
			if (tower.getName().equals("Money Generator")) {
				addBalance(30);
			}
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * get y coordinate
	 * @return y coordinate
	 */
	public int getRow() {
		return ROW;
	}

	/**
	 * set y coordinate
	 * @param y coordinate
	 */
	public void setRow(int row) {
		this.ROW = row;
	}

	/**
	 * get x coordinate
	 * @return x coordinate
	 */
	public int getCol() {
		return COL;
	}

	/**
	 * set x coordinate
	 * @param x coordinate
	 */
	public void setCol(int col) {
		this.COL = col;
	}

	/**
	 * get object board
	 * @return object board
	 */
	public Object[][] get_objBoard() {
		return this.objBoard;
	}

	/**
	 * get obj board position based upon row and col value
	 * @param row
	 * @param col
	 * @return TowerDefenseCell object
	 */
	public TowerDefenseCell get_objBoard_pos(int row, int col) {
		return objBoard[row][col];
	}

	/**
	 * set TowerDefenseCell obj to board position based upon row and col value
	 * @param row
	 * @param col
	 * @param cell
	 */
	public void set_objBoard_pos(int row, int col, TowerDefenseCell cell) {
		objBoard[row][col] = cell;
	}

	/**
	 * get int board
	 * @return int board
	 */	
	public int[][] get_intBoard() {
		return this.intBoard;
	}

	/**
	 * get int value from int board based upon row and col value
	 * @param row
	 * @param col
	 * @return
	 */
	public int get_intBoard_pos(int row, int col) {
		return intBoard[row][col];
	}

	
	/**
	 * set int value to int board position based upon row and col value
	 * @param row
	 * @param col
	 * @param cell
	 */
	public void set_intBoard_pos(int row, int col, int i) {
		intBoard[row][col] = i;
	}

	/**
	 * get the last click tower
	 * @return the last click tower
	 */
	public Tower getLCT() {
		return this.LAST_CLICKED_TOWER;
	}

	/**
	 * set the last click tower
	 * @param the tower clicked
	 */
	public void setLCT(Tower tower) {
		this.LAST_CLICKED_TOWER = tower;
	}

	/**
	 * get the money balance 
	 * @return the money balance
	 */
	public int getBalance() {
		return this.balance;
	}

	/**
	 * initialize the obj and int board
	 */
	public void init_Board() {
		this.objBoard = new TowerDefenseCell[ROW][COL];
		this.intBoard = new int[ROW][COL];

	}

	/**
	 * get the list of the enemy path point
	 * @return the list of the enemy path point
	 */
	public List<Point> getPath() {
		return path;
	}

	/**
	 * set the list of the enemy path point
	 * @param path
	 */
	public void setPath(List<Point> path) {
		this.path = path;
	}

	/**
	 * get 2d enemy wave list
	 * @return 2d enemy wave list
	 */
	public List<List<Enemy>> getWave() {
		return wave;
	}

	/**
	 * set 2d enemy wave list
	 * @param enemy wave list
	 */
	public void setWave(List<List<Enemy>> wave) {
		this.wave = wave;
	}

	/**
	 * get the enemy to point map
	 * @return the enemy to point map
	 */
	public Map<Enemy, Point> getMap() {
		return map;
	}

	/**
	 * set the enemy to point map
	 * @param map
	 */
	public void setMap(Map<Enemy, Point> map) {
		this.map = map;
	}

	/**
	 * increment the balance properly, notify the view
	 * @param credit from killing the enemy or selling the tower
	 */
	public void addBalance(int credit) {
		this.balance += credit;
		TowerDefenseMessge message = new TowerDefenseMessge(0, 0, -1);
		setChanged();
		notifyObservers(message);
	}

	/**
	 * pass tower and enemy info to the message and notify the view
	 * @param tower object with x, y coordinate
	 * @param enemy object with x, y coordinate
	 */
	public void attackAction(Tower tower, Enemy enemy) {
		TowerDefenseMessge message = new TowerDefenseMessge(tower, enemy, 99);
		setChanged();
		notifyObservers(message);

	}

	/**
	 * change to the new map and notify the view
	 */
	public void changeMap() {
		changingMap = true;
		TowerDefenseMessge message = new TowerDefenseMessge(0, 0, TowerDefenseMessge.TYPE_CHANGE_MAP);
		setChanged();
		notifyObservers(message);
	}

	/**
	 * load the map information from the model
	 */
	public void load() {
		TowerDefenseMessge message = new TowerDefenseMessge(0, 0, TowerDefenseMessge.TYPE_LOAD);
		setChanged();
		notifyObservers(message);
	}

	/**
	 * save the current level
	 */
	public void saveLevel(){
		this.level = LEVEL;
	}
	/**
	 * load the saved level
	 */
	public void loadLevel() {
		LEVEL = this.level;
	}
	

}
