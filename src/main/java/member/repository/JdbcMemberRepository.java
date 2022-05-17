package member.repository;

import member.dto.Member;
import member.dto.MemberRole;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static common.JdbcTemplate.*;

public class JdbcMemberRepository implements MemberRepository {
//    public Connection getConnection() {
//        final String DB_URL= "jdbc:oracle:thin:@orcl_high?TNS_ADMIN=/Users/dore/oracledb/Wallet_orcl/";
//        final String DB_USER = "admin";
//        final String DB_PASSWORD = "Ehdfbf8749**";
//        final String CLASS_NAME = "oracle.jdbc.OracleDriver";
//        Connection conn = null;
//        try {
//            Class.forName(CLASS_NAME);
//            System.out.println("driver load success");
//
//            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//        }catch(ClassNotFoundException e) {
//            System.out.println("driver load Fail");
//            e.printStackTrace();
//        }catch (SQLException se) {
//            System.out.println("SQLException !!");
//            se.printStackTrace();
//        }
//        return conn;
//    }
    @Override
    public void save(Member member) {
        Connection conn = getConnection();
        PreparedStatement pstmt = null;
        try{
            String sql = "insert into " +
                    "member (member_id, password, member_name, birthday, email, address, phone,  member_role, hobby) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,member.getMemberId());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3,member.getMemberName());
            pstmt.setDate(4,member.getBirthday());
            pstmt.setString(5,member.getEmail());
            pstmt.setString(6,member.getAddress());
            pstmt.setString(7,member.getPhone());
            pstmt.setString(8,member.getMemberRole() == MemberRole.U ? "U" : "A");
            pstmt.setString(9,member.getGender());

            int rs = pstmt.executeUpdate(sql);
            commit(conn);
        }catch (Exception e){
            rollback(conn);

        }finally {
            close(pstmt);
            close(conn);
        }
    }

    @Override
    public Member findByMemberId(String memberId) {
        Connection conn = getConnection();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Member member = new Member();

        try {
            String sql = "select * from member where member_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                String id = rs.getString("member_id");
                String password = rs.getString("password");
                String name = rs.getString("member_name");
                Date birthDay = rs.getDate("birthday");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String member_role = rs.getString("member_role");
                String gender = rs.getString("gender");
                String hobby = rs.getString("hobby");

                member.setMemberId(id);
                member.setPassword(password);
                member.setMemberName(name);
                member.setBirthday(birthDay);
                member.setEmail(email);
                member.setAddress(address);
                member.setPhone(phone);
                member.setMemberRole(MemberRole.valueOf(member_role));
                member.setGender(gender);
                member.setHobby(hobby);
            }


        }catch(Exception e) {
            e.printStackTrace();
        }
		finally {
            close(rs);
            close(pstmt);
            close(conn);
		}

        return member;
    }
}
