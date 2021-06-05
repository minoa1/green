package main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.db.MemberDAO;
import main.db.MemberDTO;

public class SignUp extends JFrame implements KeyListener {
	
	public Main main;
	
	private MemberDAO memberDAO = new MemberDAO();
	
	JPanel panel;
	
	JLabel lblEmail;
	JLabel lblPw;
	JLabel lblName;
	
	JTextField txtEmail;
	JPasswordField txtPw;
	JTextField txtName;
	
	JButton signUpBtn;
	JButton cancleBtn;
	
	public SignUp() {
//		JFrame
		setTitle("회원가입");
		setSize(300, 150);
		setLocation(400,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		JPanel
		panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		getContentPane().add(panel);

//		JLabel
		lblEmail = new JLabel("Email");
		lblPw = new JLabel("PW");
		lblName = new JLabel("NAME");

//		JTextField & JPasswordField
		txtEmail = new JTextField(10);
		txtPw = new JPasswordField();
		txtName = new JTextField(10);
		
//		Enter key를 통한 회원가입을 위해 Key Listener 등록.
		txtEmail.addKeyListener(this);
		txtPw.addKeyListener(this);
		txtName.addKeyListener(this);
		
//		회원가입 버튼
		signUpBtn = new JButton("회원가입");
		signUpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signUp();
			}
		});
		
//		취소 버튼 : 회원가입 창을 닫는다.
		cancleBtn = new JButton("취소");
		cancleBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

//		JPanel에 컴포넌트 추가. (컴포넌트 : 구성요소)
		panel.add(lblEmail);
		panel.add(txtEmail);
		panel.add(lblPw);
		panel.add(txtPw);
		panel.add(lblName);
		panel.add(txtName);
		panel.add(signUpBtn);
		panel.add(cancleBtn);

		setVisible(true);
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public void signUp() {
//		Email, PW, Name TextField중에 하나라도 비워져 있다면 다채우라는 알림.
//		JPasswordField에서 getText()를 하면 밑에처럼 회색 줄이 그어지지만 비추천한다는 뜻이지 작동은 잘 됨.
		if(txtEmail.getText().equals("") || txtPw.getText().equals("") || txtName.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "빈칸이 존재합니다.");
		}
		else {
//			Member를 담을 DTO를 하나만들고
			MemberDTO member = new MemberDTO();
			
//			만든 member의 Email, PW, Name을 TextField에 입력받은 값으로 설정.
//			Password부분은 위에 if문처럼 암호를 getText()로 가져오는 방법을 추천하지 않는다고 해서 이렇게도 해봤다.
			member.setEmail(txtEmail.getText());
			String p = new String(txtPw.getPassword());
			member.setPassword(p);
			member.setName(txtName.getText());
			
//			이후에 MemberDAO의 memberWrite()를 통해서 DB에 저장을 한다.
			memberDAO.memberWrite(member);

			JOptionPane.showMessageDialog(null, "회원가입 되었습니다.");
			dispose();
		}
	}
	
//	key~~()는 Enter key 사용하기 위해 KeyListener 인터페이스를 구현하려면 이 메소드들을 오버라이드 해야하는데
//	keyPressed()만 필요해서 구현했고 밑에 2개는 비워놨음.
	@Override 
	public void keyPressed(KeyEvent e) { 
//		EnterKey가 눌렸을 때 작동하는 부분.
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			signUp();
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
