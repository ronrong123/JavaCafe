package jcafe;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getProdList")
public class GetProdListSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public GetProdListSevlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// json 파일 생성.
		response.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html charset=utg-8");
		ProductDAO dao =new ProductDAO();
		List<ProductVO> list = dao.getProductList();
		String json = "[";
		int cnt = 1;
		for(ProductVO vo:list) {
			json += "{";
			json += "\"item_no\":\"" + vo.getItemNo() + "\"";
			json += ",\"item\":\"" + vo.getItem() + "\"";
			json += ",\"category\":\"" + vo.getCategory() + "\"";
			json += ",\"price\":\"" + vo.getPrice() + "\"";
			json += ",\"link\":\"" + vo.getLink() + "\"";
			json += ",\"content\":\"" + vo.getContent() + "\"";
			json += ",\"like_it\":\"" + vo.getLikeIt() + "\"";
			json += ",\"alt\":\"" + vo.getAlt() + "\"";
			json += ",\"image\":\"" + vo.getImage() + "\"";
			json += "}";
			if(list.size() != cnt++) {
				json += ", ";
			}
		}
		json += "]";		
		
		response.getWriter().append(json);
		//json 응답처리?
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
