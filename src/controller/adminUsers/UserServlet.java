package controller.adminUsers;

import bean.User;
import org.apache.commons.beanutils.BeanUtils;
import service.ImpService;
import service.Service;
import utils.MD5Util;
import utils.PageInfo;
import utils.SendJMailUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @Auther: YangTao
 * @Date: 2018/11/14 0014
 */
@WebServlet(name = "UserServlet",urlPatterns = "/admin/UserServlet")
public class UserServlet extends HttpServlet {
    Service service= new ImpService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op){
            case "findAllUser":
                findAllUser(request,response);
                break;

            case "adduser":
                toAddUser(request,response);
                break;

        }


    }

    private void toAddUser(HttpServletRequest request, HttpServletResponse response) {
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
               /* String message="<a href='http://192.168.4.15:80/ActiveUser?uuid="+uuid+"'>点击我激活</a>";
                SendJMailUtils.sendMail("940702494@qq.com",message);*/
                response.getWriter().print("添加成功！");
                response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/user/addUser.jsp");

            }else {
                response.getWriter().print("添加失败失败");
                response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/user/addUser.jsp");

            }



        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void findAllUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String num = request.getParameter("num");
        if (num!=null&&!num.isEmpty()){
            PageInfo pageInfo= null;
            try {
                pageInfo = service.findPageUsers(num);//获得pageinfo 对象，里面包含了页面集合信息和页数信息
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("error!");
                return;
            }
            request.setAttribute("pageInfo",pageInfo);
            try {
                request.getRequestDispatcher("/admin/user/userList.jsp").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            }

        }
        }

}
