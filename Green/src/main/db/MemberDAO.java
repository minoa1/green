package main.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.Main;

public class MemberDAO {

	public Main main;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "green";
	private String password = "green1234";

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

//	DB기본 설정.
	public MemberDAO() {
		try {
			Class.forName(driver);
			// driver 라는 클래스 파일이 도대체 인터페이스인지 뭔지 모르기 때문에
			// Class.를 통해 클래스 파일로 통일 시키는 것
//			System.out.println("드라이버 로딩 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

//	DB는 연결해서 처리(데이터를 넣는거나 바꾸는거나 지우는거나 뭐든)를 하고나서 다시 연결을 해제해줘야함.
//	그래서 DAO 클래스의 모든 메소드들은 항상 첫 부분에 getConnection()이 있고, 끝 부분 try catch 안에 rs, pstmt, conn 이런 애들을 .close()로 연결해제
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password);
//			System.out.println("접속 성공");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	members 테이블에 인자로 입력받은 Member를 넣는다.
	public void memberWrite(MemberDTO memberDTO) {
//		위에 말했듯이 DB에 연결하는 과정.
		getConnection();

//		실행 할 쿼리문 // ?는 밑에서 pstmt.setInt나 setString을 사용해서 인자로 전달.
		String sql = "insert into members values(member_id.nextval,?,?,?)";

		try {
			pstmt = conn.prepareStatement(sql);
//			1번째 물음표에 Email, 2번째는 Pw, 3번째는 Name 넣겠다는 말.
			pstmt.setString(1, memberDTO.getEmail());
			pstmt.setString(2, memberDTO.getPassword());
			pstmt.setString(3, memberDTO.getName());

//			쿼리문에 인자넣어서 실행
//			정상적으로 실행되면 su는 1
//			실패하면 su는 0
			pstmt.executeUpdate();

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
	}

	
//	Login.java에서 Login할 때 입력받은 Email을 인자로 받아서 해당 Email의 PW를 return 해주는 메소드
//	추가적으로 Main.java의 curMember 설정을 해준다.
	public String loginMember(String email, Main main) {
		int su = 0;
		String name = "";
		String pw = "";

		getConnection();

		String sql = "select * from members where email=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);

//			해당 이메일이 DB에 있다면 su는 1을 return
//			DB에 없으면 0 return
			su = pstmt.executeUpdate();

//			해당 email이 DB에 있는경우 그 email의 비밀번호를 string으로 리턴.
			if (su == 1) {
//				여기서 su와 rs는 둘다 똑같이 pstmt.executeQuery()를 쓰고 있는데,
//				su는 쿼리가 잘 실행 되었는지 0과 1로 체크를 하지만
//				rs(result Set)는 그 쿼리문의 결과를 저장하게 된다.
//				그래서 rs.next()를 한번 해주고 나서는 DB의 컬럼을 통해서 원하는 값을 추출 가능.
				rs = pstmt.executeQuery();
				rs.next();

//				main의 curMember를 설정하기 위한 작업
				this.main = main;

//				Member 저장하기 위해 비어있는 member 하나 생성
				MemberDTO loginMember = new MemberDTO();

//				방금 말했듯이 rs에서 값을 가져와서 방금 새로 만든 빈 member에 설정
				loginMember.setId(rs.getInt("id"));
				loginMember.setEmail(rs.getString("email"));
				loginMember.setName(rs.getString("name"));

//				main의 curMember에다가 현재 로그인 성공한 Member 정보를 저장.
//				이후에 get메소드를 통해서 간단하게 가져다 쓸 수 있음.
				main.setCurMember(loginMember);

//				공백이었던 pw 변수에 pw 저장
				pw = rs.getString("password");
			}
//			따로 else문을 만들지는 않았지만 어차피 위의 if문에 들어오지 못하니까
//			DB에 해당 Email이 없는 경우 pw는 공백일 것임.

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
		return pw;
	}

}
