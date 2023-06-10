/*
 * 
 * 두 유저가 동시에 시작하도록 해야 함
 * 동시에 시작 못하는 이유: 타일 버튼을 생성하는데 시간 차이 존재
 * solution: 타일 생성 후 server에게 ready 신호 보냄
 * 			 두 클라이언트가 모두 ready 신호 보내면 서버는 다시 클라이언트에게 ready 보냄
 * 			 클라이언트가 ready 받으면 카운트 시작
 * 
 */


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
    public static JButton buttons[][] = new JButton[SIZE][SIZE];
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

	// 3초 기다린 후에 시작
	void wait3Sec() {
		try {
			infoText.setText("The game is about to start");
			Thread.sleep(1000);
			infoText.setText("3");
			Thread.sleep(1000);
			infoText.setText("2");
			Thread.sleep(1000);
			infoText.setText("1");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
		
	// 결과 메시지를 파싱해서 결과 처리
	public void parseReceivedMessage(String message) {
		String[] parsedMessage = message.split(" ");

		// "start [gameNumber] [numbers 1 ~ 25]"
		// 게임 시작. 서버에게서 받은 숫자로 타일 초기화
		if (parsedMessage[0].equals("start")) {
			initButtons(parsedMessage);

			wait3Sec();
			playing = true;
			infoText.setText("Game start! Target: 1");
		}


		int user = Integer.parseInt(parsedMessage[1]);
		int r = Integer.parseInt(parsedMessage[2]);
		int c = Integer.parseInt(parsedMessage[3]);

		// "finish [user] [r] [c]"
		// [user]가 먼저 끝남
		if (parsedMessage[0].equals("finish")) {
			if (user == client.userNumber) {
				buttons[r][c].setText("");
				buttons[r][c].setBackground(Color.black);
				infoText.setText("win!");
			} else {
				infoText.setText("lose...");
			}

			playing = false;
			return;
		}

		// "target [user] [r] [c] [nextTarget] [nextLayer]"
		// 타깃 클릭함.
		if (parsedMessage[0].equals("target")) {
			if (Integer.parseInt(parsedMessage[1]) != client.userNumber) return;

			int nextTarget = Integer.parseInt(parsedMessage[4]);
			int nextLayer = Integer.parseInt(parsedMessage[5]);

			if (nextTarget-1 > SIZE*SIZE) {
				buttons[r][c].setText("");
				buttons[r][c].setBackground(Color.black);
			} else {
				buttons[r][c].setText(String.valueOf(nextLayer));
			}

			infoText.setText("Target: " + String.valueOf(nextTarget));

			return;
		}

		// "notTarget [user] [r] [c]"
		// do nothing
		if (parsedMessage[0].equals("notTarget")) {
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
