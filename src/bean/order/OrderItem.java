package bean.order;

import bean.Product;

/**
 * @Auther: YangTao
 * @Date: 2018/11/15 0015
 */
public class OrderItem {
    //订单项类  与产品挂钩，一个订单关联多个产品
    String itemid;
    String oid;
    String pid;
    String buynum;//购买的数量
    Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBuynum() {
        return buynum;
    }

    public void setBuynum(String buynum) {
        this.buynum = buynum;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemid='" + itemid + '\'' +
                ", oid='" + oid + '\'' +
                ", pid='" + pid + '\'' +
                ", buynum='" + buynum + '\'' +
                ", product=" + product +
                '}';
    }
}
