import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;

import socket.Client;
import gui.GuiGame1;
import gui.GuiGame2;
import gui.GuiGame3;


class mainGui extends JFrame {

    // panels
    public JPanel panel1;
    public JPanel panel2;
    public JPanel panel3;

    // buttons=
    public JButton startButton;
    public JButton exitButton;

    // game GUIs
    public int gameChoice = 0;
    public GuiGame1 gg1;
    public GuiGame2 gg2;
    public GuiGame3 gg3;

    
    //
    public JTextArea welcomeTextArea;
    public JLabel instructionsLabel;

    // color
    public Color panelBackgroundColor = new Color(102, 102, 102);

    mainGui(Client client) {
        this.client = client;

        setTitle("Mini Game");

        // panel1: 초기화면 및 게임 화면
        panel1 = new JPanel();
        panel1.setBackground(panelBackgroundColor);
        panel1.setBounds(20, 20, 460, 520);
        panel1.setLayout(new BorderLayout());

        gg1 = new GuiGame1(client);
        gg2 = new GuiGame2(client);
        gg3 = new GuiGame3(client);


        // panel2: 게임 선택 버튼
        panel2 = new JPanel();
        panel2.setBackground(panelBackgroundColor);
        panel2.setBounds(500, 20, 180, 450);
        panel2.setLayout(new GridLayout(3, 1)); // 3개의 게임 목록을 세로로 배치하기 위해 GridLayout 설정
        
        JButton game1Button = new JButton("Game 1");
        JButton game2Button = new JButton("Game 2");
        JButton game3Button = new JButton("Game 3");

        // game 선택 버튼 이벤트
        game1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameChoice = 1;
                showGameInstructions(1); // 게임 1 사용법 보여주기
            }
        });
        game2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameChoice = 2;
                showGameInstructions(2); // 게임 2 사용법 보여주기
            }
        });
        game3Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameChoice = 3;
                showGameInstructions(3); // 게임 3 사용법 보여주기
            }
        });
        
        


        // panel 3: 시작 및 종료 버튼
        panel3 = new JPanel();
        panel3.setBackground(panelBackgroundColor);
        panel3.setBounds(500, 500, 180, 40);
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(100, 30));
        startButton.setActionCommand("Start Game");
        startButton.addActionListener(new ButtonClickListener());
        
        exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(60, 30));
        exitButton.setActionCommand("Exit");
        exitButton.addActionListener(new ButtonClickListener());
        


        setLayout(null);


        panel2.add(game1Button);
        panel2.add(game2Button);
        panel2.add(game3Button);
        
        panel3.add(startButton);
        panel3.add(exitButton);
        
        add(panel1);
        add(panel2);
        add(panel3);

        // 게임 입장 시 welcome message 보여주기
        showWelcomeMessage();

        // instruction label 초기화
        instructionsLabel = new JLabel();
        instructionsLabel.setHorizontalAlignment(SwingConstants.CENTER);


        // frame 디자인
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 600);
        setResizable(false);
        setVisible(true);



        // 서버로부터 메시지 받아 처리하는 쓰레드 실행
		new getMessageThread().start();

    }



    // panel1에 있는 요소 제거
    private void clearPanel1() {
        panel1.removeAll();
        panel1.revalidate();
        panel1.repaint();
    }



    private void showWelcomeMessage() {
        /* 처음에는 JLabel 기능을 사용했다가, Text 분량 제한으로 textArea로 바꿨습니다. 필요하면 사용하세요.
        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome to MiniGame");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE); // 폰트 색상을 흰색(White)으로 설정
        panel1.add(welcomeLabel, BorderLayout.CENTER); // "Welcome!" 문구를 추가
        */
        // Welcome text area
        welcomeTextArea = new JTextArea("Welcome!\nIf you click each game name in the list \nyou can read discription of that game.");
        welcomeTextArea.setEditable(false);
        welcomeTextArea.setLineWrap(true);
        welcomeTextArea.setWrapStyleWord(true);
        welcomeTextArea.setFont(welcomeTextArea.getFont().deriveFont(20f)); // 원하는 폰트 크기로 설정
        panel1.add(welcomeTextArea, BorderLayout.CENTER); // "Welcome!" 텍스트 영역을 추가
        
    }


    // panel1에 설명을 보여주는 메소드
    private void showGameInstructions(int game) {
       
        String instructions = "";
        switch (game) {
            case 1:
                instructions = "Game 1은 ~~~~ 입니다."; // 게임 1 사용법
                break;
            case 2:
                instructions = "Game 2는 ~~~~ 입니다."; // 게임 2 사용법
                break;
            case 3:
                instructions = "Game 3은 ~~~~ 입니다."; // 게임 3 사용법
                break;
            default:
                // 게임을 선택하지 않고 시작을 눌렀을 경우
                instructions = "게임을 선택해 주세요.";
        }
        instructionsLabel.setText(instructions);
        
        clearPanel1();
        panel1.add(instructionsLabel, BorderLayout.CENTER);

    }


    private class ButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String command = e.getActionCommand();

            // 선택한 게임이 있다면 게임 시작
            if (command.equals("Start Game")) {
                clearPanel1();
                switch (gameChoice) {
                    case 1:
                        panel1.add(gg1.panel);
                        client.sendMessage("choose 1");
                        break;
                    case 2:
                        panel1.add(gg2.panel);
                        client.sendMessage("choose 2");
                        break;
                    case 3:
                        panel1.add(gg3.panel);
                        client.sendMessage("choose 3");
                        break;
                    default:
                        showGameInstructions(gameChoice);
                        break;
                }
            }


            // 프로그램 종료
            else if (command.equals("Exit")) {
                System.out.println("Exit");
                System.exit(0);
            }
        }
    }







    public Client client;
	public String receivedMessage;
    private int currentGame = 0;

	private class getMessageThread extends Thread {
        Random rd = new Random();

        @Override
        public void run() {
            while (true) {
                // 서버로부터 결과 메시지 수신
                receivedMessage = client.getReceivedMessage();

				// 결과 메시지 처리
                if (receivedMessage != null) {
					System.out.println("client >>> mainGui: " + receivedMessage);
                    parseReceivedMessage(receivedMessage);
                }
            }
        }


        	
        // 결과 메시지를 파싱해서 결과 처리
        public void parseReceivedMessage(String message) {
            String[] parsedMessage = message.split(" ");


            // 게임이 진행중이면 (메인화면이 아니면) 각 게임의 gui class에서 메시지 처리
            if (currentGame != 0) {
                switch (currentGame) {
                    case 1:
                        gg1.parseReceivedMessage(message);
                        break;
                    case 2:
                        // gg2.parseReceivedMessage(message);
                        break;
                    case 3:
                        break;
                }

                return;
            }


            // "start [gameNumber] (+[firstPlayer])"
            // 세번째 인자는 여기서는 무시해도 됨
            // 해당 게임 시작. 각 parser에서 start를 만나면 game 초기화
            if (parsedMessage[0].equals("start")) {
                int gameNumber = Integer.parseInt(parsedMessage[1]);
                currentGame = gameNumber;

                switch (gameNumber) {
                    case 1:
                        gg1.parseReceivedMessage(message);
                        break;
                    case 2:
                        // gg2.parseReceivedMessage(message);
                        break;
                    case 3:
                        break;

                }
    		}


            // "end"
            // 현재 게임 종료
            if (parsedMessage[0].equals("end")) {
                currentGame = 0;
                clearPanel1();
            }
        }
    }



}
