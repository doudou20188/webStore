package controller;

import bean.Product;
import bean.ShoppingCar;
import bean.ShoppingItem;
import bean.User;
import service.ImpService;
import service.Service;

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
 * @Date: 2018/11/15 0015
 */
@WebServlet(name = "CartServlet",urlPatterns = "/user/CartServlet")
public class CartServlet extends HttpServlet {
    Service service =new ImpService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //购物车处理项
        String op = request.getParameter("op");
        HttpSession session = request.getSession();
        switch (op){
            case "addCart":
                //添加商品到购物车，
                try {
                    toAddProductToCart(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "findCart":
                findCart(request,response);
                break;

            case "delItem":
                deletitem(request,response);
                break;

        }



    }



    /**
     * 删除相应的订单项目
     * @param request
     * @param response
     */
    private void deletitem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemid = request.getParameter("itemid");
        boolean b= false;
        try {
            b = service.deleShoppingItemByItemID(itemid);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().print("error!");
        }
        if (b){
            request.getRequestDispatcher("/user/CartServlet?op=findCart").forward(request,response);
        }

    }

    /**
     * 获取所有的购物车 商品
     * @param request
     * @param response
     */

    private void findCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // uid -sid -pid  product 集合
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int uid = user.getUid();
        ShoppingCar shoppingCar= null;
        try {
            shoppingCar = service.getshoppingCar(uid);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        int sid = shoppingCar.getSid();
        ArrayList<ShoppingItem> shoppingItemArrayList= null;
        try {
            shoppingItemArrayList = service.getShoppingItemsBySID(sid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (ShoppingItem ss:
             shoppingItemArrayList) {

            String pid = ss.getPid();
            Product product = null;
            try {
                product = service.getProduct(pid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ss.setProduct(product);

        }
        //将items 集合封装到 shoppingcar 集合中
        shoppingCar.setShoppingItems(shoppingItemArrayList);
        session.setAttribute("shoppingCart",shoppingCar);
        request.getRequestDispatcher("/user/shoppingcart.jsp").forward(request,response);

    }

    /**
     *  uid pid 添加到购物车中
     * @param request
     * @param response
     */
    private void toAddProductToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        // uid pid 对应两张表都需要添加相应数据
        //shoppingcar 标志一个用户的购物车，同一个用户添加商品该表不应发生变化,需判断若添加多个商品
        String uid = request.getParameter("uid");
        String pid = request.getParameter("pid");
        String snum = request.getParameter("snum");
        if (snum==null||snum.isEmpty()){
            snum="1";
        }
        boolean b =false;
        //该用户是否存在订单
        try {
            b=service.isExistShoppingCar(uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //已有购物车
        if (b){
            //shoppingcar 表不需要更新，添加shoppingitem 即可
            ShoppingCar shoppingCar2= null;
            try {
                shoppingCar2 = service.getshoppingCar(Integer.parseInt(uid));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int sid = shoppingCar2.getSid();
            try {
                boolean b1=service.addShoppingItem(sid,pid,snum);
                if (b1){

                    request.getRequestDispatcher("/user/CartServlet?op=findCart").forward(request,response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            /**
             * 第一次登陆的用户  暂未测验
             */
        }else {//第一次添加
            //shopping 表添加数据
            boolean b4 =service.addShoppingCar(uid);
            ShoppingCar shoppingCar = service.getshoppingCar(Integer.parseInt(uid));
            int thesid = shoppingCar.getSid();
            //根据shoppingcar  sid 添加数据到 shopping items
            try {
                boolean b3=service.addShoppingItem(thesid,pid, snum);
                if (b3){
                    request.getRequestDispatcher("/user/CartServlet?op=findCart").forward(request,response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }



        }


    }




}
