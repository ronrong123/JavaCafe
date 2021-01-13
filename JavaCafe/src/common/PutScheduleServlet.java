package common;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/insertSchedule")
public class PutScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PutScheduleServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//사용자가 넘겨준 값들을 변수에 담음
		request.setCharacterEncoding("utf-8");
		String title = request.getParameter("title");
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String url = request.getParameter("url");
		
		Schedule sch = new Schedule();
		sch.setTitle(title);
		sch.setStartDate(start);
		sch.setEndDate(end);
		sch.setUrl(url);
		
		EmpDAO dao = new EmpDAO();
		dao.insertSchedule(sch); //여기에 넣어줌 
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
