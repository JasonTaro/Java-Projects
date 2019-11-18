import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * 
 * @author guojunwei & jasonfukumoto
 * Connection class creates the connection between server and client
 */
public class Connect4Connection {
	
	public static String SERVER = "localhost";
	public static String PORT = "4000";
	
	public static int CREATE = 1;  //1 server 2 client;
	public static int PLAY_AS = 1; //1 Human 2 Computer;
	private Socket connection;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Connect4Controller controller;
	private ServerSocket serverSocket;
	private boolean close = false;

	public Connect4Connection(Connect4Controller controller) {
		super();
		this.controller = controller;
	}
 
	/**
	 * this function is to create the connection between server and client, output 
	 * is the information in the stream sent to the other user through network, and
	 * input is the information the other user accepts.
	 */
	public void createConnection() {
		
		//checkpoint to see if CREAT, PLAY_AS, SERVER, PORT work
		try {
			if (CREATE == 1) { //creates server
				ServerSocket serverSocket = new ServerSocket(Integer.parseInt(PORT));
				connection = serverSocket.accept();
			}else { //creates client
				connection =  new Socket(SERVER, Integer.parseInt(PORT));
			}
			output = new ObjectOutputStream(connection.getOutputStream());
			input = new ObjectInputStream(connection.getInputStream());
			//when its ai's turn and the ai play option is chosen
			if(Connect4.MY_TURN && Connect4Connection.PLAY_AS == 2) {
				controller.computerTurn();
			}
			//Reads all contents of input
			while (!close) {
				Connect4MoveMessage message = (Connect4MoveMessage) input.readObject();
				controller.handleMessage(message);
			}
		} catch (SocketException e) {
			System.out.println("serverSocket closed");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * the ouput function take the message and send it to the other party
	 * @param message has all the information of the user input, for example, user click
	 * which column gives a move, the color and the coordinate of the circle is in the 
	 * message, then the message is sent to the other user(ai) by writeObject method
	 * @throws IOException
	 */
	public void output(Connect4MoveMessage message) throws IOException {
		output.writeObject(message);
	}
	
	/**
	 * the close function is to close all the connection, including input, output, 
	 * and socketserver.
	 */
	public void close() {
		try {
			close = true;
			if (input!=null) { //closes input
				input.close();
			}
			if (output!=null) { //closes output
				output.close();
			}
			if (connection!=null) { //closes connection
				connection.shutdownOutput();
				connection.shutdownOutput();
				connection.close();
			}
			if (serverSocket!=null) { //closes server socket
				serverSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}