import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Start extends JPanel {
	
	public boolean started = false;
    private static boolean ser;
	private JRadioButton r1;
	private JRadioButton r2;

	private JPanel ipPanel;
	private JLabel ipLabel;
	private JTextField ipInputField;

	private ButtonGroup bg;
	private JPanel bgPan;

	private static String ip = null;
	
	public Start() {
		setLayout(new BorderLayout());
		
		// 게임 시작 버튼
		JButton startButton = new JButton("Start Game");
		
		// ip 입력 패널
		ipPanel = new JPanel();
		ipLabel = new JLabel("IP 주소");
		ipInputField = new JTextField(20);
		ipPanel.add(ipLabel);
		ipPanel.add(ipInputField);
		
		// 선택 버튼 두개 생성
		r1 = new JRadioButton("1P (방장)");
		r2 = new JRadioButton("2P");
		// 선택 버튼을 그룹화 (하나만 선택되도록)
		bg = new ButtonGroup();
		bg.add(r1);
		bg.add(r2);
		// 1P 기본 선택
		r1.setSelected(true);
		ser = true;
		
		bgPan = new JPanel();
		bgPan.add(r1);
		bgPan.add(r2);
		
		add(bgPan, BorderLayout.NORTH);
		add(ipPanel, BorderLayout.CENTER);
		add(startButton, BorderLayout.SOUTH);
		
		startButton.addActionListener(new StartButtonListener());
		
	}
	
	// start 버튼 누르면 선택한 플레이어에 따라 조건 설정 및 IP 기록
	private class StartButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			started = true;
			ip = ipInputField.getText(); // 입력한 IP 주소 가져오기
			if (r1.isSelected()) {
				ser = true;
			}
			if (r2.isSelected()) {
				ser = false;
			}
		}
	}
	
	// getters
	public synchronized String getIp() {
		return ip;
	}
	public synchronized boolean getSer() {
		return ser;
	}
}