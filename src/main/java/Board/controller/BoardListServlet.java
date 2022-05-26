package Board.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/board/boardList")
public class BoardListServlet extends HttpServlet {
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


    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
