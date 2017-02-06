

/*CIS 675 Assignment#2 
 * Part 1
 *Server Side
 *Haci Karahasanoglu
 */
import java.io.IOException;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.net.Socket;
import java.io.InputStreamReader;

public class Client implements Runnable {

	public static Socket clientSocket;
	public static PrintStream outputStream;
	public static DataInputStream inputStream;
	public static BufferedReader input;
	

	public static void main(String[] args) {
		// Create a client with default host and port number
		boolean isClosed = false;
		try {
			clientSocket = new Socket("localhost", 2500);
			input = new BufferedReader(new InputStreamReader(System.in));
			outputStream = new PrintStream(clientSocket.getOutputStream());
			inputStream = new DataInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
            System.out.println(e);
		}

		//Reading from server
		try {
			new Thread(new Client()).start();
			while (!isClosed) {
				outputStream.println(input.readLine());
			}
			// Closing connections
			outputStream.close();
			inputStream.close();
			clientSocket.close();
		} catch (IOException e) {
            System.out.println(e);
		}
	}

	// Thread read from the server side 
	public void run() {
		String reply;
		try {
			while ((reply = inputStream.readLine()) != null) {
				if (reply.trim().equals("")) {
		            // empty line
		        } else {
		            System.out.println(reply);
		        }
			}
			boolean isClosed = true;
		} catch (IOException e) {
            System.out.println(e);
		}
	}
}
