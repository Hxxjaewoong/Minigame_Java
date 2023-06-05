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

	private JPanel ipPanel; //ip주소라고 붙이기 위한 panel
	private JLabel ipLabel;
	final JTextField ipInputField; // ip 주소 입력하는 창

	private static String ip = null;
	
	public Start() {
		setLayout(new BorderLayout());
		
		// 게임 시작 버튼
		JButton startButton = new JButton("Start Game");
		
		
		ipPanel = new JPanel();
		ipLabel = new JLabel("ip주소");
		ipInputField = new JTextField(20);
		ipInputField.setText("localhost");
		ipPanel.add(ipLabel);
		ipPanel.add(ipInputField);
		
		
		r1 = new JRadioButton("1P (방장)");
		r2 = new JRadioButton("2P");
		ButtonGroup bg = new ButtonGroup();
		bg.add(r1);
		bg.add(r2);
		JPanel bgPan = new JPanel();
		bgPan.add(r1);
		bgPan.add(r2);
		
		add(bgPan,BorderLayout.NORTH);
		add(ipPanel,BorderLayout.CENTER);
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
	
	// start 버튼 누르면 선택한 플레이어에 따라 조건 설정 및 아이피 기록
	private class StartButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			start = true;
			ip = ipInputField.getText(); // 입력한 IP 주소 가져오기
			if (r1.isSelected()) {
				ser = true;
			}
			if (r2.isSelected()) {
				ser = false;
			}
		}
	}

}