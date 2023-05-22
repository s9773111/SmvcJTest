<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%request.setCharacterEncoding("UTF-8");%>
<%response.setCharacterEncoding("UTF-8");%> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>註冊會員</title>
</head>
<body>
請輸入個人資料：
<form action="<c:url value="/regmember"/>" method="post">
	<table>
        <tr>
            <td>登入帳號：</td>
            <td><input type="text" name="Uaccount" id="Uaccount" required="required" onblur="doCheck()" />
            	<span id="accounttip" style="color:red;"></span>
            </td>
           <!--  <td>帳號只能是英文、數字、底線、減號與小數點，其餘字元均不接受</td> -->
        </tr>
        <tr>
            <td>登入密碼：</td>
            <td><input type="password" name="Upassword" required="required" /></td>
            <!-- <td>至少 5 個位數以上的密碼長度</td> -->
        </tr>
        <tr>
            <td>真實姓名：</td>
            <td><input type="text" name="Uname" required="required" /></td>
            <!-- <td>填寫註冊者的姓名</td> -->
        </tr>
        <tr>
            <td>電子郵件：</td><!-- onblur="checkmail()" -->
            <td><input type="email" id="Uemail" name="Uemail" required="required" onblur="checkmail()" />
            	<span id="emailtip" style="color:red;"></span>
            </td>
           <!--  <td>請填慣用的電子郵件</td> -->
        </tr>
        <tr>
            <td>生日：</td>
            <td><input type="date" name="Ubday" required="required" /></td>
            <!-- <td>請務必使用 YYYY-MM-DD 的格式，如 1990-01-20</td> -->
        </tr>
        <tr>
            <td>性別：</td>
            <td><select name="Usex">
                    <option value="0">女性</option>
                    <option value="1">男性</option>
            </select></td>
            <!-- <td>選擇性別</td> -->
        </tr>
        <tr>
            <td colspan="4" style="text-align:center; ">
                    <input type="hidden" name="action" value="reg" />
                    <input type="submit" value="送出註冊" />
            </td>
        </tr>
	</table>
</form>

<c:choose>
		<c:when test="${not empty result}">
			${result}。
		</c:when>
		<c:otherwise>
			請註冊
		</c:otherwise>
	</c:choose>

</body>

	<script type="text/javascript">
		
		function checkmail(){
			var email = document.getElementById("Uemail");
			var strEmail = email.value;
			var mtip = document.getElementById("emailtip");
			var emailRule = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z]+$/;
			
			if(!emailRule.test(strEmail)){
				mtip.innerText = "email格式錯誤";
			}else{
				mtip.innerText = "email格式正確";
			}
		}
		
		
		/* 測試註冊者帳號是否重複 */
		function doCheck(){
			//alert("確定失焦");
			var req2;
			/* 得到瀏覽器物件 JS送出request */
			if(window.XMLHttpRequest){
				req2 = new XMLHttpRequest();
			}else if(window.ActiveXObject){
				req2 = new ActiveXObject("Microsoft.XMLHTTP");
			}else{
				alert("AJAX not supported");
			}
			
			//alert("取得request obj:"+req2);
			var account = document.getElementById("Uaccount");
			var value = account.value;
			var tip = document.getElementById("accounttip");
			
			//alert("account:"+value);
			//step1: 打開request物件 默認true異步執行 
			//controller要設定@RequestParam(value="account", required=false, defaultValue="World") String account
			//url不需要反斜線！！
			req2.open("GET", "regmember?account="+value);
			//這個有反應 但就是抓不到name 
			//req2.open("GET", "<c:url value='/regmember?account=value'/>",true);
			//step2: 發送
			req2.send(null);
			//step3:檢查request物件狀態
			req2.onreadystatechange = function(){
				//判斷response是否已經接收完成和判斷是否正常回應
				if(req2.readyState==4 && req2.status==200){
					tip.innerText = req2.responseText;
					//tip.innerText = ${resp};
				}
			}
		}
	</script>
</html>