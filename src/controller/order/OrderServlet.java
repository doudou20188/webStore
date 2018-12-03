package controller.order;

import bean.Product;
import bean.ShoppingCar;
import bean.User;
import bean.order.Order;
import bean.order.OrderItem;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.commons.beanutils.BeanUtils;
import service.ImpService;
import service.Service;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @Auther: YangTao
 * @Date: 2018/11/15 0015
 */
@WebServlet(name = "OrderServlet",urlPatterns = "/user/OrderServlet")
public class OrderServlet extends HttpServlet {
    Service service=new ImpService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理订单
        //HttpSession session = request.getSession();
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String op = request.getParameter("op");
        switch (op){
            case "placeOrder":
                toPlaceOrder(request,response);
                break;

            case "myoid":
                toShowMyOrder(request,response);
                break;
            case "showThisOrder":
                try {

                    showThisOrder(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "cancelOrder":
                    cancelOrder(request,response);
                break;

        }
    }

    private void cancelOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String state = request.getParameter("state");
        String oid = request.getParameter("oid");
        //这里先修改字段，并没有还原订单商品数量
        boolean b= false;
        try {
            b = service.updateOrderStateByOid(oid,state);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().print("error!");
        }
        if (b){
            request.getRequestDispatcher("/user/OrderServlet?op=myoid").forward(request,response);
        }

    }

    /**
     * 查看该订单的内容
     * @param request
     * @param response
     */
    private void showThisOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String oid = request.getParameter("oid");
        ArrayList<OrderItem> orderItemArrayList=service.getOrderItemByOid(oid);
       // ArrayList<Product> productArrayList = new ArrayList<>();

        for (OrderItem ot:
             orderItemArrayList) {
            String pid = ot.getPid();
            String buynum = ot.getBuynum();
            Product product = service.getProduct(pid);
            product.setBuynum(buynum);
            ot.setProduct(product);
           // productArrayList.add(product);

        }
        System.out.println(orderItemArrayList);
        request.setAttribute("thisOrder",orderItemArrayList);
        request.getRequestDispatcher("/user/theOrderDetil.jsp").forward(request,response);


    }

    /**
     * 查看我的订单
     * @param request
     * @param response
     */
    private void toShowMyOrder(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        int uid = user.getUid();
        try {
            ArrayList<Order> allOrderListByUid = service.getAllOrderListByUid(String.valueOf(uid));
            request.getSession().setAttribute("orders",allOrderListByUid);
            request.getRequestDispatcher("/user/myOrders.jsp").forward(request,response);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void toPlaceOrder(HttpServletRequest request, HttpServletResponse response) {
        //下单操作
        //1 订单表添加一行，订单项添加多行

        Order order = new Order();
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            BeanUtils.populate(order,parameterMap);
            User user = (User) request.getSession().getAttribute("user");
            int uid = user.getUid();
            order.setUid(String.valueOf(uid));
            order.setState("1");
            String temp_str="";
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            temp_str=sdf.format(date);
            order.setOrdertime(temp_str);
             //System.out.println(order);
            //添加一条信息到order 表
            boolean b=service.addOrder(order);
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            String[] pids = request.getParameterValues("pid");
            String[] buynums = request.getParameterValues("buynum");
            int len =buynums.length;
            //System.out.println(Arrays.toString(pids));
            Order isThisOrder=service.getOrderByTime(temp_str);
            for (int i = 0; i <len ; i++) {
                OrderItem orderItem = new OrderItem();
                String theoid = isThisOrder.getOid();
                orderItem.setPid(pids[i]);
                orderItem.setBuynum(buynums[i]);//暂时设置为1，后期修改
                orderItem.setOid(theoid);
                int addOrderItem = service.addOrderItem(orderItem);
                orderItems.add(orderItem);

            }

        /*    for (String pp:
                 pids) {
                OrderItem orderItem = new OrderItem();
                String theoid = isThisOrder.getOid();
                orderItem.setPid(pp);
                orderItem.setOid(theoid);
                orderItem.setBuynum("1");//暂时设置为1，后期修改
                int addOrderItem = service.addOrderItem(orderItem);

                orderItems.add(orderItem);

            }*/
            order.setOrderItemArrayList(orderItems);
            //2.对应product 表单的pnum 数量对于应减少库存
            for (OrderItem orderItem:
                 order.getOrderItemArrayList()) {
                String pid = orderItem.getPid();
                String buynum = orderItem.getBuynum();

                int i=service.updateProductByPid(String.valueOf(buynum),pid);//减少对应数量的商品
            }

            //3.对应 用户uid的购物车项 shoppingitem表 清空数据
            String uid1 = order.getUid();
            ShoppingCar shoppingCar = service.getshoppingCar(uid);
            int sid = shoppingCar.getSid();
            int delete=service.deleShoppingItemBySid(sid);

            String uid2 = order.getUid();


            ArrayList<Order> orderArrayList=service.getAllOrderListByUid(uid2);

            //转向myOrder订单页面
            System.out.println(order);

            request.getSession().setAttribute("orders",orderArrayList);
            request.getRequestDispatcher("/user/myOrders.jsp").forward(request,response);


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);


    }
}
