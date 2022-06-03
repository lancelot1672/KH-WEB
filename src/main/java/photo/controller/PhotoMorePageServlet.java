package photo.controller;

import com.google.gson.Gson;
import photo.model.dto.Photo;
import photo.model.service.PhotoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/photo/morePage")
public class PhotoMorePageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PhotoService photoService = new PhotoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 사용자 입력값 처리
        int cPage = Integer.parseInt(request.getParameter("cPage"));
        final int numPerPage = PhotoService.NUM_PER_PAGE;
        System.out.println("cPage = " + cPage);

        Map<String, Integer> param = new HashMap<>();
        param.put("start", (cPage-1) * numPerPage + 1);
        param.put("end", cPage * numPerPage);

        // 2. 업무 로직
        List<Photo> list = photoService.findMorePage(param);
        System.out.println("list = " + list);

        // 3. 응답처리 json
        response.setContentType("application/json; charset=utf-8");
        response.getWriter()
                .append(new Gson().toJson(list));

    }
}
