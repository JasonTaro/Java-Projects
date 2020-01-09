package Enemies;
 
import MVC.Point;

/**
 * The abstract enemy class provides all the info for the the MVC mode
 */
public abstract class Enemy extends Point {

	private String id;
	protected int hp;
	protected int credit;
	protected int speed;
	protected boolean alive;
	
	
	/**
	 * get the enemy hp
	 * @return enemy hp
	 */
	public int getHp() {
		return hp;
	}
	
	/**
	 * set enemy hp
	 * @param enemy hp
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	/**
	 * get the certain credits when enemy is killed
	 * @return enemy credits
	 */
	public int getCredit() {
		return credit;
	}
	
	/**
	 * get the enemy speed
	 * @return enemy speed
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * set speed of the enemy
	 * @param speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * determine if the enemy is alive
	 * @return enemy alive boolean value
	 */
	public boolean isAlive() {
		if(this.hp <= 0) {
			this.alive = false;
		}
		return alive;
	}
	
	/**
	 * set enemy alive status
	 * @param alive
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	/**
	 * get the enemy UID
	 * @return enemy UID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * set enemy UID
	 * @param UID
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * when the enemy is attacked by the tower, the hp decrement accordingly
	 * @param damage from specific tower
	 */
	public void beingAttacked(int damage) {
		this.hp = this.hp - damage;
	}
}
