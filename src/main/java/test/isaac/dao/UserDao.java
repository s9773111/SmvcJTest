package test.isaac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import test.isaac.bean.User;
import test.isaac.utils.JDBCUtils;

@Repository
public class UserDao {


	//新增使用者資料
	public  boolean inserUser(User user) throws SQLException {
		Boolean flag = null;
		System.out.println("進入model層 新增使用者");
		String sql = "INSERT INTO USER(UNAME,UBDAY,USEX,UMAIL,UPWD,UACCT) VALUES(?,?,?,?,?,?)";
		//1.獲取資料庫資訊
		Connection con = JDBCUtils.getConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, user.getUname());
		stmt.setDate(2, (java.sql.Date)(user.getUbday()));
		stmt.setInt(3, user.getUsex());
		stmt.setString(4, user.getUmail());
		stmt.setString(5,  user.getUpwd());
		stmt.setString(6, user.getUacct());
		
		int success = stmt.executeUpdate();
		if(success >= 1) {
			flag = true;
			System.out.println("插入資料成功");
		}else {
			flag = false;
			System.out.println("插入資料失敗");
		}
		
		//3.關閉連線
		JDBCUtils.close(null, stmt, con);
		return flag;
	}
	
	//查詢特定使用者資料-忘記密碼重新寄送
	public User findMail(String account) throws SQLException {
		System.out.println("進入model層 查詢使用者的信箱");
		String sql = "SELECT * FROM USER WHERE UACCT=?";
		//1.獲取資料庫資訊
		Connection con = JDBCUtils.getConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, account);
		ResultSet rs = stmt.executeQuery();
		User us = new User();
		if(!rs.next()) {
			us = null;
		}else {
			us.setUno(rs.getInt("UNO"));
			us.setUname(rs.getString("UNAME"));
			us.setUbday(rs.getDate("UBDAY"));
			us.setUsex(rs.getInt("USEX"));
			us.setUmail(rs.getString("UMAIL"));
			us.setUpwd(rs.getString("UPWD"));
			us.setUacct(rs.getString("UACCT"));
		}
		return us;
	}
//	
//	public void modesql() throws SQLException {
//		System.out.println("開始修改db mode");
//		String sqlmode = "SET SQL_SAFE_UPDATES = 0";
//		Connection con = JDBCUtils.getConnection();
//		Statement st = con.createStatement();
//		//st.execute(sqlmode);
//		Boolean mode = st.execute(sqlmode);
//		System.out.println("mode修改是否成功："+mode);
//		JDBCUtils.close(null, st, con);
//	}
	
	//更新新密碼-忘記密碼重新寄送
	public boolean updatePwd(String account,  String password) throws SQLException {
		Boolean flag = null;
		System.out.println("開始更新使用者密碼");
		String sql = "UPDATE USER SET UPWD = ? WHERE UACCT=?";
		
		
		//modesql();
		//1.獲取資料庫資訊
		Connection con = JDBCUtils.getConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, password);
		stmt.setString(2, account);
		int success = stmt.executeUpdate();
		if(success >= 1) {
			flag = true;
			System.out.println("更新資料成功");
		}else {
			flag = false;
			System.out.println("更新資料失敗");
		}
		
		//3.關閉連線
		JDBCUtils.close(null, stmt, con);
		return flag;
	}
	

	
	//查詢特定使用者資料-帳號+密碼
	public User findUser(String username, String password) throws SQLException {
		System.out.println("進入model層 查詢特定使用者");
		String sql = "SELECT * FROM USER WHERE UACCT=? and UPWD=?";
		//System.out.println(username);
		//System.out.println(password);
		//1.獲取資料庫資訊
		Connection con = JDBCUtils.getConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, username);
		stmt.setString(2, password);
		
		ResultSet rs = stmt.executeQuery();
		User us = new User();
		
		if(!rs.next()) {
			us = null;
		}else {
			us.setUno(rs.getInt("UNO"));
			us.setUname(rs.getString("UNAME"));
			us.setUbday(rs.getDate("UBDAY"));
			us.setUsex(rs.getInt("USEX"));
			us.setUmail(rs.getString("UMAIL"));
			us.setUpwd(rs.getString("UPWD"));
			us.setUacct(rs.getString("UACCT"));
		}
		//3.關閉連線
		JDBCUtils.close(rs, stmt, con);
		return us;
	}
	
	//查出所有使用者資料
	public List<User> selectUser() throws SQLException{
		System.out.println("進入model層 準備搜尋資料");
		List<User> ls = new ArrayList<User>();
		String sql = "SELECT * FROM USER";
		
		//1.獲取資料庫資訊
		Connection con = JDBCUtils.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		//2.從結果提取資料
		while(rs.next()) {
			User us = new User();
			us.setUno(rs.getInt("UNO"));
			us.setUname(rs.getString("UNAME"));
			us.setUbday(rs.getDate("UBDAY"));
			us.setUsex(rs.getInt("USEX"));
			us.setUmail(rs.getString("UMAIL"));
			us.setUpwd(rs.getString("UPWD"));
			us.setUacct(rs.getString("UACCT"));
			ls.add(us);
		}
		//3.關閉連線
		JDBCUtils.close(rs, stmt, con);
		
		return ls;
	}
}
