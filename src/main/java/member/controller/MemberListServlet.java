package member.controller;

import member.dto.Member;
import member.service.MemberService;
import member.service.MemberServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class MemberViewServlet
 */
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   List<Member> list = memberService.findAllMember();
	   System.out.println("list" + list);

	   //2. view 처리
		request.setAttribute("list",list);
		request.getRequestDispatcher("/WEB-INF/views/admin/memberList.jsp")
				.forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
