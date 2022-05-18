package member.controller;

import member.dto.Member;
import member.dto.MemberRole;
import member.service.MemberService;
import member.service.MemberServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/member/join")
public class MemberJoinServlet  extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher reqDispatcher = request.getRequestDispatcher("/WEB-INF/views/member/memberJoin.jsp");
        reqDispatcher.forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MemberService memberService = new MemberServiceImpl();

        try{
            // 1. 인코딩
            //request.setCharacterEncoding("utf-8");

            // 2. 사용자입력값 처리
            String memberId = request.getParameter("memberId");
            String password = request.getParameter("password");
            String memberName = request.getParameter("memberName");
            String gender = request.getParameter("gender");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");

            String _birthday = request.getParameter("birthday");
            System.out.println(_birthday);

            Date birthday = null;
            if(_birthday != null && !"".equals(_birthday))
                birthday = Date.valueOf(_birthday);

            String[] _hobby = request.getParameterValues("hobby");
            String hobby = null;
            if(_hobby != null)
                hobby = String.join(",", _hobby);

            Member member = new Member(
                    memberId, password, memberName, MemberRole.U,
                    gender, birthday, email, phone, address, hobby, null
            );
            System.out.println("member@MemberEnrollServlet = " + member);

            // 3. 회원가입 요청
            memberService.join(member);

            // 4-1. redirect Msg
            String msg = "성공적으로 회원가입했습니다.";

            // 4-2. 리다이렉트 (DML처리인 경우 url을 변경해서 새로고침오류를 방지한다.)
            // 사용자 경고창 처리
            // 성공적으로 회원가입했습니다. | 회원가입 실패했습니다.
            request
                    .getSession()
                    .setAttribute("msg", msg);
            response.sendRedirect(request.getContextPath() + "/");

        } catch(Exception e) {
            // 1. 로깅 및 관리팀 알림.
            e.printStackTrace();
            //errorLogToDiscord(e);

            // 2. 예외던지기 - tomcat에 통보
            throw e;
        }
    }
}
