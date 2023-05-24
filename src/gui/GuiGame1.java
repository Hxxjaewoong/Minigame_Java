package gui;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import games.Game1;

public class GuiGame1 extends Game1 implements GamePanel {

	public JPanel panel;
	public JPanel panelInfo;
    public JPanel panelPlay;
    public JButton buttons[][] = new JButton[MAP_SIZE][MAP_SIZE];
	public JTextArea infoText;
	
	public GuiGame1() {
		playing = true;

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
        		
        		// design and position of button
        		button.setBackground(Color.pink);
        		button.setBorder(BorderFactory.createLineBorder(Color.red, 2));
        		
        		
                // event on button
                button.setActionCommand(r+","+c);
                button.addActionListener(new ButtonClickListener());
        		
        		
        		buttons[r][c] = button;
        		panelPlay.add(button);
        	}
        }



		panel.add(panelInfo);
		panel.add(panelPlay);
	}
	
	
	
	
    private class ButtonClickListener implements ActionListener {
    	
    	// 각 버튼의 클릭에 대해 
		@Override
		public void actionPerformed(ActionEvent e) {
			// get the position of clicked button
			String rc[] = e.getActionCommand().split(",");
			int r = Integer.parseInt(rc[0]);
			int c = Integer.parseInt(rc[1]);
			

			int result = checkPosition(r, c);
			if (result == -1) {
				// do nothing
				System.out.println("이미 열린 버튼입니다.");
			} else if (result == 0) {
				// target을 찾았다는 메시지와 남은 target의 개수 표시
				System.out.println("Target Found! " + foundCount);
				buttons[r][c].setText("축");
				buttons[r][c].setBackground(Color.gray);

				playing = !isFinished();
			} else {
				// 가장 가까운 target과의 거리 표시
				System.out.println(r+"행 "+c+"열 - " + "가장 가까운 거리: " + result);
				buttons[r][c].setText("꽝");
				buttons[r][c].setBackground(Color.white);
			}
			

		}
    }
}
