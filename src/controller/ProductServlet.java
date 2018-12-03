package controller;

import bean.Product;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.ImpService;
import service.Service;
import utils.PageInfo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

/**
 * @Auther: YangTao
 * @Date: 2018/11/9 0009
 */
@WebServlet(name = "ProductServlet",urlPatterns = "/admin/ProductServlet")
public class ProductServlet extends HttpServlet {
    Service service=new ImpService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);



    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Service service=new ImpService();
        ServletContext servletContext = getServletContext();
        String op = request.getParameter("op");
        switch (op){
            case "findAllProduct":
                findAllProduct(request,response);

                break;
            case "deleteOne":
                //删除对应商品信息
                deleteOne(request,response);
                break;


        }

    }

    private void deleteOne(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String pid = request.getParameter("pid");
        boolean b= false;
        try {
            b = service.deleProduct(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (b){
            response.getWriter().println("删除成功！");
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/ProductServlet?op=findAllProduct&num=1");

        }else {
            response.getWriter().println("删除失败！");
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/ProductServlet?op=findAllProduct&num=1");
        }

    }

    private void findAllProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String num = request.getParameter("num");
        if (num!=null&&!num.isEmpty()){
            PageInfo pageInfo= null;
            try {
                pageInfo = service.findPageProduct(num);//获得pageinfo 对象，里面包含了页面集合信息和页数信息
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("error!");
                return;
            }
            request.setAttribute("pageInfo",pageInfo);
            try {
                request.getRequestDispatcher("/admin/product/productList.jsp").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            }

        }
    }
}
