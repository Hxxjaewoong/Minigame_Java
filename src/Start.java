import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Start extends JPanel {
	
	String Side;
	String Host;
	boolean start = false;
    private static boolean ser = false;
	private JRadioButton r1;
	private JRadioButton r2;
	final JTextField text;
	private static String ip = null;
	
	public Start() {
		setLayout(new BorderLayout());
		
		// 게임 시작 버튼
		JButton startButton = new JButton("Start Game");
		
		// ip 주소 입력하는 창
		text = new JTextField(20);
		JLabel ipad = new JLabel("ip주소");
		JPanel ipadpan = new JPanel();//ip주소라고 붙이기 위한 panel
		ipadpan.add(ipad);
		ipadpan.add(text);
		
		
		r1 = new JRadioButton("1P (방장)");
		r2 = new JRadioButton("2P");
		ButtonGroup bg = new ButtonGroup();
		bg.add(r1);
		bg.add(r2);
		JPanel bgPan = new JPanel();
		bgPan.add(r1);
		bgPan.add(r2);
		
		add(bgPan,BorderLayout.NORTH);
		add(ipadpan,BorderLayout.CENTER);
		add(startButton,BorderLayout.SOUTH);
		
		StartButtonListener listener = new StartButtonListener();
		startButton.addActionListener(listener);
		
	}

	public synchronized String getIp() {
		return ip;
	}
	public synchronized boolean getSer() {
		return ser;
	}
	


	
	private class StartButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			start = true;
			ip = text.getText(); // 입력한 IP 주소 가져오기
			if (r1.isSelected()) {
				ser = true;
			}
			if (r2.isSelected()) {
				ser = false;
			}
		}
	}


}
