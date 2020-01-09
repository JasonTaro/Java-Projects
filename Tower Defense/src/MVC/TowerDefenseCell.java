package MVC;
 
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The cell class is an array list which can hold object like enemy or tower  
 */

public class TowerDefenseCell implements Serializable {
	
	private ArrayList<Object> cellList;
	
	/**
	 * constructor initialize an obj type array list 
	 */
	TowerDefenseCell(){
		cellList = new ArrayList<Object>();
	}
	
	/**
	 * can add obj to the arraylist
	 * @param obj
	 */
	public void add(Object obj) {
		cellList.add(obj);
	}
	
	/**
	 * remove the object from the list
	 */
	public void setToEmpty(){
		cellList.remove(0);
	}
	
	/**
	 * determine if the list is empty
	 * @return
	 */
	public boolean isEmpty() {
		return cellList.size() == 0;
	}
	
	/**
	 * get the value out of the list
	 * @return obj at the first position
	 */
	public Object getFirst() {
		return cellList.get(0);
	}
	
	/**
	 * getter, get the obj array list
	 * @return obj array list
	 */
	public ArrayList<Object> getList(){
		return cellList;
	}
}
