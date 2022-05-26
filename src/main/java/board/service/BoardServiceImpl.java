package board.service;

import board.dto.Attachment;
import board.dto.Board;
import board.dto.BoardExt;
import board.repository.BoardRepository;
import board.repository.JdbcBoardRepository;

import java.util.List;
import java.util.Map;

public class BoardServiceImpl implements BoardService{
    BoardRepository boardRepository = new JdbcBoardRepository();

    @Override
    public int getTotalContents() {
        return boardRepository.getTotalContents();
    }

    @Override
    public List<BoardExt> findAll(Map<String, Object> param) {
        List<BoardExt> list = boardRepository.findAll(param);

        return list;
    }

    @Override
    public int insertBoard(Board board) {
        return boardRepository.insertBoard(board);
    }

    @Override
    public BoardExt findByNo(int no) {
        return  boardRepository.findByNo(no);
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
