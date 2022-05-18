package common.filter;

import member.dto.Member;
import member.dto.MemberRole;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({
        "/admin/list",
        "/admin/memberRoleUpdate",
        "/admin/memberFinder"
})
public class AdminFilter implements Filter {
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

        //로그인하지 않거나, 일반 사용자가 단순히 url를 요청하게 되면 리다이렉트
        if(loginMember == null || !loginMember.getMemberRole().equals(MemberRole.A)){
            String msg = "관리자 권한이 없습니다.";
            session.setAttribute("msg",msg);

            // index로 리다이렉트
            httpRes.sendRedirect(httpReq.getContextPath() + "/");
            return; //조기 리턴
        }

        chain.doFilter(request, response);
    }
}
