package controller;

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
 * @Date: 2018/11/10 0010
 */
@WebServlet(name = "SearchProductServlet",urlPatterns = "/admin/SearchProductServlet")
public class SearchProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("id");
        String cid = request.getParameter("cid");
        String pname = request.getParameter("name");
        String minprice = request.getParameter("minprice");
        String maxprice = request.getParameter("maxprice");
        Service service=new ImpService();
        try {
            ArrayList<Product> productArrayList=service.queryProducts(pid,cid,pname,minprice,maxprice);

            request.setAttribute("searchProducts",productArrayList);
            request.getRequestDispatcher("/admin/product/searchProduct.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
