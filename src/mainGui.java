import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.sun.tools.javac.Main;

import gui.GuiGame1;
import gui.GuiGame2;
import gui.GuiGame3;
import gui.GuiGame4;
// import gui.GuiGame5;

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
    public GuiGame4 gg4;
    // public GuiGame5 gg5;
    
    //
    public JTextArea welcomeTextArea;
    public JLabel instructionsLabel;

    // color
    public Color panelBackgroundColor = new Color(102, 102, 102);

    mainGui() {
        setTitle("Mini Game");
        
        // panel1: Game contents
        
        panel1 = new JPanel();

        panel1.setBackground(panelBackgroundColor);
        panel1.setBounds(20, 20, 460, 520);
        panel1.setLayout(new BorderLayout());
        
        JLabel imageLabel;
        
        ImageIcon imageIcon = new ImageIcon("C:/Users/bread/eclipse-workspace/MINIGAME2/src/MINIGAME_MAIN.jpg");
        Image image = imageIcon.getImage().getScaledInstance(460, 520, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        imageLabel = new JLabel(imageIcon);

        panel1.add(imageLabel, BorderLayout.CENTER);

        setLayout(null);
        add(panel1);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 600);
        setResizable(false);
        
        
        gg1 = new GuiGame1();
        gg2 = new GuiGame2();
        gg3 = new GuiGame3();
        gg4 = new GuiGame4();
        // gg5 = new GuiGame5();

        // panel2: Game list
        panel2 = new JPanel();
        panel2.setBackground(panelBackgroundColor);
        panel2.setBounds(500, 20, 180, 450);
        panel2.setLayout(new GridLayout(4, 1)); // 4개의 게임 목록을 세로로 배치하기 위해 GridLayout 설정
        
        // game 1 
        //이미지 아이콘 설정
        ImageIcon game1Image = new ImageIcon("C:\\Users\\bread\\eclipse-workspace\\MINIGAME2\\src\\TREASUREHUNT.jpg");
        //이미지 크기 변경하는 법   
        //Image Img1 = game1Image.getImage();
        //Image changeImg1 = Img1.getScaledInstance(200, 180, Image.SCALE_SMOOTH);
        //ImageIcon changeIcon1 = new ImageIcon(changeImg1);
        
        JButton game1Button = new JButton(game1Image);
        
        game1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Game 1 실행하는 코드 작성
                // Game.Java와의 연결을 구현해야 합니다.
                gameChoice = 1;
                showGameInstructions(1); // 게임 1 사용법 보여주기
            }
        });
        
        // game 2
        //이미지 아이콘 설정
        ImageIcon game2Image = new ImageIcon("C:\\Users\\bread\\eclipse-workspace\\MINIGAME2\\src\\NUMBERGAME.jpg");
        JButton game2Button = new JButton(game2Image);
        
        game2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Game 2 실행하는 코드 작성
                // Game.Java와의 연결을 구현해야 합니다.
                gameChoice = 2;
                showGameInstructions(2); // 게임 2 사용법 보여주기
            }
        });
        
        // game 3
        //이미지 아이콘 설정
        ImageIcon game3Image = new ImageIcon("C:\\Users\\bread\\eclipse-workspace\\MINIGAME2\\src\\HALLIGALLI.jpg");
        JButton game3Button = new JButton(game3Image);
        
        game3Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Game 3 실행하는 코드 작성
                // Game.Java와의 연결을 구현해야 합니다.
                gameChoice = 3;
                showGameInstructions(3); // 게임 3 사용법 보여주기
            }
        });
        
     	// game 4
        //이미지 아이콘 설정
        ImageIcon game4Image = new ImageIcon("C:\\Users\\bread\\eclipse-workspace\\MINIGAME2\\src\\DIRECTIONGAME.jpg");
        JButton game4Button = new JButton(game4Image);
        game4Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Game 4 실행하는 코드 작성
                // Game.Java와의 연결을 구현해야 합니다.
                gameChoice = 4;
                showGameInstructions(4); // 게임 4 사용법 보여주기
            }
        });

        // panel 3: buttons
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
        
        panel3.add(startButton);
        panel3.add(exitButton);
        
        add(panel1);
        add(panel2);
        add(panel3);
        
        //showWelcomeMessage();

        // init instruction label
        instructionsLabel = new JLabel();
        instructionsLabel.setHorizontalAlignment(SwingConstants.CENTER);


        // frame design
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 600);
        setResizable(false);
        setVisible(true);

    }


    private void clearPanel1() {
        panel1.removeAll();
        panel1.revalidate();
        panel1.repaint();
    }



    //private void showWelcomeMessage() {
        /* 처음에는 JLabel 기능을 사용했다가, Text 분량 제한으로 textArea로 바꿨습니다. 필요하면 사용하세요.
        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome to MiniGame");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE); // 폰트 색상을 흰색(White)으로 설정
        panel1.add(welcomeLabel, BorderLayout.CENTER); // "Welcome!" 문구를 추가
        */
        // Welcome text area
    	/*
        welcomeTextArea = new JTextArea("Welcome!\nIf you click each game name in the list \nyou can read discription of that game.");
        welcomeTextArea.setEditable(false);
        welcomeTextArea.setLineWrap(true);
        welcomeTextArea.setWrapStyleWord(true);
        welcomeTextArea.setFont(welcomeTextArea.getFont().deriveFont(20f)); // 원하는 폰트 크기로 설정
        panel1.add(welcomeTextArea, BorderLayout.CENTER); // "Welcome!" 텍스트 영역을 추가
        */
        
    //}


    // Method to show game instructions in Panel 1
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
            case 4:
                instructions = "Game 4은 ~~~~ 입니다."; // 게임 4 사용법
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
                        break;
                    case 2:
                        panel1.add(gg2.panel);
                        break;
                    case 3:
                        panel1.add(gg3.panel);
                        break;
                    case 4:
                        panel1.add(gg4.panel);
                        break;
                    default:
                        // 게임 미 선택
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
}
