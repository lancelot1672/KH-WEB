package photo.controller;

import photo.model.service.PhotoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/photo/photoList")
public class PhotoListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PhotoService photoService = new PhotoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. 업무로직
        // 전체 게시물 수 조회 => 전체 페이지 수
        int totalContents = photoService.getTotalContents();
        System.out.println("totalContents = " + totalContents); // 21

        final int numPerPage = PhotoService.NUM_PER_PAGE;
        int totalPage = (int) Math.ceil((double) totalContents / numPerPage);
        System.out.println("totalPage = " + totalPage);

        request.setAttribute("totalPage",totalPage);
        request.getRequestDispatcher("/WEB-INF/views/photo/photoList.jsp")
                .forward(request,response);
    }
}
