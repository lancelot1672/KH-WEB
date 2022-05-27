package board.repository;

import board.dto.Attachment;
import board.dto.Board;
import board.dto.BoardComment;
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
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        int totalContents = 0;

        String sql = "select count(*) from board";
        try{
            pstmt = conn.prepareStatement(sql);
            rset = pstmt.executeQuery();

            while(rset.next()) {
                totalContents = rset.getInt(1);
            }
        } catch (Exception e) {
            rollback(conn);
            //throw new BoardException("게시글 등록 오류", e);
        } finally {
            close(rset);
            close(pstmt);
        }
        System.out.println("totalContents = " + totalContents);
        return totalContents;
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
            close(conn);
        }
        return result;
    }
    @Override
    public int insertBoardComment(BoardComment bc){
        Connection conn = getConnection();
        int result = 0;
        PreparedStatement pstmt = null;
        String sql = "insert into board_comment values(seq_board_comment_no.nextval,?,?,?,?,?,default)";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bc.getCommentLevel());
            pstmt.setString(2, bc.getMemberId());
            pstmt.setString(3,bc.getContent());
            pstmt.setInt(4, bc.getBoardNo());
            // BoardComment#commentRef 0 ~ n => board_comment.comment_ref null ~ n
            pstmt.setObject(5, bc.getCommentRef() == 0 ? null : bc.getCommentRef());

            result = pstmt.executeUpdate();
            commit(conn);
        }catch(SQLException e){
            rollback(conn);
            //throw new e;
        }finally {
            close(pstmt);
            close(conn);
        }
        return result;
    }
    @Override
    public List<BoardComment> findBoardCommentByBoardNo(int no){
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        List<BoardComment> comments = new ArrayList<>();
        String sql = "select * from board_comment bc where board_no = ? start with comment_level = 1 connect by comment_ref = prior no";
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, no);

            rset = pstmt.executeQuery();

            while (rset.next()){
                BoardComment bc= new BoardComment();
                bc.setNo(rset.getInt("no"));
                bc.setCommentLevel(rset.getInt("comment_level"));
                bc.setMemberId(rset.getString("member_id"));
                bc.setContent(rset.getString("content"));
                bc.setBoardNo(rset.getInt("board_no"));
                bc.setCommentRef(rset.getInt("comment_ref"));
                bc.setRegDate(rset.getDate("reg_date"));

                comments.add(bc);
            }
        }catch(SQLException e){
            rollback(conn);
            //throw new e;
        }finally {
            close(rset);
            close(pstmt);
            close(conn);
        }
        return comments;
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
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        BoardExt board = null;

        try{
            String sql = "select * from board where no = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, no);
            rs = pstmt.executeQuery();
            rs.next();

            board = handleBoardResultSet(rs);

        }catch (Exception e){

        }finally {
            close(rs);
            close(pstmt);
        }
        return board;
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
