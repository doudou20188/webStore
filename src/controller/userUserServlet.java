package controller;

import bean.User;
import org.apache.commons.beanutils.BeanUtils;
import service.ImpService;
import service.Service;
import utils.MD5Util;
import utils.SendJMailUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: YangTao
 * @Date: 2018/11/13 0013
 */
@WebServlet(name = "user/UserServlet",urlPatterns = "/user/UserServlet")
public class userUserServlet extends HttpServlet {
    Service service=new ImpService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //用户注册
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String op = request.getParameter("op");
        switch (op){
            case "register":
                toDealRegister(request,response);
                break;
            case "login":
                toDealLogin(request,response);
                break;

            case "logout":
                toDealLogout(request,response);
                break;
            case "update":
                toUpdateUser(request,response);
                break;
        }



    }

    /**
     * 只更新用户的密码，昵称  邮箱 和 出生日期
     * @param request
     * @param response
     */
    private void toUpdateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = new User();
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            BeanUtils.populate(user,parameterMap);
            String password = user.getPassword();
            String md5Password = MD5Util.getMD5(password);
            user.setPassword(md5Password);
            User newUser =service.updateUser(user);
            if (newUser!=null){
                //修改成功
                request.getSession().setAttribute("user",newUser);
                request.getSession().setAttribute("updateMessage","修改成功.....");
                response.setHeader("refresh","0;url="+request.getContextPath()+"/user/personal.jsp");

            }else {
                request.getSession().setAttribute("updateMessage","修改失败....");
                response.setHeader("refresh","0;url="+request.getContextPath()+"/user/personal.jsp");
            }


        } catch (IllegalAccessException e) {
            e.printStackTrace();
            response.getWriter().print("error");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            response.getWriter().print("error");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().print("error");

        }


    }

    /**
     * 注销操作
     * @param request
     * @param response
     */
    private void toDealLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session!=null){
            session.invalidate();
        }
        response.setHeader("refresh","0;url="+request.getContextPath()+"/index.jsp");

    }

    private void toDealLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String md5Password = MD5Util.getMD5(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(md5Password);
        User checkUser = null;
        try {
            checkUser = service.checkUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().print("error!");
        }
        //验证码校验
        String verifyCode = request.getParameter("verifyCode");
        String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
        if (!verifyCode.equals(checkcode_session)){
            getServletContext().setAttribute("message","验证码错误...");
            response.setHeader("refresh","0;url="+request.getContextPath()+"/user/login.jsp");

        }else {
            if (checkUser!=null){
                //检验邮箱是否激活
                int staus = checkUser.getStaus();
                if (staus==1){
                    HttpSession session = request.getSession();
                    session.setAttribute("user",checkUser);
                    response.getWriter().print("登陆成功，即将跳转到首页");
                    response.setHeader("refresh","1;url="+request.getContextPath()+"/index.jsp");
                }else {
                    response.getWriter().print("请先登陆你的邮箱，激活邮箱");
                    response.setHeader("refresh","1;url="+request.getContextPath()+"/user/login.jsp");
                }

            }else {
                //response.getWriter().print("登陆失败，用户名密码错误");
                ServletContext servletContext = getServletContext();
                servletContext.setAttribute("message","登陆失败，用户用户名密码错误");
                response.setHeader("refresh","0;url="+request.getContextPath()+"/user/login.jsp");
            }
        }


    }

    private void toDealRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = new User();
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            BeanUtils.populate(user,parameterMap);
            String password = user.getPassword();
            String md5Password = MD5Util.getMD5(password);
            //加密密码储存
            user.setPassword(md5Password);
            //追加 注册时间  邮箱激活状态  和标志唯一用户uuid字段值
            String temp_str="";
            Date date = new Date();
            //最后的aa表示“上午”或“下午”  HH表示24小时制    如果换成hh表示12小时制
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            temp_str=sdf.format(date);
            user.setUpdatetime(temp_str);
            //将数据添加到数据库中
            user.setStaus(0);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            user.setUuid(uuid);

            boolean b =service.addUser(user);
            if (b){
                String message="<a href='http://192.168.4.15:80/ActiveUser?uuid="+uuid+"'>点击我激活</a>";
                SendJMailUtils.sendMail("940702494@qq.com",message);
                response.getWriter().print("注册成功，即将跳转到登陆界面");
                response.setHeader("refresh","1;url="+request.getContextPath()+"/user/login.jsp");

            }else {
                response.getWriter().print("注册失败,请重新注册");
                response.setHeader("refresh","1;url="+request.getContextPath()+"/user/register.jsp");

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            response.getWriter().print("error!");

        } catch (InvocationTargetException e) {
            e.printStackTrace();
            response.getWriter().print("error!");

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().print("error!");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);


    }
}
