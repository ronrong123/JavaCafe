package jcafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
	Connection conn = null;
	public ProductDAO() {
		try {
			String user = "hr";
			String pw = "hr";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);

			System.out.println("Database에 연결되었습니다.\n");
			// try ~ catch : 예외처리
		} catch (ClassNotFoundException cnfe) {
			System.out.println("DB 드라이버 로딩 실패 :" + cnfe.toString());
		} catch (SQLException sqle) {
			System.out.println("DB 접속실패 : " + sqle.toString());
		} catch (Exception e) {
			System.out.println("Unkonwn error");
			e.printStackTrace();
		}
	}// ProductDAO(생성자부분)
	public List<ProductVO> getProductList(){
		String sql = "select * from product order by 1";
		List<ProductVO> list = new ArrayList<>();  //list에 조회한 결과를 담음
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);//sql을 실행할수있도록하는것
			ResultSet rs = psmt.executeQuery(); // sql을 쿼리한 결과가 담겨져있음 
			while(rs.next()) {
				//next(): rs의 데이터를 하나씩 리턴해줌 더이상 리턴할게 없으면 false를 리턴
				ProductVO vo = new ProductVO();
				vo.setItemNo(rs.getString("item_no"));//vo.setItemNo에 rs에서 가져온 값이 담겨져있음
				vo.setItem(rs.getString("item"));
				vo.setAlt(rs.getString("alt"));
				vo.setCategory(rs.getString("category"));
				vo.setContent(rs.getString("content"));
				vo.setImage(rs.getString("image"));
				vo.setLink(rs.getString("link"));
				vo.setLikeIt(rs.getInt("like_it"));
				vo.setPrice(rs.getInt("price"));
				
				list.add(vo);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 		
		return list;
	}
} 

