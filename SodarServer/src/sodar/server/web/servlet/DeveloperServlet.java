package sodar.server.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sodar.server.web.model.DeveloperModel;

public class DeveloperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DeveloperModel developerModel;

	public DeveloperServlet() {
		super();
		developerModel = new DeveloperModel();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		request.getRequestDispatcher("/developer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// ���oRequest�Ѽ�
		String devpName = request.getParameter("devp_name");
		String email = request.getParameter("email");
		String appName = request.getParameter("app_name");
		String company = request.getParameter("company");
		String webPage = request.getParameter("web_page");
		String description = request.getParameter("description");
		
		// �I�sModel�|�h��Ʈw�s�W��T�óB�z�ͦ�API Key
		String APIKey = developerModel.registerDeveloper(devpName, email, appName, company, webPage, description);

		// ��^developer.jsp�e�{���G
		request.setAttribute("api_key", APIKey);
		request.getRequestDispatcher("/developer.jsp").forward(request, response);
	}
}
