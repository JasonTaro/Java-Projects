import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * @author guojunwei & jasonfukumoto
 * File: Connect4View.java
 * Description: Displays a 7x6 connect 4 game with yellow and red colors
 * for two players. Uses networking with server/client. Allows human and
 * computer to play.
 */
public class Connect4View extends Application implements Observer {

	public final int GAP = 8;
	public final int RAD = 20;
	Connect4Controller controller  = null;
	private Circle[][] board = new Circle[Connect4.ROW][Connect4.COL]; 
	
	public Connect4View() {
		super();
		controller = new Connect4Controller(this);
		Connect4.connection = new Connect4Connection(controller);
	}
	
	/**
	 * start is called on JAVAFX application thread, it creates the board
	 * window
	 */
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Connect 4");
		BorderPane window = new BorderPane();
		window = createMenu(window);
		createGrid(window);
		Scene scene = new Scene(window, 344, 324);
		stage.setScene(scene);
		stage.show();
		//stage.setResizable(false);
		stage.setOnCloseRequest(event -> {
			Connect4.connection.close();
		});
	}

	/**
	 * Adds a file drop down to pop up a menu, the user would choose the game
	 * setups to start a new game
	 * 
	 * @return scene
	 */
	private BorderPane createMenu(BorderPane window) {
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("File");
		MenuItem menuItem = new MenuItem("New Game");
		menuItem.setOnAction(event->{
			Connect4Dialog dialog = new Connect4Dialog();
			dialog.display();
		});
		menu.getItems().add(menuItem);
		menuBar.getMenus().add(menu);
		VBox menuBox = new VBox(menuBar);
		window.setTop(menuBox);
		return window;
	}

	/**
	 * the method keeps track of users click and pass it to the human turn, 
	 * if the column is full, then pop up the error alert
	 * it also sets up the board
	 * @param window
	 */
	private void createGrid(BorderPane window) {
		Pane pane = new Pane();
		window.setCenter(pane);
		// EventHandler<? super MouseEvent> event;
		pane.setOnMouseClicked(event -> {
			if (Connect4.MY_TURN) {
				double sceneX = event.getSceneX();
				int clickCol = controller.getCol(sceneX);
				//int clickCol = getCol(sceneX);
				//if full column
				if (this.board[0][clickCol].getFill().equals(Color.WHITE)) {
					controller.humanTurn(clickCol);
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Error");
					alert.setContentText("Column full, pick somewhere else!");
					alert.showAndWait();
				}
			}
		});
		
		pane.setBackground(new Background(new BackgroundFill(Color.BLUE, 
				CornerRadii.EMPTY, Insets.EMPTY)));
		for (int i = 0; i < Connect4.ROW; i++) {
			for (int j = 0; j < Connect4.COL; j++) {
				int x = (GAP + RAD * 2) * j + 28;
				int y = (GAP + RAD * 2) * i + 28;
				Circle circle = new Circle(x, y, RAD);
				circle.setFill(Color.WHITE);
				pane.getChildren().add(circle);
				board[i][j] = circle;
			}
		}
	}

	/**
	 * the model change and user move are passed to the view, as the game
	 * continues, it keeps track of the color of the circles and determine who is 
	 * the winner.
	 */
	@Override
	public void update(Observable o, Object arg) {
		Connect4MoveMessage message = (Connect4MoveMessage) arg;
		Color color = Color.RED;
		if (message.getColor() == Connect4MoveMessage.YELLOW) {
			color = Color.YELLOW;
		}
		board[message.getRow()][message.getColumn()].setFill(color);
		// determine the winner
		Platform.runLater(() -> {
			winner();
			if (Connect4.MY_TURN && Connect4Connection.PLAY_AS == 2) {
				controller.computerTurn();
			}
	    });
	}
	
	/**
	 * this methods find horizontal, vertical and diagnol direction for
	 * 4 consecutive same colors, when it's found, stop the user play thread
	 * and pop up the window for each user for result, either win or lose
	 */
	private void winner() {
		Color winColor = null;
		for (int i = 0; i < Connect4.ROW; i++) {
			for (int j = 0; j < Connect4.COL; j++) {
				Color circleColor = (Color) board[i][j].getFill();
				if (circleColor.equals(Color.WHITE)) {
					continue;
				}
				//checks right to left diagonal
				Color diagnol = findCircle(i,j,1,1);
				if (diagnol != null) {
					winColor = diagnol;
					break;
				}
				Color horizontal = findCircle(i,j,0,1);
				if (horizontal != null) {
					winColor = horizontal;
					break;
				}
				Color vertical = findCircle(i,j,1,0);
				if (vertical != null) {
					winColor = vertical;
					break;
				}
				//checks left to right diagonal
				Color diagnol2 = findCircle(i,j,1,-1);
				if (diagnol2 != null) {
					winColor = diagnol2;
					break;
				}
			}
		}
		if (winColor!=null) {
			//if yellow, intcolor = 1. else 2
			int intColor = winColor.equals(Color.YELLOW)?1:2;	
			if (intColor == Connect4.MY_COLOR) {
				displayMessage("won");
			}else {
				displayMessage("lost");
			}
		}
	}
	
	/**
	 * Helper method displaying alerts to determine win or lose.
	 * @param message String "win" or "lost"
	 */
	private void displayMessage(String message) {
		Connect4.MY_TURN = false;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Message");
		alert.setHeaderText("Message");
		alert.setContentText("You " + message + "!");
		alert.showAndWait();
	}
	
	/**
	 * this method finds the 4 consecutive same color
	 * @param i is the position of the row
	 * @param j is the position of the column
	 * @param dx is the offset on x axis
	 * @param dy is the offset on y axis
	 * @return the 4 consecutive same color
	 */
	private Color findCircle(int i, int j, int dx, int dy) {
		int count = 0;
		Color circleColor = (Color) board[i][j].getFill();
		while (i >= 0 && i < Connect4.ROW && j >= 0 && j < Connect4.COL) {
			if (board[i][j].getFill().equals(circleColor)) {
				count++;
			}else {
				break;
			}
			if (count==4) {
				return circleColor;
			}
			i+=dx;
			j+=dy;
		}
		return null;
	}
}