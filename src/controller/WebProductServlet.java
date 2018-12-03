package controller;

import bean.Product;
import service.ImpService;
import service.Service;

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
@WebServlet(name = "WebProductServlet",urlPatterns = "/WebProductServlet")
public class WebProductServlet extends HttpServlet {
    Service service =new ImpService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //前端的产品处理页面,分开写，避免后端进行过滤
        String op = request.getParameter("op");
        switch (op){
            case "findProductByCid":
                findProductByCid(request,response);
                break;
            case "findProductsByName":
                try {
                    findProductsByName(request,response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "findProductByPid":
                findProductByPid(request,response);
                break;

        }




    }

    private void findProductByPid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pid = request.getParameter("pid");
        try {
            Product product = service.getProduct(pid);
            request.getSession().setAttribute("product",product);
            request.getRequestDispatcher("/productdetail.jsp").forward(request,response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("error");
        } catch (ServletException e) {
            e.printStackTrace();
        }


    }

    private void findProductsByName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pname = request.getParameter("pname");
        ArrayList<Product> productArrayList= null;
        try {
            productArrayList = service.getProductName(pname);
            //System.out.println(productArrayList);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("error");
        }
        request.setAttribute("searchProducts",productArrayList);
        request.getRequestDispatcher("/searchProducts.jsp").forward(request,response);



    }

    /**
     * 通过侧边栏点击，获取相应的商品集合
     * @param request
     * @param response
     */
    private void findProductByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        ArrayList<Product> arrayListProducts= null;
        try {
            arrayListProducts = service.getProductByCid(cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(arrayListProducts);
        request.setAttribute("products",arrayListProducts);
        request.getRequestDispatcher("/products.jsp").forward(request,response);


    }
}
