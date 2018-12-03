<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<link href="${pageContext.request.contextPath }/user/css/style.css" rel='stylesheet' type='text/css' />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!--webfonts-->
		<!--//webfonts-->
		<script src="js/setDate.js" type="text/javascript"></script>
		<script type="text/javascript">
        function isUserUsernameAvailable() {
                var username = document.getElementById("username");
                var message = document.getElementById("message");
                if (username.value === "") {
                    message.innerText = "";
                    return;
                }
                //1. 创建xmlreq 对象
                var request = new XMLHttpRequest();
                //2.回调函数
				//用户名为3-5位的字母或者下划线
                var valid = /^\w{3,8}$/.test(username.value);
				//正则判断用户名
                if (!valid){
                    message.innerText="必须为3-8位字母数字下划线组合";
                    message.style.color = 'red';

                }else{
                    message.innerText="用户名合法";
                    message.style.color = 'blue';
                    //2.异步请求 回调方法
                    request.onreadystatechange = function () {
                        if (request.readyState == 4 && request.status == 200) {
                            var responseText = request.responseText;
                            if (responseText === "true") {
                                message.innerText = "用户名可用";
                                message.style.color = 'blue';
                            } else {
                                message.innerText = "用户名重复";
                                message.style.color = 'red';
                            }
                        }
                    };

                }
                //3.建立连接
                request.open("post","${pageContext.request.contextPath}/AjaxServlet?op=isuser");
                //4、发出异步请求
                request.setRequestHeader("CONTENT-TYPE","application/x-www-form-urlencoded");
                request.send("username="+username.value);

                //request.send("op=forusername");
               /* get 方法
                var url = "${pageContext.request.contextPath}/AjaxServlet?ajax=isUserUsernameAvailable&username=" + username.value;
                request.open("GET",url, true);
                //4.发送请求
                request.send(null);*/

            }

        function isUseremailAvailable() {
            var email = document.getElementById("email");
            var message = document.getElementById("message2");
            if (email.value === "") {
                message.innerText = "";
                return;
            }
            //1. 创建xmlreq 对象
            var request = new XMLHttpRequest();
            //2.回调函数
            //邮箱正则
            var valid = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/.test(email.value);
            //正则判断用户名
            if (!valid){
                message.innerText="邮箱格式错误！";
                message.style.color = 'red';

            }else{
                message.innerText="邮箱格式正确";
                message.style.color = 'blue';
                //2.异步请求 回调方法 查看邮箱是否已经注册
                request.onreadystatechange = function () {
                    if (request.readyState == 4 && request.status == 200) {
                        var responseText = request.responseText;
                        if (responseText === "true") {
                            message.innerText = "邮箱可用";
                            message.style.color = 'blue';
                        } else {
                            message.innerText = "邮箱已经被注册，请更改";
                            message.style.color = 'red';
                        }
                    }
                };
            }
            //3.建立连接
            request.open("post","${pageContext.request.contextPath}/AjaxServlet?op=isemail");
            //4、发出异步请求
            request.setRequestHeader("CONTENT-TYPE","application/x-www-form-urlencoded");
            request.send("email="+email.value);


        }
		</script>
	</head>

	<body>
		<div class="main" align="center">
			<div class="header">
				<h1>创建一个免费的新帐户！</h1>
			</div>
			<p></p>
			<form method="post" action="${pageContext.request.contextPath }/user/UserServlet">
				<input type="hidden" name="op" value="register" />
				<ul class="left-form">
					<li>
						<span id="message">${msg.error.username }</span><br/>
						<input type="text" name="username" id="username" placeholder="用户名" value="${msg.username}" required="required" onblur="isUserUsernameAvailable()"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.nickname }<br/>
						<input type="text" name="nickname" placeholder="昵称" value="${msg.nickname}" required="required"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						<span id="message2">${msg.error.email }</span><br/>
						<input type="text" name="email" placeholder="邮箱" id="email" value="${msg.email}" required="required" onblur="isUseremailAvailable()"/>
						<a href="#" class="icon ticker"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.password }<br/>
						<input type="password" name="password" placeholder="密码" value="${msg.password}" required="required"/>
						<a href="#" class="icon into"> </a>
						<div class="clear"> </div>
					</li>
					<li>
						${msg.error.birthday }<br/>
						<input type="text" placeholder="出生日期 2018-3-16" name="birthday" value="${msg.birthday}" size="15" />
						<div class="clear"> </div>
					</li>
					<li style="height: 10px;"></li>
					<li>
						<input type="submit" value="创建账户">
						<div class="clear"> </div>
					</li>
			</ul>
				<p class="submit">
					<a href="${pageContext.request.contextPath }/user/login.jsp">登录</a>
					<a href="${pageContext.request.contextPath }/index.jsp">返回首页</a>
				</p>

			<div class="clear"> </div>

			</form>

		</div>


	</body>

</html>