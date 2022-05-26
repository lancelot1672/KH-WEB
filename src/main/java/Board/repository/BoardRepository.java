package Board.repository;

import Board.dto.Attachment;
import Board.dto.Board;
import Board.dto.BoardExt;

import java.util.List;
import java.util.Map;

public interface BoardRepository {
    List<BoardExt> findAll(Map<String, Object> param);
    int getTotalContents();
    int insertBoard(Board board);
    int findCurrentBoardNo();
    int insertAttachment(Attachment attach);
    BoardExt findByNo(int no);
}
