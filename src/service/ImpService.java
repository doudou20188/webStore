package service;

import DAO.DAO;
import DAO.ImpDAO;
import bean.*;
import bean.order.Order;
import bean.order.OrderItem;
import utils.PageInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: YangTao
 * @Date: 2018/11/7 0007
 */
public class ImpService implements Service {
    DAO dao=new ImpDAO();
    private static  final int PAGE_COUNT=3;//常量，每页显示固定数目
    @Override
    public boolean addCategory(String cname) {
        boolean b = dao.addCategoryToDB(cname);
        return b;
    }

    @Override
    public ArrayList<Category> allCategory() {
        ArrayList<Category> allCategory= dao.queryAllCategoryFromDB();
        return allCategory;

    }

    @Override
    public boolean updateCategory(String cid, String cname1) {
        boolean b=dao.updateCategoryFromDB(cid,cname1);

        return b;
    }

    @Override
    public boolean deleCategory(String cid) {
       boolean b= dao.deleCategoryFromDB(cid);
       return b;

    }

    @Override
    public boolean addProduct(Product product) {
        boolean b= dao.addProductionToDB(product);

        return b;
    }

    @Override
    public ArrayList<Product> allProduct() {
        ArrayList<Product> productArrayList=dao.queryAllProductsFromDB();
        return productArrayList;
    }

    @Override
    public boolean deleProduct(String cid) throws SQLException {

         boolean b=dao.deleProductFromDB(cid);
         return b;
    }

    @Override
    public Product getProduct(String pid1) throws SQLException {
        Product product = dao.queryPorductByID(pid1);
        return product;
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {
        boolean b=dao.updateProductFromDB(product);
        return b;
    }
    //更新商品信息，不更新图片地址
    @Override
    public boolean updateProductOT(Product product) throws SQLException {
        boolean b=dao.updateProductOTFromDB(product);
        return b;
    }

    @Override
    public ArrayList<Product> queryProducts(String pid, String cid, String pname, String minprice, String maxprice) throws SQLException {

        ArrayList<Product> productArrayList = dao.queryProductsFromDB(pid, cid, pname, minprice, maxprice);
        return productArrayList;
    }

    /**
     *
     * @param num 参数页码数
     * @return
     * @throws SQLException
     */
    @Override
    public PageInfo findPageCategory(String num) throws Exception {
        PageInfo<Category> pageInfo=new PageInfo();
        int pageNumber =-1;
        try {
            pageNumber = Integer.parseInt(num);
        }catch (Exception e){
            return  null;
        }

        //查询下总的记录数
        int totalNumber = dao.findTotalNumber();
        // 如果当前页面超出范围，则直接报错

        //总的记录数
        pageInfo.setTotalRecordNumber(totalNumber);
        //当前的页码
        pageInfo.setCurrentPageNumber(pageNumber);
        //总的页数
        int totalpageNumber = (totalNumber + PAGE_COUNT - 1) / PAGE_COUNT;
        pageInfo.setTotalPageNumber(totalpageNumber);
        //页码范围后端校验
        if (pageNumber>totalpageNumber){
            throw new Exception("页码超出范围！");
        }
        //上一页
        pageInfo.setPreviousPageNumber(pageNumber==1?pageNumber:pageNumber-1);
        //下一页
        pageInfo.setNextPageNumber(pageNumber==totalpageNumber?totalpageNumber:pageNumber+1);

        int limit = PAGE_COUNT;
        int offset = (pageNumber-1)*PAGE_COUNT;
        //获得对应页数的对象集合
        List<Category> categories= dao.findOnePageCategory(limit,offset);
        pageInfo.setPageList(categories);
        return pageInfo;
    }

    /**
     * 系统用户注册
     * @param admin
     * @return
     */
    @Override
    public boolean addAdmin(Admin admin) throws SQLException {
        boolean b=dao.addAdminToDB(admin);
        return b;
    }

    @Override
    public PageInfo findPageAdmin(String num) throws Exception {
        PageInfo<Admin> pageInfo=new PageInfo();
        int pageNumber =-1;
        try {
            pageNumber = Integer.parseInt(num);
        }catch (Exception e){
            return  null;
        }

        //查询下总的记录数
        int totalNumber = dao.findTotalNumberAdmin();
        // 如果当前页面超出范围，则直接报错

        //总的记录数
        pageInfo.setTotalRecordNumber(totalNumber);
        //当前的页码
        pageInfo.setCurrentPageNumber(pageNumber);
        //总的页数
        int totalpageNumber = (totalNumber + PAGE_COUNT - 1) / PAGE_COUNT;
        pageInfo.setTotalPageNumber(totalpageNumber);
        //页码范围后端校验
        if (pageNumber>totalpageNumber){
            throw new Exception("页码超出范围！");
        }
        //上一页
        pageInfo.setPreviousPageNumber(pageNumber==1?pageNumber:pageNumber-1);
        //下一页
        pageInfo.setNextPageNumber(pageNumber==totalpageNumber?totalpageNumber:pageNumber+1);

        int limit = PAGE_COUNT;
        int offset = (pageNumber-1)*PAGE_COUNT;
        //通过偏移量获得相应页面的集合
        List<Admin> admin= dao.findOnePageAdmin(limit,offset);
        pageInfo.setPageList(admin);
        return pageInfo;
    }

    @Override
    public boolean updateAdmin(Admin admin) throws SQLException {
        boolean b=dao.updateAdminFromDB(admin);
        return b;

    }

    @Override
    public boolean deleAdmin(Admin admin) throws SQLException {
        boolean b=dao.deleAdminFormDB(admin);
        return b;
    }

    /**
     * 获取产品页面的页数数据集合
     * @param num
     * @return
     */
    @Override
    public PageInfo findPageProduct(String num) throws Exception {
        PageInfo<Product> pageInfo=new PageInfo();
        int pageNumber =-1;
        try {
            pageNumber = Integer.parseInt(num);
        }catch (Exception e){
            return  null;
        }
        //查询下总的记录数
        int totalNumber = dao.findTotalNumberProduct();//
        //总的记录数
        pageInfo.setTotalRecordNumber(totalNumber);
        //当前的页码
        pageInfo.setCurrentPageNumber(pageNumber);
        //总的页数
        int totalpageNumber = (totalNumber + PAGE_COUNT - 1) / PAGE_COUNT;
        pageInfo.setTotalPageNumber(totalpageNumber);
        //页码范围后端校验
        if (pageNumber>totalpageNumber){
            throw new Exception("页码超出范围！");
        }
        //上一页
        pageInfo.setPreviousPageNumber(pageNumber==1?pageNumber:pageNumber-1);
        //下一页
        pageInfo.setNextPageNumber(pageNumber==totalpageNumber?totalpageNumber:pageNumber+1);

        int limit = PAGE_COUNT;
        int offset = (pageNumber-1)*PAGE_COUNT;

        //通过偏移量获得相应页面的集合
        List<Product> productList= dao.findOnePageProduct(limit,offset);
        pageInfo.setPageList(productList);
        return pageInfo;
    }

    @Override
    public boolean checkAddProduct(Map map) {
        boolean b =false;
        String estoreprice = (String) map.get("estoreprice");
        String imgurl = (String) map.get("imgurl");
        String pnum = (String) map.get("pnum");
        String pname = (String) map.get("pname");
        String pid = (String) map.get("pid");
        String markprice = (String) map.get("markprice");
        String cid = (String) map.get("cid");
        String desc = (String) map.get("desc");

        /*  {estoreprice=, imgurl=/admin/pic/, pnum=, pname=, pid=, markprice=, cid=1, desc=}*/
        if (estoreprice==null||estoreprice.isEmpty()||imgurl==null||imgurl.isEmpty()||pnum==null||pnum.isEmpty()||
        pname==null||pname.isEmpty()||pid==null||pid.isEmpty()||markprice==null||markprice.isEmpty()||cid==null||cid.isEmpty()||
        desc==null||desc.isEmpty()){
            b=true;
        }

        return b;
    }

    @Override
    public Admin checkAdmin(Admin admin) throws SQLException {
        Admin admin1=dao.checkAdminFromDB(admin);
        return admin1;
    }

    @Override
    public ArrayList<Product> getProductByCid(String cid) throws SQLException {
        ArrayList<Product> productArrayList=dao.queryPorductByCid(cid);
        return productArrayList;
    }

    @Override
    public ArrayList<Product> getProductName(String pname) throws SQLException {
        ArrayList<Product> productArrayList=dao.getProductByNameFromDB(pname);
        return productArrayList;
    }

    @Override
    public ArrayList<Product> getAllActiceProduct() throws SQLException {
        ArrayList<Product> activeProducts=dao.queryAllActiveProductsFromDB();
        return activeProducts;
    }

    @Override
    public ArrayList<Product> allProductExceptActive() throws SQLException {
        ArrayList<Product> productArrayList=dao.queryAllProductsExceptActiveFromDB();
        return productArrayList;
    }

    @Override
    public boolean getUserByName(String username) throws SQLException {
        boolean b=dao.getUserByNameFromDB(username);
        return b;
    }

    @Override
    public boolean getEmail(String email) throws SQLException {
        boolean b =dao.getEmailFromUserDB(email);
        return b;
    }

    @Override
    public boolean addUser(User user) throws SQLException {
        //检验 user 注册注册内容是否为空
        boolean b = false;
        if (user.getUsername().isEmpty()||user.getPassword().isEmpty()||user.getNickname().isEmpty()||user.getBirthday().isEmpty()){
            b =false;
            return b;
        }
        b = dao.addUserToDB(user);

        return b;


    }

    @Override
    public User checkUser(User user) throws SQLException {
        User queryUser=dao.checkUserFromDB(user);

        return queryUser;

    }

    @Override
    public User updateUser(User user) throws SQLException {
        User user1= dao.updateUserFromDB(user);
        return user1;


    }

    @Override
    public User updateUserStausByUUID(String uuid) throws SQLException {
        User user =dao.updateUserStausFromDBByUUID(uuid);
        return user;
    }

    /**
     * 获取user 分页的集合
     * @param num
     * @return
     */
    @Override
    public PageInfo findPageUsers(String num) throws Exception {
        PageInfo<User> pageInfo=new PageInfo();
        int pageNumber =-1;
        try {
            pageNumber = Integer.parseInt(num);
        }catch (Exception e){
            return  null;
        }
        //查询下总的记录数
        int totalNumber = dao.findTotalNumberUser();
        //总的记录数
        pageInfo.setTotalRecordNumber(totalNumber);
        //当前的页码
        pageInfo.setCurrentPageNumber(pageNumber);
        //总的页数
        int totalpageNumber = (totalNumber + PAGE_COUNT - 1) / PAGE_COUNT;
        pageInfo.setTotalPageNumber(totalpageNumber);
        //页码范围后端校验
        if (pageNumber>totalpageNumber){
            throw new Exception("页码超出范围！");
        }
        //上一页
        pageInfo.setPreviousPageNumber(pageNumber==1?pageNumber:pageNumber-1);
        //下一页
        pageInfo.setNextPageNumber(pageNumber==totalpageNumber?totalpageNumber:pageNumber+1);
        int limit = PAGE_COUNT;
        int offset = (pageNumber-1)*PAGE_COUNT;
        //通过偏移量获得相应页面的集合
        List<User> productList= dao.findOnePageUser(limit,offset);
        pageInfo.setPageList(productList);
        return pageInfo;



    }

    /**
     * 购物车
     * @param uid
     * @return
     */
    @Override
    public ShoppingCar getshoppingCar(int uid) throws SQLException {
        ShoppingCar shoppingCar =dao.getShoppingCarByUidFromDB(uid);
        return shoppingCar;
    }

    @Override
    public ArrayList<ShoppingItem> getShoppingItemsBySID(int sid) throws SQLException {
        ArrayList<ShoppingItem> shoppingItemArrayList=null;
        shoppingItemArrayList=dao.getShoppingItemsBySidFromDB(sid);
        return shoppingItemArrayList;
    }

    @Override
    public boolean isExistShoppingCar(String uid) throws SQLException {
        boolean b=dao.isExistShoppingCarByUidFromDB(uid);

        return b;
    }

    @Override
    public boolean addShoppingItem(int sid, String pid, String snum) throws SQLException {
        boolean b =false;
        b=dao.addShoppingItemToDB(sid,pid,snum);
        return b;

    }

    @Override
    public boolean addShoppingCar(String uid) throws SQLException {
        boolean b=dao.addShoppingCarByUidToDB(uid);
        return b;
    }

    @Override
    public boolean addOrder(Order order) throws SQLException {
        boolean b =dao.addOrderToDB(order);
        return b;
    }

    @Override
    public int addOrderItem(OrderItem orderItem) throws SQLException {
        int i =dao.addOrderItemToDB(orderItem);
        return  i;
    }

    @Override
    public int updateProductByPid(String pnum,String pid) throws SQLException {
        int i =dao.updateProductByPidFormDB(pnum,pid);
        return i;
    }

    @Override
    public int deleShoppingItemBySid(int sid) throws SQLException {
        int i = dao.deleShoppingItemBySidFromDB(sid);
        return i;
    }

    @Override
    public ArrayList<Order> getAllOrderListByUid(String uid2) throws SQLException {
        ArrayList<Order> arrayList=dao.getAllOrderListByUidFromDB(uid2);
        return arrayList;

    }

    @Override
    public Order getOrderByTime(String temp_str) throws SQLException {
        Order order=dao.getOrderByTimeFromDB(temp_str);
        return order;
    }

    @Override
    public ArrayList<OrderItem> getOrderItemByOid(String oid) throws SQLException {
        ArrayList<OrderItem> arrayList=dao.getOrderItemByOidFromDB(oid);
        return arrayList;

    }

    @Override
    public boolean deleShoppingItemByItemID(String itemid) throws SQLException {
       boolean b = dao.deleSHoppingItemByItemIDFromDB(itemid);
       return b;
    }

    @Override
    public boolean updateOrderStateByOid(String oid, String state) throws SQLException {
        boolean b = dao.updateOrderStateByOidFromDB(oid,state);
        return b;

    }


}
