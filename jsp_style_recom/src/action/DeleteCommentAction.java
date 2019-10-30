package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bbs.BbsDAO;

public class DeleteCommentAction implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int cm_id = Integer.parseInt(request.getParameter("cm_id"));
		
		BbsDAO bbsDAO = new BbsDAO();
		bbsDAO.deleteComment(cm_id);
		return "";
	}

}
