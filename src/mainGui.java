// 상대방이 접속해야 게임 시작 가능하도록 해야 오류 안남



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import socket.Client;
import gui.GuiGame1;
import gui.GuiGame2;
import gui.GuiGame3;
import gui.GuiGame4;
import gui.GuiGame5;


class mainGui extends JFrame {
    public Client client;

    // panels
    public JPanel gamePanel;
    public JPanel selectPanel;
    public JPanel behavePanel;

    // buttons
    public JButton startButton;
    public JButton exitButton;

    // game GUIs
    public int gameChoice = 0;
    public GuiGame1 gg1;
    public GuiGame2 gg2;
    public GuiGame3 gg3;
    public GuiGame4 gg4;
    public GuiGame5 gg5;

    public JLabel instructionsLabel;

    // color
    public Color panelBackgroundColor = new Color(102, 102, 102);


    // main image
    JLabel mainInitialImageLabel;
    Image mainInitialImage;

    // image icons
    ImageIcon mainInitialImageIcon = new ImageIcon("image/MINIGAME_MAIN.jpg");
    ImageIcon game1ImageIcon = new ImageIcon("image/TREASUREHUNT.jpg");
    ImageIcon game2ImageIcon = new ImageIcon("image/NUMBERGAME.jpg");
    ImageIcon game3ImageIcon = new ImageIcon("image/HALLIGALLI.jpg");
    ImageIcon game4ImageIcon = new ImageIcon("image/DIRECTIONGAME.jpg");
    ImageIcon game5ImageIcon = new ImageIcon("image/DIRECTIONGAME.jpg");

    mainGui(Client client) {
        this.client = client;

        setTitle("Mini Game");

        // panel1: 초기화면 및 게임 화면
        gamePanel = new JPanel();
        gamePanel.setBackground(panelBackgroundColor);
        gamePanel.setBounds(20, 20, 460, 520);
        gamePanel.setLayout(new BorderLayout());


        
        mainInitialImage = mainInitialImageIcon.getImage().getScaledInstance(460, 520, Image.SCALE_SMOOTH);
        mainInitialImageIcon = new ImageIcon(mainInitialImage);
        mainInitialImageLabel = new JLabel(mainInitialImageIcon);

        gamePanel.add(mainInitialImageLabel, BorderLayout.CENTER);

        gg1 = new GuiGame1(client);
        gg2 = new GuiGame2(client);
        gg3 = new GuiGame3(client);
        gg4 = new GuiGame4(client);
        gg5 = new GuiGame5(client);


        // panel2: 게임 선택 버튼
        selectPanel = new JPanel();
        selectPanel.setBackground(panelBackgroundColor);
        selectPanel.setBounds(500, 20, 180, 450);
        selectPanel.setLayout(new GridLayout(5, 1)); // 5개의 게임 목록을 세로로 배치하기 위해 GridLayout 설정

        
        JButton game1Button = new JButton(game1ImageIcon);
        JButton game2Button = new JButton(game2ImageIcon);
        JButton game3Button = new JButton(game3ImageIcon);
        JButton game4Button = new JButton(game4ImageIcon);
        JButton game5Button = new JButton(game5ImageIcon);

        game1Button.setActionCommand("1");
        game2Button.setActionCommand("2");
        game3Button.setActionCommand("3");
        game4Button.setActionCommand("4");
        game5Button.setActionCommand("5");
        
        game1Button.addActionListener(new ButtonClickListener());
        game2Button.addActionListener(new ButtonClickListener());
        game3Button.addActionListener(new ButtonClickListener());
        game4Button.addActionListener(new ButtonClickListener());
        game5Button.addActionListener(new ButtonClickListener());


        


        // panel 3: 시작 및 종료 버튼
        behavePanel = new JPanel();
        behavePanel.setBackground(panelBackgroundColor);
        behavePanel.setBounds(500, 500, 180, 40);
        behavePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(100, 30));
        startButton.setActionCommand("Start Game");
        startButton.addActionListener(new ButtonClickListener());
        
        exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(60, 30));
        exitButton.setActionCommand("exit");
        exitButton.addActionListener(new ButtonClickListener());
        


        setLayout(null);


        selectPanel.add(game1Button);
        selectPanel.add(game2Button);
        selectPanel.add(game3Button);
        selectPanel.add(game4Button);
        selectPanel.add(game5Button);
        
        behavePanel.add(startButton);
        behavePanel.add(exitButton);
        
        add(gamePanel);
        add(selectPanel);
        add(behavePanel);


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
		new GetMessageThread().start();

    }


    // gamePanel에 있는 요소 제거
    private void clearGamePanel() {
        gamePanel.removeAll();
        gamePanel.revalidate();
        gamePanel.repaint();
    }



    // gamePanel에 설명을 보여주는 메소드
    private void showGameInstructions(int game) {
       
        String instructions = "";
        switch (game) {
            case 1:
                instructions = "Game 1은 ~\n\n~~~ 입니다."; // 게임 1 사용법
                break;
            case 2:
                instructions = "Game 2는 ~~~~ 입니다."; // 게임 2 사용법
                break;
            case 3:
                instructions = "Game 3은 ~~~~ 입니다."; // 게임 3 사용법
                break;
            case 4:
                instructions = "Game 4은 ~~~~ 입니다."; // 게임 4 사용법
                break;
            case 5:
                instructions = "Game 5은 ~~~~ 입니다."; // 게임 5 사용법
                break;
            default:
                // 게임을 선택하지 않고 시작을 눌렀을 경우
                instructions = "게임을 선택해 주세요.";
        }
        instructionsLabel.setText(instructions);
        
        clearGamePanel();
        gamePanel.add(instructionsLabel, BorderLayout.CENTER);
    }


    // 게임 시작 버튼에 대한 액션 리스너
    private class ButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String command = e.getActionCommand();

            // 선택한 게임이 있다면 게임 시작
            if (command.equals("Start Game")) {
                clearGamePanel();
                switch (gameChoice) {
                    case 1:
                        gamePanel.add(gg1.panel);
                        break;
                    case 2:
                        gamePanel.add(gg2.panel);
                        break;
                    case 3:
                        gamePanel.add(gg3.panel);
                        break;
                    case 4:
                        gamePanel.add(gg4.panel);
                        break;
                    case 5:
                        gamePanel.add(gg5.panel);
                        break;
                    default:
                        showGameInstructions(gameChoice);
                        return;
                }
                
                client.sendMessage("start " + client.userNumber + " " + gameChoice);
                gameChoice = 0;
                return;
            }

            // 프로그램 종료
            if (command.equals("exit")) {
                System.exit(0);
            }
            
            // 게임 선택 (1~5)
            int numberChosenGame = Integer.parseInt(command); // 선택한 게임의 순번
            gameChoice = numberChosenGame;
            showGameInstructions(numberChosenGame);
        }
    }


	public String receivedMessage;
    public int currentGame = 0;

	private class GetMessageThread extends Thread {

        @Override
        public void run() {
            while (true) {
                // 서버로부터 결과 메시지 수신
                receivedMessage = client.getReceivedMessage();

				// 결과 메시지 처리
                if (receivedMessage != null) {
                    parseReceivedMessage(receivedMessage);
                }
            }
        }
        	
        // 결과 메시지를 파싱해서 결과 처리
        public void parseReceivedMessage(String message) {
            String[] parsedMessage = message.split(" ");

            // "exit"
            // 상대가 게임 종료함 -> 3초 뒤에 게임 종료
            if (parsedMessage[0].equals("EXIT")) {
                try {
                    clearGamePanel();
                    gamePanel.add(instructionsLabel);
                    for (int i = 3; i >= 1; i--) {
                        instructionsLabel.setText("The opponent has quit the game.\nThe game will end in " + i + " seconds.");
                        Thread.sleep(1000);
                    }
                    System.exit(0);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // "start [gameNumber] ([initString])"
            // 세번째 인자는 여기서는 무시해도 됨
            // 해당 게임 시작
            if (parsedMessage[0].equals("start")) {
                int gameNumber = Integer.parseInt(parsedMessage[1]);
                currentGame = gameNumber;

                switch (gameNumber) {
                    case 1:
                        gg1.parseReceivedMessage(message);
                        break;
                    case 2:
                        gg2.parseReceivedMessage(message);
                        break;
                    case 3:
                        gg3.parseReceivedMessage(message);
                        break;
                    case 4:
                        // gg4.parseReceivedMessage(message);
                        break;
                    case 5:
                        // gg5.parseReceivedMessage(message);
                        break;
                }

                return;
    		}

            // start 이외의 메시지
            // -> 플레이 중인 각 게임의 gui에서 처리하도록 함
            switch (currentGame) {
                case 1:
                    gg1.parseReceivedMessage(message);
                    break;
                case 2:
                    gg2.parseReceivedMessage(message);
                    break;
                case 3:
                    gg3.parseReceivedMessage(message);
                    break;
                case 4:
                    // gg4.parseReceivedMessage(message);
                    break;
                case 5:
                    // gg5.parseReceivedMessage(message);
                    break;
            }

            return;
        }
    }
}
