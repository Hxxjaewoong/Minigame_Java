package gui;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

import socket.Client;
import games.Game4;

public class GuiGame4 extends Game4 implements GamePanel{

	Client client;

	boolean playing;

	public JPanel panel;
	public JPanel panelInfo;
    public JPanel panelPlay;
	public JTextArea infoText;
	public JLabel scoreLabel;

    public int count = 3; //시작 전 3초 세고 시작함
    public int countGame = 1;
    
	JTextArea TextQ;
	JPanel keyboardArea;
	JButton upButton;
	JButton downButton;
	JButton leftButton;
	JButton rightButton;


	public String keyPressed;
    
	public GuiGame4(Client clinet) {
		this.client = clinet;

		panel = new JPanel();
		panel.setBounds(gameArea);
		panel.setFocusable(true); // 패널에 포커스를 설정

		panelInfo = new JPanel();
		panelInfo.setPreferredSize(gameInfoArea);
		panelInfo.setBackground(Color.yellow);

		scoreLabel = new JLabel("Score: 0");
        scoreLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
        
		infoText = new JTextArea("Contains info of game4");
		panelInfo.add(infoText);

        panelPlay = new JPanel();
        panelPlay.setPreferredSize(new Dimension(460, 430));
        panelPlay.setBackground(Color.white);
        panelPlay.setLayout(new GridLayout(2,1,	 10, 10));
        
        
        TextQ = new JTextArea("\n        It will start soon");
        TextQ.setEditable(false);
        TextQ.setFont(TextQ.getFont().deriveFont(40f)); // 원하는 폰트 크기로 설정
        
        keyboardArea = new JPanel();
        //keyboardArea.setPreferredSize(new Dimension(230, 280));
        keyboardArea.setBackground(Color.white);
        keyboardArea.setLayout(new GridLayout(2, 3, 10, 10));


		// buttons
        upButton = new JButton("▴");
        downButton = new JButton("▾");
    	leftButton = new JButton("◂");
        rightButton = new JButton("▸");
		
        upButton.setBackground(Color.white);
        downButton.setBackground(Color.white);
        leftButton.setBackground(Color.white);
        rightButton.setBackground(Color.white);	

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
        
        panel.add(panelInfo);
        panel.add(scoreLabel); // 점수 레이블 추가
		panel.add(panelPlay);
		panelPlay.add(TextQ);
		panelPlay.add(keyboardArea);
		
    }
	

	private class QueryChange extends Thread  {
		Random random = new Random();
        	
		@Override
		public void run() {
			int randomInt;
			keyPressed = "";

			for(int i = 1; i <= 20; ++i) {
				randomInt = random.nextInt(12);
				TextQ.setText(MENT[randomInt]);

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(ANSWER[randomInt] == 0 && keyPressed.equals("right")) {
					scoring(true);
					scoreLabel.setText("Score: "+ Integer.toString(score));
				}
				else if(ANSWER[randomInt] == 1 && keyPressed.equals("left")) {
					scoring(true);
					scoreLabel.setText("Score: "+ Integer.toString(score));
				}
				else if(ANSWER[randomInt] == 2 && keyPressed.equals("down")) {
					scoring(true);
					scoreLabel.setText("Score: "+ Integer.toString(score));
				}
				else if(ANSWER[randomInt] == 3 && keyPressed.equals("up")) {
					scoring(true);
					scoreLabel.setText("Score: "+ Integer.toString(score));
				}
				else {
					scoring(false);
					scoreLabel.setText("Score: "+ Integer.toString(score));
				}
				keyPressed = "";
			}

			client.sendMessage("finish " + client.userNumber + " " + score);
		}
	}

	Timer readyTimer = new Timer();
	TimerTask readyTimer1 = new TimerTask() {
		public void run() {
			if(count > 0) {
				// 카운트다운 값 감소
				TextQ.setText("                   "+ Integer.toString(count));
				count--;// 레이블에 숫자 표시
			}
			else {
				TextQ.setText("                  Go!"); // 카운트다운 종료 후 메시지 표시
				readyTimer.cancel();

				// 게임 시작
				Thread queryChangeThread = new QueryChange();
				queryChangeThread.start();
			}
		}
	};



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

		// "start [gameNumber] [numbers 1 ~ 25]"
		// 서버에게서 정보 받을 수 있을 듯
		if (parsedMessage[0].equals("start")) {
			playing = true;
			readyTimer.schedule(readyTimer1, 3000, 1000);
		}

		
		// "finish [winner]"
		// 두 유저 모두 게임 종료. 점수와 승자 출력
		if (parsedMessage[0].equals("finish")) {
			int winner = Integer.parseInt(parsedMessage[1]);

			if (winner == -1) {
				TextQ.setText("Final Score: "+score+"\nDraw!");
			}
			else if (winner == client.userNumber) {
				TextQ.setText("Final Score: "+score+"\nWin!");
			} else {
				TextQ.setText("Final Score: "+score+"\nLose...");
			}

			playing = false;
			return;
		}
	}
}
