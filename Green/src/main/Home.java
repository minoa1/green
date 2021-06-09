package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


class ImagePanel extends JPanel{
	private Image img;
	
	public ImagePanel(Image img) {
		this.img = img;
		setSize(new Dimension(img.getWidth(null),img.getHeight(null)));
		setPreferredSize(new Dimension(img.getWidth(null),img.getHeight(null)));
		setLayout(null);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
}

public class Home extends JFrame {

	public Main main;

	JPanel panel;

	JLabel lblTitle;

	JButton signUpBtn;
	JButton loginBtn;

	public Home() {
		
		JFrame  frame = new JFrame("home");
//		JFrame
		getContentPane().setLayout(null);
		setTitle("홈 화면");
		setSize(600, 600);
		setLocation(400, 150);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		JPanel
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 584, 499);
		
		ImagePanel panel = new ImagePanel(new ImageIcon("./image/image2.png").getImage());
		frame.add(panel);
		frame.pack();
		
		getContentPane().add(panel);

//		JLabel
		lblTitle = new JLabel("배달의 민족");
		lblTitle.setFont(new Font("���� ���", Font.PLAIN, 24));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(40, 75, 477, 79);
		panel.add(lblTitle);

		

//		회원가입 버튼
		signUpBtn = new JButton("회원가입");

		signUpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.signUp = new SignUp();
				main.signUp.setMain(main);

			}
		});

		signUpBtn.setBounds(59, 300, 200, 85);
		panel.add(signUpBtn);

//		로그인 버튼
		loginBtn = new JButton("로그인");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.login = new Login();
				main.login.setMain(main);
			}
		});

		loginBtn.setBounds(320, 300, 200, 85);
		panel.add(loginBtn);

		setVisible(true);
	}

	public void setMain(Main main) {
		this.main = main;

	}

		}


