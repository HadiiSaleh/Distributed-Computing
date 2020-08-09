package TCPChatRoom;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class NetworkToUser extends Thread {
	private Socket s;
	private Scanner in;

	public NetworkToUser(Socket s) {
		this.s = s;
		try {
			in = new Scanner(s.getInputStream());
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void run() {

		while (true) {
			String msg = in.nextLine();
			System.out.println(msg);
		}

	}

}
