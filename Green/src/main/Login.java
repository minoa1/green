package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.db.MemberDAO;




public class Login extends JFrame implements KeyListener {
	
	public Main main;
	public Frame testFrm;
	
	private MemberDAO memberDAO = new MemberDAO();
	
	JPanel panel;
	
	JLabel lblEmail;
	JLabel lblPw;
	
	JTextField txtEmail;
	JPasswordField txtPw;
	
	JButton signUpBtn;
	JButton cancleBtn;
	JButton loginBtn;
	

	public Login() {
		JFrame  frame1 = new JFrame("login");
//		JFrame
		setTitle("로그인 화면");
		setSize(288, 147);
		setResizable(false);
		setLocation(520, 380);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		JPanel
		panel = new JPanel();
		panel.setLayout(null);
		ImagePanel panel = new ImagePanel(new ImageIcon("./image/backgro.png").getImage());
		frame1.add(panel);
		frame1.pack();
		getContentPane().add(panel);
		
		
//		JLabel
		lblEmail = new JLabel("이메일");
		lblEmail.setHorizontalAlignment(JLabel.CENTER);
		lblEmail.setForeground(Color.white);
		lblEmail.setBounds(10, 10, 80, 25);
		panel.add(lblEmail);

		lblPw = new JLabel("비밀번호");
		lblPw.setHorizontalAlignment(JLabel.CENTER);
		lblPw.setForeground(Color.white);
		lblPw.setBounds(10, 40, 80, 25);
		panel.add(lblPw);

//		JTextField & JPasswordField
		txtEmail = new JTextField(20);
		txtEmail.setBounds(100, 10, 160, 25);
		txtEmail.addKeyListener(this);
		panel.add(txtEmail);

		txtPw = new JPasswordField(20);
		txtPw.setBounds(100, 40, 160, 25);
		txtPw.addKeyListener(this);
		panel.add(txtPw);
		
//		회원가입으로 가는 버튼 : 회원가입 화면을 켜주고 현재창인 로그인 화면은 닫는다.
		signUpBtn = new JButton("회원가입");
		signUpBtn.setBackground(new java.awt.Color(42,193,188));
		signUpBtn.setForeground(Color.white);
		signUpBtn.setBounds(1, 75, 90, 25);
		signUpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.signUp = new SignUp();
				main.signUp.setMain(main);
				dispose();	
			}	
		});
		
//		취소 버튼 : 현재창인 로그인 화면을 닫는다.
		cancleBtn = new JButton("취소");
		cancleBtn.setBackground(new java.awt.Color(42,193,188));
		cancleBtn.setForeground(Color.white);
		cancleBtn.setBounds(90, 75, 90, 25);
		cancleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

//		로그인 버튼
		loginBtn = new JButton("로그인");
		loginBtn.setBackground(new java.awt.Color(42,193,188));
		loginBtn.setForeground(Color.white);
		loginBtn.setBounds(180, 75, 90, 25);
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processLogin();
			}
		});

		
		panel.add(signUpBtn);
		panel.add(cancleBtn);
		panel.add(loginBtn);
		
		setVisible(true);
	}

	public void setMain(Main main) {
		this.main = main;
	}
	
	public void processLogin() {
//		입력받은 Email을 email 변수에 저장.
		String email = txtEmail.getText();
		
//		입력받은 email이 DB에 있는지 확인을 한다.
//		return 값 :
//			email이 있으면 해당 email의 비밀번호
//			email이 없으면 공백 ("") 
		
//		인자로 입력받은 email과 main을 보내는데
//		main은 왜 보내냐면, 이 프로젝트의 모든 클래스는 main을 공유하고 있다. (setMain()를 통해서 main을 새로 만들지 않고 계속 이어가는 방식으로.)
//		그래서 Main.java에 있는 curMember(현재 로그인 한 사람)를 사용하기 위해서 설정하기 위해 main을 보내주었다.
		String pw = memberDAO.loginMember(email, main);

//		입력받은 email이 DB에 없는 경우.
		if(pw.equals("")) {
			JOptionPane.showMessageDialog(null, "존재하지 않는 이메일입니다.");
		}
//		입력받은 email이 DB에 있는 경우. pw 변수에 해당 email의 비밀번호가 return값으로 받아서 저장 되어 있음.
		else {
//			입력받은 패스워드는 p
			String p = new String(txtPw.getPassword());

//			pw는 실제 비밀번호, p는 입력받은 비밀번호 pw 와 p 가 같으면 sucLogin().
			if(pw.equals(p)) {
				sucLogin();
			}
//			비밀번호가 틀렸다는 것은 Email은 존재하지만 비밀번호가 틀렸다는 것.
			else {
				JOptionPane.showMessageDialog(null, "잘못된 비밀번호입니다.");
			}
		}
	}
	
//	Success login : testFrm 화면을 켜주고 현재창인 login창과 뒤에 깔려있는 home화면을 둘다 꺼준다.
	public void sucLogin() {
		main.testFrm = new Frame();
		main.testFrm.setMain(main);
		
		dispose();
		main.home.dispose();
	}
	


//	SignUp.java와 같이 Enter key를 사용하기 위해 override한 것.
	@Override 
	public void keyPressed(KeyEvent e) { 
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			processLogin();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub	
	}
	
}