package order.domain;

import com.mchange.v2.c3p0.DataSources;
import user.domain.user;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/27 19:17
 * @email: wangyuhang_mocas@163.com
 */
public class order {
    private  String oid;
    private Date orderTime;//下单时间
    private  double total;//合计
    private user ower;//订单所有者
    private String address;
    private  int state;//订单状态
    /*1 未付款
    * 2已付款，但未发货
    * 3. 已发货，但未确认收货
    * 4已确认，交易成功*/

    private List<orderItem> orderItemList;//当前订单下所有条目

    public List<orderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<orderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public user getOwer() {
        return ower;
    }

    public void setOwer(user ower) {
        this.ower = ower;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
