package gui;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import games.Game3;
import socket.Client;

public class GuiGame3 extends Game3 implements GamePanel {

	Client client;

	public JPanel panel;
	public JPanel panelInfo;
    public JPanel panelPlay;
	public JTextArea infoText;

	// image icons
	ImageIcon bellImageIcon;
	ImageIcon myCardOpenImageIcon;
	ImageIcon opponentCardOpenImageIcon;
	ImageIcon[][] fruitsImagesIcons;



	JButton bellButton;
	JButton myCardOpenButton;
	JButton opponentCardOpenButton;

	JLabel myCardLabel;
	JLabel opponentCardLabel;
	
	public GuiGame3(Client client) {
		this.client = client;

		panel = new JPanel();
		panel.setBounds(gameArea);

		panelInfo = new JPanel();
		panelInfo.setPreferredSize(gameInfoArea);
		panelInfo.setBackground(Color.yellow);

		infoText = new JTextArea("Contains info of game3");
		panelInfo.add(infoText);

        panelPlay = new JPanel();
        panelPlay.setPreferredSize(gamePlayArea);
        panelPlay.setBackground(Color.gray);

        
		// initialize image icons
		bellImageIcon = getImageIcon("image/bell.png", 80, 80);
		myCardOpenImageIcon = getImageIcon("image/back_side.png", 48, 76);
		opponentCardOpenImageIcon = getImageIcon("image/back_side.png", 48, 76);
		initFruitsImages();

        
		// bell button
		bellButton = new JButton(bellImageIcon);
		bellButton.setBorderPainted(false);
        bellButton.setContentAreaFilled(false);
        bellButton.setFocusPainted(false);
		bellButton.setBounds((int)(gamePlayArea.getWidth())/2 - 40, (int)(gamePlayArea.getHeight())/2 - 40, 80, 80);

		bellButton.setActionCommand("bell");
		bellButton.addActionListener(new ButtonClickListener());


		// card open button
		myCardOpenButton = new JButton(myCardOpenImageIcon);
		myCardOpenButton.setBorderPainted(false);
        myCardOpenButton.setContentAreaFilled(false);
        myCardOpenButton.setFocusPainted(false);
		myCardOpenButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		myCardOpenButton.setBounds((int)(gamePlayArea.getWidth())/2 - 26, (int)(gamePlayArea.getHeight()) - 88, 52, 80);

		myCardOpenButton.setActionCommand("my card open");
		myCardOpenButton.addActionListener(new ButtonClickListener());

		opponentCardOpenButton = new JButton(opponentCardOpenImageIcon);
		opponentCardOpenButton.setBorderPainted(false);
        opponentCardOpenButton.setContentAreaFilled(false);
        opponentCardOpenButton.setFocusPainted(false);
		opponentCardOpenButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		opponentCardOpenButton.setBounds((int)(gamePlayArea.getWidth())/2 - 26, 12, 52, 80);

		opponentCardOpenButton.setActionCommand("opponent card open");
		opponentCardOpenButton.addActionListener(new ButtonClickListener());
		

		// area for showing cards on board
		myCardLabel = new JLabel();
		myCardLabel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		myCardLabel.setBounds((int)(gamePlayArea.getWidth())/2 - 26, (int)(gamePlayArea.getHeight()) - 176, 52, 80);
		opponentCardLabel = new JLabel();
		opponentCardLabel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		opponentCardLabel.setBounds((int)(gamePlayArea.getWidth())/2 - 26, 100, 52, 80);



		panelPlay.setLayout(null);
		panelPlay.add(bellButton);
		panelPlay.add(myCardOpenButton);
		panelPlay.add(opponentCardOpenButton);
		panelPlay.add(myCardLabel);
		panelPlay.add(opponentCardLabel);
     

		panel.add(panelInfo);
		panel.add(panelPlay);
	}
	

	// return the resized image icon
	ImageIcon getImageIcon(String path, int width, int height) {
		Image image = (new ImageIcon(path)).getImage();
		ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
		return imageIcon;
	}
	

	void initFruitsImages() {
		String path;
		fruitsImagesIcons = new ImageIcon[FRUITS][6];
		for (int fruits = 0; fruits < FRUITS; fruits++) {
			for (int num = 1; num <= 5; num++) {
				path = "image/" + FRUITS_NAME[fruits] + num + ".png";
				fruitsImagesIcons[fruits][num] = getImageIcon(path, 48, 76);
			}
		}
	}



	
    private class ButtonClickListener implements ActionListener {
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			// get the position of clicked button
			String command = e.getActionCommand();
			
			// update the click buttona
			if (command.equals("bell")) {
				boolean result = isSum5();
				if (result == true) {
					clearBoard();
					myCardLabel.setIcon(null);
					opponentCardLabel.setIcon(null);
				} else {
					; // 벌칙?
				}

				

			} else if (command.equals("my card open")) {
				boolean result = openCard();
				if (result == false) {
					// 자기차례 아님
					return;
				}

				int myTopFruits = myTopCard[0];
				int myTopNum = myTopCard[1];
				int opponentTopFruits = opponentTopCard[0];
				int opponentTopNum = opponentTopCard[1];

				myCardLabel.setIcon(fruitsImagesIcons[myTopFruits][myTopNum]);
				opponentCardLabel.setIcon(fruitsImagesIcons[opponentTopFruits][opponentTopNum]);
			}			

		}
    }
}
