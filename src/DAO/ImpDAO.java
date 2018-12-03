package DAO;

import bean.*;
import bean.order.Order;
import bean.order.OrderItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.C3P0Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: YangTao
 * @Date: 2018/11/7 0007
 */
public class ImpDAO implements DAO {
    public boolean addCategoryToDB(String name){
        boolean b=false;
        if (name!=null&&!name.isEmpty()){
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            try {
                int update = queryRunner.update("insert into category values(null,?)", name);
                if (update==1){
                    b=true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return b;
    }

    @Override
    public ArrayList<Category> queryAllCategoryFromDB() {
        ArrayList<Category> categoryArrayList=null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        try {
          categoryArrayList = (ArrayList<Category>) queryRunner.query("select * from category", new BeanListHandler<Category>(Category.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryArrayList;
    }

    @Override
    public boolean updateCategoryFromDB(String cid, String cname1) {
        boolean b=false;
        if (cid!=null&&!cid.isEmpty()&&cname1!=null&&!cname1.isEmpty()){
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            int update = 0;
            try {
                update = queryRunner.update("update category set cname=? where cid=?", cname1, cid);
                if (update==1){
                    b=true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return b;

    }

    @Override
    public boolean deleCategoryFromDB(String cid) {
        boolean b=false;
        if (cid!=null&&!cid.isEmpty()){
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());

            try {
                int update = queryRunner.update("delete from category where cid=?", cid);
                if (update==1){
                    b=true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return b;


    }

    @Override
    public boolean addProductionToDB(Product product) {
        boolean b=false;
        if (product!=null){
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());

            try {
                int update = queryRunner.update("insert into product values(?,?,?,?,?,?,?,?);", product.getPid(), product.getPname(),
                        product.getEstoreprice(), product.getMarkprice(), product.getPnum(), product.getCid(), product.getImgurl(), product.getDesc());
                if (update==1){
                    b=true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    /**
     * 已舍弃
     * @return
     */
    @Override
    public ArrayList<Product> queryAllProductsFromDB() {
        ArrayList<Product> productList=null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        try {
            productList = (ArrayList<Product>) queryRunner.query("select * from product", new BeanListHandler<Product>(Product.class));
            for (Product pp:
                    productList) {
                int cid = pp.getCid();
                Category category = queryRunner.query("select * from category where cid =?", new BeanHandler<Category>(Category.class), cid);
                pp.setCategory(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public boolean deleProductFromDB(String pid) throws SQLException {
        boolean b=false;
        if (pid!=null&&!pid.isEmpty()){
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            int update = queryRunner.update("delete from product where pid=?", pid);
            if (update==1){
                b=true;
            }
        }
        return b;
    }

    @Override
    public Product queryPorductByID(String pid1) throws SQLException {
        Product product=null;
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            product = queryRunner.query("select * from product where pid=?", new BeanHandler<Product>(Product.class), pid1);


        return product;
    }

    @Override
    public boolean updateProductFromDB(Product product) throws SQLException {
        boolean b=false;
        if (product!=null){
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            int update = queryRunner.update("update product set pid=?,pname=?,estoreprice=?,markprice=?,pnum=?,cid=?,imgurl=?,`desc`=?" +
                    "where pid=?", product.getPid(), product.getPname(), product.getEstoreprice(), product.getMarkprice(), product.getPnum(), product.getCid(), product.getImgurl(), product.getDesc(),product.getPid());
            if (update==1){
                b=true;
            }
        }
        return b;
    }

    @Override
    public boolean updateProductOTFromDB(Product product) throws SQLException {
        boolean b=false;
        if (product!=null){
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            int update = queryRunner.update("update product set pid=?,pname=?,estoreprice=?,markprice=?,pnum=?,cid=?,`desc`=?" +
                    "where pid=?", product.getPid(), product.getPname(), product.getEstoreprice(), product.getMarkprice(), product.getPnum(), product.getCid(), product.getDesc(),product.getPid());
            if (update==1){
                b=true;
            }
        }
        return b;
    }
    public ArrayList<Product> queryProductsFromDB(String pid, String cid, String pname, String minprice, String maxprice) throws SQLException {
        ArrayList arrayList=new ArrayList();
        String sql="select * from product where 1=1 ";
        if (pid!=null&&!pid.isEmpty()){
            sql=sql+"and pid=?";
            arrayList.add(pid);

        }
        if (cid!=null&&!cid.isEmpty()){
            sql=sql+"and cid=?";
            arrayList.add(cid);

        }
        if (pname!=null&&!pname.isEmpty()){
            sql=sql+"and pname=?";
            arrayList.add(pname);

        }
        if (minprice!=null&&!minprice.isEmpty()){
            sql=sql+"and estoreprice >?";
            arrayList.add(minprice);

        }
        if (maxprice!=null&&!maxprice.isEmpty()){
            sql=sql+"and estoreprice<?";
            arrayList.add(maxprice);

        }
        System.out.println(sql);
        Object[] params =arrayList.toArray();
        System.out.println(Arrays.toString(params));
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        ArrayList<Product> productList = (ArrayList<Product>) queryRunner.query(sql, new BeanListHandler<Product>(Product.class), params);

        return productList;

    }

//获取总的记录数
    @Override
    public int findTotalNumber() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getCpds());

        Long query = (Long) qr.query("select count(*) from category", new ScalarHandler());

        return query.intValue();


    }

    @Override
    public List<Category> findOnePageCategory(int limit, int offset) throws SQLException {
        //获得指定偏移量的集合
        QueryRunner qr = new QueryRunner(C3P0Utils.getCpds());
        List<Category> query = qr.query("select * from category limit  ? offset ? ", new BeanListHandler<>(Category.class), limit,offset);

        return query;
    }

    @Override
    public boolean addAdminToDB(Admin admin) throws SQLException {
        boolean b =false;
        if (admin!=null){
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            int update = queryRunner.update("insert into `admin` values(null,?,?)", admin.getUsername(), admin.getPassword());
            if (update==1){
                b=true;
            }

        }
        return b;
    }

    @Override
    public int findTotalNumberAdmin() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getCpds());

        Long query = (Long) qr.query("select count(*) from `admin`", new ScalarHandler());

        return query.intValue();
    }

    @Override
    public List<Admin> findOnePageAdmin(int limit, int offset) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getCpds());
        List<Admin> query = qr.query("select * from `admin` limit  ? offset ? ", new BeanListHandler<Admin>(Admin.class), limit,offset);

        return query;
    }

    @Override
    public boolean updateAdminFromDB(Admin admin) throws SQLException {
        boolean b= false;
        if (admin!=null){
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            int update = queryRunner.update("update `admin` set password=? where aid=?", admin.getPassword(), admin.getAid());
            if (update==1){
                b=true;
            }
        }

        return b;
    }

    @Override
    public boolean deleAdminFormDB(Admin admin) throws SQLException {
        boolean b =false;
        if (admin!=null){
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            int update = queryRunner.update("delete from `admin` where aid=? ", admin.getAid());
            if (update==1){
                b=true;
            }
        }
        return b;
    }

    @Override
    public int findTotalNumberProduct() throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getCpds());
        Long query = (Long) qr.query("select count(*) from `product`", new ScalarHandler());
        return query.intValue();
    }

    @Override
    public List<Product> findOnePageProduct(int limit, int offset) throws SQLException {
        //获得指定偏移量的集合
        List<Product> productList=null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        try {
            productList = queryRunner.query("select * from product limit  ? offset ? ", new BeanListHandler<Product>(Product.class), limit,offset);
            for (Product pp:
                    productList) {
                int cid = pp.getCid();
                Category category = queryRunner.query("select * from category where cid =?", new BeanHandler<Category>(Category.class), cid);
                pp.setCategory(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public Admin checkAdminFromDB(Admin admin) throws SQLException {
        boolean b =false;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        Admin query = queryRunner.query("select * from `admin` where username=?", new BeanHandler<Admin>(Admin.class), admin.getUsername());
        return query;

    }

    @Override
    public ArrayList<Product> queryPorductByCid(String cid) throws SQLException {
        ArrayList<Product> productArrayList=null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        productArrayList = (ArrayList<Product>) queryRunner.query("select *from product where cid=?", new BeanListHandler<Product>(Product.class), cid);
        return productArrayList;
    }

    @Override
    public ArrayList<Product> getProductByNameFromDB(String pname) throws SQLException {
        ArrayList<Product> productArrayList=null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        pname="%"+pname+"%";

        productArrayList = (ArrayList<Product>) queryRunner.query("select * from product where `desc` like ? ;", new BeanListHandler<Product>(Product.class), pname);

        return productArrayList;
    }

    /**
     * 获得活动商品集合
     * @return
     */
    @Override
    public ArrayList<Product> queryAllActiveProductsFromDB() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        ArrayList<Product> products = (ArrayList<Product>) queryRunner.query("select * from product where pname like '活动%'", new BeanListHandler<Product>(Product.class));


        return products;
    }

    /**
     * 获得出去活动外的所有产品
     * @return
     */
    @Override
    public ArrayList<Product> queryAllProductsExceptActiveFromDB() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        ArrayList<Product> query = (ArrayList<Product>) queryRunner.query("select * from product where pname not like '活动%'", new BeanListHandler<Product>(Product.class));

        return query;
    }

    @Override
    public boolean getUserByNameFromDB(String username) throws SQLException {
        boolean b =false;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        User user = queryRunner.query("select * from  user where username=?", new BeanHandler<User>(User.class), username);
        if (user!=null){
            b=true;
        }
        return b;

    }

    /**
     * 通过email 查看user 表中是否有该用户
     * @param email
     * @return
     */
    @Override
    public boolean getEmailFromUserDB(String email) throws SQLException {
        boolean b = false;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        User query = queryRunner.query("select * from user where email=?", new BeanHandler<>(User.class), email);
        if (query != null) {
            b=true;
        }

        return b;
    }

    @Override
    public boolean addUserToDB(User user) throws SQLException {
        boolean b = false;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int update = queryRunner.update("insert into user values(null,?,?,?,?,?,?,?,?)", user.getUsername(), user.getNickname()
                , user.getPassword(), user.getEmail(), user.getBirthday(), user.getUpdatetime(),user.getStaus(),user.getUuid());
        if (update==1){
            b= true;

        }
        return b;

    }

    @Override
    public User checkUserFromDB(User user) throws SQLException {
        String username = user.getUsername();
        String password = user.getPassword();
        User queryUser=null;
        if (username!=null&&!username.isEmpty()&&password!=null&&!password.isEmpty()){

            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            queryUser= queryRunner.query("select * from user where username=? and password=?", new BeanHandler<>(User.class), username, password);
        }
        return queryUser;
    }

    @Override
    public User updateUserFromDB(User user) throws SQLException {
        User newUser=null;
        if (user!=null){
            QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
            int update = queryRunner.update("update user set nickname=?,password=?,email=?,birthday=? where uid=?", user.getNickname()
                    , user.getPassword(), user.getEmail(), user.getBirthday(),user.getUid());
            if (update==1){
                newUser = queryRunner.query("select * from user where uid=?", new BeanHandler<>(User.class), user.getUid());
            }
        }
        return newUser;


    }

    @Override
    public User updateUserStausFromDBByUUID(String uuid) throws SQLException {
        User user = null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int update = queryRunner.update("update user set staus =? where uuid=?",1,uuid);
        if (update==1){
            user = queryRunner.query("select * from user where uuid=?", new BeanHandler<>(User.class), uuid);
        }
        return user;


    }

    @Override
    public int findTotalNumberUser() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        Long count = (Long) queryRunner.query("select count(*) from `user`", new ScalarHandler());
        return count.intValue();

    }

    @Override
    public List<User> findOnePageUser(int limit, int offset) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getCpds());
        List<User> userList = qr.query("select * from `user` limit  ? offset ? ", new BeanListHandler<>(User.class), limit,offset);
        return userList;
    }

    /**
     * 通过uid 获得购物车对象
     * @param uid
     * @return
     */
    @Override
    public ShoppingCar getShoppingCarByUidFromDB(int uid) throws SQLException {
        ShoppingCar shoppingCar= null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        shoppingCar = queryRunner.query("select  * from shoppingcar where uid=?", new BeanHandler<>(ShoppingCar.class), uid);
        return shoppingCar;
    }

    @Override
    public ArrayList<ShoppingItem> getShoppingItemsBySidFromDB(int sid) throws SQLException {
        ArrayList<ShoppingItem> shoppingItemArrayList=null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        shoppingItemArrayList= (ArrayList<ShoppingItem>) queryRunner.query("select * from shoppingitem where sid=?", new BeanListHandler<>(ShoppingItem.class), sid);

        return shoppingItemArrayList;
    }

    /**
     * 检验uid 在购物车是否存在
     * @param uid
     * @return
     */
    @Override
    public boolean isExistShoppingCarByUidFromDB(String uid) throws SQLException {
        boolean b = false;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        ShoppingCar query = queryRunner.query("select * from shoppingcar where uid= ?", new BeanHandler<>(ShoppingCar.class), uid);
        if (query!=null){
            b=true;
        }
        return b;

    }

    @Override
    public boolean addShoppingItemToDB(int sid, String pid,String snum) throws SQLException {
        boolean b =false;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int update = queryRunner.update("insert into shoppingitem values(null,?,?,?)",sid, pid, snum);
        if (update==1){
            b=true;
        }

        return b;
    }

    @Override
    public boolean addShoppingCarByUidToDB(String uid) throws SQLException {
        boolean b =false;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int update = queryRunner.update("insert into shoppingcar VALUES(null,?)", uid);
        if (update==1){
            b=true;
        }

        return b;
    }

    /**
     * 添加订单到表order
     * @param order
     * @return
     */
    @Override
    public boolean addOrderToDB(Order order) throws SQLException {
        boolean b= false;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int update = queryRunner.update("insert into `order` values(null,?,?,?,?,?,?,?)", order.getMoney(), order.getRecipients()
                , order.getTel(), order.getAddress(), order.getState(), order.getOrdertime(), order.getUid());
        if (update==1){
            b=true;
        }

        return b;
    }

    @Override
    public int addOrderItemToDB(OrderItem orderItem) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int update = queryRunner.update("insert into orderitem values(null,?,?,?)", orderItem.getOid(), orderItem.getPid(), orderItem.getBuynum());
        return update;
    }

    @Override
    public int updateProductByPidFormDB(String pnum,String pid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int update = queryRunner.update("update product set pnum=pnum-? where pid=?",pnum, pid);
        return update;


    }

    /**
     * 删除对应的购物车项表
     * @param sid
     * @return
     */
    @Override
    public int deleShoppingItemBySidFromDB(int sid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int update = queryRunner.update("delete from shoppingitem where sid=?", sid);
        return update;
    }

    @Override
    public ArrayList<Order> getAllOrderListByUidFromDB(String uid2) throws SQLException {
        ArrayList<Order> orderArrayList=null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        orderArrayList= (ArrayList<Order>) queryRunner.query("select *from `order` where uid=?", new BeanListHandler<>(Order.class), uid2);
        return orderArrayList;

    }

    @Override
    public Order getOrderByTimeFromDB(String temp_str) throws SQLException {
        Order order=null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        order= queryRunner.query("select *from `order` where ordertime=?", new BeanHandler<>(Order.class), temp_str);

        return order;
    }

    @Override
    public ArrayList<OrderItem> getOrderItemByOidFromDB(String oid) throws SQLException {
        ArrayList<OrderItem> arrayList=null;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        arrayList= (ArrayList<OrderItem>) queryRunner.query("select * from orderitem where oid =?", new BeanListHandler<>(OrderItem.class), oid);

        return arrayList;

    }

    @Override
    public boolean deleSHoppingItemByItemIDFromDB(String itemid) throws SQLException {
        boolean b =false;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int update = queryRunner.update("delete from shoppingitem where itemid=?", itemid);
        if (update==1){
            b=true;
        }

        return b;


    }

    @Override
    public boolean updateOrderStateByOidFromDB(String oid, String state) throws SQLException {
        boolean b =false;
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getCpds());
        int update = queryRunner.update("update `order` set state=? where oid=?", state, oid);
        if (update==1){
            b=true;
        }
        return b;

    }


}
