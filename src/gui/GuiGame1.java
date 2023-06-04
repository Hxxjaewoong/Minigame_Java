package gui;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import games.Game1;
import socket.Client;

public class GuiGame1 extends Game1 implements GamePanel {

	Client client;
	public boolean myTurn;
	

	public JPanel panel;
	public JPanel panelInfo;
    public JPanel panelPlay;
    public JButton buttons[][] = new JButton[MAP_SIZE][MAP_SIZE];
	public JTextArea infoText;
	
	public GuiGame1(Client client) {
		this.client = client;

		panel = new JPanel();
		panel.setBounds(gameArea);

		panelInfo = new JPanel();
		panelInfo.setPreferredSize(gameInfoArea);
		panelInfo.setBackground(Color.yellow);

		infoText = new JTextArea("Contains info of game1");
		panelInfo.add(infoText);


        panelPlay = new JPanel();
        panelPlay.setPreferredSize(gamePlayArea);
        panelPlay.setBackground(Color.gray);
        panelPlay.setLayout(new GridLayout(MAP_SIZE, MAP_SIZE, 5, 5));
        
		for (int r = 0; r < MAP_SIZE; r++) {
        	for (int c = 0; c < MAP_SIZE; c++) {
        		JButton button = new JButton("???");
        		
        		// 버튼 디자인 및 위치
        		button.setBackground(Color.pink);
        		button.setBorder(BorderFactory.createLineBorder(Color.red, 2));
        
                // 버튼 클릭 이벤트
                button.setActionCommand(r+","+c);
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
	public void initButtons() {
		for (int r = 0; r < MAP_SIZE; r++) {
        	for (int c = 0; c < MAP_SIZE; c++) {
        		buttons[r][c].setBackground(Color.pink);
				buttons[r][c].setText("???");
        	}
        }
	}

	
	// 결과 메시지를 파싱해서 결과 처리
	public void parseReceivedMessage(String message) {
		String[] parsedMessage = message.split(" ");
		System.out.println(message);

		// "init [r0] [c0] [r1] [c1] ...  [r4] [c4]"
		// 보물 초기화. 행과 열에 따라 보물 할당
		// 이건 필요 없네. game2에는 필요함

		if (parsedMessage[0].equals("start")) {
			initTarget();
			initButtons();
			

			int firstUserNumber = Integer.parseInt(parsedMessage[2]);
			myTurn = (firstUserNumber == client.userNumber);
			return;
		}



		// "alreadyOpen"
		// 이미 열린 버튼. 아무 행동 안 함
		if (parsedMessage[0].equals("alreadyOpen")) {
			System.out.println("이미 열린 버튼입니다.");
			return;
		}
		
		int r = Integer.parseInt(parsedMessage[1]);
		int c = Integer.parseInt(parsedMessage[2]);

		// "winner [user]"
		// 보물 찾음. 버튼 오픈. 게임 종료
		if (parsedMessage[0].equals("winner")) {
			System.out.println("Target Found! " + foundCount);
			buttons[r][c].setText("축");
			buttons[r][c].setBackground(Color.gray);
			if (myTurn) {
				System.out.println("I'm a winner!");
			} else {
				System.out.println("I'm a loser...");
			}
			return;
		}

		// "target [r] [c]"
		// 보물 찾음. 버튼 오픈
		if (parsedMessage[0].equals("target")) {
			int foundCount = Integer.parseInt(parsedMessage[3]);
			System.out.println(foundCount + " target found!");
			buttons[r][c].setText("축");
			buttons[r][c].setBackground(Color.gray);
			myTurn = !myTurn; // 턴 변경
			return;
		}

		// "empty [r] [c] [distance]"
		// 빈칸. 버튼 오픈. 가까운 보물 거리 알려줌.
		if (parsedMessage[0].equals("empty")) {
			int distance = Integer.parseInt(parsedMessage[3]);
			System.out.println(r+"행 "+c+"열 - " + "가장 가까운 거리: " + distance);
			buttons[r][c].setText("꽝");
			buttons[r][c].setBackground(Color.white);
			myTurn = !myTurn; // 턴 변경
			return;
		}


	}




    private class ButtonClickListener implements ActionListener {
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			// 자신의 턴이 아님 -> 클릭 불가
			if (!myTurn) {
				System.out.println("It's not your turn.");
				return;
			}

			// 클릭한 버튼의 좌표 획득
			String rc[] = e.getActionCommand().split(",");
			int r = Integer.parseInt(rc[0]);
			int c = Integer.parseInt(rc[1]);
			
			// 서버에게 행동 메시지 전송
			client.sendMessage("click " + client.userNumber + " " + r + " " + c ); // "click 0 1 2"

		}

    }
}
