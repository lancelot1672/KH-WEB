package board.repository;

import board.dto.Attachment;
import board.dto.Board;
import board.dto.BoardExt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static common.JdbcTemplate.*;

public class JdbcBoardRepository implements BoardRepository{
    @Override
    public List<BoardExt> findAll(Map<String, Object> param) {
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<BoardExt> list = new ArrayList<>();

        String sql = "select * " +
                "from ( " +
                    "select b.*, (select count(*) from attachment where board_no = b.no) attach_cnt, row_number() over(order by reg_date desc) rnum from board b) b where rnum between ? and ?";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, (int) param.get("start"));
            pstmt.setInt(2, (int) param.get("end"));
            rset = pstmt.executeQuery();
            while(rset.next()) {
                BoardExt board = handleBoardResultSet(rset);
                board.setAttachCount(rset.getInt("attach_cnt"));
                list.add(board);
            }
        } catch (Exception e) {
            //throw new BoardException("회원목록 조회 오류", e);
        } finally {
            close(rset);
            close(pstmt);
        }
        return list;
    }

    @Override
    public int getTotalContents() {
        return 0;
    }

    @Override
    public int insertBoard(Board board) {
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        int result = 0;
        String sql = "insert into board (no, title, member_id, content) values (seq_board_no.nextval, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, board.getTitle());
            pstmt.setString(2, board.getMemberId());
            pstmt.setString(3, board.getContent());
            result = pstmt.executeUpdate();

            commit(conn);
        } catch (Exception e) {
            rollback(conn);
            //throw new BoardException("게시글 등록 오류", e);
        } finally {
            close(pstmt);
        }
        return result;
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
    private BoardExt handleBoardResultSet(ResultSet rset) throws SQLException {
        BoardExt board = new BoardExt();
        board.setNo(rset.getInt("no"));
        board.setTitle(rset.getString("title"));
        board.setMemberId(rset.getString("member_id"));
        board.setContent(rset.getString("content"));
        board.setReadCount(rset.getInt("read_count"));
        board.setRegDate(rset.getDate("reg_date"));
        return board;
    }
}
