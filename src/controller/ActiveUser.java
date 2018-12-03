package controller;

import bean.User;
import com.sun.org.apache.regexp.internal.RE;
import service.ImpService;
import service.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Auther: YangTao
 * @Date: 2018/11/14 0014
 */
@WebServlet(name = "ActiveUser",urlPatterns = "/ActiveUser")
public class ActiveUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //用户邮箱激活
        response.setContentType("text/html;charset=utf-8");
        Service service = new ImpService();
        String uuid = request.getParameter("uuid");
        System.out.println(uuid);

        //更改uuid 对应对象的staus 属性
        User user= null;
        try {
            user = service.updateUserStausByUUID(uuid);
            if (user!=null){
                request.getSession().setAttribute("user",user);
                response.getWriter().print("邮箱激活成功");
                //跳转到登陆界面把
                response.setHeader("refresh","1;url="+request.getContextPath()+"/user/login.jsp");
            }else {
                response.getWriter().print("邮箱激活失败");
                response.setHeader("refresh","1;url="+request.getContextPath()+"/user/login.jsp");

            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().print("error");
        }

    }
}
