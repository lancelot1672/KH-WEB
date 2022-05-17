package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/member/logout")
public class MemberLogoutServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);	//세션이 없을 시 false로 생성하지 않음.
    	if(session != null) {
			//세션 만료
    		session.invalidate();
    	}
    	
    	//2. redirect
    	response.sendRedirect(request.getContextPath() + "/");
    }
}
