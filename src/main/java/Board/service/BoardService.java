package Board.service;

import Board.dto.Attachment;
import Board.dto.Board;
import Board.dto.BoardExt;

import java.util.List;

public interface BoardService {
    List<BoardExt> findAll();
    int insertBoard(Board board);
    BoardExt findByNo(int no);
    int updateReadCount(int no);
    Attachment findAttachmentByNo(int no);
}
