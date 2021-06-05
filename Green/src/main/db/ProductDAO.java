package main.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.Main;

public class ProductDAO {

	public Main main;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "green";
	private String password = "green1234";

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

//	DB기본 설정 (DAO 공통)
	public ProductDAO() {
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

	
//	Product Name을 이용해서 Product ID를 return.
	public int findIDByName(String name) {
		int id = 0;

		getConnection();
		
		String sql = "select * from products where name=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);

//			쿼리문의 결과인 Product의 id컬럼 값을 가져와서 id에 저장.
			rs = pstmt.executeQuery();
			rs.next();
			id = rs.getInt("id");


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
		return id;
	}

	
//	Product Id를 이용해서 Product Name을 return.
	public String findNameById(int id) {
		String name = "";
		
		getConnection();
		
		String sql = "select * from products where id=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();
			rs.next();
			name = rs.getString("name");


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
		return name;
	}
	
	
//	Product Id를 이용해서 Product Price를 return.
	public int findPriceById(int id) {
		int price = 0;
		
		getConnection();
		
		String sql = "select * from products where id=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();
			rs.next();
			price = rs.getInt("price");


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
		return price;
	}

}
