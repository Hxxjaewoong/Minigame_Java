import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import socket.Client;
import gui.GuiGame1;
import gui.GuiGame2;
import gui.GuiGame3;
import gui.GuiGame4;
//import gui.GuiGame5;


class MainGui extends JFrame {
	
	

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

    public JLabel instructionsLabel;

    // color
    public Color panelBackgroundColor = new Color(102, 102, 102);


    // image icons
    ImageIcon mainInitialImage = new ImageIcon("image/MINIGAME_MAIN.jpg");
    ImageIcon game1Image = new ImageIcon("image/TREASUREHUNT.jpg");
    ImageIcon game2Image = new ImageIcon("image/NUMBERGAME.jpg");
    ImageIcon game3Image = new ImageIcon("image/HALLIGALLI.jpg");
    ImageIcon game4Image = new ImageIcon("image/DIRECTIONGAME.jpg");

    ImageIcon game1InstructionImage = new ImageIcon("image/Game1Inst.png");
    ImageIcon game2InstructionImage = new ImageIcon("image/Game2Inst.png");
    ImageIcon game3InstructionImage = new ImageIcon("image/Game3Inst.png");
    ImageIcon game4InstructionImage = new ImageIcon("image/Game4Inst.png");
    
    

    MainGui(Client client) {
        this.client = client;

        getContentPane().setBackground(new Color(238, 238, 238));
        
        setTitle("Mini Game");

        // panel1: 초기화면 및 게임 화면
        panel1 = new JPanel();
        panel1.setBackground(new Color(238, 238, 238));
        panel1.setBounds(20, 20, 470, 520);
        panel1.setLayout(new BorderLayout());


        JLabel imageLabel;
        

        Image image = mainInitialImage.getImage().getScaledInstance(460, 520, Image.SCALE_SMOOTH);
        mainInitialImage = new ImageIcon(image);
        imageLabel = new JLabel(mainInitialImage);
        
        imageLabel.setOpaque(true);
        imageLabel.setBackground(new Color(238, 238, 238)); //흰색 줄 오류 해결

        panel1.add(imageLabel, BorderLayout.CENTER);

        gg1 = new GuiGame1(client);
        gg2 = new GuiGame2(client);
        gg3 = new GuiGame3(client);
        gg4 = new GuiGame4(client);


        // panel2: 게임 선택 버튼
        panel2 = new JPanel();
        panel2.setOpaque(false);
        panel2.setBounds(500, 20, 180, 450);
        panel2.setLayout(new GridLayout(4, 1)); // 4개의 게임 목록을 세로로 배치하기 위해 GridLayout 설정

        
        JButton game1Button = new JButton(game1Image);
        JButton game2Button = new JButton(game2Image);
        JButton game3Button = new JButton(game3Image);
        JButton game4Button = new JButton(game4Image);

        game1Button.setActionCommand("1");
        game2Button.setActionCommand("2");
        game3Button.setActionCommand("3");
        game4Button.setActionCommand("4");
        
        game1Button.addActionListener(new ButtonClickListener());
        game2Button.addActionListener(new ButtonClickListener());
        game3Button.addActionListener(new ButtonClickListener());
        game4Button.addActionListener(new ButtonClickListener());


        


     // panel 3: 시작 및 종료 버튼
        panel3 = new JPanel();
        panel3.setOpaque(false); // 배경색 투명 설정
        panel3.setBounds(500, 500, 180, 40);
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        startButton = new JButton();
        startButton.setPreferredSize(new Dimension(100, 30));
        ImageIcon startIcon = new ImageIcon("image/start.png");
        Image startImage = startIcon.getImage().getScaledInstance(100, 30, Image.SCALE_SMOOTH);
        startButton.setIcon(new ImageIcon(startImage));
        startButton.setContentAreaFilled(false); // 버튼 배경 투명 설정
        startButton.setBorderPainted(false); // 버튼 테두리 제거
        startButton.setFocusPainted(false); // 버튼 선택 시 포커스 표시 제거
        startButton.setActionCommand("Start Game");
        startButton.addActionListener(new ButtonClickListener());

        exitButton = new JButton();
        exitButton.setPreferredSize(new Dimension(60, 30));
        ImageIcon exitIcon = new ImageIcon("image/exit.png");
        Image exitImage = exitIcon.getImage().getScaledInstance(60, 30, Image.SCALE_SMOOTH);
        exitButton.setIcon(new ImageIcon(exitImage));
        exitButton.setContentAreaFilled(false); // 버튼 배경 투명 설정
        exitButton.setBorderPainted(false); // 버튼 테두리 제거
        exitButton.setFocusPainted(false); // 버튼 선택 시 포커스 표시 제거
        exitButton.setActionCommand("Exit");
        exitButton.addActionListener(new ButtonClickListener());


        
    
        setLayout(null);


        panel2.add(game1Button);
        panel2.add(game2Button);
        panel2.add(game3Button);
        panel2.add(game4Button);
        
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

//보물 5갠데 5개 중에 더 많이 
//1-50
//카드가 0장이 되는 순간 패배

    // panel1에 있는 요소 제거
    private void clearPanel1() {
        panel1.removeAll();
        panel1.revalidate();
        panel1.repaint();
    }





 // panel1에 설명을 보여주는 메소드
    private void showGameInstructions(int game) {
        String instructions = "";
        ImageIcon gameImage = null;
        
        switch (game) {
	        case 1:
	            instructions = "<html>Treasure Hunt Game<br><br>"
	                    + "보물이 묻혀있는 비밀스러운 세계로 초대합니다! 이곳에는 당신을 기다리는 숨겨진 보물이 총 5개가 묻혀져 있습니다.<br><br>"
	                    + "각 플레이어는 서로 번갈아가며 10 x 10 칸 중 하나를 선택하여 클릭합니다. 클릭한 순간, X 표시나 보물 표시 중 하나가 나타나며, 당신은 보물에 한 걸음 더 가까워집니다.<br><br>"
	                    + "하지만 여기서 중요한 점은, 보물의 위치를 정확히 파악하는 것입니다. 클릭한 행과 열의 정보와 함께, 가장 가까운 보물과의 거리도 함께 표시됩니다. 이 정보를 근거로 당신만의 전략을 세우세요. 가까운 거리에 보물이 있다는 것을 알게 되면, 그 위치로 향해 더욱 집중적으로 탐색을 이어나갈 수 있습니다.<br><br>"
	                    + "어디에 숨겨진 보물들이 위치해 있는지 알아내고, 가장 많은 보물을 찾아내어 승리자가 되세요!</html>";
	            gameImage = game1InstructionImage;
	            break;
            case 2:
            	instructions = "<html>Number Game<br><br>"
                        + "\"Number Game\"은 빠른 순발력과 판단력이 요구되는 게임으로, 숫자 1부터 50까지의 랜덤하게 숫자가 배치된 보드 위에서 진행됩니다. 여러분은 차례대로 1부터 50까지의 숫자를 클릭하여 먼저 50까지 클릭한 사람이 승리합니다.<br><br>"
                        + "숫자가 보드 위에 랜덤하게 나타나기 때문에, 판단력과 순발력이 중요합니다. 그렇기 때문에 어떤 숫자가 어디에 위치해 있는지 정확히 파악하고, 빠르게 찾아내야 합니다.<br><br>"
                        + "차분하고 집중력을 유지하며, 숫자의 세계에서 최고의 승자로서의 자리를 차지하세요. 여러분이 얼마나 빠르게 숫자를 찾아 클릭할 수 있는지, 그리고 50까지의 숫자를 제일 먼저 완성할 수 있는지 기대해 보겠습니다.</html>";
                gameImage = game2InstructionImage;
                break;
            case 3:
            	instructions = "<html>Halli Galli<br><br>"
                        + "\"할리갈리\"는 카드의 세계에서 펼쳐지는 빠른 속도의 대결입니다. 여러분은 한 더미의 카드를 소유하게 되며, 다른 플레이어와 한 번씩 한 개의 카드를 내놓으며 경쟁하게 됩니다.<br><br>"
                        + "당신이 아래쪽에 있는 카드를 클릭해서 카드를 내놓을 수 있으며, 상대방이 내놓은 카드와 당신이 내놓은 카드들에서 같은 과일 그림이 5개가 되는 것을 먼저 보고 빠르게 종을 클릭하는 사람이 내놓은 카드 모두를 가져가는 게임입니다.<br><br>"
                        + "여러분은 순간적으로 다른 플레이어들의 카드를 파악해야 합니다. 플레이어들이 카드를 빠르게 내려놓을 것이기 때문에, 각 특성을 빠르게 인식하고 판단하는 능력이 매우 중요합니다.<br><br>"
                        + "한 장의 카드도 남기지 못하고 모든 카드를 내놓게 되면 패배하게 됩니다.<br><br>"
                        + "자, 이제 카드의 세계로 뛰어들어 \"할리갈리\"의 재미와 긴장감을 느껴보세요. 집중과 빠른 판단으로 승리하세요. 행운을 빕니다!</html>";
                gameImage = game3InstructionImage;
                break;
            case 4:
            	instructions = "<html>Direction Game<br><br>"
                        + "\"Direction Game\"에 오신 것을 환영합니다. 이 게임은 텍스트를 읽고 정확한 방향을 클릭하는 게임입니다.<br><br>"
                        + "게임은 40초 동안 진행되며, 여러분은 화면에 나타나는 텍스트를 읽고 해당하는 방향을 빠르게 클릭해야 합니다. 주어진 시간 동안 정확한 클릭을 최대한 많이 한 사람이 승리합니다.<br><br>"
                        + "여러분은 빠른 판단력과 순발력을 갖추어야 합니다. 텍스트를 빠르게 읽고 정확히 이해한 후, 올바른 방향을 선택해야 합니다. 정확성은 물론 속도도 중요하니 여러분의 집중력과 반응속도를 최대한 발휘하세요.<br><br>"
                        + "지금부터 여러분의 능력을 시험해보세요. 40초 동안 정확한 클릭을 최대한 많이 해보고, 승리의 영광을 거두세요!</html>";
                gameImage = game4InstructionImage;
                break;
            default:
                // 게임을 선택하지 않고 시작을 눌렀을 경우
                instructions = "게임을 선택해 주세요.";
                break;
        }
        
        instructionsLabel.setText(instructions);
        
        clearPanel1();
        panel1.setLayout(new BorderLayout());
        
     // 이미지를 보여주기 위한 JLabel 생성 및 설정
        JLabel gameImageLabel = new JLabel();
        gameImageLabel.setIcon(new ImageIcon(gameImage.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH)));
        gameImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        
        // 이미지와 설명을 담을 패널 생성 및 설정
        JPanel instructionsPanel = new JPanel(new BorderLayout());
        instructionsPanel.setBackground(new Color(238, 238, 238));
        instructionsPanel.add(gameImageLabel, BorderLayout.CENTER);
        instructionsPanel.add(instructionsLabel, BorderLayout.SOUTH);
        
        panel1.add(instructionsPanel, BorderLayout.CENTER);
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
            

            // 게임 선택 (1~4)
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
                        gg4.parseReceivedMessage(message);
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
                    gg4.parseReceivedMessage(message);
                    break;
            }

            return;
        }
    }
}
