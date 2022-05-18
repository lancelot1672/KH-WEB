package common.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class LogFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //Servlet 전처리
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        String uri = httpReq.getRequestURI();   // /mvc/member/memberView
        String method = httpReq.getMethod();

        System.out.println();
        System.out.println("====================================");
        System.out.println(method + " " + uri);
        System.out.println("------------------------------------");

        // filter chain의 다음 필터 호출(마지막 필터일 경우는 servlet 호출)
        chain.doFilter(request,response);

        // servlet, jsp 후처리
//        System.out.println("____________________________________");
//        System.out.println(httpRes.getStatus());
//        System.out.println();
    }
}
