package test.isaac.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import test.isaac.bean.User;
import test.isaac.dao.UserDao;
import test.isaac.mail.JavaMail;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserDao ud;
	
	@Override
	public List<User> selectUser() throws SQLException {
		
		//UserDao ud = new UserDao();
		List<User> result = ud.selectUser();
		return result;
	}
	
	@Override
	public User findUser(String username, String password) throws SQLException{
		//UserDao ud = new UserDao();
		User result = ud.findUser(username, password);
		return result;
	}

	@Override
	public Boolean insertUser(User user) throws SQLException {
		Boolean result = ud.inserUser(user);
		return result;
	}

	@Override
	public User findMail(String account) throws SQLException {
		User result = ud.findMail(account);
		return result;
	}

	@Override
	public Boolean updatePwd(String account, String password) throws SQLException {
		Boolean result  = ud.updatePwd(account, password);
		return result;
	}


	@Override
	public String getmd5Pwd(String pwd) {
		String md5Str = DigestUtils.md5DigestAsHex(pwd.getBytes());
		return md5Str;
	}

	@Override
	public String getnewPwd() {
		Random ran = new Random();
		String pwd = "";
		for(int i=1;i<9;i++) {
			int newpwd = ran.ints(1,9+1).findFirst().getAsInt();
			pwd += newpwd;
		}
		System.out.println("新密碼："+pwd);
		return pwd;
	}

	@Override
	public void sendMail(String mail, String pwd) {
		JavaMail jmail = new JavaMail(mail, pwd);
		jmail.SendMail();
	}
	
	
	
}
