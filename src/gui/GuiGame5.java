package gui;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import games.Game5;
import socket.Client;

public class GuiGame5 extends Game5 implements GamePanel {

	Client client;

	public JPanel panel;
	public JPanel panelInfo;
    public JPanel panelPlay;
    public JButton buttons[][] = new JButton[SIZE][SIZE];
	public JTextArea infoText;
	
	public GuiGame5(Client client) {
		this.client = client;

		panel = new JPanel();
		panel.setBounds(gameArea);

		panelInfo = new JPanel();
		panelInfo.setPreferredSize(gameInfoArea);
		panelInfo.setBackground(Color.yellow);

		infoText = new JTextArea("Contains info of Game5");
		panelInfo.add(infoText);

        panelPlay = new JPanel();
        panelPlay.setPreferredSize(gamePlayArea);
        panelPlay.setBackground(Color.gray);
        panelPlay.setLayout(new GridLayout(SIZE, SIZE, 5, 5));
        
        
        for (int r = 0; r < SIZE; r++) {
        	for (int c = 0; c < SIZE; c++) {

        		JButton button = new JButton(String.valueOf(layer1[r][c]));
        		
        		// design and position of button
        		button.setBackground(Color.pink);
        		button.setBorder(BorderFactory.createLineBorder(Color.red, 2));
        		
        		
                // event on button
                button.setActionCommand(r+","+c);
                button.addActionListener(new ButtonClickListener());
        		
        		
        		buttons[r][c] = button;
        		panelPlay.add(button);
        	}
        }

		panel.add(panelInfo);
		panel.add(panelPlay);
	}
	
	
	
	
    private class ButtonClickListener implements ActionListener {
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			// get the position of clicked button
			String rc[] = e.getActionCommand().split(",");
			int r = Integer.parseInt(rc[0]);
			int c = Integer.parseInt(rc[1]);
			
			// update the click button
			if (isTargetNumber(r, c)) {
				System.out.println("Clicked " + (targetNumber-1));
				if (opened2[r][c] == false) {
					buttons[r][c].setText(String.valueOf(layer2[r][c]));
				} else {
					buttons[r][c].setText("");
					buttons[r][c].setBackground(Color.black);
				}

			} else {
				; // do nothing (or 벌칙)
			}			

		}
    }
}
