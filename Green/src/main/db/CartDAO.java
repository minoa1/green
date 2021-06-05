package main.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Main;

public class CartDAO {

	public Main main;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "green";
	private String password = "green1234";

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

//	DB기본 설정 (DAO 공통)
	public CartDAO() {
		try {
			Class.forName(driver); // 생성
			// driver 라는 클래스 파일이 도대체 인터페이스인지 뭔지 모르기 때문에
			// Class.를 통해 클래스 파일로 통일 시키는 것
//			System.out.println("드라이버 로딩 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

//	DB연결 (DAO 공통)
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password); // 접속시도
//			System.out.println("접속 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
//	cart 테이블에 현재 member_id, product_id, amount를 받아서 입력.
	public int cartInsert(int m_id, int p_id, int amount) {
		int su = 0;

		getConnection();

		String sql = "insert into cart values(cart_id.nextval,?,?,?)";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, m_id);
			pstmt.setInt(2, p_id);
			pstmt.setInt(3, amount);

			su = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return su;
	}

	
//	cart 테이블에 이미 저장 된 것들의 amount를 수정해주기 위한 메소드.
	public int cartUpdate(int m_id, int p_id, int amount) {
		int su = 0;

		getConnection();

//		where 조건문이 and로 2개 : m_id와 p_id 둘다라는 뜻
		String sql = "update cart set amount = ? where m_id = ? and p_id = ?";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, amount);
			pstmt.setInt(2, m_id);
			pstmt.setInt(3, p_id);

			su = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return su;
	}

	
//	cart 테이블에 product 하나를 지우는 메소드. (장바구니의 삭제)
	public int cartDeleteProduct(int m_id, int p_id) {
		int su = 0;
		
		getConnection();
		
//		마찬가지로 where 조건문 2개
		String sql = "delete from cart where m_id = ? and p_id = ?";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, m_id);
			pstmt.setInt(2, p_id);

			su = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return su;
	}

//	cart 테이블에 현재 접속한 member의 모든 product들을 지우는 메소드. (장바구니의 구매)
	public int cartDeleteAllProduct(int m_id) {
		int su = 0;
		
		getConnection();
		
		String sql = "delete from cart where m_id = ?";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, m_id);

			su = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return su;
	}

	
//	Member Id와 Product Id를 이용해서 접속한 member의 해당 product의 amount를 return.
	public int findAmountById(int m_id, int p_id) {
		int su = 0;
		int amount = 0;
		
		getConnection();

		String sql = "select * from cart where m_id = ? and p_id = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, m_id);
			pstmt.setInt(2, p_id);

			su = pstmt.executeUpdate();
//			처음 장바구니에 넣는 상품일 경우 su가 0이기 때문에 rs를 쓰면 오류발생.
			if (su == 1) {
				rs = pstmt.executeQuery();
				rs.next();
				amount = rs.getInt("amount");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

//		언제 findAmountById메소드가 실행되냐면 장바구니에 담을 때
//		장바구니에 데이터를 넣기 전에 amount를 조회하기 위해 이 메소드를 실행하는데
//		최초로 들어오게 될 경우 amount는 0이고
//		이미 넣었던 경우 현재 amount를 return 위에 if문 처음 장바구니에 넣는 부분을 확인.
		return amount;
	}

	
//	현재 로그인 한 Member의 모든 product들의 id들을 arrayList로 묶어서 return
	public ArrayList findProductsByMember(int m_id) {
		
//		Integer값을 갖는 ArrayList 생성.
		ArrayList<Integer> p_ids = new ArrayList();
		
		getConnection();

		String sql = "select * from cart where m_id = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, m_id);

			rs = pstmt.executeQuery();

//			쿼리문의 결과를 모두 돌면서 p_id(product_id)를 ArrayList에 넣는다.
			while (rs.next()) {
				p_ids.add(rs.getInt("p_id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return p_ids;
	}

}
