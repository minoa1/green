package main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.db.MemberDAO;
import main.db.MemberDTO;


public class Main extends JFrame{
	

	public Home home;
	public Login login;
	public SignUp signUp;
	public Frame testFrm;
	public MemberDAO memberDAO;
	
	public MemberDTO curMember;
	
	
	

	public static void main(String[] args) {

		Main main = new Main();
		
		main.home = new Home();
		main.home.setMain(main);		
		
	}
	
	
//	다른 클래스에서 현재 로그인 한 Member를 가져오기 위해서 get,set method 생성
	public MemberDTO getCurMember() {
		return curMember;
	}
	
	public void setCurMember(MemberDTO member) {
		this.curMember = member;
	}


}