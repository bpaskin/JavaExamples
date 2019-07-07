package com.ibm.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

public class Server {

	private static ServerSocket server;
	
	public static void main(String[] args) {
		
		var sinistar = "HTTP/1.1 200 OK\r\n\r\nI Am Sinistar!";
		var alive = "Beware, I Live!";
		var port = 9080;				
		var publisher = new PublisherStream();
		var subscriber = new SubscriberCount();
		var subscriber2 = new SubscriberSaying();

		
		// Set hook when SIGINT is detected to send ending message
		Runtime.getRuntime().addShutdownHook(new GoodBye());

		// add the subscriber to the publisher
		publisher.subscribe(subscriber);
		publisher.subscribe(subscriber2);

		try {
			
			// open the socket for port
			server = new ServerSocket(port);
			System.out.println(alive);

			// always listen for new messages unless a SIG is sent
			while (true) {
				
				// start accepting messages
				var client = server.accept();
				
				// read the incoming messages
				var in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				
				// loop through the lines sent and place them in messages
				var inputLine = in.readLine();
				var messages = "";
				
				while (!inputLine.isEmpty()) {
					messages += inputLine + "\n";
					inputLine = in.readLine();
				}
				
				// send a reply message to the client
				client.getOutputStream().write(sinistar.getBytes("UTF-8"));
				
				// close the input and connection
				in.close();
				client.close();
				
				// send the messages to the publisher for further processing
				publisher.publishAgent(messages);
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				// try to cleanup nicely by closing the connection
				server.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static class GoodBye extends Thread {
		public void run() {		
			// send goodbye message
			System.out.println("Run Coward!");
		}	
	}

}
