package service;

import bean.*;
import bean.order.Order;
import bean.order.OrderItem;
import utils.PageInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Auther: YangTao
 * @Date: 2018/11/7 0007
 */
public interface Service {
    /**
     *
     * 类别表增删改查api
     *
     */
    //添加分类标签
    boolean addCategory(String cname);
    //查询封装所有分类表的信息到category 对象
    ArrayList<Category> allCategory();
    //修改类别的cname
    boolean updateCategory(String cid, String cname1);
    //删除类别
    boolean deleCategory(String cid);

    /**
     *
     * 产品的增删改查api
     *
     */
    //添加产品
    boolean addProduct(Product product);

    ArrayList<Product> allProduct();

    boolean deleProduct(String pid) throws SQLException;

    Product getProduct(String pid1) throws SQLException;

    boolean updateProduct(Product product) throws SQLException;
    boolean updateProductOT(Product product) throws SQLException;
    ArrayList<Product> queryProducts(String pid, String cid, String pname, String minprice, String maxprice) throws SQLException;

    PageInfo findPageCategory(String num) throws Exception;

    boolean addAdmin(Admin admin) throws SQLException;

    PageInfo findPageAdmin(String num) throws Exception;

    boolean updateAdmin(Admin admin) throws SQLException;

    boolean deleAdmin(Admin admin) throws SQLException;

    PageInfo findPageProduct(String num) throws Exception;
    //添加商品 检验表单是否有空项
    boolean checkAddProduct(Map map);

    Admin checkAdmin(Admin admin) throws SQLException;

    ArrayList<Product> getProductByCid(String cid) throws SQLException;

    ArrayList<Product> getProductName(String pname) throws SQLException;

    ArrayList<Product> getAllActiceProduct() throws SQLException;

    ArrayList<Product> allProductExceptActive() throws SQLException;

    boolean getUserByName(String username) throws SQLException;

    boolean getEmail(String email) throws SQLException;

    boolean addUser(User user) throws SQLException;

    User checkUser(User user) throws SQLException;

    User updateUser(User user) throws SQLException;

    User updateUserStausByUUID(String uuid) throws SQLException;

    PageInfo findPageUsers(String num) throws Exception;

    ShoppingCar getshoppingCar(int uid) throws SQLException;

    ArrayList<ShoppingItem> getShoppingItemsBySID(int sid) throws SQLException;

    boolean isExistShoppingCar(String uid) throws SQLException;


    boolean addShoppingItem(int sid, String pid, String snum) throws SQLException;

    boolean addShoppingCar(String uid) throws SQLException;

    boolean addOrder(Order order) throws SQLException;

    int  addOrderItem(OrderItem orderItem) throws SQLException;

    int updateProductByPid(String pnum,String pid) throws SQLException;

    int deleShoppingItemBySid(int sid) throws SQLException;

    ArrayList<Order> getAllOrderListByUid(String uid2) throws SQLException;

    Order getOrderByTime(String temp_str) throws SQLException;

    ArrayList<OrderItem> getOrderItemByOid(String oid) throws SQLException;

    boolean deleShoppingItemByItemID(String itemid) throws SQLException;

    boolean updateOrderStateByOid(String oid, String state) throws SQLException;
}
