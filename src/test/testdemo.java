package test;

import bean.Category;
import bean.Product;
import bean.User;
import org.junit.Assert;
import org.junit.Test;
import service.ImpService;
import service.Service;
import utils.MD5Util;
import utils.SendJMailUtils;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

/**
 * @Auther: YangTao
 * @Date: 2018/11/7 0007
 */
public class testdemo {

    @Test
    public void test1(){
        Service service=new ImpService();
        boolean b = service.addCategory("辅助");
        Assert.assertTrue(b);

    }
    @Test
    public void test2(){
        Service service=new ImpService();
        ArrayList<Category> categoryArrayList = service.allCategory();
        System.out.println(categoryArrayList);
        //Assert.assertTrue(b);
    }
    @Test
    public void test3(){
        Service service=new ImpService();
        boolean yangtao123 = service.updateCategory("2", "yangtao123");
        Assert.assertTrue(yangtao123);
    }
    @Test
    public void test4(){
        Service service=new ImpService();
        boolean b = service.deleCategory("10");

        Assert.assertTrue(b);
    }
    @Test
    public void test5(){
        Service service=new ImpService();
        ArrayList<Product> productArrayList = service.allProduct();
        Category category = productArrayList.get(0).getCategory();
        System.out.println(category);

        //System.out.println(productArrayList);

        //Assert.assertTrue(b);
    }

    @Test
    public void test6(){
        Service service=new ImpService();
        try {
            Product product = service.getProduct("101");
            System.out.println(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //System.out.println(productArrayList);

        //Assert.assertTrue(b);
    }
    @Test
    public void test7(){
        Service service=new ImpService();

        try {
            ArrayList<Product> productArrayList = service.queryProducts("109", "", "", "", "");
            System.out.println(productArrayList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Assert.assertTrue(b);
    }

    @Test
    public void test8(){

     /*   String line = "0.5";
        boolean matches = line.matches("0\\.[0-9]]");
        System.out.println(matches);*/

        String temp_str="";
        Date date = new Date();
        //    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        temp_str=sdf.format(date);

        System.out.println(temp_str);

    }

    /**
     * 邮箱发送验证
     */
    @Test
    public void test9(){
        String message="<a href='http://192.168.4.15:80/ActiveUser?http://192.168.4.24:80/ActiveUser?'>点击我激活</a>";
        SendJMailUtils.sendMail("940702494@qq.com",message);


    }

    /**
     * MD5 hash 算法，用于密码加密储存
     */
    @Test
    public void test10(){
        String md5 = MD5Util.getMD5("2333");
        System.out.println(md5);

    }
    @Test
    public void test11(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(uuid);

    }
    @Test
    public void test12() throws SQLException {
        Service service = new ImpService();
        User user = service.updateUserStausByUUID("094249f7fa544e3faa509f147a4b42fb");
        System.out.println(user);

    }
    @Test
    public void test13() throws SQLException {
        Service service = new ImpService();
        boolean existShoppingCar = service.isExistShoppingCar("1");
        System.out.println(existShoppingCar);

    }
/*    @Test
    public void test14() throws SQLException {
        Service service = new ImpService();
        service.addOrder()
        System.out.println(existShoppingCar);

    }*/



}
