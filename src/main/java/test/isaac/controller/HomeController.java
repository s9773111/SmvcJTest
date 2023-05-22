package test.isaac.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import test.isaac.bean.User;
import test.isaac.service.UserService;

@Controller
public class HomeController {
	
	//不用request
	//@Autowired(required=false)
	//HttpServletRequest request;
	//@Autowired(required=false)
	//@Autowired
	//HttpServletResponse response;
	
	@Autowired
	UserService us;
	
	@RequestMapping(value= {"/","/homepage"}, method=RequestMethod.GET)
	public ModelAndView home() {
		//測試抓資料
		System.out.println("到/homepage中");
		
		//導到helloworld.jsp中
		ModelAndView mv = new ModelAndView("home");
		
		//與jsp同名
		return mv;
	}
	
	//忘記密碼頁面-GET方法
	@RequestMapping(value= {"/password"}, method=RequestMethod.GET)
	public ModelAndView password() {
		System.out.println("到達忘記密碼頁面");
		ModelAndView mv =  new ModelAndView("password");
		return mv;
	}
	
	//忘記密碼頁面-POST方法
	@RequestMapping(value= {"/password"}, method=RequestMethod.POST)
	public ModelAndView passwordUpdate(@RequestParam(value="account", required=false)String account) {
		System.out.println("查詢帳號的信箱(POST)");
		ModelAndView mv = null;
		System.out.println("account:"+account);
		
		//一定要輸入正確的帳號
		//要透過帳號去查詢本人密碼及信箱
		User result = null;
		String mail = null;
		Boolean updatepwd = false;
		try {
			result = us.findMail(account);
			mail = result.getUmail();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Controller只要知道寄信是否成功或失敗 
		//Controller把資料丟給service service在處理資料
		//產生新密碼
		String newpwd = us.getnewPwd();
		//新密碼加密
		String md5Str = us.getmd5Pwd(newpwd);
		
		//資料庫更新新密碼
		try {
			updatepwd = us.updatePwd(account, md5Str);
		} catch (SQLException e) {
			System.out.println("更換資料庫密碼失敗："+e);
		}
		
		
		//放到service controller任務分配
		//寄送信箱並傳送相對應頁面
		if(updatepwd) {
			us.sendMail(mail, newpwd);
			//mv.addObject("result", "成功更換密碼請收信");
			//request.setAttribute("result", "成功更換密碼請收信");
			return mv = new ModelAndView("home", "result", "成功更換密碼請收信");
		}else {
			//request.setAttribute("mail", "失敗更換密碼");
			return mv = new ModelAndView("password","mail", "失敗更換密碼");
		}	
	}
	
	//登入資訊，登入成功會到個人頁面userInfo.jsp，登入失敗回到home.jsp
	@RequestMapping(value={"/login"}, method=RequestMethod.POST)
	public ModelAndView login(
			@RequestParam(value="username", required=false, defaultValue="World") String username,
			@RequestParam(value="password", required=false, defaultValue="World") String password){
		System.out.println("檢查是否為會員");
		//抓出網頁元素name = username
		
		//String username = request.getParameter("username");
		//String password = request.getParameter("password");
		//System.out.println("username:"+username);
		//System.out.println("password:"+password);
		
		ModelAndView mv;
		//設定md5
		String md5Str = us.getmd5Pwd(password);
		//String md5Str = DigestUtils.md5DigestAsHex(password.getBytes());
		
		//UserService us = new UserServiceImpl();
		User result = null;
		try {
			result = us.findUser(username, md5Str);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("是否查詢人在資料庫中:"+result);
		
		if(result != null) {
			//設定是否登入成功
			//request.setAttribute("result", result);
			//return "userInfo";
			return mv = new ModelAndView("userInfo","result", result); 
		}else {
			//request.setAttribute("result", "登入失敗請重新輸入");
			//return "home";
			return mv = new ModelAndView("home","result", "登入失敗請重新輸入");
		}
	}
	
	//註冊頁面
	@RequestMapping(value={"/register"}, method=RequestMethod.GET)
	public ModelAndView register() {
		System.out.println("到達註冊controller");
		ModelAndView mv = new ModelAndView("register");
		//mv.addObject("resp", "test");
		return mv;
	}
	
	//註冊會員-表單
	@RequestMapping(value= {"/regmember"}, method=RequestMethod.POST)
	public ModelAndView regmemeber(
			@RequestParam(value="Uaccount", required=false, defaultValue="World") String Uaccount,
			@RequestParam(value="Upassword", required=false, defaultValue="World") String Upassword,
			@RequestParam(value="Uname", required=false, defaultValue="World") String Uname,
			@RequestParam(value="Uemail", required=false, defaultValue="World") String Uemail,
			@RequestParam(value="Ubday", required=false, defaultValue="World") Date Ubday,
			@RequestParam(value="Usex", required=false, defaultValue="World") int Usex
			) {
		System.out.println("註冊會員  審查資料...");
		ModelAndView mv;
		String resulttxt = null;
		
		//使用md5調整密碼
		String md5Str = us.getmd5Pwd(Upassword);
		//String md5Str = DigestUtils.md5DigestAsHex(Upassword.getBytes());
		
		//判斷帳號是否註冊過，註冊過就無法再註冊
		User user = null;
		
		try {
			user = us.findMail(Uaccount);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		//判斷帳號有無在資料庫中
		if(user != null) {
			//若有在資料庫，會顯示帳號重複
			//request.setAttribute("result", "帳號重複");
			return mv = new ModelAndView("register","result", "帳號重複"); 
		}else {
			//若無表示資料庫無資料，可插入新資料。
			user = new User(Uname, Ubday, Usex, Uemail, md5Str, Uaccount);
			try {
				Boolean result = us.insertUser(user);
				System.out.println("新增會員結果："+result);
				if(result) {
					//request.setAttribute("result", "成功註冊");
					resulttxt = "成功註冊";
				}else {
					//request.setAttribute("result", "註冊失敗");
					resulttxt = "註冊失敗";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			return mv = new ModelAndView("home", "result", resulttxt); 
		}
	}
	
	//?account=
	//註冊會員-檢查帳號是否有重複
	@RequestMapping(value= {"/regmember"}, method=RequestMethod.GET)
	public ModelAndView checkAccount(@RequestParam(value="account", required=false, defaultValue="World") String account,HttpServletResponse response) {
		
		System.out.println("檢查帳號是否重複...");
		//String name = request.getParameter("account");
		
		ModelAndView mv = new ModelAndView("register");
		
		response.setContentType("text/html;charset=utf-8");
		
		System.out.println("參數帳號值:"+account);
		
		//列出所有會員
		//UserService us = new UserServiceImpl();
		List<User> ls = new ArrayList<User>();
		try {
			ls = us.selectUser();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Boolean flag = false;
		for(User ur:ls) {
			if(account.equals(ur.getUacct())) {
				flag = true;
				break;
			}
	
		}
		if(flag) {
			try {
				response.getWriter().write("帳號已存在");
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			try {
				response.getWriter().write("帳號可以使用");
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mv; 
	}
}


