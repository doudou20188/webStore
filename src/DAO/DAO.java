package DAO;

import bean.*;
import bean.order.Order;
import bean.order.OrderItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: YangTao
 * @Date: 2018/11/7 0007
 */
public interface DAO {
    boolean addCategoryToDB(String name);
    //查询所有类别
    ArrayList<Category> queryAllCategoryFromDB();

    boolean updateCategoryFromDB(String cid, String cname1);

    boolean deleCategoryFromDB(String cid);

    boolean addProductionToDB(Product product);

    ArrayList<Product> queryAllProductsFromDB();

    boolean deleProductFromDB(String cid) throws SQLException;

    Product queryPorductByID(String pid1) throws SQLException;

    boolean updateProductFromDB(Product product) throws SQLException;

    boolean updateProductOTFromDB(Product product) throws SQLException;

    ArrayList<Product> queryProductsFromDB(String pid, String cid, String pname, String minprice, String maxprice) throws SQLException;


    int findTotalNumber() throws SQLException;

    List<Category> findOnePageCategory(int limit, int offset) throws SQLException;

    boolean addAdminToDB(Admin admin) throws SQLException;

    int findTotalNumberAdmin() throws SQLException;

    List<Admin> findOnePageAdmin(int limit, int offset) throws SQLException;

    boolean updateAdminFromDB(Admin admin) throws SQLException;

    boolean deleAdminFormDB(Admin admin) throws SQLException;

    int findTotalNumberProduct() throws SQLException;

    List<Product> findOnePageProduct(int limit, int offset) throws SQLException;

    Admin checkAdminFromDB(Admin admin) throws SQLException;

    ArrayList<Product> queryPorductByCid(String cid) throws SQLException;

    ArrayList<Product> getProductByNameFromDB(String pname) throws SQLException;

    ArrayList<Product> queryAllActiveProductsFromDB() throws SQLException;

    ArrayList<Product> queryAllProductsExceptActiveFromDB() throws SQLException;

    boolean getUserByNameFromDB(String username) throws SQLException;

    boolean getEmailFromUserDB(String email) throws SQLException;

    boolean addUserToDB(User user) throws SQLException;

    User checkUserFromDB(User user) throws SQLException;

    User updateUserFromDB(User user) throws SQLException;

    User updateUserStausFromDBByUUID(String uuid) throws SQLException;

    int findTotalNumberUser() throws SQLException;

    List<User> findOnePageUser(int limit, int offset) throws SQLException;

    ShoppingCar getShoppingCarByUidFromDB(int uid) throws SQLException;

    ArrayList<ShoppingItem> getShoppingItemsBySidFromDB(int sid) throws SQLException;

    boolean isExistShoppingCarByUidFromDB(String uid) throws SQLException;

    boolean addShoppingItemToDB(int sid, String pid,String snum) throws SQLException;

    boolean addShoppingCarByUidToDB(String uid) throws SQLException;

    boolean addOrderToDB(Order order) throws SQLException;

    int addOrderItemToDB(OrderItem orderItem) throws SQLException;

    int updateProductByPidFormDB(String pnum,String pid) throws SQLException;

    int deleShoppingItemBySidFromDB(int sid) throws SQLException;

    ArrayList<Order> getAllOrderListByUidFromDB(String uid2) throws SQLException;

    Order getOrderByTimeFromDB(String temp_str) throws SQLException;

    ArrayList<OrderItem> getOrderItemByOidFromDB(String oid) throws SQLException;


    boolean deleSHoppingItemByItemIDFromDB(String itemid) throws SQLException;

    boolean updateOrderStateByOidFromDB(String oid, String state) throws SQLException;
}
