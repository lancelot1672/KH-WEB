package photo.model.service;


import photo.model.dao.PhotoDAO;
import photo.model.dto.Photo;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import static common.JdbcTemplate.close;
import static common.JdbcTemplate.getConnection;

public class PhotoService {
    public static final int NUM_PER_PAGE = 5;
    private PhotoDAO photoDAO = new PhotoDAO();

    public int getTotalContents() {
        Connection connection = getConnection();

        int totalContent = photoDAO.getTotalContent(connection);
        close(connection);

        return totalContent;
    }

    public List<Photo> findMorePage(Map<String, Integer> param) {
        Connection connection = getConnection();
        List<Photo> list = photoDAO.findMorePage(connection, param);

        return list;
    }
}
