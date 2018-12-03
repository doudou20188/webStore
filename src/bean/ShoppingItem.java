package bean;

import java.util.ArrayList;

/**
 * @Auther: YangTao
 * @Date: 2018/11/15 0015
 */
public class ShoppingItem {
    //购物项表，每一个购物车对应多个商品
    int itemid;
    int sid;
    String pid;
    int snum;
    Product product;

    public int getSnum() {
        return snum;
    }

    public void setSnum(int snum) {
        this.snum = snum;
    }

    public ShoppingItem() {
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ShoppingItem{" +
                "itemid=" + itemid +
                ", sid=" + sid +
                ", pid='" + pid + '\'' +
                ", snum=" + snum +
                ", product=" + product +
                '}';
    }
}

