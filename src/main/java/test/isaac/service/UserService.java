package test.isaac.service;

import java.sql.SQLException;
import java.util.List;

import test.isaac.bean.User;

public interface UserService {
	//查詢使用者資料
	public List<User> selectUser() throws SQLException;
	
	//查詢特定使用者
	public User findUser(String username, String password) throws SQLException;
	
	//插入使用者資料
	public Boolean insertUser(User user) throws SQLException;
	
	//查詢特定使用者-忘記密碼重新寄送
	public User findMail(String account)throws SQLException;
	
	//設定密碼-忘記密碼
	public Boolean updatePwd(String account, String password) throws SQLException;
	
	//取得加密後密碼
	public String getmd5Pwd(String pwd);
	
	//取得新密碼
	public String getnewPwd();
	
	//寄信功能
	public void sendMail(String mail, String pwd);
}
