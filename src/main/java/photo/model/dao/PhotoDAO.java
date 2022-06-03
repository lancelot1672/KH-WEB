package photo.model.dao;

import photo.model.dto.Photo;
import photo.model.exception.PhotoException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static common.JdbcTemplate.close;



public class PhotoDAO {
    private Properties properties = new Properties();

    public PhotoDAO() {
        String filename = PhotoDAO.class.getResource("/sql/photo-query.properties").getPath();
        try{
            properties.load(new FileReader(filename));

        }catch (IOException e){
            String message = e.getMessage();
        }
        System.out.println("filename = " + filename);

    }

    public int getTotalContent(Connection connection) {
        //준비
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = properties.getProperty("getTotalContent");
        
        int totalContent = 0;

        //로직
        try{
            pstmt = connection.prepareStatement(sql);
            rset = pstmt.executeQuery();

            while (rset.next()){
                totalContent = rset.getInt(1);
            }
        }catch (Exception e){
            throw new PhotoException("전체 게시물 수 조회 오류!");
        }finally {
            close(rset);
            close(pstmt);
        }

        //리턴
        return totalContent;
    }
    public List<Photo> findMorePage(Connection connection, Map<String,Integer> param){
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = properties.getProperty("findMorePage");
        List<Photo> list = new ArrayList<>();

        try{
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, param.get("start"));
            pstmt.setInt(2, param.get("end"));

            rset = pstmt.executeQuery();

            while (rset.next()){
                Photo photo = handlePhotoResultSet(rset);
                list.add(photo);
            }
        }catch (SQLException e){
            throw new PhotoException("더보기 페이지 조회 오류",e);
        }finally {
            close(rset);
            close(pstmt);
        }
        return list;
    }

    private Photo handlePhotoResultSet(ResultSet rset) throws SQLException{
        int no = rset.getInt("no");
        String memberId = rset.getString("member_id");
        String content = rset.getString("content");
        String original_filename = rset.getString("original_filename");
        String renamed_filename = rset.getString("renamed_filename");
        int readCount = rset.getInt("read_count");
        Date reg_date = rset.getDate("reg_date");

        return new Photo(no,memberId,content,original_filename,renamed_filename,readCount,reg_date);
    }
}
