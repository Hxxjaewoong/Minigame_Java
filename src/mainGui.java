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

    // panels
    public JPanel panel1;
    public JPanel panel2;
    public JPanel panel3;

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


    // image icons
    ImageIcon mainInitialImage = new ImageIcon("image/MINIGAME_MAIN.jpg");
    ImageIcon game1Image = new ImageIcon("image/TREASUREHUNT.jpg");
    ImageIcon game2Image = new ImageIcon("image/NUMBERGAME.jpg");
    ImageIcon game3Image = new ImageIcon("image/HALLIGALLI.jpg");
    ImageIcon game4Image = new ImageIcon("image/DIRECTIONGAME.jpg");
    ImageIcon game5Image = new ImageIcon("image/DIRECTIONGAME.jpg");

    mainGui(Client client) {
        this.client = client;

        setTitle("Mini Game");

        // panel1: 초기화면 및 게임 화면
        panel1 = new JPanel();
        panel1.setBackground(panelBackgroundColor);
        panel1.setBounds(20, 20, 460, 520);
        panel1.setLayout(new BorderLayout());


        JLabel imageLabel;
        

        Image image = mainInitialImage.getImage().getScaledInstance(460, 520, Image.SCALE_SMOOTH);
        mainInitialImage = new ImageIcon(image);
        imageLabel = new JLabel(mainInitialImage);

        panel1.add(imageLabel, BorderLayout.CENTER);

        gg1 = new GuiGame1(client);
        gg2 = new GuiGame2(client);
        gg3 = new GuiGame3(client);
        gg4 = new GuiGame4(client);
        gg5 = new GuiGame5(client);


        // panel2: 게임 선택 버튼
        panel2 = new JPanel();
        panel2.setBackground(panelBackgroundColor);
        panel2.setBounds(500, 20, 180, 450);
        panel2.setLayout(new GridLayout(5, 1)); // 5개의 게임 목록을 세로로 배치하기 위해 GridLayout 설정

        
        JButton game1Button = new JButton(game1Image);
        JButton game2Button = new JButton(game2Image);
        JButton game3Button = new JButton(game3Image);
        JButton game4Button = new JButton(game4Image);
        JButton game5Button = new JButton(game5Image);

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
        panel2.add(game4Button);
        panel2.add(game5Button);
        
        panel3.add(startButton);
        panel3.add(exitButton);
        
        add(panel1);
        add(panel2);
        add(panel3);


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






    // panel1에 설명을 보여주는 메소드
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
        
        clearPanel1();
        panel1.add(instructionsLabel, BorderLayout.CENTER);
    }


    // 게임 시작 버튼에 대한 액션 리스너
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
                        client.sendMessage("start " + client.userNumber + " 1");
                        break;
                    case 2:
                        panel1.add(gg2.panel);
                        client.sendMessage("start " + client.userNumber + " 2");
                        break;
                    case 3:
                        panel1.add(gg3.panel);
                        client.sendMessage("start " + client.userNumber + " 3");
                        break;
                    case 4:
                        panel1.add(gg4.panel);
                        client.sendMessage("start " + client.userNumber + " 4");
                        break;
                    case 5:
                        panel1.add(gg5.panel);
                        client.sendMessage("start " + client.userNumber + " 5");
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
            

            // 게임 선택 (1~5)
            else {
                int numberChosenGame = Integer.parseInt(command); // 선택한 게임의 순번
                gameChoice = numberChosenGame;
                showGameInstructions(numberChosenGame);
            }
        }
    }







    public Client client;
	public String receivedMessage;
    private int currentGame = 0;

	private class getMessageThread extends Thread {

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

            // "end"
            // 현재 게임 종료
            if (parsedMessage[0].equals("end")) {
                currentGame = 0;
                clearPanel1();
                return;
            }


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
                default:
                    ;
            }

            return;
        }
    }
}
