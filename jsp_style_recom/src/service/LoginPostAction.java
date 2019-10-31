package service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserDAO;

public class LoginPostAction implements CommandProcess{

	public String requestPro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDAO userDAO = new UserDAO();
		
		System.out.println("mem_id: " + request.getParameter("mem_id"));
		
		int result = userDAO.login((String) request.getParameter("mem_id"), (String) request.getParameter("mem_pw")); 
		 /* login ���������� ID, Password �Էµ� ������ �Ѿ�ͼ� login�Լ��� �־ ����
		 -2(DB����), -1(���̵� ����), 0(�α��� ����), 1(�α��� ����)�� ����*/
		
		System.out.println(result);
		 
		 if(result == 1)
		 {
			 HttpSession httpSession = request.getSession(true);
			 httpSession.setAttribute("mem_id", request.getParameter("mem_id"));
		 }
		 
		 else if(result == 0)
		 {
			 
		 }
		 
		 else if(result == -1)
		 {

		 }
		 
		 else if(result == -2)
		 {

		 }
		return "/main.jsp";
	}

}
