package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bbs.BbsDAO;
import bbs.Board;

public class BbsAction implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BbsDAO bd = new BbsDAO();
		request.setAttribute("bbsList", bd.getList());
		BbsDAO bd1 = new BbsDAO(); //안녕하세요 일단
		request.setAttribute("bbsList2", bd1.getList2()); 
		return "bbs.jsp"; 
	}
}

	


