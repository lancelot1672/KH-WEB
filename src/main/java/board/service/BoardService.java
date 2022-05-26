package board.service;

import board.dto.Attachment;
import board.dto.Board;
import board.dto.BoardExt;

import java.util.List;
import java.util.Map;

public interface BoardService {
    List<BoardExt> findAll(Map<String, Object> param);
    int insertBoard(Board board);
    BoardExt findByNo(int no);
    int updateReadCount(int no);
    Attachment findAttachmentByNo(int no);

    int getTotalContents();
}
