package member.controller;

import member.service.MemberService;
import member.service.MemberServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/member/memberDelete")
public class MemberDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemberService memberService = new MemberServiceImpl();

        // 1. 삭제할 아이디의 ID 값 받기
        String memberId = request.getParameter("memberId");
        
        // 2. 서비스에 삭제 요청
        int result = memberService.expire(memberId);
        System.out.println("result = " + result);

        // 3-1. 탈퇴 후 처리 - 세션 폐기, 쿠키 폐기
        Cookie cookie = new Cookie("saveId",memberId);
        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(0);    //응답을 받은 즉시 삭제
        response.addCookie(cookie);

        // 3-2. 세션의 로그인 정보 제거
        HttpSession session = request.getSession();
        Enumeration<String> names = session.getAttributeNames();
        while(names.hasMoreElements()){
            String name = names.nextElement();
            session.removeAttribute(name);
        }

        // 4. Home redirect
        response.sendRedirect(request.getContextPath() + "/");
    }
}
