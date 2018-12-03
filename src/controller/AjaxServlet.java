package controller;

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
 * @Date: 2018/11/13 0013
 */
@WebServlet(name = "AjaxServlet",urlPatterns = "/AjaxServlet")
public class AjaxServlet extends HttpServlet {
    Service service=new ImpService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op){
            case "isuser":
                forusername(request,response);
                break;

            case "isemail":
                foremail(request,response);
                break;


        }



    }

    private void foremail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");

        try {
           boolean b = service.getEmail(email);
            if (b){
                //邮箱已经存在
                response.getWriter().print("false");
            }else {
                response.getWriter().print("true");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().print("error!");
        }


    }

    private void forusername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        boolean b= false;
        try {
            b = service.getUserByName(username);
            if (b){
                //用户名已存在
                response.getWriter().print("false");
            }else {
                response.getWriter().print("true");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().print("error!");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
