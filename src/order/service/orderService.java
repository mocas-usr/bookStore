package order.service;

import cn.itcast.jdbc.JdbcUtils;
import order.dao.orderDao;
import order.domain.order;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/27 19:36
 * @email: wangyuhang_mocas@163.com
 */
public class orderService {
    private orderDao orderDao=new orderDao();


    /**
     * 支付方法
     * @param oid
     */
    public void zhiFu(String oid) {
        /*
         * 1. 获取订单的状态
         *   * 如果状态为1，那么执行下面代码
         *   * 如果状态不为1，那么本方法什么都不做
         */
        int state = orderDao.getStateByOid(oid);
        if(state == 1) {
            // 修改订单状态为2
            orderDao.updateState(oid, 2);
        }
    }
    /*添加订单*/
    public void addOrder(order order) throws SQLException {

        try{
            //开启事务
            JdbcUtils.beginTransaction();
            orderDao.addOrder(order);
            orderDao.addOrderItemList(order.getOrderItemList());//插入订单所有条目

            //提交事务
            JdbcUtils.commitTransaction();
        } catch (SQLException e) {
            JdbcUtils.rollbackTransaction();
        }
    }

    /*我的订单*/
    public List<order> myOrders(String uid) {
        return orderDao.findByUid(uid);
    }
    public order load(String oid) {
        return orderDao.load(oid);
    }
    /*校验订单状态，确认收货    */
    public void confirm(String oid) throws orderException{

        int state=orderDao.getStateByOid(oid);
        if(state!=3)
        {
            throw new orderException("订单交易失败，您不是好东西");

        }
        /*订单交易成功后，修改状态为4*/
        orderDao.updateState(oid,4);


    }
}
