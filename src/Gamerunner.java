 import java.awt.*;
import javax.swing.*;
import java.io.IOException;

import socket.Client;
import socket.Server;

public class Gamerunner {

	public static Start start;
	public static Server server;
	public static Client client;

	public static void main(String[] args) throws IOException {
		JFrame logInFrame = new JFrame("Mini Game");
		logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logInFrame.setLayout(new BorderLayout());
		
		start = new Start();
		logInFrame.getContentPane().add(start, BorderLayout.NORTH);
		ImageIcon icon = new ImageIcon("image/gameMain.png");
        Image img = icon.getImage();

        JPanel imagePanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        
        logInFrame.getContentPane().add(new Start(),BorderLayout.NORTH);
        logInFrame.getContentPane().add(imagePanel, BorderLayout.CENTER);
        logInFrame.setSize(500,500);
		logInFrame.setVisible(true);	

		// start button을 누를 때까지 spin
		while (start.getIp() == null) {;}
		
		// 1P면 서버도 열어야 함
		if (start.getSer()) {
			Server server = new Server();
			Thread serverThread = new Thread (server);
			serverThread.start();
		}

		Client client = new Client(start.getIp());
		Thread clientThread = new Thread (client);
		clientThread.start();

		// login창 숨기고 게임 창 띄우기
		logInFrame.dispose();
		mainGui maingui = new mainGui(client);
	}
}
