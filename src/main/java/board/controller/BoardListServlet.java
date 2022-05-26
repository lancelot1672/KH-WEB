package board.controller;

import board.dto.BoardExt;
import board.service.BoardService;
import board.service.BoardServiceImpl;
import common.HelloMvcUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/board/boardList")
public class BoardListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BoardService boardService = new BoardServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int numPerPage = 10;
        int cPage = 1;
        try {
            cPage = Integer.parseInt(request.getParameter("cPage"));
        } catch(NumberFormatException e) {}
        Map<String, Object> param = new HashMap<>();
        int start = (cPage - 1) * numPerPage + 1;
        int end = cPage * numPerPage;
        param.put("start", start);
        param.put("end", end);

        // 10개씩 게시글 가져오기
        // 2. 업무로직
        // 2.a content 영역
        List<BoardExt> list = boardService.findAll(param);
        System.out.println(list);

        int totalContents = boardService.getTotalContents();
        String pagebar = HelloMvcUtils.getPagebar(cPage,numPerPage,totalContents, request.getRequestURI());
        System.out.println(pagebar);

        request.setAttribute("list", list);
        request.setAttribute("pagebar", pagebar);
        request.getRequestDispatcher("/WEB-INF/views/board/boardList.jsp")
                .forward(request,response);

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
