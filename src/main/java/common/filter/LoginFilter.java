package common.filter;

import member.dto.Member;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({
        "/member/memberView",
        "/member/memberUpdate",
        "/member/memberDelete",
        "/member/passwordUpdate"
})
public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        //로그인 여부 검사
        HttpSession session = httpReq.getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");

        //로그인 정보가 없으면 처리
        if(loginMember == null){
            session.setAttribute("msg", "로그인 후 사용가능합니다.");
            httpRes.sendRedirect(httpReq.getContextPath() + "/");
            return; //조기 리턴
        }
        chain.doFilter(request, response);
    }
}
