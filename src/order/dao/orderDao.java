package order.dao;

import book.domain.book;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import order.domain.order;
import order.domain.orderItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.print.attribute.standard.NumberUp;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @Author: mocas
 * @Date: 2020/5/27 19:36
 * @email: wangyuhang_mocas@163.com
 */
public class orderDao {
    private QueryRunner qr=new TxQueryRunner();


    /*查询订单状态*/
    public int getStateByOid(String oid)
    {

        try
        {
            String sql="select state from orders where oid=?";
            return (Integer)qr.query(sql,new ScalarHandler(),oid);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*修改订单状态*/
    public void updateState(String oid,int state)
    {

        try
        {
            String sql="update orders set state=? where oid=?";
            qr.update(sql,state,oid);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /*添加订单*/
    public void addOrder(order order)
    {

        try
        {
            String sql="insert into  orders values(?,?,?,?,?,?)";
                /*转换时间格式*/
            Timestamp   timestamp=new Timestamp(order.getOrderTime().getTime());
            Object[] params={order.getOid(),timestamp,
                    order.getTotal(),order.getState(),order.getOwer().getUid(),order.getAddress()};
            qr.update(sql,params);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*插入订单条目*/
    public void addOrderItemList(List<orderItem> orderItemList)
    {
        try {

            String sql = "insert into  orderitem values(?,?,?,?,?)";
            /*把orderItemList转换成二维数组*/
            Object[][] params = new Object[orderItemList.size()][];
            for (int i=0;i<orderItemList.size();i++)
            {
                orderItem item=orderItemList.get(i);
                params[i]=new Object[]{item.getIid(),item.getCount(),
                        item.getSubtotal(),item.getOrder().getOid(),item.getBook().getBid()};
            }
            qr.batch(sql,params);//执行批处理

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /*按uid查询订单*/
    public List<order> findByUid(String uid) {
        /*通过uid查询当前用户的所有order
        * 循环遍历所有order，为其加载所有的orderitem*/
        try
        {
            /*得到当前用户的所有订单*/
            String sql="select * from orders where uid=?";
            List<order> orderList=qr.query(sql,new BeanListHandler<order>(order.class),uid);
            /*循环遍历每个order，为他加载所有订单条目*/
            for (order order:orderList)
            {
                loadOrderItems(order);
            }
            /*
             * 3. 返回订单列表
             */
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /*记载订单所有条目*/
    private void loadOrderItems(order order) throws SQLException {
        String sql="select * from orderitem i,book b where i.bid=b.bid and oid=?";
        /*
         * 因为一行结果集对应的不再是一个javabean，所以不能再使用BeanListHandler，而是MapListHandler
         */
        List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), order.getOid());
        /*
         * mapList是多个map，每个map对应一行结果集
         * 一行：
         * {iid=C7AD5492F27D492189105FB50E55CBB6, count=2, subtotal=60.0, oid=1AE8A70354C947F8B81B80ADA6783155, bid=7, bname=精通Hibernate,price=30.0, author=张卫琴, image=book_img/8991366-1_l.jpg, cid=2}
         * ...
         *
         * 我们需要使用一个Map生成两个对象：OrderItem、Book，然后再建立两者的关系（把Book设置给OrderItem）
         */
        /*
         * 循环遍历每个Map，使用map生成两个对象，然后建立关系（最终结果一个OrderItem），把OrderItem保存起来
         */
        List<orderItem> orderItemList = toOrderItemList(mapList);
        order.setOrderItemList(orderItemList);
    }
    /**
     * 把mapList中每个Map转换成两个对象，并建立关系
     * @param mapList
     * @return
     */
    private List<orderItem> toOrderItemList(List<Map<String, Object>> mapList) {
        List<orderItem> orderItemList = new ArrayList<orderItem>();
        for(Map<String,Object> map : mapList) {
            orderItem item = toOrderItem(map);
            orderItemList.add(item);
        }
        return orderItemList;
    }

    /**
     * 把一个Map转换成一个OrderItem对象
     * @param map
     * @return
     */
    private orderItem toOrderItem(Map<String, Object> map) {
        orderItem orderItem = CommonUtils.toBean(map, orderItem.class);
        book book = CommonUtils.toBean(map, book.class);
        orderItem.setBook(book);
        return orderItem;
    }
    /**
     * 加载订单
     * @param oid
     * @return
     */
    public order load(String oid) {
        try {
            /*
             * 1. 得到当前用户的所有订单
             */
            String sql = "select * from orders where oid=?";
            order order = qr.query(sql, new BeanHandler<order>(order.class), oid);

            /*
             * 2. 为order加载它的所有条目
             */
            loadOrderItems(order);


            /*
             * 3. 返回订单列表
             */
            return order;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
