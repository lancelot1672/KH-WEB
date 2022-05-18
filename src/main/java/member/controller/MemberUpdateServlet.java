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
import java.sql.Date;

@WebServlet("/member/update")
public class MemberUpdateServlet  extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MemberService memberService = new MemberServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 인코딩 처리
        request.setCharacterEncoding("utf-8");

        // 2. 사용자 입력값 처리
        String memberId = request.getParameter("memberId");
        String memberName = request.getParameter("memberName");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        String _birthday = request.getParameter("birthday");
        String[] hobbies = request.getParameterValues("hobby");

        Date birthday = null;
        if(_birthday != null && !"".equals(_birthday)){
            birthday = Date.valueOf(_birthday);
        }

        String hobby = null;
        if(hobbies  != null){
            hobby = String.join(",", hobbies);
        }
        Member member = new Member(memberId, null, memberName, null, gender, birthday, email, phone, address, hobby, null);
        System.out.println("member@MemberUpdateServlet = " + member);

        // 3. 로직
        memberService.updateMember(member);
        String msg = "회원정보를 성공적으로 수정했습니다.";

        // 4. 세션 정보 갱신
        Member updateMember = memberService.findMember(memberId);
        request.getSession().setAttribute("loginMember", updateMember);

        // 5. redirect - msg는 세션에 저장
        request.getSession().setAttribute("msg",msg);
        response.sendRedirect(request.getContextPath() + "/member/memberView");
    }
}
