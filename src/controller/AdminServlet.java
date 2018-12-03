package controller;

import bean.Admin;
import org.apache.commons.beanutils.BeanUtils;
import service.ImpService;
import service.Service;
import utils.PageInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

/**
 * @Auther: YangTao
 * @Date: 2018/11/11 0011
 */
@WebServlet(name = "AdminServlet",urlPatterns = "/admin/AdminServlet")
public class AdminServlet extends HttpServlet {
    Service service=new ImpService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //管理系统用户servlet
        String op = request.getParameter("op");
        switch (op){
            case "addAdmin":
                //添加系统用户到数据库
                addAdmin(request,response);

                break;
            case "findAllAdmin":
                findAllAdmin(request,response);

                break;
            case "updateAdmin":
                //修改密码管理员操作
                updateAdmin(request,response);
                break;
            case "deleteOne":
                //删除系统管理用户
                deleteOne(request,response);

                break;

            case "login":
                //管理员登陆
                try {
                    CheckAdminlogin(request,response);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.getWriter().println("登陆出现错误！");
                }

                break;

            case "lgout":
                //推出登陆
                adminToLgout(request,response);

                break;
        }


    }

    private void adminToLgout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.invalidate();
        response.setHeader("refresh","0;url="+request.getContextPath()+"/admin/index.jsp");



    }

    private void CheckAdminlogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Admin admin = new Admin();
        Map<String, String[]> parameterMap = request.getParameterMap();
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60*10);
        try {
            BeanUtils.populate(admin, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        try {
            Admin checkAdmin=service.checkAdmin(admin);
            if (checkAdmin!=null){
                // 跳转该页面main.jsp，并且一段时间后需要校验
                if (checkAdmin.getUsername().equals(checkAdmin.getUsername())&&checkAdmin.getPassword().equals(admin.getPassword())){
                    session.setAttribute("admin",admin);
                    response.setHeader("refresh","0;url="+request.getContextPath()+"/admin/main.jsp");
                }else {
                    response.setHeader("refresh","0;url="+request.getContextPath()+"/admin/index.jsp");
                }
            }else {
                response.setHeader("refresh","0;url="+request.getContextPath()+"/admin/index.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("error!");
        }




    }

    private void deleteOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String aid = request.getParameter("aid");
        Admin admin = new Admin();
        admin.setAid(Integer.parseInt(aid));

        boolean b= false;
        try {
            b = service.deleAdmin(admin);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("error！");

        }
        if (b){
            response.getWriter().println("删除成功！");
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/AdminServlet?op=findAllAdmin&num=1");

        }else {
            response.getWriter().println("删除失败");
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/AdminServlet?op=findAllAdmin&num=1");
        }

    }

    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String aid = request.getParameter("aid");
        int aid1 = Integer.parseInt(aid);
        String password = request.getParameter("password");
        String rpassword = request.getParameter("password1");
        Admin admin = new Admin();
        if (password!=null&&!password.isEmpty()){
            if (!password.equals(rpassword)){
                response.getWriter().println("密码不一致，请重新填写");
                response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/AdminServlet?op=findAllAdmin&num=1");
            }
        }

        if(password.isEmpty()||rpassword.isEmpty()){
            response.getWriter().println("密码不可为空，请重新填写");
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/AdminServlet?op=findAllAdmin&num=1");
        }else {
            admin.setAid(aid1);
            admin.setPassword(password);
            try {
                boolean b = service.updateAdmin(admin);
                if (b){
                    response.getWriter().println("修改成功!");
                    response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/AdminServlet?op=findAllAdmin&num=1");
                }else {
                    response.getWriter().println("修改失败，请重新填写");
                    response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/AdminServlet?op=findAllAdmin&num=1");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("error!");

            }

        }


    }

    private void findAllAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String num = request.getParameter("num");

        if (num!=null&&!num.isEmpty()){
            //页面范围的合法性验证 交给业务层

            PageInfo pageInfo= null;
            try {
                pageInfo = service.findPageAdmin(num);//获得pageinfo 对象，里面包含了页面集合信息和页数信息
                //System.out.println(pageInfo);
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("error!");
                return;
            }
            request.setAttribute("pageInfo",pageInfo);
            //System.out.println(pageInfo.getPageList());
            try {
                request.getRequestDispatcher("/admin/admin/adminList.jsp").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            }

        }


    }

    private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rpassword = request.getParameter("password1");
        Admin admin = new Admin();

        if (password!=null&&!password.isEmpty()){
            if (!password.equals(rpassword)){
                response.getWriter().println("密码不一致，请重新填写");
                response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/admin/addAdmin.jsp");
            }
        }
        if(username.isEmpty()||password.isEmpty()||rpassword.isEmpty()){
            response.getWriter().println("注册信息不可为空，请重新填写");
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/admin/addAdmin.jsp");
        }else {
            admin.setUsername(username);
            admin.setPassword(password);
            try {
                boolean b = service.addAdmin(admin);
                if (b){
                    response.getWriter().println("注册成功!");
                    response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/admin/addAdmin.jsp");
                }else {
                    response.getWriter().println("注册失败，请重新填写");
                    response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/admin/addAdmin.jsp");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().println("error!!! 添加系统用户失败！!");

            }

        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
