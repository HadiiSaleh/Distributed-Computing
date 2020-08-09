package TCPChatRoom;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private static Socket link;
	private static Scanner in;
	private static Scanner keyboard;
	private static PrintWriter out;
	private static String HOST = "localhost";
	private static int PORT = 1234;

	public static void main(String[] args) {

		try {
			link = new Socket(HOST, PORT);
			in = new Scanner(link.getInputStream());
			keyboard = new Scanner(System.in);
			out = new PrintWriter(link.getOutputStream(), true);

		} catch (UnknownHostException e) {
			System.out.println("problem IP");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("server connection problem");
			System.exit(1);

		}

		System.out.println("Enter your name :");
		String nom = keyboard.nextLine();

		String command = "SignUp:" + nom;
		out.println(command);

		String reponse = in.nextLine();

		System.out.println(reponse);
		if (!reponse.equals("SignUp OK")) {
			System.out.println(reponse);
			System.exit(1);
		}

		NetworkToUser ntu = new NetworkToUser(link);
		ntu.start();

		do {
			System.out.println(
					"Enter \nMessage to send a message for a specific user \nBroadcast to send a message for all clients \nEND close the connection");
			String msg = keyboard.nextLine();
			if (msg.equals("Broadcast")) {
				System.out.println("Enter the message content");
				String message_a_diffuse = keyboard.nextLine();
				command = msg + ":" + message_a_diffuse;
				out.println(command);

			} else if (msg.equals("Message")) {
				System.out.println("Enter the destination name");
				String destination = keyboard.nextLine();
				System.out.println("Enter the message content");
				String message_a_envoye = keyboard.nextLine();
				command = msg + ":" + destination + ":" + message_a_envoye;
				out.println(command);

			} else if (msg.equals("END")) {
				ntu.stop();
				try {
					link.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			} else {
				System.out.println("Incorrect Command");
			}

		} while (true);

	}

}
