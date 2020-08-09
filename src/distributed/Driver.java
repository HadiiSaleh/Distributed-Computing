package distributed;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) throws SocketException, FileNotFoundException{
		// Chapter 1
//		try {
//			InetAddress address = InetAddress.getByName("www.ul.edu.lb");
//			System.out.println("IP address: "+address);
//			InetAddress domain = InetAddress.getByName("8.8.8.8");
//			System.out.println("domain name: "+domain.getHostName());
//			InetAddress[] addresses = InetAddress.getAllByName("www.google.com");
//			for (int i = 0; i < addresses.length; i++) {
//				System.out.println("address "+i+" : "+addresses[i]);
//			}
//		}
//		catch(UnknownHostException ex){
//			System.out.println(ex);
//		}

//		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//		while (interfaces.hasMoreElements()) {
//			
//			NetworkInterface ni = interfaces.nextElement();
//			System.out.println();
//			
//		}
		//Chapter 2
		Scanner input = new Scanner(new File("brain1.txt"));
		System.out.println(input.nextLine());
		
		PrintWriter output = new PrintWriter("brain1.txt");
		output.println("No animal to animal");
		output.close();
				
	}

}
