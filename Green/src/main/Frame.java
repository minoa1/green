package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.db.CartDAO;
import main.db.ProductDAO;

public class Frame extends JFrame implements ListSelectionListener {

	public Main main;

	private ProductDAO productDAO = new ProductDAO();
	private CartDAO cartDAO = new CartDAO();

	JPanel top;

	JPanel center;
	JPanel list;
	JPanel cart;

	JPanel productList;
	JPanel priceList;
	JPanel amountList;
	JPanel btnList;

	JPanel bottom;

	JLabel lblMemberName;
	JButton cartBtn;
	JButton logoutBtn;

	JButton category1;
	JButton category2;
	JButton category3;
	JButton category4;
	JButton category5;
	JButton category6;
	JButton category7;
	JButton category8;

	JButton store1;
	JButton store2;
	JButton store3;

	JList product_list;
	DefaultListModel product_model;

	JList price_list;
	DefaultListModel price_model;

	JList amount_list;
	DefaultListModel amount_model;

	JButton buyBtn;
	JButton delBtn;

	JButton backBtn;

	int checkProductIdx = 0;

	public Frame() {
//		JFrame
		getContentPane().setBackground(Color.PINK);
		setTitle("배달의 민족 홈");
		setSize(419, 420);
		setLocation(300, 150);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		JPanel
		top = new JPanel();
		top.setBackground(Color.CYAN);
		top.setBounds(0, 0, 400, 50);
		getContentPane().add(top);

		center = new JPanel();
		center.setBackground(Color.CYAN);
		center.setBounds(0, 50, 400, 279);
		center.setLayout(null);
		getContentPane().add(center);

		list = new JPanel();
		list.setBackground(Color.CYAN);
		list.setBounds(0, 50, 400, 279);
		getContentPane().add(list);
		list.setLayout(null);
		list.setVisible(false);

		cart = new JPanel();
		cart.setBackground(Color.CYAN);
		cart.setBounds(0, 50, 400, 279);
		getContentPane().add(cart);
		cart.setLayout(null);
		cart.setVisible(false);

		productList = new JPanel();
		productList.setBounds(0, 0, 133, 250);
		cart.add(productList);

		priceList = new JPanel();
		priceList.setBounds(133, 0, 133, 250);
		cart.add(priceList);

		amountList = new JPanel();
		amountList.setBounds(266, 0, 134, 250);
		cart.add(amountList);

		btnList = new JPanel();
		btnList.setBounds(0, 250, 400, 29);
		cart.add(btnList);

		bottom = new JPanel();
		bottom.setBackground(Color.CYAN);
		bottom.setBounds(0, 329, 400, 50);
		getContentPane().add(bottom);
		bottom.setVisible(false);

//		Top panel
		lblMemberName = new JLabel("Member Name");
		lblMemberName.setBounds(131, 0, 171, 15);

		top.add(lblMemberName);

//		장바구니 버튼 : center, list panel은 숨기고  cart, bottom panel은 보인다.
		cartBtn = new JButton("장바구니");
		cartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				center.setVisible(false);
				list.setVisible(false);
				cart.setVisible(true);
				bottom.setVisible(true);

			}
		});

		top.add(cartBtn);

//		로그아웃 버튼 : 현재 창을 닫고 home 화면 실행.
		logoutBtn = new JButton("로그아웃");
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				main.home = new Home();
				main.home.setMain(main);
			}
		});

		top.add(logoutBtn);

//		Center panel
		category1 = new JButton("한식");
		category1.setBounds(0, 67, 100, 100);

		category2 = new JButton("중식");
		category2.setBounds(102, 67, 100, 100);

		category3 = new JButton("치킨");
		category3.setBounds(202, 67, 100, 100);

		category4 = new JButton("피자");
		category4.setBounds(302, 67, 100, 100);

		category5 = new JButton("일식");
		category5.setBounds(0, 176, 100, 100);

		category6 = new JButton("족발보쌈");
		category6.setBounds(102, 177, 100, 100);

		category7 = new JButton("찜,탕");
		category7.setBounds(202, 176, 100, 100);

		category8 = new JButton("패스트푸드");
		category8.setBounds(302, 176, 100, 100);

//		8개의 버튼에 대해서 하나의 Listener로 처리.(간단하게 하기 위해서)
//		inner Class로 Listener 구현
		category1.addActionListener(new Listener());
		category2.addActionListener(new Listener());
		category3.addActionListener(new Listener());
		category4.addActionListener(new Listener());
		category5.addActionListener(new Listener());
		category6.addActionListener(new Listener());
		category7.addActionListener(new Listener());
		category8.addActionListener(new Listener());

		center.add(category1);
		center.add(category2);
		center.add(category3);
		center.add(category4);
		center.add(category5);
		center.add(category6);
		center.add(category7);
		center.add(category8);

//		List panel
		store1 = new JButton();
		store1.setBounds(0, 0, 200, 50);
		store1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				store의 Test(Product의 name)를 가져와서 DB거쳐서 p_id 가져온다.
				int p_id = productDAO.findIDByName(store1.getText());
				insertCart(p_id);
			}
		});

		store2 = new JButton();
		store2.setBounds(0, 50, 200, 50);
		store2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int p_id = productDAO.findIDByName(store2.getText());
				insertCart(p_id);
			}
		});

		store3 = new JButton();
		store3.setBounds(0, 100, 200, 50);
		store3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int p_id = productDAO.findIDByName(store3.getText());
				insertCart(p_id);
			}
		});

		list.add(store1);
		list.add(store2);
		list.add(store3);

//		Cart panel
//		product List
		product_model = new DefaultListModel();
		product_list = new JList(product_model);
//		JListSelectionListener추가
		product_list.addListSelectionListener(this);
		productList.add(product_list);

//		price List
		price_model = new DefaultListModel();
		price_list = new JList(price_model);
		priceList.add(price_list);

//		amout List
		amount_model = new DefaultListModel();
		amount_list = new JList(amount_model);
		priceList.add(amount_list);

//		구매 버튼
		buyBtn = new JButton("구매");
		buyBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				알림창에 나오는 버튼 2개
				Object[] options = { "주문", "취소" };
//				알림창에 나올 내용
				String content = "주문하시겟슴꽈?";

//				상품의 갯수만큼 for문을 돌면서 가격*양 의 합계 계산
				int sum = 0;
				for (int i = 0; i < product_model.size(); i++) {
					sum += (int) price_model.get(i) * (int) amount_model.get(i);
				}

//				String 끼리 더해서 알림창의 내용을 더할 수 있음.
				content = content + "\n" + Integer.toString(sum);

//				알림창 띄우기
				int n = JOptionPane.showOptionDialog(null, content, "주문하기", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

//				알림창에서 Yes를 눌렀을 경우
				if (n == 0) {
//					주문되었다고 알림창을 띄운다.
					JOptionPane.showMessageDialog(null, "주문되었습니다.");
//					DB에 현재 접속한 member의 모든 상품들을 지워버린다
					cartDAO.cartDeleteAllProduct(main.curMember.getId());

//					화면에 보이는 JList들의 model을 모두 지워버린다
					product_model.clear();
					price_model.clear();
					amount_model.clear();
				}
//				알림창에서 No를 눌렀을 경우
				else {

				}
			}
		});

		btnList.add(buyBtn);

//		삭제 버튼
		delBtn = new JButton("삭제");
		delBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				Product JList에서 클릭한 상품의 Name으로 Product_id를 가져온다.
				int p_id = productDAO.findIDByName((String) product_model.get(checkProductIdx));
//				Amount JList에 해당 상품의 amount를 curAmount에 저장.
				int curAmount = (int) amount_model.get(checkProductIdx);

//				amount가 1이라면
				if (curAmount == 1) {
//					해당 상품을 DB에서 지운다.
					cartDAO.cartDeleteProduct(main.curMember.getId(), p_id);

//					화면에 보이는 JList에서 해당 상품과 같은 index들의 product, price, amount만 지운다.
					product_model.remove(checkProductIdx);
					price_model.remove(checkProductIdx);
					amount_model.remove(checkProductIdx);
				}
//				amount가 1이상일 때
				else {
//					amount를 1깎아서, DB에 update 해준다.
					curAmount -= 1;
					cartDAO.cartUpdate(main.curMember.getId(), p_id, curAmount);

//					화면에 보이는 JList의 amount_model만 1깎은 것을 적용 (product, price는 그대로)
					amount_model.set(checkProductIdx, curAmount);
				}
			}
		});

		btnList.add(delBtn);

//		Bottom panel 
//		뒤로가기 버튼
		backBtn = new JButton("이전으로");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				center panel만 보이고 나머지 list, cart, bottom은 숨긴다.
				center.setVisible(true);
				list.setVisible(false);
				cart.setVisible(false);
				bottom.setVisible(false);
			}
		});

		bottom.add(backBtn);

		setVisible(true);
	}

//	이 화면에서는 setMain()이 단순히 main만 설정해주는 것이 아니고 현재 접속한 member의 장바구니를 DB에서 가져오는 과정 필요
	public void setMain(Main main) {
		this.main = main;
//		main에 저장했던 curMember의 name을 가져와서 인사.
		lblMemberName.setText(main.curMember.getName() + "님, 안녕하세요");

//		현재 접속 member가 장바구니에 넣어뒀던 product들의 id를 모아놓은 ArrayList를 DB에서 가져온다.
		ArrayList<Integer> p_ids = cartDAO.findProductsByMember(main.curMember.getId());

//		이 for문은 화면에 보이는 JList들을 채우는 과정
		for (int i = 0; i < p_ids.size(); i++) {
			String pName = productDAO.findNameById(p_ids.get(i));
			int pPrice = productDAO.findPriceById(p_ids.get(i));
			int pAmount = cartDAO.findAmountById(main.curMember.getId(), p_ids.get(i));

			product_model.addElement(pName);
			price_model.addElement(pPrice);
			amount_model.addElement(pAmount);

		}
	}

	public void insertCart(int p_id) {
//		알림창에 나오는 버튼 2개
		Object[] options = { "예", "아니오" };
//		알림창에 나올 내용
		String content = "장바구니에 넣으시겠습니까?";

//		알림창 띄우기
		int n = JOptionPane.showOptionDialog(null, content, "장바구니", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

//		알림창에서 Yes를 눌렀을 경우
		if (n == 0) {
			JOptionPane.showMessageDialog(null, "추가되었습니다.");
//			cart에 데이터를 넣기 전에 amount를 조회한다.
			int amount = cartDAO.findAmountById(main.curMember.getId(), p_id);

			String pName = productDAO.findNameById(p_id);
			int pPrice = productDAO.findPriceById(p_id);

//			amount가 0으로 해당 상품을 최초로 넣는 경우.
			if (amount < 1) {
//				amount는 1인 상태로 cart(장바구니)에 추가
				amount = 1;
				cartDAO.cartInsert(main.curMember.getId(), p_id, amount);

//				화면에 보이는 JList의 model들에도 추가
				product_model.addElement(pName);
				price_model.addElement(pPrice);
				amount_model.addElement(amount);

			}
//			amount가 1이상 이미 해당 상품을 장바구니에 넣은 적이 있는 경우.
			else {
//				DB에서 가져온 amount값에 1을 더하고 update해준다.
				amount++;
				cartDAO.cartUpdate(main.curMember.getId(), p_id, amount);

//				화면에 보이는 JList중 amount_model만 1을 더한 amount로 값을 수정.
				int idx = product_model.indexOf(pName);
				amount_model.set(idx, amount);
			}
		}
//		알림창에서 No를 눌렀을 경우
		else {

		}
	}

//	8개의 버튼에 대한 공통적인 Listener 클래스
	class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
//			store 버튼들을 보여주는 list panel과 이전으로 갈 수 있는 bottom panel만 보이고 나머지는 숨긴다.
			center.setVisible(false);
			list.setVisible(true);
			cart.setVisible(false);
			bottom.setVisible(true);

//			store.setText("A"+e.getActionCommand());
//			store2.setText("B"+e.getActionCommand());
//			store3.setText("C"+e.getActionCommand());

//			8개의 버튼 중 어느 버튼이 눌렸는지 체크해서 store 버튼들의 이름을 붙혀준다.
//			DB의 products 테이블의 name과 같아야함
			if (e.getSource() == category1) {
				store1.setText("김치찌개");
				store2.setText("된장찌개");
				store3.setText("부대찌개");
//				System.out.println("한식");
			} else if (e.getSource() == category2) {
				store1.setText("짜장면");
				store2.setText("짬뽕");
				store3.setText("볶음밥");
//				System.out.println("중식");
			} else if (e.getSource() == category3) {
				store1.setText("A치킨");
				store2.setText("B치킨");
				store3.setText("C치킨");
//				System.out.println("치킨");
			} else if (e.getSource() == category4) {
				store1.setText("A피자");
				store2.setText("B피자");
				store3.setText("C피자");
//				System.out.println("피자");
			} else if (e.getSource() == category5) {
				store1.setText("참치초밥");
				store2.setText("연어초밥");
				store3.setText("연어+참치");
//				System.out.println("일식");
			} else if (e.getSource() == category6) {
				store1.setText("족발");
				store2.setText("보쌈");
				store3.setText("족발+보쌈");
//				System.out.println("족발보쌈");
			} else if (e.getSource() == category7) {
				store1.setText("매운탕");
				store2.setText("김치찜");
				store3.setText("뼈찜");
//				System.out.println("찜,탕");
			} else {
				store1.setText("A세트");
				store2.setText("B세트");
				store3.setText("C세트");
//				System.out.println("패스트푸드");
			}
		}
	}

//	JList인 Product_list에서 어떤 상품이 눌렸는지 check하는 메소드
	@Override
	public void valueChanged(ListSelectionEvent e) {
//		상품리스트에서 상품을 클릭 한 경우 그 상품의 index를 checkProductIdx에 저장.
		if (product_list.getSelectedIndex() != -1) {
			checkProductIdx = product_list.getSelectedIndex();
		}

	}
}