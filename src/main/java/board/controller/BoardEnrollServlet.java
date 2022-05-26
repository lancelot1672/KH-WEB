package board.controller;

import board.dto.Attachment;
import board.dto.BoardExt;
import board.service.BoardService;
import board.service.BoardServiceImpl;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;
import common.HelloMvcFileRenamePolicy;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/board/boardEnroll")
public class BoardEnrollServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BoardService boardService = new BoardServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/board/boardEnroll.jsp")
                .forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String saveDirectory = getServletContext().getRealPath("/upload/board");
        System.out.println("saveDirectory = " + saveDirectory);

        // c. 최대파일크기 10MB
        int maxPostSize = 1024 * 1024 * 10;
        // d. 인코딩
        String encoding = "utf-8";

        // e. 파일명 재지정 정책 객체
        // DefaultFileRenamePolicy 파일명 중복시 numbering처리함.
        FileRenamePolicy policy = new HelloMvcFileRenamePolicy();
        MultipartRequest multiReq =
                new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);

        //제목, 작성자, 내용
        String title = multiReq.getParameter("title");
        String memberId = multiReq.getParameter("memberId");
        String content = multiReq.getParameter("content");
        BoardExt board = new BoardExt();
        board.setTitle(title);
        board.setMemberId(memberId);
        board.setContent(content);

        //첨부파일
        File upFile1 = multiReq.getFile("upFile1");
        File upFile2 = multiReq.getFile("upFile2");

        // 첨부한 파일이 하나라도 있는 경우
        if(upFile1 != null || upFile2 != null) {
            List<Attachment> attachments = new ArrayList<>();
            if(upFile1 != null)
                attachments.add(getAttachment(multiReq, "upFile1"));
            if(upFile2 != null)
                attachments.add(getAttachment(multiReq, "upFile2"));
            board.setAttachments(attachments);
        }

        System.out.println("board = " + board);

        // 4. 업무로직
        int result = boardService.insertBoard(board);

        // 5. redirect
        response.sendRedirect(request.getContextPath() + "/board/boardList");
    }
    private Attachment getAttachment(MultipartRequest multiReq, String name) {
        Attachment attach = new Attachment();
        String originalFilename = multiReq.getOriginalFileName(name); // 업로드한 파일명
        String renamedFilename = multiReq.getFilesystemName(name); // 저장된 파일명
        attach.setOriginalFilename(originalFilename);
        attach.setRenamedFilename(renamedFilename);
        return attach;
    }
}
