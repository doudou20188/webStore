package bean;

import java.util.ArrayList;

/**
 * @Auther: YangTao
 * @Date: 2018/11/15 0015
 */
public class ShoppingCar {
    //购物车类，每个购物车对应一个user 用户

    int sid ;
    int uid;
    ArrayList<ShoppingItem> shoppingItems;


    public ShoppingCar() {
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public ArrayList<ShoppingItem> getShoppingItems() {
        return shoppingItems;
    }

    public void setShoppingItems(ArrayList<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
    }

    @Override
    public String toString() {
        return "ShoppingCar{" +
                "sid=" + sid +
                ", uid=" + uid +
                ", shoppingItems=" + shoppingItems +
                '}';
    }
}
