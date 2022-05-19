package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import member.dto.Member;
import member.service.MemberService;
import member.service.MemberServiceImpl;
import common.HelloMvcUtils;

@WebServlet("/member/login")
public class MemberLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final MemberService memberService = new MemberServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 인코딩 (filter)
        //request.setCharacterEncoding("utf-8");
        
        // 2. 
        String memberId = request.getParameter("memberId");
        //String password = HelloMvcUtils.encrypt(request.getParameter("password"), memberId);
        String password = request.getParameter("password");

        String saveId = request.getParameter("saveId");

        System.out.println("memberId@MemberLoginServlet = " + memberId);
        System.out.println("password@MemberLoginServlet = " + password);
        
        // 3. 회원 정보 검색
        Member member = memberService.findMember(memberId);
        System.out.println("member@MemberLoginServlet = " + member);
        System.out.println("saveId = " + saveId);
        HttpSession session = request.getSession();
        
        if(member != null && password.equals(member.getPassword())) {
            
            session.setAttribute("loginMember", member);
            // saveId 쿠키 처리
            if(saveId != null){
                Cookie cookie = new Cookie("saveId",memberId);
                cookie.setPath(request.getContextPath());
                cookie.setMaxAge(1*60*60);

                // 응답객체 쿠키 추가, Set-Cookie 헤더에 작성
                response.addCookie(cookie);
            }
        }
        else {
            // 회원정보 틀렸을시
        	session.setAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        RequestDispatcher reqDispatcher = request.getRequestDispatcher("/index.jsp");
        reqDispatcher.forward(request, response);
    }

}