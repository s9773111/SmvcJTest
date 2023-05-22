package test.isaac.utils;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
	private static Connection conn;
	
	public static Connection getConnection() throws SQLException{
		try {
			System.out.println("開始資料庫連線");
			Properties prop = new Properties();
			
			ClassLoader classLoader = JDBCUtils.class.getClassLoader();
			URL res = classLoader.getResource("db.properties");
			String path = res.getPath();
			prop.load(new FileReader(path));
			//prop.load(JDBCUtils.class.getClassLoader().getResourceAsStream("db.properties"));
			String url = prop.getProperty("url");
			String user = prop.getProperty("user");
			String pass = prop.getProperty("pass");
			String driver = prop.getProperty("driverClassName");
			
			//註冊驅動
			//Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName(driver);
			//conn = DriverManager.getConnection(URL, USER, PASS);
			conn = DriverManager.getConnection(url, user, pass);
			System.out.println("完成設定Connection"+conn);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//釋放資源1
	public static void close(ResultSet rs, Statement stmt, Connection conn) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//釋放資源2
	public static void close(Statement stmt, Connection conn) {
		close(null, stmt, conn);
	}
	
	//釋放資源3
	public static void close(ResultSet rs, Connection con, PreparedStatement pre){
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(pre != null) {
			try {
				pre.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//釋放資源4
	public static void close(Connection con, PreparedStatement pre){
		close(null, pre, conn);
	}
	
}
