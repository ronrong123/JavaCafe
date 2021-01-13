package common;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getMembersByDept")
public class GetEmpByDept extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GetEmpByDept() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EmpDAO dao = new EmpDAO();
		Map<String, Integer> map = dao.getMemberByDept();
		//map은 바로 for을 사용하지못함
		Set<String> keySet = map.keySet(); //결과값을 가져와서 for문을 만들어야함 map은 Set에 담음
		int cnt = 1;
		String json = "[";		
		for(String key:keySet) {
//			System.out.println(key + "," + map.get(key));
			json += "{";
			json += "\"" + key + "\"" + ": " + map.get(key);
			json += "}";
			if(map.size() != cnt++) {
				json += ", ";
			}
		}
		json += "]";
		
		response.getWriter().append(json);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
