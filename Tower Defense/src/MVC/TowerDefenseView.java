package MVC;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javax.swing.JOptionPane;

import Enemies.Enemy;
import Enemies.Enemy1;
import Enemies.Enemy2;
import Enemies.Enemy3;
import Enemies.Enemy4;
import Networking.TowerDefenseMessge;
import Tower.Tower;
import Tower.Tower1;
import Tower.Tower2;
import Tower.Tower3;
import Tower.Tower4;
import Tower.Tower5;
import Tower.Tower6;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.TowerDefense;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * 
 * @author  YongqiJia & JasonFukumoto & Qingzhou Pan & Guojun Wei
 * CSC 335, Fall 2019
 * Team Project
 * The view class takes in player's action, click, place, sell tower, and menu 
 * operation, the enemy comes out and the tower gives corresponding attack
 *
 */

public class TowerDefenseView extends Application implements Observer {

	public static final double HEIGHT = 50; //each grid pane square size in pixel

	public TowerDefenseController controller;
	private GridPane gridPane = new GridPane();
	private Tower sellTower = null;
	private Label moneyBalance;
	private Label livesLeft;
	private static Timeline timeline;
	private PathTransition pathTransition;
	private Group root;
	private BorderPane window;
	private AudioStream audios;
	private Stage stages;

	/**
	 * the constructor initialize the the controller and start sound for each stage
	 */
	public TowerDefenseView() {
		init();

	}

	/**
	 * the init method initialize the the controller and start sound for each stage
	 */
	public void init() {
		controller = new TowerDefenseController(this);
		playSound("start.wav");
	}

	/**
	 * the entry point of JavaFX applications, the boarderpane is created with 
	 * specified size.
	 * @param the stage class is the outside container of JavaFX
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stages = stage;
		stage.setTitle("Tower Defense");
		window = new BorderPane();
		root = new Group(window);
		createBoard(window);
		createRightContainer(window);
		Scene scene = new Scene(root, 1150, 750);
		stage.setScene(scene);
		
		stage.show();
	}

	/**
	 * paste image to the 15*15 grid pane based upon specific map info, set LCT 
	 * tower info based upon the event, place or sell tower 
	 * 
	 * @param window: BorderPane to set grid
	 */
	private void createBoard(BorderPane window) {
		//add background image from the map folder
		int get_imagePos = controller.get_imagePos();
		for (int i = 0; i < controller.getCol(); i++) {
			for (int j = 0; j < controller.getRow(); j++) {
				if (get_imagePos > 0) {
					Image image = new Image(
							"file:image/map" + controller.getLEVEL() + "/" + 
					get_imagePos + ".jpg");
					ImageView imageView = new ImageView();
					imageView.setImage(image);
					imageView.setFitHeight(50);
					imageView.setFitWidth(50);

					gridPane.add(imageView, j, i);
					get_imagePos--;
				}

			}
		}
		//place tower
		gridPane.setOnMouseClicked(e -> {
			int x = (int) e.getX();
			int y = (int) e.getY();
			//if no tower at this position, place a tower
			if (!controller.is_tower_here(x, y)) {
				if (controller.getLCT() != null) {
					controller.placeTower(x, y);
				} else {
					// lct is null, nothing happen
					playSound("404.wav");
				}
			//sell tower
			} else {
				if (controller.getLCT() == null) {
					for (Tower tower : controller.getTowerList()) {
						if (tower.getTowerCOL() == x / 50 && tower.getTowerROW() == y / 50) {
							sellTower = tower;
						}
					}
					Tower temp = controller.getTowerAt(x, y);
					int credit = temp.getSoldPrice();
					sellTower.setCost(credit);
					controller.setLCT(sellTower);
				} else {
					controller.setLCT_null();
				}
			}
		});
		gridPane.setVgap(0.2); // vertical gap between each grid pane square
		gridPane.setHgap(0.2); // horizontal gap between each grid pane square
		window.setCenter(gridPane);

	}

	/**
	 * Creates the menu, money, and tower vBoxes that's displayed on the right.
	 * 
	 * @param window
	 *            BorderPane to add vBoxes
	 */
	private void createRightContainer(BorderPane window) {
		GridPane rightGrid = new GridPane();
		rightGrid.setPadding(new Insets(10,10,10, 20));
		rightGrid.setVgap(30);
		rightGrid.setHgap(10);
		rightGrid.setStyle("-fx-background-image:url('file:image/tut5_win.png')");
		createMenu(rightGrid); // menu VBox
		createMoney(rightGrid); // money VBox
		createTowers(rightGrid); // tower VBox
		createTowerInfo(rightGrid);
		window.setRight(rightGrid);
		
	}

	/**
	 * Creates the menu drop down for new game, pause, and speedup, save and load
	 * 
	 * @param vBox
	 *            Vertical box to add children.
	 */
	private void createMenu(GridPane rightGrid) {
		// Menu VBox
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Menu");
		MenuItem startGame = new MenuItem("Play");
		MenuItem pause = new MenuItem("Pause");
		MenuItem speedUp = new MenuItem("Speed++");
		MenuItem save = new MenuItem("Save");
		MenuItem load = new MenuItem("Load");

		pause.setDisable(true); // disable until new game is clicked
		speedUp.setDisable(true); // disable until new game is clicked

		
		save.setOnAction(event -> {
			this.controller.save();
		});
		load.setOnAction(event -> {
			this.controller.load();
		});
		// event handler
		startGame.setOnAction(event -> {
			startNewGame(startGame, pause, speedUp); // parameters used when new game
		});
		menu.getItems().addAll(startGame, pause, speedUp, save, load);
		menuBar.getMenus().add(menu);
		rightGrid.add(menuBar,0,0);
	}

	/**
	 * Creates the total balance for the game.
	 * 
	 * @param vBox
	 *            Vertical box to add children
	 */
	private void createMoney(GridPane rightGrid) {	
		HBox moneyBox = new HBox(5);	
		Image imgBalance = new Image("file:image/menu_sell1.png");	
		ImageView imgView = new ImageView(imgBalance);	
		Image imgLives = new Image("file:image/heart.png");	
		ImageView imgViewLives = new ImageView(imgLives);	
		imgView.setFitHeight(30);	
		imgView.setFitWidth(30);	
		imgViewLives.setFitHeight(30);	
		imgViewLives.setFitWidth(30);	
		moneyBalance = new Label(Integer.toString(controller.getBalance()));	
		moneyBalance.setTextFill(Color.web("#FFD700"));	
		moneyBalance.setStyle("-fx-font: 24 arial;");	
		moneyBalance.setPadding(new Insets(0,20,0,5));	
		livesLeft = new Label("5");	
		livesLeft.setStyle("-fx-font: 24 arial;");	
		livesLeft.setTextFill(Color.web("#FF0000"));	
		livesLeft.setPadding(new Insets(0,0,0,5));	
		moneyBox.getChildren().addAll(imgView,moneyBalance,imgViewLives, livesLeft);	
		rightGrid.add(moneyBox,1,0);	
	}

	/**
	 * Creates tower boxes with event handlers, sell button
	 * 
	 * @param vBox
	 *            Vertical box to add children
	 */
	private void createTowers(GridPane rightGrid) {
		List<String> towerName = new ArrayList<String>(Arrays.asList("Machine Gun", "Sniper", "Cannon", "Rocket\nLauncher", "Tesla Gun", "Money\nGenerator"));
		List<Tower> towerList = towerList();
		List<ImageView> imgList = imgList();
		for (int i = 0; i < towerName.size(); i++) {
			final String index = i + 1 + "";
			ImageView image = imgList.get(i);
			Tower tower = towerList.get(i);
			VBox towerBox = new VBox(5);
			towerBox.setAlignment(Pos.CENTER);
			Button towerButton = new Button(towerName.get(i));
			towerBox.getChildren().addAll(image, towerButton);
			rightGrid.add(towerBox, 0, i + 1);
			towerButton.setOnAction(e -> {
				playSound("Click.wav");
				controller.new_tower_to_LCT(index);
			});
		}
		VBox towerBox = new VBox(3);
		towerBox.setAlignment(Pos.CENTER);
		Button sellButton = new Button("sell");
		Label sellReturn = new Label("Returns 75% cost of tower");
		sellReturn.setTextFill(Color.web("#FFFFFF"));
		towerBox.getChildren().addAll(sellButton);
		rightGrid.add(towerBox,0, 7);
		rightGrid.add(sellReturn, 1, 7);
		sellButton.setOnMouseClicked(e -> {
			int x = sellTower.getTowerCOL();
			int y = sellTower.getTowerROW();
			controller.sellTower(x, y);
		});
	}

	/**
	 * Displays the information about each tower that includes the name, damage, description, and cost
	 * @param rightGrid
	 */
	private void createTowerInfo(GridPane rightGrid) {
		List<Tower> towerName = towerList();
		for(int i = 0; i < towerName.size(); i++) {
			Tower tower = towerName.get(i);
			Label label = new Label();
			for(int j = 0; j < 5; j++) {// 5 labels
				String info = "";
				info = "Name: "+tower.getName()+"\nDamage: "+tower.getDamage()+" dmg\nDescription: "+tower.getDirection()+"\nCost: $"+tower.getCost();
				label = new Label(info);
				label.setTextFill(Color.web("#FFFFFF"));
			}
			rightGrid.add(label, 1, i+1);
		}
	}
	
	
	/**
  	* Creates a list of tower objects to be used as buttons
  	* @return List of Tower obejcts
  	*/
	private List<Tower> towerList(){
		List<Tower> towerList = new ArrayList<Tower>();
		Tower t1 = new Tower1();
		Tower t2 = new Tower2();
		Tower t3 = new Tower3();
		Tower t4 = new Tower4();
		Tower t5 = new Tower5();
		Tower t6 = new Tower6();
		Collections.addAll(towerList, t1,t2,t3,t4,t5,t6);
		return towerList;
	 }
	 
	 /**
	  * Creates individual Images in an image view into a list
	  * @return A list of ImageView Objects to be displayed.
	  */
	private List<ImageView> imgList(){
		List<ImageView> imgList = new ArrayList<ImageView>();
		List<Image> imgs = new ArrayList<Image>();
		Image image1 = new Image("file:image/tower/t1.png");
		Image image2 = new Image("file:image/tower/t2.png");
		Image image3 = new Image("file:image/tower/t3.png");
		Image image4 = new Image("file:image/tower/t4.png");
		Image image5 = new Image("file:image/tower/t5.png");
		Image image6 = new Image("file:image/tower/t6.png");
		Collections.addAll(imgs, image1,image2,image3,image4,image5, image6);
		for(int i = 0; i < imgs.size(); i ++) {
			ImageView imageView = new ImageView();
			imageView.setImage(imgs.get(i));
			imageView.setFitHeight(35);
			imageView.setFitWidth(45);
			imgList.add(imageView);
		}
		return imgList;
	 }
	
	/**
	 * start a new timeline to start the game
	 * @param startGame action
	 * @param pause game action
	 * @param speedUp action
	 */
	private void startNewGame(MenuItem startGame, MenuItem pause, MenuItem speedUp) {
		pause.setDisable(false);
		speedUp.setDisable(false);
		startTimer();
		pause.setOnAction(event -> {
			pauseGame();
		});
		speedUp.setOnAction(event -> {
			speedUpGame();
		});

	}

	/**
	 * pause the game
	 */
	private void pauseGame() {
		timeline.pause();
	}

	/**
	 * double the game speed
	 */
	private void speedUpGame() {
		timeline.stop();
		timeline = new Timeline();
		addKeyFrame(100);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(false);
		timeline.play();
	}

	/**
	 * choose different music for different levels, initialize the speed 1 sec/move
	 */
	private void startTimer() {
		if(controller.getLEVEL() == 1 || controller.getLEVEL() == 2) {
			playBackground("1and2.wav");
		}else {
			playBackground("3.wav");
		}
		double speed = 1000;
		timeline = new Timeline();
		addKeyFrame(speed);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(false);
		timeline.play();
	}

	/**
	 * create a new keyFrame and gives a update shorter time for faster speed,
	 * call the fps(), add it to the timeline.
	 * @param speed as the time in milliseconds
	 */
	private void addKeyFrame(double speed) {
		KeyFrame keyFrame = new KeyFrame(Duration.millis(speed), e -> fps());
		KeyFrame refrashEndStatus = new KeyFrame(Duration.millis(speed), e -> refrashEndStatus());
		timeline.getKeyFrames().add(keyFrame);
		timeline.getKeyFrames().add(refrashEndStatus);
	}

	/**
	 * calls the frame per second from controller
	 */
	private void fps() {
		controller.fps();
	}

	/**
	 * change map with certain conditions with details in controller
	 */
	private void refrashEndStatus() {
		controller.refrashEndStatus();
	}

	Map<String, ImageView> circleMap = new HashMap<>();

	/**
	 * draw images or gifs or execute different command based upon different 
	 * instruction
	 */
	@Override
	public void update(Observable o, Object arg) {
		TowerDefenseModel model = (TowerDefenseModel) o;
		if (arg != null) {
			TowerDefenseMessge message = (TowerDefenseMessge) arg;
			if (message.getColor() == 0) { // Grass
				sellTower(message);
			} else if (message.getColor() > 0 && message.getColor() < 7) { // Tower
				placeTower(message);
			} else if (message.getColor() == -1) { // < 0 update balance
				
			} else if (message.getColor() == 99) { // attack action
				attackAction(message);
			}else if (message.getColor() == 404) { // attack action
				playSound("404.wav");
			}else if (message.getColor() == TowerDefenseMessge.TYPE_LOAD) { // attack action
					createBoard(window);
			}else if (message.getColor() == TowerDefenseMessge.TYPE_CHANGE_MAP) { // attack action
				// alert 'continue?'
				Platform.runLater(() -> {
					AudioPlayer.player.stop(audios);
					playSound("victory.wav");
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Congratulations!");
					alert.setHeaderText("You did a great a job!");
					alert.setContentText("Do you want to move to next stage?");
					Optional<ButtonType> result = alert.showAndWait();
					TowerDefenseView.timeline.stop();
//					ObservableList<KeyFrame> keyFrames = TowerDefenseView.timeline.getKeyFrames();
//					for (int i = 0; i < keyFrames.size(); i++) {
//					}
					if (result.get() == ButtonType.OK) {
						// ... user chose OK
						controller.addLevel();
						System.out.println(controller.getLEVEL());
						if(controller.getLEVEL() > 3) {
							Alert al = new Alert(AlertType.INFORMATION);
							al.setTitle("Thank You!");
							al.setHeaderText(null);
							al.setContentText("Thank you for choosing Tower Defense Game!");
							al.showAndWait();
							System.exit(1);
						}
						init();
						createBoard(window);
					} else {
						// ... user chose CANCEL or closed the dialog
						Alert al = new Alert(AlertType.INFORMATION);
						al.setTitle("Thank You!");
						al.setHeaderText(null);
						al.setContentText("Thank you for choosing Tower Defense Game!");
						al.showAndWait();
						System.exit(1);
					}
				});
			}
		} else {
			moveEnemy(model);
		}
		moneyBalance.setText(Integer.toString(controller.getBalance()));  
	}

	/**
	 * move enemies on the view based on the data from model
	 * @param model provides the enemy x, y coordinate for 
	 */
	private void moveEnemy(TowerDefenseModel model) {
		Map<Enemy, Point> map = model.getMap();
		ArrayList<Enemy> removelist = new ArrayList<>();
		// enemy in view
		for (Entry<Enemy, Point> element : map.entrySet()) {
			Enemy key = element.getKey();
			Point value = element.getValue();
			if (value == null) {
				createEnemyView(key);
				// create line
			} else {
				// find circle
				ImageView circle = circleMap.get(key.getId());
				if (circle==null) {
					circle = createEnemyView(key);
				}
				
				// Trigger die?
				if (!key.isAlive()) {
					circle.setVisible(false);
					removelist.add(key);
					circleMap.remove(key.getId());

					//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					model.addBalance(key.getCredit());
					//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				} else {
					Path path = new Path();
					path.getElements().add(new MoveTo(value.getX(), value.getY()));
					path.getElements().add(new LineTo(key.getX(), key.getY()));

					// move in line
					pathTransition = new PathTransition();
					pathTransition.setDuration(Duration.millis(1000));
					pathTransition.setNode(circle);
					pathTransition.setPath(path);
					//
					pathTransition.play();
					pathTransition.setOnFinished((event) -> {
						path.getElements().clear();
					});

					// enemy exits map without dying	
					if (key.getX() == 15 * HEIGHT + 25) {	
						Integer lives = Integer.parseInt(livesLeft.getText());	
						lives--;	
						livesLeft.setText(Integer.toString(lives));	
						circle.setVisible(false);	
						if(lives <=0 ) {	
							timeline.stop();
							Platform.runLater(() -> {
								Alert alert = new Alert(AlertType.CONFIRMATION);
								alert.setTitle("Congratulations!");
								alert.setHeaderText("You suck.");
								alert.setContentText("Do you want to try again?");
								Optional<ButtonType> result = alert.showAndWait();
								
								if (result.get() == ButtonType.OK) {
									// ... user chose OK
//									controller.addLevel();
//									System.out.println(controller.getLEVEL());
//									if(controller.getLEVEL() > 3) {
//										Alert al = new Alert(AlertType.INFORMATION);
//										al.setTitle("Thank You!");
//										al.setHeaderText(null);
//										al.setContentText("Thank you for choosing Tower Defense Game!");
//										al.showAndWait();
//										System.exit(1);
//									}
									init();
//									createBoard(window);
									try {
										start(stages);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {
									// ... user chose CANCEL or closed the dialog
									Alert al = new Alert(AlertType.INFORMATION);
									al.setTitle("Thank You!");
									al.setHeaderText(null);
									al.setContentText("Thank you for choosing Tower Defense Game!");
									al.showAndWait();
									System.exit(1);
								}
							});
								
							//ALERT	
							//	
							//init();	
						}	
					}
				}
			}
		}
		for (Enemy enemy : removelist) {
			controller.removeEnemy(enemy);
		}
		System.out.println();
	}

	/**
	 * draw the enemy on the board according to the enemy type
	 * @param key as enemy type UID
	 * @return
	 */
	private ImageView createEnemyView(Enemy key) {
		ImageView imageView = new ImageView();

		imageView.setX(-50);
		imageView.setY(-50);
		if (key instanceof Enemy1) {
			Image image = new Image("file:image/enemy/e1.gif");
			imageView.setImage(image);
		} else if (key instanceof Enemy2) {
			Image image = new Image("file:image/enemy/e2.gif");
			imageView.setImage(image);
		} else if (key instanceof Enemy3) {
			Image image = new Image("file:image/enemy/e3.gif");
			imageView.setImage(image);
		} else if (key instanceof Enemy4) {
			Image image = new Image("file:image/enemy/e4.gif");
			imageView.setImage(image);
		} else {
		}

		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		circleMap.put(key.getId(), imageView);
		root.getChildren().add(imageView);
		return imageView;
		
	}

	/**
	 * this method creates a black ball as bullet, shooting from the tower
	 * to the enemy, gives different sound for different tower
	 * @param message has the x, y coordinate of the tower and the enemy
	 */
	private void attackAction(TowerDefenseMessge message) {
		String sound = "";
		if (message.getTower() instanceof Tower1) {
			sound = "Minigun_lvl3.wav";
			playSound(sound);
		} else if (message.getTower() instanceof Tower2) {
			sound = "Sniper.wav";
			playSound(sound);
		} else if (message.getTower() instanceof Tower3) {
			sound = "Rocketgun_launch.wav";
			playSound(sound);
		} else if (message.getTower() instanceof Tower4) {
			sound = "Explosion.wav";
			playSound(sound);
		} else if (message.getTower() instanceof Tower5) {
			sound = "Teslagun.wav";
			playSound(sound);
		}
		Circle c = new Circle();
		c.setFill(Color.BLACK);
		c.setRadius(5);
		Path p = new Path();
		p.getElements().add(
				new MoveTo(message.getTower().getTowerCOL() * 50 + 25, message.getTower().getTowerROW() * 50 + 25));
		p.getElements().add(new LineTo(message.getEnemy().getX(), message.getEnemy().getY()));

		PathTransition pt = new PathTransition();
		pt.setDuration(Duration.millis(100));
		pt.setNode(c);
		pt.setPath(p);
		pt.play();

		pt.setOnFinished((event) -> {
			c.setVisible(false);
		});
		root.getChildren().add(c);
	}

	/**
	 * place tower in the view, set x,y on the board a gif tower 
	 * @param message with x, y coordinate for the tower
	 */
	private void placeTower(TowerDefenseMessge message) {
		playSound("Building.wav");
		ImageView imageView = new ImageView();
		if (message.getColor() == 1) {
			Image image = new Image("file:image/tower/t1.gif");
			imageView.setImage(image);
		} else if (message.getColor() == 2) {
			Image image = new Image("file:image/tower/t2.gif");
			imageView.setImage(image);
		} else if (message.getColor() == 3) {
			Image image = new Image("file:image/tower/t3.gif");
			imageView.setImage(image);
		} else if (message.getColor() == 4) {
			Image image = new Image("file:image/tower/t4.gif");
			imageView.setImage(image);
		} else if (message.getColor() == 5) {
			Image image = new Image("file:image/tower/t5.gif");
			imageView.setImage(image);
		} else if (message.getColor() == 6) {
			Image image = new Image("file:image/tower/t6.gif");
			imageView.setImage(image);
		}
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		gridPane.add(imageView, message.getCol(), message.getRow());
	}

	/**
	 * when you sell tower, replace the image with the original background
	 * @param message
	 */
	private void sellTower(TowerDefenseMessge message) {
		playSound("Buying.wav");
		Image image = new Image("file:image/map" + controller.getLEVEL() + "/"
				+ ((15 - message.getRow() - 1) * 15 + (15 - message.getCol())) + ".jpg");
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setFitHeight(50);
		imageView.setFitWidth(50);
		gridPane.add(imageView, message.getCol(), message.getRow());
	}

	/**
	 * play basic operation audio
	 * @param sound
	 */
	private void playSound(String sound) {
		try {
			InputStream music;
			music = new FileInputStream(new File("audio/" + sound));
			AudioStream audios = new AudioStream(music);
			AudioPlayer.player.start(audios);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error");
		}
	}
	
	/**
	 * play background audio
	 * @param sound as audio name
	 */
	private void playBackground(String sound) {
		try {
			InputStream music;
			music = new FileInputStream(new File("audio/" + sound));
			audios = new AudioStream(music);
			AudioPlayer.player.start(audios);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error");
		}
	}

}
