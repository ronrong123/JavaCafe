package common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getSchedules")
public class GetScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetScheduleServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html charset=utg-8");
		EmpDAO dao = new EmpDAO();
		List<Schedule> list = dao.getScheduleList();
		PrintWriter out = response.getWriter();
		int cnt = 1;
		String json ="[";
		for(Schedule scd:list) {
			json += "{";
			json += "\"title\":\"" + scd.getTitle() + "\"" ;
			json += ",\"start\":\"" + scd.getStartDate() + "\"" ;
			json += ",\"end\":\"" + scd.getEndDate() + "\"" ;
			json += ",\"url\":\"" + scd.getUrl() + "\"" ;
			json += "}";
			if(list.size() != cnt++) {
				json += ", ";
			}
		}
		json += "]";
		out.print(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
