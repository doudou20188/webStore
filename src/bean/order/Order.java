package bean.order;

import java.util.ArrayList;

/**
 * @Auther: YangTao
 * @Date: 2018/11/15 0015
 */
public class Order {
    //订单类  订单与user 关联， 一个用户user 对应多个order
    String oid;
    String money;
    String recipients;

    String tel;
    String address;
    String state;  ;//标志订单是否取消  取消为0
    String ordertime;
    String uid;
    ArrayList<OrderItem> orderItemArrayList;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<OrderItem> getOrderItemArrayList() {
        return orderItemArrayList;
    }

    public void setOrderItemArrayList(ArrayList<OrderItem> orderItemArrayList) {
        this.orderItemArrayList = orderItemArrayList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid='" + oid + '\'' +
                ", money='" + money + '\'' +
                ", recipients='" + recipients + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", ordertime='" + ordertime + '\'' +
                ", uid='" + uid + '\'' +
                ", orderItemArrayList=" + orderItemArrayList +
                '}';
    }

}
