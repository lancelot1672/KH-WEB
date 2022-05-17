package jdbc.test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/test/dbConnection")
public class JdbcConnectionTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		final String DB_URL= "jdbc:oracle:thin:@orcl_high?TNS_ADMIN=C:/Users/lancelot/instantclient_19_13/network/admin";
		final String DB_URL= "jdbc:oracle:thin:@orcl_high?TNS_ADMIN=/Users/dore/oracledb/Wallet_orcl/";
		final String DB_USER = "admin";
		final String DB_PASSWORD = "Ehdfbf8749**";
		final String CLASS_NAME = "oracle.jdbc.OracleDriver";
		
		ResultSet rs = null; //��� �޾Ƽ� ó���Ҷ�
		PreparedStatement pstmt = null; //DB�� �����ϴ� ���
		 try {
	            Class.forName(CLASS_NAME);
	            System.out.println("driver load ����!");
	            
	            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	            System.out.println("DB ���� ����!");
	            
	            String sql = "select * from POS_TABLE";
	            pstmt = conn.prepareStatement(sql);
	            rs = pstmt.executeQuery();
	            
	            while(rs.next()) {

	            	String menu = rs.getString("MENU");
	            	System.out.println(menu);
	            }
	        } catch (ClassNotFoundException e) {
	            System.out.println("driver load ����!");
	            e.printStackTrace();
	        } catch (SQLException se) {
	            System.out.println("SQLException ����!");
	            se.printStackTrace();
	        }
	}

//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}

}
