package gui;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

import games.Game4;

public class GuiGame4 extends Game4 implements GamePanel{
	public JPanel panel;
	public JPanel panelInfo;
    public JPanel panelPlay;
	public JTextArea infoText;
	public JLabel scoreLabel;

    public int count = 3; //시작 전 3초 세고 시작함
    public int countGame = 1;
    
    
	public boolean isUpKeyPressed;
    public boolean isDownKeyPressed;
    public boolean isLeftKeyPressed;
    public boolean isRightKeyPressed;
    
	public GuiGame4()
	{
		isUpKeyPressed = false;
		isDownKeyPressed = false;
		isLeftKeyPressed = false;
		isRightKeyPressed = false;
		panel = new JPanel();
		panel.setBounds(gameArea);
		panel.setFocusable(true); // 패널에 포커스를 설정

		panelInfo = new JPanel();
		panelInfo.setPreferredSize(gameInfoArea);
		panelInfo.setBackground(Color.yellow);

		scoreLabel = new JLabel("Score: 0");
        scoreLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
        
		infoText = new JTextArea("Contains info of game2");
		panelInfo.add(infoText);

        panelPlay = new JPanel();
        panelPlay.setPreferredSize(new Dimension(460, 430));
        panelPlay.setBackground(Color.white);
        panelPlay.setLayout(new GridLayout(2,1,	 10, 10));
        
        
        JTextArea TextQ = new JTextArea("\n        It will start soon");
        TextQ.setEditable(false);
        TextQ.setFont(TextQ.getFont().deriveFont(40f)); // 원하는 폰트 크기로 설정
        
        JPanel keyboardArea = new JPanel();
        //keyboardArea.setPreferredSize(new Dimension(230, 280));
        keyboardArea.setBackground(Color.white);
        keyboardArea.setLayout(new GridLayout(2, 3, 10, 10));

        JButton upButton = new JButton("▴");
        upButton.setFont(upButton.getFont().deriveFont(40f));
        upButton.setBackground(Color.white);
        
        JButton downButton = new JButton("▾");
        downButton.setFont(downButton.getFont().deriveFont(40f));
        downButton.setBackground(Color.white);
        
        JButton leftButton = new JButton("◂");
        leftButton.setFont(leftButton.getFont().deriveFont(40f));
        leftButton.setBackground(Color.white);
        
        JButton rightButton = new JButton("▸");
        rightButton.setFont(rightButton.getFont().deriveFont(40f));
        rightButton.setBackground(Color.white);	

        // 키보드 버튼을 패널에 추가
        keyboardArea.add(new JPanel()); // 빈 패널
        keyboardArea.add(upButton);
        keyboardArea.add(new JPanel()); // 빈 패널
        keyboardArea.add(leftButton);
        keyboardArea.add(downButton);
        keyboardArea.add(rightButton);
        
        //upButton이 눌렸을 때 동작
        
        upButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try {
					isUpKeyPressed = true;
					upButton.setBackground(Color.RED);
					Thread.sleep(500);
					upButton.setBackground(Color.WHITE);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
        	
        });
        
      //DownButton이 눌렸을 때 동작
        downButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					isDownKeyPressed = true;
					downButton.setBackground(Color.RED);
					Thread.sleep(500);
					downButton.setBackground(Color.WHITE);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        	
        });
        
      //LeftButton이 눌렸을 때 동작
        leftButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					isLeftKeyPressed = true;
					leftButton.setBackground(Color.RED);
					Thread.sleep(500);
					leftButton.setBackground(Color.WHITE);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        	
        });
        
      //RightButton이 눌렸을 때 동작
        rightButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					isRightKeyPressed = true;
					rightButton.setBackground(Color.RED);
					Thread.sleep(500);
					rightButton.setBackground(Color.WHITE);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
        	
        });

        
        panel.add(panelInfo);
        panel.add(scoreLabel); // 점수 레이블 추가
		panel.add(panelPlay);
		panelPlay.add(TextQ);
		panelPlay.add(keyboardArea);
		
        Random random = new Random();
        SwingWorker<Void , String> worker = new SwingWorker<Void, String>(){
        	
        	@Override
        	protected Void doInBackground() throws Exception {
        		for(int i = 1; i <= 20; ++i)
        		{
        			int randomInt = random.nextInt(12);
        			
        			publish(ment[randomInt]);
        			Thread.sleep(2000);
        			if(answer[randomInt] == 0 && isRightKeyPressed == true)
        			{
        				scoring(true);
        				scoreLabel.setText("Score: "+Integer.toString(score));
        			}
        			else if(answer[randomInt] == 1 && isLeftKeyPressed == true)
        			{
        				scoring(true);
        				scoreLabel.setText("Score: "+Integer.toString(score));
        			}
        			else if(answer[randomInt] == 2 && isDownKeyPressed == true)
        			{
        				scoring(true);
        				scoreLabel.setText("Score: "+Integer.toString(score));
        			}
        			else if(answer[randomInt] == 3 && isUpKeyPressed == true)
        			{
        				scoring(true);
        				scoreLabel.setText("Score: "+Integer.toString(score));
        			}
        			else
        			{
        				scoring(false);
        				scoreLabel.setText("Score: "+Integer.toString(score));
        			}
        			isRightKeyPressed = false;
        			isLeftKeyPressed = false;
        			isDownKeyPressed = false;
        			isUpKeyPressed = false;
        		}
        		return null;
        	}
        	@Override
        	protected void process(java.util.List<String> chunks) {
        		for(String chunk: chunks)
        		{
        			//setText
        			TextQ.setText(chunk);
        			
        		}
        	}
        	@Override
        	protected void done() {
        		
        		TextQ.setText("Final Score: "+score+"\nGAMEOVER");
        		
        	}
        	
        };
        

        Timer readyTimer = new Timer();
        TimerTask readyTimer1 = new TimerTask() {
        
	        public void run() {
	        	if(count > 0)
	        	{
	        		// 카운트다운 값 감소
	                TextQ.setText("                   "+ Integer.toString(count));
	                count--;// 레이블에 숫자 표시
	        	}
	        	else
	        	{
	        		TextQ.setText("                  Go!"); // 카운트다운 종료 후 메시지 표시
	        		readyTimer.cancel();
	        		worker.execute();
	        		
	        	}
	        }
        };
        readyTimer.schedule(readyTimer1, 3000, 1000);

    }
       

	
}
