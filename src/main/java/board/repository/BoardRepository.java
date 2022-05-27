package board.repository;

import board.dto.Attachment;
import board.dto.Board;
import board.dto.BoardComment;
import board.dto.BoardExt;

import java.util.List;
import java.util.Map;

public interface BoardRepository {
    List<BoardExt> findAll(Map<String, Object> param);
    int getTotalContents();
    int insertBoard(Board board);
    int findCurrentBoardNo();
    int insertAttachment(Attachment attach);
    BoardExt findByNo(int no);

    int insertBoardComment(BoardComment bc);
    List<BoardComment> findBoardCommentByBoardNo(int no);
}
