package TCPChatRoom;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class ClientHandler extends Thread {

	private Socket link;
	private Scanner in;
	private PrintWriter out;
	private Vector<ClientHandler> clients;
	private String Name;

	public ClientHandler(Socket link, Vector<ClientHandler> clients) {

		// get the socket related with this thread

		this.link = link;

		// get the list of connections of the server with other clients

		this.clients = clients;

		// open input and output streams used to communicate with the client

		try {
			in = new Scanner(link.getInputStream());

			out = new PrintWriter(link.getOutputStream(), true);

			System.err.println("input and output streams opened successfully");

		} catch (IOException e) {

			System.out.println("Error while opening input and output streams");
		}

	}

	// Here all the logic behind the protocol will be implemented

	public void run() {

		// read the first message

		String message = in.nextLine();

		// use it to split the string to get the message
		// SignUp:"name"
		StringTokenizer st = new StringTokenizer(message, ":");

		try {
			// the first message should be in the format SignUp:"name"
			// if the first message don't match the format, close the connection

			if (st.countTokens() != 2 || !st.nextToken().equals("SignUp")) {
				out.println("SignUp failed");
				throw new SignUpFailed();
			}

			// SignUp succeeded

			this.Name = st.nextToken();

			System.out.println("sign up ok: " + this.Name);

			out.println("SignUp OK");

			// read the next message

			message = in.nextLine();

			while (!message.equals("END")) {

				st = new StringTokenizer(message, ":");

				String mess = st.nextToken();

				String content;

				try {
					// Broadcast:"message"

					if (st.countTokens() == 1 && mess.equals("Broadcast")) {
						content = st.nextToken();

						broadCast(content);
					}

					// Message:"Destination name": "message content"

					else if (st.countTokens() == 2 && mess.equals("Message")) {
						String name = st.nextToken();
						content = st.nextToken();

						findHandlerByName(name).sendMessage(content, this.getMyName());
					}

					// Incorrect message form

					else {

						throw new BadMessage();
					}
				} catch (BadMessage ex) {

					System.out.println("Incorrect Command");

					out.println("Incorrect Command");
				}

				message = in.nextLine();
			}

			// END of connection

			clients.remove(this);
			closeConnection();

		} catch (SignUpFailed ex) {

			this.clients.remove(this);

			closeConnection();
		}

	}

	// close the connection of this client with the server by closing the socket

	public void closeConnection() {
		try {
			this.link.close();
			System.out.println("End of connection :)");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void broadCast(String content) {

		for (int i = 0; i < this.clients.size(); i++) {

			if (!this.clients.elementAt(i).equals(this))
				this.clients.elementAt(i).sendMessage(content, this.getMyName());
		}
	}

	public void sendMessage(String content, String name) {
		content = "From: " + name + " ,To: " + this.getMyName() + " ,Message: " + content;

		out.println(content);
	}

	public String getMyName() {
		return this.Name;
	}

	public ClientHandler findHandlerByName(String name) {
		for (int j = 0; j < this.clients.size(); j++) {
			if (this.clients.elementAt(j).getMyName().equals(name))
				return this.clients.elementAt(j);
		}

		return null;

	}
}
