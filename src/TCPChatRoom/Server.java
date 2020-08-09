package TCPChatRoom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {

	// welcome socket of Class ServerSocket

	private static ServerSocket welcomeSocket;

	// Port number which is used to listen to clients connection requests

	private static int PORT = 1234;

	// Vector of ClientHandlers to save clients connections

	private static Vector<ClientHandler> clients;

	public static void main(String[] args) {

		// create an object of welcomeSocket then initialize the vector of
		// ClientHandlers

		try {
			welcomeSocket = new ServerSocket(PORT);

			System.out.println("Server listning on port " + PORT);

			clients = new Vector<ClientHandler>();

		} catch (IOException e) {
			System.out.println("Port " + PORT + " is busy");
			System.exit(1);
		}

		Socket link;

		while (true) {

			try {

				// listen for new connection requests

				link = welcomeSocket.accept();

				System.out.println("New Socket created " + link.toString());

				// If a new socket is created the create new thread to handle it then save it to
				// the list of threads

				ClientHandler cl = new ClientHandler(link, clients);

				clients.add(cl);

				// Start the thread

				cl.start();

			} catch (IOException e) {
				System.out.println("Failed to accept new socket");
			}

		}
	}
}
