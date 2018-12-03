package controller;

import bean.Category;
import bean.Product;
import service.ImpService;
import service.Service;
import utils.PageInfo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @Auther: YangTao
 * @Date: 2018/11/7 0007
 */
@WebServlet(name = "CategoryServlet",urlPatterns = "/admin/CategoryServlet")
public class CategoryServlet extends HttpServlet {
    Service service=new ImpService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op){
            case "addCategory":
                //添加类别 到类别表
                addCategory(request,response);
               break;
            case "findAllCategory":
            //显示所有分类,封装信息到session 域中 allCategory
                findAllCategory(request,response);
                break;

            case "updateCategory":
                updateCategory(request,response);
                //修改分类的名字
                break;

            case "deleCategory":
                deleCategory(request,response);
                //删除分类 操作
                break;

            case "findCategory":
                findCategory(request,response);
                //添加商品 存放到公共域 servletContext  categories 域名中
                break;

            case "findCategoryByUpdate":
                findCategoryByUpdate(request,response);
                //根据pid 获取相应产品


                break;

            case "findAllCategory1":
                //显示所有分类,封装信息到session 域中 allCategory
                HttpSession session = request.getSession();
                ArrayList<Category> allCategory1=service.allCategory();
                session.setAttribute("categories",allCategory1);
                //转发到categorylist.jsp
                request.getRequestDispatcher("/admin/product/searchProduct.jsp").forward(request,response);
                //response.setHeader("refresh","0;url="+request.getContextPath()+"/admin/product/searchProduct.jsp");


        }





    }

    private void findCategoryByUpdate(HttpServletRequest request, HttpServletResponse response) {
        String pid1 = request.getParameter("pid");
        Product product= null;
        try {
            product = service.getProduct(pid1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //更新categories 数据
        ArrayList<Category> categoryArrayList1 = service.allCategory();
        ServletContext servletContext1 = getServletContext();
        request.setAttribute("categories",categoryArrayList1);
        //servletContext1.setAttribute("categories",categoryArrayList1);
        // request.getSession().setAttribute("product",product);
        request.setAttribute("product",product);
        //response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/product/updateProduct.jsp");
        try {
            request.getRequestDispatcher("/admin/product/updateProduct.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<Category> categoryArrayList = service.allCategory();
        ServletContext servletContext = getServletContext();
        request.setAttribute("categories",categoryArrayList);
        //servletContext.setAttribute("categories",categoryArrayList);
        //response.setHeader("refresh","0;url="+request.getContextPath()+"/admin/product/addProduct.jsp");
        request.getRequestDispatcher("/admin/product/addProduct.jsp").forward(request,response);

    }

    private void deleCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cid1 = request.getParameter("cid");
        boolean b2=service.deleCategory(cid1);
        //跟新数据
        if (b2){
            response.getWriter().println("删除成功");//还没有完成检验是否重名
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/CategoryServlet?op=findAllCategory&num=1");
        }else {
            response.getWriter().println("删除失败，请先删除该分类的所有商品");//还没有完成检验是否重名
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/CategoryServlet?op=findAllCategory&num=1");
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cid = request.getParameter("cid");
        String cname1 = request.getParameter("cname");//获取修改后的名字
        boolean b= service.updateCategory(cid,cname1);
        if (b){
            response.getWriter().println("修改成功");//跳转到表单显示窗口
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/CategoryServlet?op=findAllCategory&num=1");
        }else {
            response.getWriter().println("内容不可为空");//还没有完成检验是否重名
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/category/updateCategory.jsp");
        }
    }

    //添加分类列表
    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cname = request.getParameter("cname");
        boolean flag=service.addCategory(cname);
        if (flag){
            response.getWriter().println("添加成功");
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/category/addCategory.jsp");
        }else {
            response.getWriter().println("请先输入信息");
            response.setHeader("refresh","1;url="+request.getContextPath()+"/admin/category/addCategory.jsp");
        }


    }

    private void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //用分页写

        String num = request.getParameter("num");

        if (num!=null&&!num.isEmpty()){
            //页面范围的合法性验证 交给业务层

            PageInfo pageInfo= null;
            try {
                pageInfo = service.findPageCategory(num);//获得pageinfo 对象，里面包含了页面集合信息和页数信息
                //System.out.println(pageInfo);
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().println("error!");
                return;
            }
            request.setAttribute("pageInfo",pageInfo);
            //System.out.println(pageInfo.getPageList());
            try {
                request.getRequestDispatcher("/admin/category/categoryList.jsp").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            }

        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
