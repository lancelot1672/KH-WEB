package Board.service;

import Board.dto.Attachment;
import Board.dto.Board;
import Board.dto.BoardExt;

import java.util.List;

public class BoardServiceImpl implements BoardService{
    @Override
    public List<BoardExt> findAll() {
        return null;
    }

    @Override
    public int insertBoard(Board board) {
        return 0;
    }

    @Override
    public BoardExt findByNo(int no) {
        return null;
    }

    @Override
    public int updateReadCount(int no) {
        return 0;
    }

    @Override
    public Attachment findAttachmentByNo(int no) {
        return null;
    }
}
