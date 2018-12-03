package bean;

/**
 * @Auther: YangTao
 * @Date: 2018/11/9 0009
 */
public class Product {
    //产品类
    String pid;
    String pname;
    Double estoreprice;
    Double markprice;
    int pnum;
    int cid;
    String imgurl;//图片的地址信息
    String desc;
    Category category;
    String cname;
    String buynum;

    public String getBuynum() {
        return buynum;
    }

    public void setBuynum(String buynum) {
        this.buynum = buynum;
    }

    public Product() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Double getEstoreprice() {
        return estoreprice;
    }

    public void setEstoreprice(Double estoreprice) {
        this.estoreprice = estoreprice;
    }

    public Double getMarkprice() {
        return markprice;
    }

    public void setMarkprice(Double markprice) {
        this.markprice = markprice;
    }

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", estoreprice=" + estoreprice +
                ", markprice=" + markprice +
                ", pnum=" + pnum +
                ", cid=" + cid +
                ", imgurl='" + imgurl + '\'' +
                ", desc='" + desc + '\'' +
                ", category=" + category +
                ", cname='" + cname + '\'' +
                ", buynum='" + buynum + '\'' +
                '}';
    }
}
