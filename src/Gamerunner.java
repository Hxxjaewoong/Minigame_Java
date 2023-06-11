 import java.awt.*;
import javax.swing.*;
import java.io.IOException;

import socket.Client;
import socket.Server;

public class Gamerunner {

	public static LogIn logIn;
	public static Server server;
	public static Client client;

	public static void main(String[] args) throws IOException {
		JFrame logInFrame = new JFrame("Mini Game");
		logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logInFrame.setLayout(new BorderLayout());
		
		logIn = new LogIn();
		logInFrame.getContentPane().add(logIn, BorderLayout.NORTH);
		ImageIcon icon = new ImageIcon("image/gameMain.png");
        Image img = icon.getImage();

        JPanel imagePanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        
        logInFrame.getContentPane().add(new LogIn(),BorderLayout.NORTH);
        logInFrame.getContentPane().add(imagePanel, BorderLayout.CENTER);
        logInFrame.setSize(500,500);
		logInFrame.setVisible(true);	

		// start button을 누를 때까지 spin
		while (logIn.getIp() == null) {;}
		
		// 1P면 서버도 열어야 함
		if (logIn.getSer()) {
			Server server = new Server();
			Thread serverThread = new Thread (server);
			serverThread.start();
		}

		Client client = new Client(logIn.getIp());
		Thread clientThread = new Thread (client);
		clientThread.start();

		// login창 숨기고 게임 창 띄우기
		logInFrame.dispose();
		MainGui maingui = new MainGui(client);
	}
}
