import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class mainGui extends JFrame {

    mainGui() {
        setTitle("Mini Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        int red = 102;
        int green = 102;
        int blue = 102;

        Color customColor = new Color(red, green, blue);

        // Game contents
        JPanel panel1 = new JPanel();
        panel1.setBackground(customColor);
        panel1.setBounds(20, 20, 350, 400);
        add(panel1);

        // Game list
        JPanel panel2 = new JPanel();
        panel2.setBackground(customColor);
        panel2.setBounds(400, 10, 200, 300);
        panel2.setLayout(new GridLayout(3, 1)); // 3개의 게임 목록을 세로로 배치하기 위해 GridLayout 설정
        add(panel2);

        JButton game1Button = new JButton("Game 1");
        game1Button.setPreferredSize(new Dimension(150, 30));
        game1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Game 1 실행하는 코드 작성
                // Game.Java와의 연결을 구현해야 합니다.
            }
        });
        panel2.add(game1Button);

        JButton game2Button = new JButton("Game 2");
        game2Button.setPreferredSize(new Dimension(150, 30));
        game2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Game 2 실행하는 코드 작성
                // Game.Java와의 연결을 구현해야 합니다.
            }
        });
        panel2.add(game2Button);

        JButton game3Button = new JButton("Game 3");
        game3Button.setPreferredSize(new Dimension(150, 30));
        game3Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Game 3 실행하는 코드 작성
                // Game.Java와의 연결을 구현해야 합니다.
            }
        });
        panel2.add(game3Button);

        JPanel panel3 = new JPanel();
        panel3.setBackground(customColor);
        panel3.setBounds(400, 380, 200, 50);
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        add(panel3);

        JButton startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(100, 30));
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Game 시작 버튼을 눌렀을 때 처리할 내용 작성
                // panel1과 연결된 Game.Java 실행 등의 코드를 작성해야 합니다.
            }
        });
        panel3.add(startButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(80, 30));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // 종료 버튼을 눌렀을 때 프로그램 종료
            }
        });
        panel3.add(exitButton);

        pack();

        setSize(640, 500);
        setResizable(false);
        setVisible(true);
    }
}
