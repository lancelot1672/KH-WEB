package board.controller;

import board.dto.BoardComment;
import board.service.BoardService;
import board.service.BoardServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/boardCommentEnroll")
public class BoardCommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BoardService boardService = new BoardServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1. 사용자 입력값 처리
            int boardNo = Integer.parseInt(request.getParameter("boardNo"));
            int commentLevel = Integer.parseInt(request.getParameter("commentLevel"));
            int commentRef = Integer.parseInt(request.getParameter("commentRef"));
            String memberId = request.getParameter("memberId");
            String content = request.getParameter("content");

            BoardComment bc = new BoardComment(0, commentLevel, memberId, content, boardNo, commentRef, null);
            System.out.println("bc = " + bc);

            // 2. 업무로직
            int result = boardService.insertBoardComment(bc);

            // 3. Redirect 처리
            response.sendRedirect(request.getContextPath() + "/board/boardView?no=" + boardNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
