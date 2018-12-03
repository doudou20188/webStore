package controller;

import bean.Category;
import bean.Product;
import service.ImpService;
import service.Service;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @Auther: YangTao
 * @Date: 2018/11/12 0012
 */
@WebServlet(name = "MainServlet",urlPatterns = "/MainServlet")
public class MainServlet extends HttpServlet {
    Service service=new ImpService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得所有的分类信息，放入application 域中返回给首页
        ArrayList<Category> categoryArrayList = service.allCategory();


        ArrayList<Product> hotProducts = null;
        try {
            hotProducts = service.allProductExceptActive();
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("error");
        }
        ArrayList<Product>topProducts=null;
        try {
            topProducts =service.getAllActiceProduct();
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("error");

        }
        ServletContext servletContext = getServletContext();


        servletContext.setAttribute("categories",categoryArrayList);
        request.setAttribute("topProducts",topProducts);//活动图片
        request.setAttribute("hotProducts",hotProducts);//热门商品
        request.getRequestDispatcher("/index.jsp").forward(request,response);



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
