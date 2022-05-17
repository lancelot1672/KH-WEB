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

@WebServlet("/member/checkIdDuplicate")
public class MembeerIdDuplicateServlet  extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemberService memberService = new MemberServiceImpl();

        // 1. 중복확인할 Id 가져오기
        String memberId = request.getParameter("memberId");
        System.out.println("memberId = " + memberId);
        // 2. 중복확인
        Member member = memberService.findMember(memberId);
        System.out.println("member : " + member);

        Boolean available = true;
        // 3. 결과 return
        if(member.getMemberId() != null){
            available = false;
        }
        // 4. view단 처리
        request.setAttribute("available", available);
        request
                .getRequestDispatcher("/WEB-INF/views/member/checkIdDuplicate.jsp")
                .forward(request, response);
    }
}
