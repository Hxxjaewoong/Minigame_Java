import java.awt.*;
import javax.swing.*;
import java.io.IOException;

import socket.Client;
import socket.Server;

public class Gamerunner extends Thread{

	public static Start start;
	static Server server;
	static Client client;

	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame("Mini Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		start = new Start();
		frame.getContentPane().add(start, BorderLayout.NORTH);
		ImageIcon icon = new ImageIcon("game.jpg"); //아직 이미지 추가하지 않았음
        Image img = icon.getImage();

        JPanel imagePanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        
        frame.getContentPane().add(new Start(),BorderLayout.NORTH);
        frame.getContentPane().add(imagePanel, BorderLayout.CENTER);
        frame.setSize(500,500);
		frame.setVisible(true);	


		while (start.getIp() == null) {;}
		

		if (start.getSer()) {
			server = new Server();
			Thread s = new Thread(server);
			s.start();
		}
		
		client = new Client(start.getIp());
		Thread c = new Thread(client);
		c.start();
		mainGui maingui = new mainGui(client);
	}
}
