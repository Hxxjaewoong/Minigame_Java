package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.Random;

import socket.Client;
import games.Game4;

public class GuiGame4 extends Game4 implements GamePanel{

	Client client;

	boolean playing;

	public JPanel panel;
	//public JPanel panelInfo;
    public JPanel panelPlay;
	public JTextArea infoText;
	public JLabel scoreLabel;

    public int count = 3; //시작 전 3초 세고 시작함
    public int countGame = 1;
    
	JPanel keyboardArea;
	JButton upButton;
	JButton downButton;
	JButton leftButton;
	JButton rightButton;

	JLabel mentLabel;

	public String keyPressed;
    
	public GuiGame4(Client clinet) {
		this.client = clinet;

		panel = new JPanel();
		panel.setBounds(gameArea);

		scoreLabel = new JLabel("상대방 대기 중...");
        scoreLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
        
        // 폰트 크기 조정
        scoreLabel.setFont(scoreLabel.getFont().deriveFont(20f));

        // 크기 조정
        Dimension scoreLabelSize = new Dimension(200, 50);
        scoreLabel.setPreferredSize(scoreLabelSize);

        panelPlay = new JPanel();
        panelPlay.setPreferredSize(new Dimension(460, 430));
        panelPlay.setBackground(new Color(238, 238, 238));
        panelPlay.setLayout(new GridLayout(2,1,	 10, 10));
        
		//곧시작 멘트 이미지 띄우기
        ImageIcon mentImage = new ImageIcon("image/game4Start.png");
        Image image = mentImage.getImage().getScaledInstance(460, 230, Image.SCALE_SMOOTH);
        mentImage = new ImageIcon(image);
        mentLabel = new JLabel(mentImage);
        
        keyboardArea = new JPanel();
        keyboardArea.setBackground(new Color(238, 238, 238));
        keyboardArea.setLayout(new GridLayout(2, 3, 10, 10));


		// buttons
        upButton = new JButton("▴");
        downButton = new JButton("▾");
    	leftButton = new JButton("◂");
        rightButton = new JButton("▸");
		
        upButton.setBackground(new Color(228, 241, 195));
        downButton.setBackground(new Color(228, 241, 195));
        leftButton.setBackground(new Color(228, 241, 195));
        rightButton.setBackground(new Color(228, 241, 195));	

		upButton.setActionCommand("up");
		downButton.setActionCommand("down");
		leftButton.setActionCommand("left");
		rightButton.setActionCommand("right");
        
        upButton.setFont(upButton.getFont().deriveFont(40f));
        downButton.setFont(downButton.getFont().deriveFont(40f));
        leftButton.setFont(leftButton.getFont().deriveFont(40f));
        rightButton.setFont(rightButton.getFont().deriveFont(40f));
        
		upButton.addActionListener(new ButtonClickListener());
		downButton.addActionListener(new ButtonClickListener());
		leftButton.addActionListener(new ButtonClickListener());
		rightButton.addActionListener(new ButtonClickListener());


        // 키보드 버튼을 패널에 추가
        keyboardArea.add(new JPanel()); // 빈 패널
        keyboardArea.add(upButton);
        keyboardArea.add(new JPanel()); // 빈 패널
        keyboardArea.add(leftButton);
        keyboardArea.add(downButton);
        keyboardArea.add(rightButton);
        
        panel.add(scoreLabel); // 점수 레이블 추가
		panel.add(panelPlay);
		panelPlay.add(mentLabel);
		panelPlay.add(keyboardArea);
		
    }
	

	private class QueryChange extends Thread {
		Random random = new Random();
        	
		@Override
		public void run() {
			if (!playing) return;

			int randomInt;
			keyPressed = "";

			for(int i = 1; i <= 30; ++i) {
				randomInt = random.nextInt(12);
				final ImageIcon mentImage = new ImageIcon("image/dir" + Integer.toString(randomInt+1) + ".png");
				final Image image = mentImage.getImage().getScaledInstance(460, 230, Image.SCALE_SMOOTH);

				mentLabel.setIcon(new ImageIcon(image));

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(ANSWER[randomInt] == 0 && keyPressed.equals("right")) {
					scoring(true);
					scoreLabel.setText("점수: "+ Integer.toString(score));
				}
				else if(ANSWER[randomInt] == 1 && keyPressed.equals("left")) {
					scoring(true);
					scoreLabel.setText("점수: "+ Integer.toString(score));
				}
				else if(ANSWER[randomInt] == 2 && keyPressed.equals("down")) {
					scoring(true);
					scoreLabel.setText("점수: "+ Integer.toString(score));
				}
				else if(ANSWER[randomInt] == 3 && keyPressed.equals("up")) {
					scoring(true);
					scoreLabel.setText("점수: "+ Integer.toString(score));
				}
				else {
					scoring(false);
					scoreLabel.setText("점수: "+ Integer.toString(score));
				}
				keyPressed = "";
			}

			client.sendMessage("finish " + client.userNumber + " " + score);
		}
	}


	// 3초 기다린 후에 시작
	private class Wait3Sec extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
				while (count > 0) {
					// 카운트다운 값 감소
					ImageIcon mentImage = new ImageIcon("image/count" + count + ".png");
					Image image = mentImage.getImage().getScaledInstance(460, 230, Image.SCALE_SMOOTH);
					mentImage = new ImageIcon(image);
					mentLabel.setIcon(mentImage);
					Thread.sleep(1000);
					count--;// 레이블에 숫자 표시
				}

				//go 이미지 띄우기
				ImageIcon mentImage = new ImageIcon("image/go.png");
				Image image = mentImage.getImage().getScaledInstance(460, 230, Image.SCALE_SMOOTH);
				mentImage = new ImageIcon(image);
				mentLabel.setIcon(mentImage);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}



	private class ButtonClickListener implements ActionListener {
    	
		@Override
		public void actionPerformed(ActionEvent e) {
			// 게임 종료됨. 클릭 불가
			if (!playing) {
				return;
			}

			keyPressed = e.getActionCommand();
		}
    }

		
	// 결과 메시지를 파싱해서 결과 처리
	public void parseReceivedMessage(String message) {
		String[] parsedMessage = message.split(" ");

		// "start [gameNumber]"
		// 게임 시작. 점수 및 기타 사항 초기화
		if (parsedMessage[0].equals("start")) {
			playing = false;
			count = 3;
			score = 0;
			scoreLabel.setText("점수: " + score);
			Thread wait3Sec = new Wait3Sec();
			wait3Sec.start();
			try {
				wait3Sec.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			playing = true;
			
			// 게임 시작
			Thread queryChangeThread = new QueryChange();
			queryChangeThread.start();
		}

		
		// "finish [score0] [score1]"
		// 두 유저 모두 게임 종료. 점수와 승자 출력
		if (parsedMessage[0].equals("finish")) {
			int user0score = Integer.parseInt(parsedMessage[1]);
			int user1score = Integer.parseInt(parsedMessage[2]);
			int winner = user0score == user1score ? -1 : user0score > user1score ? 0 : 1;
			
			// 최종 점수 표시
			if (client.userNumber == 0) {
				scoreLabel.setText("나 " + user0score + " : " + user1score + " 상대");
			} else {
				scoreLabel.setText("나 " + user1score + " : " + user0score + " 상대");
			}

			// 승리 패배 표시
			if (winner == -1) {
				ImageIcon mentImage = new ImageIcon("image/DRAW.png");
				Image image = mentImage.getImage().getScaledInstance(460, 230, Image.SCALE_SMOOTH);
				mentImage = new ImageIcon(image);
				mentLabel.setIcon(mentImage);
			}
			else if (winner == client.userNumber) {
				ImageIcon mentImage = new ImageIcon("image/WIN.png");
				Image image = mentImage.getImage().getScaledInstance(460, 230, Image.SCALE_SMOOTH);
				mentImage = new ImageIcon(image);
				mentLabel.setIcon(mentImage);
			} else {
				ImageIcon mentImage = new ImageIcon("image/LOSE.png");
				Image image = mentImage.getImage().getScaledInstance(460, 230, Image.SCALE_SMOOTH);
				mentImage = new ImageIcon(image);
				mentLabel.setIcon(mentImage);
			}

			playing = false;
			return;
		}
	}
}