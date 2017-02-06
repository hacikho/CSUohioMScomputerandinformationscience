
/* CIS 675 Assignment#2 
 * Part 1
 * Server Side
 * Haci Karahasanoglu
 */
import java.io.IOException;
import java.io.DataInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.ServerSocket;

public class Server {
	// Server soket
	public static ServerSocket serverSocket;
	// Client soket
	public static Socket clientSocket;
	// Client Threads
	public static ClientThread[] clientThreads = new ClientThread[4];

	public static void main(String args[]) {
		// default port 2500
		try {
			serverSocket = new ServerSocket(2500);
		} catch (IOException e) {
			System.out.println(e);
		}

		// Creating sockets and threads foe each client, maximum client number
		// is 4
		do {
			try {
				clientSocket = serverSocket.accept();
				for (int i = 0; i < 4; i++) {
					if (clientThreads[i] == null) {
						(clientThreads[i] = new ClientThread(clientSocket, clientThreads)).start();
						break;
					}
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		} while (true);
	}
}

// Matching clients for each thread
class ClientThread extends Thread {

	public DataInputStream inputStream;
	public PrintStream outputStream;
	public Socket clientSocket;
	public ClientThread[] threads;
	public int maxClientNumber;

	public ClientThread(Socket clientSocket, ClientThread[] threads) {
		this.clientSocket = clientSocket;
		this.threads = threads;
		maxClientNumber = threads.length;
	}

	public void run() {
		int maxClientNumber = this.maxClientNumber;
		ClientThread[] threads = this.threads;
		// Creating input and output stream for the client and ask username at
		// first login
		try {
			inputStream = new DataInputStream(clientSocket.getInputStream());
			outputStream = new PrintStream(clientSocket.getOutputStream());
			outputStream.println("Enter username: ");
			String username = inputStream.readLine();
			// Echo client message to all other clients
			do {
				String text = inputStream.readLine();
				for (int i = 0; i < maxClientNumber; i++) {
					if (threads[i] != null) {
						threads[i].outputStream.println("<<" + username.toUpperCase() + " says >>: " + text);
					}
				}
			} while (true);
		} catch (IOException e) {
		}
	}
}
