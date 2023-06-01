import java.io.IOException;

import socket.Client;

public class Gamerunner {

	public static void main(String[] args) throws IOException {
		Client client = new Client();
		mainGui maingui = new mainGui(client);
	}

}
