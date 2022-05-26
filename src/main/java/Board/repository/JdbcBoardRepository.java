package Board.repository;

import Board.dto.Attachment;
import Board.dto.Board;
import Board.dto.BoardExt;

import java.util.List;
import java.util.Map;

public class JdbcBoardRepository implements BoardRepository{
    @Override
    public List<BoardExt> findAll(Map<String, Object> param) {
        return null;
    }

    @Override
    public int getTotalContents() {
        return 0;
    }

    @Override
    public int insertBoard(Board board) {
        return 0;
    }

    @Override
    public int findCurrentBoardNo() {
        return 0;
    }

    @Override
    public int insertAttachment(Attachment attach) {
        return 0;
    }

    @Override
    public BoardExt findByNo(int no) {
        return null;
    }
}
