import java.awt.GridLayout;

import javax.swing.*;

class test extends JFrame{

	public test() {
		
		setTitle("GridLayout 배치관리자");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		JLabel l = new JLabel("Hey!");
		l.setBounds(130, 50 , 200, 20);
		//add(l);
		for(int i = 1; i < 10; i++) {
			JButton b = new JButton(Integer.toString(i));
			b.setBounds(i * 15, i * 15, 50, 20);
			add(b);
		}
		setSize(900, 500);
		setVisible(true);
		
		
	/*	JPanel jp = new JPanel();
		
		// 1. 컴포넌트를 만들어 보자.
		JButton jb1 = new JButton("1");
		JButton jb2 = new JButton("2");
		JButton jb3 = new JButton("3");
		JButton jb4 = new JButton("4");
		JButton jb5 = new JButton("5");
		JButton jb6 = new JButton("6");
		JButton jb7 = new JButton("7");
		JButton jb8 = new JButton("8");
		JButton jb9 = new JButton("9");
		JButton jb10 = new JButton("*");
		JButton jb11 = new JButton("0");
		JButton jb12 = new JButton("#");
		
		// 2. 컴포넌트를 컨테이너에 올려야 한다.
		jp.setLayout(new GridLayout(2, 2));
		
		jp.add(jb1); jp.add(jb2); jp.add(jb3);
		jp.add(jb4); jp.add(jb5); jp.add(jb6);
		jp.add(jb7); jp.add(jb8); jp.add(jb9);
		jp.add(jb10); jp.add(jb11); jp.add(jb12);
		
		// 3. 컨테이너를 프레임에 올려야 한다.
		add(jp);
		setBounds(500,500,300,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	*/	
	}
	
	

}