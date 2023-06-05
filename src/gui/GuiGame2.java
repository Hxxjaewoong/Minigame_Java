package gui;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import games.Game2;
import socket.Client;

public class GuiGame2 extends Game2 implements GamePanel {

	Client client;

	public boolean playing;

	public JPanel panel;
	public JPanel panelInfo;
    public JPanel panelPlay;
    public JButton buttons[][] = new JButton[SIZE][SIZE];
	public JTextArea infoText;
	
	public GuiGame2(Client client) {
		this.client = client;

		panel = new JPanel();
		panel.setBounds(gameArea);

		panelInfo = new JPanel();
		panelInfo.setPreferredSize(gameInfoArea);
		panelInfo.setBackground(Color.yellow);

		infoText = new JTextArea("상대방 대기 중...");
		infoText.setFont(infoFont);
		panelInfo.add(infoText);

        panelPlay = new JPanel();
        panelPlay.setPreferredSize(gamePlayArea);
        panelPlay.setBackground(Color.gray);
        panelPlay.setLayout(new GridLayout(SIZE, SIZE, 5, 5));
        
        
        for (int r = 0; r < SIZE; r++) {
        	for (int c = 0; c < SIZE; c++) {
				JButton button = new JButton();
        		
        		// 버튼 디자인 및 위치
        		button.setBackground(Color.pink);
        		button.setBorder(BorderFactory.createLineBorder(Color.red, 2));
				button.setFont(game2ButtonFont);
        		
                // event on button
                button.setActionCommand(r+" "+c);
                button.addActionListener(new ButtonClickListener());
        		
        		buttons[r][c] = button;
        		panelPlay.add(button);
        	}
        }

		panel.repaint();

		panel.add(panelInfo);
		panel.add(panelPlay);
	}
	
	
	// 게임을 시작할 때 버튼 디자인 초기화
	public void initButtons(String[] parsed) {
		for (int r = 0; r < SIZE; r++) {
			for (int c = 0; c < SIZE; c++) {
				buttons[r][c].setBackground(Color.pink);
				buttons[r][c].setText(parsed[r*SIZE + c + 2]);
			}
		}
	}

	
		
	// 결과 메시지를 파싱해서 결과 처리
	public void parseReceivedMessage(String message) {
		String[] parsed = message.split(" ");

		// "start [gameNumber] [numbers 1 ~ 25]"
		// 게임 시작. 서버에게서 받은 숫자로 타일 초기화
		if (parsed[0].equals("start")) {
			initButtons(parsed);
			infoText.setText("Game start! Target: 1");

			playing = true;
		}



		int user = Integer.parseInt(parsed[1]);
		int r = Integer.parseInt(parsed[2]);
		int c = Integer.parseInt(parsed[3]);

		// "finish [user] [r] [c]"
		// [user]가 먼저 끝남
		if (parsed[0].equals("finish")) {
			if (user == client.userNumber) {
				buttons[r][c].setText("");
				infoText.setText("win!");
			} else {
				infoText.setText("lose...");
			}

			playing = false;
			return;
		}

		// "target [user] [r] [c] [nextTarget] [nextLayer]"
		// 타깃 클릭함.
		if (parsed[0].equals("target")) {
			if (Integer.parseInt(parsed[1]) != client.userNumber) return;

			int nextTarget = Integer.parseInt(parsed[4]);
			int nextLayer = Integer.parseInt(parsed[5]);

			if (nextTarget-1 > SIZE*SIZE) {
				buttons[r][c].setText("");
				buttons[r][c].setBackground(Color.black);
			}
			else {
				buttons[r][c].setText(String.valueOf(nextLayer));
			}

			infoText.setText("Target: " + String.valueOf(nextTarget));

			return;
		}

		// "notTarget [user] [r] [c]"
		// do nothing
		if (parsed[0].equals("notTarget")) {
			return;
		}



	}
	

	
	
    private class ButtonClickListener implements ActionListener {
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			// 게임 종료됨. 클릭 불가
			if (!playing) {
				return;
			}

			// 클릭한 버튼의 좌표 획득
			String rc[] = e.getActionCommand().split(" ");
			int r = Integer.parseInt(rc[0]);
			int c = Integer.parseInt(rc[1]);
			
			// 서버에게 행동 메시지 전송
			client.sendMessage("click " + client.userNumber + " " + r + " " + c ); // "click 0 1 2"

		}
    }
}
