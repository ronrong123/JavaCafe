package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpDAO {
	private Connection conn;

	public EmpDAO() {
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
	} // end of 생성자.
	
	public void insertSchedule(Schedule sch) {
		String sql = "insert into calendar values(?,?,?,?)";
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, sch.getTitle());
			psmt.setString(2, sch.getStartDate());
			psmt.setString(3, sch.getEndDate());
			psmt.setString(4, sch.getUrl());
			int r =psmt.executeUpdate();
			System.out.println(r + "건 입력되었습니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public List<Schedule> getScheduleList() {
		String sql = "select * from calendar";
		List<Schedule> list = new ArrayList<>();
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()) {
				Schedule sd = new Schedule();
				sd.setTitle(rs.getString("title"));
				sd.setStartDate(rs.getString("start_date"));
				sd.setEndDate(rs.getString("end_date"));
				sd.setUrl(rs.getString("url"));
				list.add(sd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	public Map<String, Integer> getMemberByDept(){
		String sql ="select department_name, count(*) "
				+ "from employees e, departments d "
				+ "where e.department_id = d.department_id "
				+ "group by department_name";
		Map<String, Integer> map = new HashMap<>();
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			while(rs.next()) {
				map.put(rs.getString(1), rs.getInt(2));
				// map: key, value값을 지정하는것
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	public EmployeeVO updateEmp(EmployeeVO vo) {
		String sql ="update emp_temp set first_name=?, email=?,phone_number=?, hire_date=sysdate, job_id=?  where employee_id=?";
		int r=0;
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setString(1, vo.getFirstName());
			psmt.setString(2, vo.getEmail());
			psmt.setString(3, vo.getPhoneNumber());
			psmt.setString(4, vo.getJobId());
			psmt.setInt(5, vo.getEmployeeId());

			r = psmt.executeUpdate();					
			System.out.println(r+ "건 수정됨.");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vo;
	}
	public EmployeeVO insertEmp(EmployeeVO vo) {
		String sql1 = "select employees_seq.nextval from dual";
		String sql2 = "select * from emp_temp where employee_id = ?";
		String sql = "insert into emp_temp (employee_id, first_name, last_name, email,phone_number,hire_date, job_id, salary)"
				+ "values(?, ?, ?, ?, ?, sysdate, ?, 6000)";
		int r=0;
		String newSeq = null;
		EmployeeVO newVo = new EmployeeVO();
		try {
			PreparedStatement psmt = conn.prepareStatement(sql1);
			ResultSet rs = psmt.executeQuery(); //쿼리결과를 가지고옴
			if(rs.next()) {
				newSeq = rs.getString(1); //
			}			
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, newSeq);
			psmt.setString(2, vo.getFirstName());
			psmt.setString(3, vo.getLastName());
			psmt.setString(4, vo.getEmail());
			psmt.setString(5, vo.getPhoneNumber());
			psmt.setString(6, vo.getJobId());
			r = psmt.executeUpdate();
			//executeUpdate: 이걸해야 업데이트됨
			System.out.println(r+ "건 입력됨.");
			
			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, newSeq);
			rs = psmt.executeQuery();
			if(rs.next()) {
				newVo.setEmail(rs.getString("email"));
				newVo.setEmployeeId(rs.getInt("employee_id"));
				newVo.setFirstName(rs.getString("first_name"));
				newVo.setHireDate(rs.getString("hire_date"));
				newVo.setJobId(rs.getString("job_id"));
				newVo.setLastName(rs.getString("last_name"));
				newVo.setPhoneNumber(rs.getString("phone_number"));
				newVo.setSalary(rs.getInt("salary"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newVo;
	}

	public boolean deleteEmp(EmployeeVO vo) {
		String sql = "delete from emp_temp where employee_id = ?";
		int r = 0;
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, vo.getEmployeeId());

			r = psmt.executeUpdate();
			System.out.println(r + "건 삭제됨.");
			// 업데이트 딜리트 등은 executeUpdate로 함
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r == 1 ? true : false;
	}

	public List<EmployeeVO> getEmpList() {
		String sql = "select * from emp_temp order by 1 desc";
		List<EmployeeVO> list = new ArrayList<>();
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				EmployeeVO vo = new EmployeeVO();
				vo.setEmployeeId(rs.getInt("employee_id"));
				vo.setFirstName(rs.getString("first_name"));
				vo.setLastName(rs.getString("last_name"));
				vo.setEmail(rs.getString("email"));
				vo.setPhoneNumber(rs.getString("phone_number"));
				vo.setHireDate(rs.getString("hire_date"));
				vo.setJobId(rs.getString("job_id"));
				vo.setSalary(rs.getInt("salary"));

				list.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	} // end of getEmpList()
}
