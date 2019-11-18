import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Jason Fukumoto
 * File: Connect4Dialog.java
 * Description: The dialog creates a class which extends stage, it acts as a model dialog
 */
public class Connect4Dialog extends Stage {

	/**
	 * the display gives the options to the user to choose to create either server or 
	 * client connection, either human or AI player
	 */
	public void display() {
		this.setTitle("Network Setup");
		this.initModality(Modality.APPLICATION_MODAL);
 		
		VBox vBox = new VBox(15);
		vBox.setPadding(new Insets(20, 20, 20, 20));
		ToggleGroup createGroup = createContainer(vBox);
		ToggleGroup playasGroup = playAsContainer(vBox);
 
		// server and port
		HBox hBox3 = new HBox(15);
		Label server = new Label("Server");
		TextField serverText = new TextField(Connect4Connection.SERVER);
		Label port = new Label("Port");
		TextField portText = new TextField(Connect4Connection.PORT);
		hBox3.getChildren().addAll(server, serverText, port, portText);
		vBox.getChildren().add(hBox3);

		// button OK or cancel
		HBox hBox4 = new HBox(15);
		Button OK = new Button("OK");
		OK.setOnAction(event -> {
			Connect4Connection.CREATE = (int)createGroup.getSelectedToggle().getUserData();
			Connect4Connection.PLAY_AS = (int)playasGroup.getSelectedToggle().getUserData();
			Connect4Connection.SERVER = serverText.getText();
			Connect4Connection.PORT = portText.getText();
			if (Connect4Connection.CREATE==1) {
				Connect4.MY_TURN = true;
				Connect4.MY_COLOR = Connect4MoveMessage.YELLOW;
			}else {
				Connect4.MY_COLOR = Connect4MoveMessage.RED;
			}
			Task<Void> task = new Task<Void>() {
			    @Override public Void call() {
			    	Connect4.connection.createConnection();
			        return null;
			    }
			};
//			Platform.runLater(task);
			new Thread(task).start();
			this.close();
		});
		Button cancle = new Button("Cancle");
		cancle.setOnAction(event -> {
			this.close();
		});
		hBox4.getChildren().addAll(OK, cancle);
		vBox.getChildren().add(hBox4);

		Scene scene = new Scene(vBox);
		this.setScene(scene);
		this.showAndWait();
	}

	/**
	 * Creates the top portion of the networksetup with two buttons that sets
	 * the CREATE global in the connection class to either 1 or 2
	 * @param vBox vbox object to add hboxes
	 */
	private ToggleGroup createContainer(VBox vBox) {
		HBox hBox1 = new HBox(15);
		Label userName = new Label("Create:");
		ToggleGroup createGroup = new ToggleGroup();
		RadioButton radioServer = new RadioButton("Server");
		radioServer.setUserData(1);
		radioServer.setSelected(Connect4Connection.CREATE == 1);
		RadioButton radioClient = new RadioButton("Client");
		radioClient.setUserData(2);
		radioClient.setSelected(Connect4Connection.CREATE == 2);
		
		radioServer.setToggleGroup(createGroup);
		radioClient.setToggleGroup(createGroup);
		hBox1.getChildren().addAll(userName,radioServer,radioClient);
		vBox.getChildren().add(hBox1);
		return createGroup;
	}
	
	/**
	 * Creates the play as container to either choose a human or computer
	 * as buttons and sets the PLAY_AS global to either 1 or 2, in connection
	 * class.
	 * @param vBox adds hbox under the create hbox
	 */
	private ToggleGroup playAsContainer(VBox vBox) {
		HBox hBox2 = new HBox(15);
		Label playas = new Label("Play as:");
		ToggleGroup playasGroup = new ToggleGroup();
		RadioButton radioHuman = new RadioButton("Human");
		radioHuman.setUserData(1);
		radioHuman.setSelected(Connect4Connection.PLAY_AS == 1);
		RadioButton radioComputer = new RadioButton("Computer");
		radioComputer.setUserData(2);
		radioComputer.setSelected(Connect4Connection.PLAY_AS == 2);
		//addAll appends to create line so manually add.
		radioHuman.setToggleGroup(playasGroup);
		radioComputer.setToggleGroup(playasGroup);
		hBox2.getChildren().add(playas); //playas = play as
		hBox2.getChildren().add(radioHuman);
		hBox2.getChildren().add(radioComputer);
		vBox.getChildren().add(hBox2);
		return playasGroup;
	} 
}
