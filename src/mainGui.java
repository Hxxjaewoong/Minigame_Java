import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import gui.GuiGame1;
import gui.GuiGame2;

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
    
    // color
    public Color panelBackgroundColor = new Color(102, 102, 102);

    mainGui() {
        setTitle("Mini Game");


        // panel1: Game contents
        panel1 = new JPanel();
        panel1.setBackground(panelBackgroundColor);
        panel1.setBounds(20, 20, 460, 520);
        panel1.setLayout(new BorderLayout());
        
        gg1 = new GuiGame1();
        gg2 = new GuiGame2();
        gg1.panel.setVisible(false);
        gg2.panel.setVisible(false);




        // panel2: Game list
        panel2 = new JPanel();
        panel2.setBackground(panelBackgroundColor);
        panel2.setBounds(500, 20, 180, 450);
        panel2.setLayout(new GridLayout(3, 1)); // 3개의 게임 목록을 세로로 배치하기 위해 GridLayout 설정
        
        // game 1
        JButton game1Button = new JButton("Game 1");
        game1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Game 1 실행하는 코드 작성
                // Game.Java와의 연결을 구현해야 합니다.
                gameChoice = 1;
            }
        });
        
        // game 2
        JButton game2Button = new JButton("Game 2");
        game2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Game 2 실행하는 코드 작성
                // Game.Java와의 연결을 구현해야 합니다.
                gameChoice = 2;
            }
        });
        
        // game 3
        JButton game3Button = new JButton("Game 3");
        game3Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Game 3 실행하는 코드 작성
                // Game.Java와의 연결을 구현해야 합니다.
                gameChoice = 3;
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

        panel1.add(gg1.panel);
        panel1.add(gg2.panel);


        panel2.add(game1Button);
        panel2.add(game2Button);
        panel2.add(game3Button);
        
        panel3.add(startButton);
        panel3.add(exitButton);
        
        add(panel1);
        add(panel2);
        add(panel3);
        
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 600);
        setResizable(false);
        setVisible(true);
    }



    private class ButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String command = e.getActionCommand();

            // Game 시작 버튼을 눌렀을 때 처리할 내용 작성
            // panel1과 연결된 Game.Java 실행 등의 코드를 작성해야 합니다.
            if (command.equals("Start Game")) {
                gg1.panel.setVisible(false);
                gg2.panel.setVisible(false);

                switch (gameChoice) {
                    case 1:
                        System.out.println(1);
                        gg1.panel.setVisible(true);
                        break;
                    case 2:
                        System.out.println(2);
                        gg2.panel.setVisible(true);
                        break;
                    case 3:
                        System.out.println(3);
                        break;
                
                }
            }

            else if (command.equals("Exit")) {
                System.out.println("Exit");
                System.exit(0); // 종료 버튼을 눌렀을 때 프로그램 종료
            }


		}
    }
}
