package main;
import MVC.TowerDefenseView;
import javafx.application.Application;
/**
 * 
 * @author  YongqiJia & JasonFukumoto & Qingzhou Pan & Guojun Wei
 * CSC 335, Fall 2019
 * Team Project
 * This project is to create a 2-D tower defense game, the player must prevent
 * the enemy from getting to the end with defense towers
 *
 */

public class TowerDefense {	
	/**
	 * the main goes directly to the view start method
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(TowerDefenseView.class, args);
	}

}
